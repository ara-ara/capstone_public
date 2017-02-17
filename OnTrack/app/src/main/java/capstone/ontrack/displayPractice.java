package capstone.ontrack;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;


import java.util.ArrayList;

public class displayPractice extends AppCompatActivity {

    private ArrayList<String> practiceInfo = new ArrayList<>();

    public displayPractice() {
        practiceInfo.add("Hammer Time");
        practiceInfo.add("11/2/16");
        practiceInfo.add("Hammer");
        practiceInfo.add("6");
        practiceInfo.add("5k");
        practiceInfo.add("150");
        practiceInfo.add("Hammer 2 Turn");
        practiceInfo.add("4");
        practiceInfo.add("4k");
        practiceInfo.add("");
        practiceInfo.add("Hammer Toe & Two");
        practiceInfo.add("6");
        practiceInfo.add("4k");
        practiceInfo.add("");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_practice);

        //get view
        View view = getWindow().getDecorView().getRootView();

        //set practie focus into textView
        TextView focus = (TextView) view.findViewById(R.id.practice);
        focus.setText(practiceInfo.get(0));

        //set practice date into textView
        TextView date = (TextView) view.findViewById(R.id.date);
        date.setText("Date: " + practiceInfo.get(1));

        final LinearLayout Container = (LinearLayout) view.findViewById(R.id.container);

        for (Integer i = 2; i < practiceInfo.size(); i = i + 4) {
            LayoutInflater layoutInflater =
                    (LayoutInflater) this.getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final View addView = layoutInflater.inflate(R.layout.display_practice, null);

            //set event into textView
            TextView event = (TextView) addView.findViewById(R.id.event);
            event.setText("Event: " + practiceInfo.get(i));

            //set reps in meet into textView
            TextView reps = (TextView) addView.findViewById(R.id.reps);
            reps.setText("Reps: " + practiceInfo.get(i + 1));

            //set weight in meet into textView
            TextView weight = (TextView) addView.findViewById(R.id.weight);
            if (practiceInfo.get(i + 2).equals("")) {
                weight.setText(practiceInfo.get(i + 1));
            } else {
                weight.setText("Weight: " + practiceInfo.get(i + 2));
            }

            //set practice best into textView
            TextView best = (TextView) addView.findViewById(R.id.best);
            if (practiceInfo.get(i + 3).equals("")) {
                best.setText(practiceInfo.get(i + 3));
            } else {
                best.setText("Practice Best: " + practiceInfo.get(i + 3));
            }


            Container.addView(addView);
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        //Toolbar will now take on default actionbar characteristics
        setSupportActionBar(toolbar);
        toolbar.setTitle(practiceInfo.get(0));
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
        intent.putExtra("practice", practiceInfo);
        startActivity(intent);
        finish();
    }
}
