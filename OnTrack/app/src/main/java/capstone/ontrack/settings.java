package capstone.ontrack;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v7.widget.SearchView;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.MenuItemCompat.OnActionExpandListener;
import android.app.Activity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;


public class settings extends AppCompatActivity  {

    @Override //called when activity is created
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        //sets up toolbar
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        //make the keyboard disappear if click outside edittext
        View main = (View) findViewById(R.id.mainLayout);

        main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
            }
        });

    }

    //toolbar add button pressed go to profile activity
    public void addRoutine(View view) {
        Intent intent = new Intent(this, routine.class);
        startActivity(intent);
        finish();
    }

    //toolbar home button pressed go to profile activity
    public void home(View view) {
        Intent intent = new Intent(this, home.class);
        startActivity(intent);
        finish();
    }

    //toolbar graph button pressed go to profile activity
    public void graph(View view) {
        Intent intent = new Intent(this, graph.class);
        startActivity(intent);
        finish();
    }

    //toolbar profile button pressed go to profile activity
    public void profile(View view) {
        Intent intent = new Intent(this, profile.class);
        startActivity(intent);
        finish();
    }

    //set up action icons
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);

        return super.onCreateOptionsMenu(menu);
    }

    //logout selected
    @Override public boolean onOptionsItemSelected(MenuItem item) {
        GlobalsActivity appState = (GlobalsActivity) getApplication();
        appState.setUserId(null);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
        return true;
    }

    public void changeName(View view) {

        EditText firstName = (EditText) findViewById(R.id.firstName);
        EditText lastName = (EditText) findViewById(R.id.lastName);
        if(firstName.getText().toString().trim().equalsIgnoreCase("") &&
                lastName.getText().toString().trim().equalsIgnoreCase("")){
            firstName.setError("Enter first or last name");
            lastName.setError("Enter first or last Name");

        }else {
            Intent intent = new Intent(this, home.class);
            startActivity(intent);
            finish();
        }
    }

    public void changePassword(View view) {
        Boolean error = false;
        EditText password = (EditText) findViewById(R.id.password);
        EditText repeatPassword = (EditText) findViewById(R.id.repeatPassword);
        if(password.getText().toString().trim().equalsIgnoreCase("")){
            password.setError("Enter new password");
            error = true;

        }
        if(repeatPassword.getText().toString().trim().equalsIgnoreCase("")){
            repeatPassword.setError("Repeat password");
            error = true;
        }
        if(repeatPassword.getText() != password.getText()){
            password.setError("Password does not match");
            repeatPassword.setError("Password does not match");
            error = true;
        }
        if(error = false){
            Intent intent = new Intent(this, home.class);
            startActivity(intent);
        }
    }

    public void editInfo(View view){
        EditText height = (EditText) findViewById(R.id.height);
        EditText weight = (EditText) findViewById(R.id.weight);
        EditText event = (EditText) findViewById(R.id.event);
        if(height.getText().toString().trim().equalsIgnoreCase("") &&
                weight.getText().toString().trim().equalsIgnoreCase("")&&
                event.getText().toString().trim().equalsIgnoreCase(""))
        {
            height.setError("Enter Height");
            weight.setError("Enter Weight");
            event.setError("Enter Event");

        }else {
            Intent intent = new Intent(this, home.class);
            startActivity(intent);
        }
    }
}

