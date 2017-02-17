package capstone.ontrack;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import java.util.ArrayList;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Spinner;
import java.util.List;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;

public class practice extends Fragment {
    private ImageButton add;
    private List<View> myList = new ArrayList<>();
    private ArrayList<String> edit = new ArrayList<>();
    private LinearLayout Container;

    public practice(){

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_practice, container, false);
        routine activity = (routine) getActivity();
        edit = activity.getpracticeInfo();
        Container = (LinearLayout)view.findViewById(R.id.container);

        if(edit != null){
            EditText name = (EditText) view.findViewById(R.id.name);
            name.setText(edit.get(0));

            EditText date= (EditText) view.findViewById(R.id.date);
            date.setText(edit.get(1));

            for(int i = 2; i < edit.size(); i = i + 4){
                setOnClick(edit.get(i), edit.get(i+1), edit.get(i+2), edit.get(i+3));
            }

        }
        add = (ImageButton) view.findViewById(R.id.add);
        add.setOnClickListener(new OnClickListener() {


            @Override
            public void onClick(View arg0) {
                setOnClick("", "", "", "");
            }
        });

        Button create = (Button) view.findViewById(R.id.create);
        create.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Boolean error = false;
                EditText e = (EditText) view.findViewById(R.id.name);
                EditText date = (EditText) view.findViewById(R.id.date);
                if (e.getText().toString().trim().equalsIgnoreCase("")) {
                    e.setError("Enter event");
                    error = true;
                }
                if (date.getText().toString().trim().equalsIgnoreCase("")) {
                    date.setError("Enter date");
                    error = true;
                }

                for(View v: myList){
                    EditText reps = (EditText) v.findViewById(R.id.reps);
                    EditText events = (EditText) v.findViewById(R.id.event);
                    if (events.getText().toString().trim().equalsIgnoreCase("")) {
                        events.setError("Enter event");
                        error = true;
                    }
                    if (reps.getText().toString().trim().equalsIgnoreCase("")) {
                        reps.setError("Enter the number of repetitions");
                        error = true;
                    }
                }
                if(error == false){
                    Intent intent = new Intent(getActivity(), home.class);
                    startActivity(intent);
                    getActivity().finish();
                }
            }});

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

    private void setOnClick(final String event, final String reps, final String weight, final String practiceBest) {

        LayoutInflater layoutInflater =
                (LayoutInflater) getActivity().getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View addView = layoutInflater.inflate(R.layout.practice, null);

        EditText editEvent = (EditText) addView.findViewById(R.id.event);
        editEvent.setText(event);

        EditText editReps = (EditText) addView.findViewById(R.id.reps);
        editReps.setText(reps);

        EditText editWeight = (EditText) addView.findViewById(R.id.weight);
        editWeight.setText(weight);

        EditText editBest = (EditText) addView.findViewById(R.id.best);
        editBest.setText(practiceBest);

        Spinner d = (Spinner) addView.findViewById(R.id.spinner2);
        String[] values =
                {"Seconds", "Minutes", "Meters", "Feet"};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, values);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        d.setAdapter(adapter);

        Spinner we = (Spinner) addView.findViewById(R.id.spinner);
        String[] w =
                {"Pounds", "Kilograms"};
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, w);
        adapter2.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        we.setAdapter(adapter2);

        myList.add(addView);
        ImageButton buttonRemove = (ImageButton) addView.findViewById(R.id.delete);
        buttonRemove.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                myList.remove(addView);
                ((LinearLayout) addView.getParent()).removeView(addView);
            }
        });

        Container.addView(addView);
    }

}