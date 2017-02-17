package capstone.ontrack;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class workout extends Fragment {
    private List<List<View>> myList = new ArrayList<>();
    private View view;
    private ArrayList<List<String>> edit = new ArrayList<>();


    public workout() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_workout, container, false);

        //get passed values from profile
        routine activity = (routine) getActivity();
        edit = activity.getworkoutInfo();

        //set where to add a lift
        final LinearLayout Container = (LinearLayout) view.findViewById(R.id.container);

        if (edit != null) {
            for (List<String> lift : edit) {
                if (edit.indexOf(lift) == 0) {

                    //set workout name into textView
                    EditText workout = (EditText) view.findViewById(R.id.workoutName);
                    workout.setText(lift.get(0));

                    //set workout date into textView
                    EditText date = (EditText) view.findViewById(R.id.date);
                    date.setText(lift.get(1));


                } else {
                    setOnClickLift(lift, Container);
                }
            }
        }

        Button create = (Button) view.findViewById(R.id.create);
        create.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Boolean error = false;
                EditText date = (EditText) view.findViewById(R.id.date);
                EditText name = (EditText) view.findViewById(R.id.workoutName);
                if (date.getText().toString().trim().equalsIgnoreCase("")) {
                    date.setError("Enter date");
                    error = true;
                }
                if (name.getText().toString().trim().equalsIgnoreCase("")) {
                    name.setError("Enter workout name");
                    error = true;
                }

                for (List<View> lift : myList) {

                    EditText exercise = (EditText) lift.get(0).findViewById(R.id.exercise);
                    if (exercise.getText().toString().trim().equalsIgnoreCase("")) {
                        exercise.setError("Enter exercise");
                        error = true;
                    }
                    for (int i = 1; i < lift.size(); i++) {
                        EditText reps = (EditText) lift.get(i).findViewById(R.id.reps);
                        EditText weight = (EditText) lift.get(i).findViewById(R.id.weight);
                        if (reps.getText().toString().trim().equalsIgnoreCase("")) {
                            reps.setError("Enter reps");
                            error = true;
                        }
                        if (weight.getText().toString().trim().equalsIgnoreCase("")) {
                            weight.setError("Enter weight");
                            error = true;
                        }
                    }
                    if (lift.size() == 1) {
                        exercise.setError("Add reps and weight");
                        error = true;
                    }
                }
                if (error == false) {
                    Intent intent = new Intent(getActivity(), home.class);
                    startActivity(intent);
                    getActivity().finish();
                }
            }
        });

        Button exercise = (Button) view.findViewById(R.id.addExercise);

        exercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                LayoutInflater layoutInflater =
                        (LayoutInflater) getActivity().getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View addView = layoutInflater.inflate(R.layout.exercise, null);
                final LinearLayout C = (LinearLayout) addView.findViewById(R.id.containerRep);
                final List<View> temp = new ArrayList<>();
                temp.add(addView);

                ImageButton a = (ImageButton) addView.findViewById(R.id.add);
                a.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View arg0) {
                        LayoutInflater layoutInflater =
                                (LayoutInflater) getActivity().getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        final View setAdd = layoutInflater.inflate(R.layout.workout, null);
                        Spinner s = (Spinner) setAdd.findViewById(R.id.spinner3);
                        String[] values =
                                {"Pounds", "Kilograms"};

                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, values);
                        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
                        s.setAdapter(adapter);

                        temp.add(setAdd);

                        ImageButton buttonRemove = (ImageButton) setAdd.findViewById(R.id.delete);
                        buttonRemove.setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View v) {
                                temp.remove(setAdd);
                                ((LinearLayout) setAdd.getParent()).removeView(setAdd);
                            }
                        });

                        C.addView(setAdd);
                    }
                });
                myList.add(temp);

                ImageButton buttonRemove = (ImageButton) addView.findViewById(R.id.delete);
                buttonRemove.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        myList.remove(temp);
                        ((LinearLayout) addView.getParent()).removeView(addView);
                    }
                });

                Container.addView(addView);
            }
        });

        View main = (View) view.findViewById(R.id.mainLayout);

        main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager inputMethodManager = (InputMethodManager)getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
            }
        });

        return view;
    }

    void setOnClickLift (List<String> exercise, LinearLayout Container){
        LayoutInflater layoutInflater =
                (LayoutInflater) getActivity().getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View addView = layoutInflater.inflate(R.layout.exercise, null);
        ImageButton a = (ImageButton) addView.findViewById(R.id.add);
        final LinearLayout C = (LinearLayout) addView.findViewById(R.id.containerRep);
        final List<View> temp = new ArrayList<View>();

        //add exercise to edittext
        EditText exerciseName = (EditText) addView.findViewById(R.id.exercise);
        exerciseName.setText(exercise.get(0));

        temp.add(addView);

        for(Integer i = 1; i < exercise.size(); i = i + 2){
            final View setAdd = layoutInflater.inflate(R.layout.workout, null);
            Spinner s = (Spinner) setAdd.findViewById(R.id.spinner3);
            String[] values =
                    {"Pounds", "Kilograms"};

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, values);
            adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
            s.setAdapter(adapter);

            //add exercise to edittext
            EditText reps = (EditText) setAdd.findViewById(R.id.reps);
            reps.setText(exercise.get(i));

            //add exercise to edittext
            EditText weight = (EditText) setAdd.findViewById(R.id.weight);
            weight.setText(exercise.get(i + 1));

            temp.add(setAdd);

            ImageButton buttonRemove = (ImageButton) setAdd.findViewById(R.id.delete);
            buttonRemove.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    temp.remove(setAdd);
                    ((LinearLayout) setAdd.getParent()).removeView(setAdd);
                }
            });

            C.addView(setAdd);
        }

        a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                LayoutInflater layoutInflater =
                        (LayoutInflater) getActivity().getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View setAdd = layoutInflater.inflate(R.layout.workout, null);
                Spinner s = (Spinner) setAdd.findViewById(R.id.spinner3);
                String[] values =
                        {"Pounds", "Kilograms"};

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, values);
                adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
                s.setAdapter(adapter);

                temp.add(setAdd);

                ImageButton buttonRemove = (ImageButton) setAdd.findViewById(R.id.delete);
                buttonRemove.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        temp.remove(setAdd);
                        ((LinearLayout) setAdd.getParent()).removeView(setAdd);
                    }
                });
                C.addView(setAdd);
            }
        });
        myList.add(temp);

        ImageButton buttonRemove = (ImageButton) addView.findViewById(R.id.delete);
        buttonRemove.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                myList.remove(temp);
                ((LinearLayout) addView.getParent()).removeView(addView);
            }
        });

        Container.addView(addView);
    }
}