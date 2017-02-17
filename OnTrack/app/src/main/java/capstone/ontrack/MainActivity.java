package capstone.ontrack;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.os.AsyncTask;
import java.net.Socket;
import java.io.*;
import java.net.*;
import java.util.Objects;


public class MainActivity extends AppCompatActivity {

    private serverValidation loginTask = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);

        //Toolbar will now take on default actionbar characteristics
        setSupportActionBar(toolbar);
        toolbar.setTitle("OnTrack");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //hide keyboard when touch outside view
        View main = (View) findViewById(R.id.mainLayout);
        main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
            }
        });
    }

    public void createAccount(View view) {
        Intent intent = new Intent(this, AccountActivity.class);
        startActivity(intent);
    }

    public void verifyAccount(View view) {
        Boolean error = false;
        EditText email = (EditText)findViewById(R.id.loginEmail);
        EditText password = (EditText)findViewById(R.id.loginPassword);
        if (email.getText().toString().trim().equalsIgnoreCase("")) {
            email.setError("Enter email");
            error = true;
        }
        else if(!isEmailValid(email.getText().toString())) {
            email.setError("Invalid email");
            error = true;
        }
        if (password.getText().toString().trim().equalsIgnoreCase("")) {
            password.setError("Enter password");
            error = true;
        }
        else if(!isPasswordValid(password.getText().toString())) {
            password.setError("Invalid password");
            error = true;
        }
        if(!error) {
            loginTask =
                    new serverValidation(email.getText().toString(), password.getText().toString());

            loginTask.execute();
        }
    }

    // Check for valid email format
    // Created By: Andy Aceto
    private boolean isEmailValid(String email) {
        return email.contains("@");
    }

    // Check for valid password length
    // Created By: Andy Aceto
    private boolean isPasswordValid(String password) {
        return password.length() > 4;
    }

    // Checks to see if the user id was returned by the server
    // and loads the home screen
    // Created By: Andy Aceto
    private void login() {
        if(((GlobalsActivity) getApplication()).getUserId() != null) {
            Intent intent = new Intent(this, home.class);
            startActivity(intent);
        }
    }

    // Client to validate user credentials with the server and
    // then logs the user in
    // Created By: Andy Aceto
    public class serverValidation extends AsyncTask<Void, Void, Boolean> {

        private final String valEmail;
        private final String valPass;

        // Initialized with credentials entered by user
        serverValidation(String email, String password) {
            valEmail = email;
            valPass = password;
        }

        // Used to store any potential errors encountered when communicating
        // with server
        String errMessage;

        // Used to store and access user id returned by server globally
        GlobalsActivity appState = (GlobalsActivity) getApplication();

        // Create socket to communicate with server, then send and receive
        // data based on current task
        // Executes separately from UI thread
        @Override
        protected Boolean doInBackground(Void... params) {

            String hostName = "35.163.225.233" ;
            Integer portNumber = 5657;

            try (

                    Socket onTrkSock = new Socket(hostName, portNumber);

                    PrintWriter out =
                            new PrintWriter(onTrkSock.getOutputStream(), true);

                    BufferedReader in =
                            new BufferedReader(
                                    new InputStreamReader(onTrkSock.getInputStream()))

            ) {

                String input = "val:email:" + valEmail + ":password:" + valPass;

                out.println(input);

                String response = in.readLine();

                if (response == null) {

                    errMessage = "Socket closed before response was received";

                    return false;

                }

                if (response.equals("failed")) {

                    errMessage = "Email/Password does not match";

                    return false;

                } else {

                    appState.setUserId(response);

                    return true;

                }
            } catch (UnknownHostException e) {

                errMessage = "Don't know about host " + hostName;

                return false;

            } catch (IOException e) {

                errMessage = "Couldn't get I/O for the connection to " + hostName;

                return false;

            }
        }

        // Update view based on response from server
        @Override
        protected void onPostExecute(final Boolean success) {

            loginTask = null;

            if(success){

                login();

            } else {

                EditText emailErr = (EditText)findViewById(R.id.loginEmail);
                EditText passErr = (EditText)findViewById(R.id.loginPassword);
                emailErr.setError(errMessage);
                passErr.setError(errMessage);

            }
        }

        @Override
        protected void onCancelled() {

            loginTask = null;

        }
    }
}