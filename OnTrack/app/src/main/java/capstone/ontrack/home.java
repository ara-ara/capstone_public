package capstone.ontrack;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v7.widget.SearchView;
import android.support.v4.view.MenuItemCompat;
import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;
import android.view.View;
import android.support.v4.app.FragmentTransaction;
import android.icu.util.Calendar;

import java.io.StringBufferInputStream;
import java.util.ArrayList;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.Arrays;
import java.util.List;
import android.graphics.drawable.ColorDrawable;
import java.util.HashMap;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import static capstone.ontrack.R.color.orange;

public class home extends AppCompatActivity {

    private ArrayList<List <String>> mylist = new ArrayList<>();
    private CaldroidFragment caldroidFragment;
    private HashMap<Date, dailyEvent> myEvents = new HashMap<Date, dailyEvent>();
    private List<View> addedViews = new ArrayList<>();
    private  LinearLayout Container;


    public home(){
        mylist.add(Arrays.asList("11/13/16", "Hammer Time", "Practice"));
        mylist.add(Arrays.asList("11/25/16", "German Volume", "Workout"));
        mylist.add(Arrays.asList("11/4/16", "Intersquad", "Meet"));
        mylist.add(Arrays.asList("11/1/16", "Arm day", "Workout"));
        mylist.add(Arrays.asList("11/1/16", "Discus Day", "Practice"));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        //required to set calendar
        caldroidFragment = new CaldroidFragment();
        Calendar cal = Calendar.getInstance();
        Bundle args = new Bundle();
        args.putInt(CaldroidFragment.THEME_RESOURCE, com.caldroid.R.style.CaldroidDefaultDark);
        caldroidFragment.setArguments(args);
        args.putInt(CaldroidFragment.THEME_RESOURCE, com.caldroid.R.style.CaldroidDefaultDark);
        args.putInt(CaldroidFragment.MONTH, cal.get(Calendar.MONTH) + 1);
        args.putInt(CaldroidFragment.YEAR, cal.get(Calendar.YEAR));
        caldroidFragment.setArguments(args);
        FragmentTransaction t = getSupportFragmentManager().beginTransaction();
        t.replace(R.id.calendar1, caldroidFragment);
        t.commit();


        //change color of current date
        Date current = cal.getTime();
        ColorDrawable blue = new ColorDrawable(getResources().getColor(R.color.red));
        caldroidFragment.setBackgroundDrawableForDate(blue, current);

        //add all user events into a hashmap
        addEvents();

        //initialize the view that events will be added to
        Container = (LinearLayout) findViewById(R.id.container);

        //set when a date is clicked
        final CaldroidListener listener = new CaldroidListener() {

            @Override
            public void onSelectDate(Date date, View view) {

                //delete previously added views
                for (View remove : addedViews) {
                    ((LinearLayout) remove.getParent()).removeView(remove);
                }

                //set added views to empty
                addedViews = new ArrayList<>();

                displayEvents(date);
            }
        };

        // Setup Caldroid
        caldroidFragment.setCaldroidListener(listener);
        displayEvents(current);

    }
    //set up action icons
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);

        return super.onCreateOptionsMenu(menu);
    }

    //logout options
    @Override public boolean onOptionsItemSelected(MenuItem item) {
        GlobalsActivity appState = (GlobalsActivity) getApplication();
        appState.setUserId(null);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
        return true;
    }

    //toolbar add button pressed go to profile activity
    public void addRoutine(View view) {
        Intent intent = new Intent(this, routine.class);
        startActivity(intent);
        finish();
    }

    //toolbar profile button pressed go to profile activity
    public void profile(View view) {
        Intent intent = new Intent(this, profile.class);
        startActivity(intent);
        finish();
    }

    //toolbar graph button pressed go to profile activity
    public void graph(View view) {
        Intent intent = new Intent(this, graph.class);
        startActivity(intent);
        finish();
    }

    //toolbar settings button pressed go to profile activity
    public void settings(View view) {
        Intent intent = new Intent(this, settings.class);
        startActivity(intent);
        finish();
    }

    public void addEvents(){
        for(List<String> event: mylist){
            DateFormat dateFormat = new SimpleDateFormat("MM/dd/yy");
            Date startDate;
            try {
                startDate = dateFormat.parse(event.get(0));
                ColorDrawable aqua = new ColorDrawable(getResources().getColor(R.color.orange));
                caldroidFragment.setBackgroundDrawableForDate(aqua, startDate);
                dailyEvent d;

                if(myEvents.get(startDate) == null){
                    d = new dailyEvent();
                    d.addEventName(event.get(1));
                    d.addType(event.get(2));
                    myEvents.put(startDate, d);
                }else{
                    d = myEvents.get(startDate);
                    d.addEventName(event.get(1));
                    d.addType(event.get(2));
                }

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    public void displayEvents(Date date){
        //get the events for the day selected
        dailyEvent e = myEvents.get(date);
        TextView display = (TextView) findViewById(R.id.header);
        DateFormat dateInstance = SimpleDateFormat.getDateInstance();

        //if there are events display the events
        if(myEvents.get(date) != null){
            display.setText("Planned Events on " + dateInstance.format(date));
            //get the name and type of events on this day
            List<String> eventName = e.getEventName();
            List<String> eventType = e.getType();

            //add set text to textviews for the event
            for(Integer i = 0; i < e.size(); ++i){

                LayoutInflater layoutInflater =
                        (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                //view that will be added to home
                final View addView = layoutInflater.inflate(R.layout.calendar_events, null);

                //set event name
                TextView name = (TextView) addView.findViewById(R.id.name);
                name.setText(eventName.get(i));

                //set event type
                TextView typeDisplay = (TextView) addView.findViewById(R.id.type);
                typeDisplay.setText(eventType.get(i) + ":");

                Button showEvent = (Button) addView.findViewById(R.id.view);

                //for each button call the correct intent for the type of event
                switch (eventType.get(i)){
                    case "Practice":
                        showEvent.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View arg0) {
                                Intent intent = new Intent(home.this, displayPractice.class);
                                startActivity(intent);
                            }
                        });
                        break;
                    case "Meet":
                        showEvent.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View arg0) {
                                Intent intent = new Intent(home.this, displayMeet.class);
                                startActivity(intent);
                            }
                        });
                        break;
                    case "Workout":
                        showEvent.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View arg0) {
                                Intent intent = new Intent(home.this, displayWorkout.class);
                                startActivity(intent);
                            }
                        });
                        break;

                }
                Container.addView(addView);
                addedViews.add(addView);
            }
        }else{
            display.setText("No events on " + dateInstance.format(date));
        }
    }
}
