package capstone.ontrack;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;
import android.widget.RadioButton;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.EditText;
import java.net.Socket;
import java.io.*;
import java.net.*;

//create account screen
public class AccountActivity extends AppCompatActivity {

    private serverCreation createTask = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        View main = (View) findViewById(R.id.mainLayout);

        main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
            }
        });

    }

    public void newAccount(View view) {

        //get reference to text fields
        Boolean error = false;
        EditText first = (EditText)findViewById(R.id.firstName);
        EditText last = (EditText)findViewById(R.id.lastName);
        EditText email = (EditText)findViewById(R.id.email);
        EditText password1 = (EditText)findViewById(R.id.password);
        EditText password2 = (EditText)findViewById(R.id.repeatPassword);
        EditText event = (EditText)findViewById(R.id.event);
        EditText height = (EditText)findViewById(R.id.height);
        EditText weight = (EditText)findViewById(R.id.weight);

        //Give errors if first name text field is empty
        if (first.getText().toString().trim().equalsIgnoreCase("")) {
            first.setError("Enter First Name");
            error = true;
        }
        //Give errors if last name text field is empty
        if (last.getText().toString().trim().equalsIgnoreCase("")) {
            last.setError("Enter Last Name");
            error = true;
        }
        //Give errors if email text field is empty
        if (email.getText().toString().trim().equalsIgnoreCase("")) {
            email.setError("Enter Email");
            error = true;
        }
        //Give errors if password1 text field is empty
        if (password1.getText().toString().trim().equalsIgnoreCase("")) {
            password1.setError("Enter Password");
            error = true;
        }
        //Give errors if password2 field is empty
        if (password2.getText().toString().trim().equalsIgnoreCase("")) {
            password2.setError("Repeat Password");
            error = true;
        }

        //no errors go to home screen
        if(!error){
            createTask =
                    new serverCreation(first.getText().toString(), last.getText().toString(),
                            email.getText().toString(), password1.getText().toString(),
                            event.getText().toString(), weight.getText().toString(),
                            height.getText().toString());

            createTask.execute();
            Intent intent = new Intent(this, home.class);
            startActivity(intent);
        }
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

    // Client to create new user account and store the new account on
    // the database. Gets newly created user id from the server then
    // logs the user in
    // Created By: Andy Aceto
    public class serverCreation extends AsyncTask<Void, Void, Boolean> {

        private final String cFirst;
        private final String cLast;
        private final String cEmail;
        private final String cPass;
        private final String cEvent;
        private final String cWeight;
        private final String cHeight;

        // Initialized with credentials entered by user
        serverCreation(String first, String last, String email, String password, String event,
                       String weight, String height) {
            cFirst = first;
            cLast = last;
            cEmail = email;
            cPass = password;
            cEvent = event;
            cWeight = weight;
            cHeight = height;
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
                // create:email:data:password:data:organization:data:anything_else_in_any_order
                String input = "create:email:" + cEmail + ":password:" + cPass + ":organization:"
                        + cEvent + ":firstname:" + cFirst + ":lastname:" + cLast + ":height:" +
                        cHeight + ":weight:" + cWeight;

                out.println(input);

                String response = in.readLine();

                if (response == null) {

                    errMessage = "Socket closed before response was received";

                    return false;

                }

                if (response.equals("Failed:email")) {

                    errMessage = "Email already exists";

                    return false;

                } else {
                    // Server response should be Created:user_id:data
                    String[] resPieces = response.split(":");

                    appState.setUserId(resPieces[2]);

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

            createTask = null;

            if(success){

                login();

            } else {

                EditText emailErr = (EditText)findViewById(R.id.email);
                emailErr.setError(errMessage);

            }
        }

        @Override
        protected void onCancelled() {

            createTask = null;

        }
    }
}



