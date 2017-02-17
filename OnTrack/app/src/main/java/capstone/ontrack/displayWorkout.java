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
import java.util.List;
import java.util.Arrays;

public class displayWorkout extends AppCompatActivity {

    private ArrayList<List<String>> workoutInfo = new ArrayList<>();

    public displayWorkout(){
        workoutInfo.add(Arrays.asList("German, Volume", "10/1/16"));
        workoutInfo.add(Arrays.asList("Back Squat", "10", "185", "10", "185", "10", "185"));
        workoutInfo.add(Arrays.asList("Bench", "10", "135", "10", "135", "10", "135"));
        workoutInfo.add(Arrays.asList("Pull Downs", "10", "100", "10", "100", "10", "100"));
        workoutInfo.add(Arrays.asList("Pull Downs", "10", "100", "10", "100", "10", "100"));
        workoutInfo.add(Arrays.asList("Pull Downs", "10", "100", "10", "100", "10", "100"));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_workout);

        //get view
        View view = getWindow().getDecorView().getRootView();

        final LinearLayout Container = (LinearLayout) view.findViewById(R.id.container);

        for(List<String> lift : workoutInfo){
            if(workoutInfo.indexOf(lift) == 0){

                //set workout name into textView
                TextView workout = (TextView) view.findViewById(R.id.workout);
                List<String> temp = workoutInfo.get(0);
                workout.setText(temp.get(0));

                //set workout date into textView
                TextView date = (TextView) view.findViewById(R.id.date);
                date.setText(temp.get(1));

            }else {
                LayoutInflater layoutInflater =
                        (LayoutInflater) this.getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View addView = layoutInflater.inflate(R.layout.display_exercise, null);
                TextView exercise = (TextView) addView.findViewById(R.id.exercise);
                exercise.setText(lift.get(0));
                Container.addView(addView);

                for (int i = 1; i < lift.size(); i = i + 2) {
                    LayoutInflater inflater =
                            (LayoutInflater) this.getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    final View addSet = layoutInflater.inflate(R.layout.display_set, null);
                    TextView reps = (TextView) addSet.findViewById(R.id.reps);
                    reps.setText(lift.get(i));

                    TextView weight = (TextView) addSet.findViewById(R.id.weight);
                    weight.setText(lift.get(i + 1));
                    Container.addView(addSet);
                }
            }

        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        //Toolbar will now take on default actionbar characteristics
        setSupportActionBar(toolbar);
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
        intent.putExtra("workout", workoutInfo);
        startActivity(intent);
        finish();
    }
}

