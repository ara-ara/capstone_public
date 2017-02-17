package capstone.ontrack;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.support.v4.app.FragmentManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;
import  android.support.design.widget.NavigationView;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

//screen that is called in profile when meet fragment button is called
public class displayMeet extends AppCompatActivity {

    private ArrayList<String> meetInfo = new ArrayList<>();

    public displayMeet(){
        meetInfo.add("Intersquad");
        meetInfo.add("Kent State");
        meetInfo.add("10/27/16");
        meetInfo.add("Hammer");
        meetInfo.add("Second");
        meetInfo.add("179");
        meetInfo.add("Discus");
        meetInfo.add("");
        meetInfo.add("151");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_meet);

        //get view
        View view = getWindow().getDecorView().getRootView();

        //set meet name into textView
        TextView meet = (TextView) view.findViewById(R.id.meet);
        meet.setText(meetInfo.get(0));

        //set location into textView
        TextView location = (TextView) view.findViewById(R.id.location);
        location.setText("Location: " + meetInfo.get(1));

        //set date into textView
        TextView date = (TextView) view.findViewById(R.id.date);
        date.setText("Date: " + meetInfo.get(2));

        final LinearLayout Container = (LinearLayout) view.findViewById(R.id.container);

        for (Integer i = 3; i < meetInfo.size(); i = i + 3) {
            LayoutInflater layoutInflater =
                    (LayoutInflater) this.getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final View addView = layoutInflater.inflate(R.layout.display_event, null);

            //set event into textView
            TextView event = (TextView) addView.findViewById(R.id.event);
            event.setText("Event: " + meetInfo.get(i));

            //set place in meet into textView
            TextView place = (TextView) addView.findViewById(R.id.place);
            if(meetInfo.get(i + 1).equals("")){
                place.setText(meetInfo.get(i + 1));
            }else{
                place.setText("Place: " + meetInfo.get(i + 1));
            }

            //set time or distance into textView
            TextView time = (TextView) addView.findViewById(R.id.time);
            time.setText("Best: " + meetInfo.get(i + 2));


            Container.addView(addView);
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        //Toolbar will now take on default actionbar characteristics
        setSupportActionBar(toolbar);
        toolbar.setTitle(meetInfo.get(0));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    public void edit(View view) {
        Intent intent = new Intent(this, routine.class);
        intent.putExtra("meet", meetInfo);
        startActivity(intent);
        finish();
    }
}
