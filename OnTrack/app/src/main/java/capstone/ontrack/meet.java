package capstone.ontrack;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.app.Activity;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.view.MotionEvent;

import java.util.ArrayList;
import java.util.List;

public class meet extends Fragment {
    private ImageButton add;
    private List<View> myList = new ArrayList<>();
    private ArrayList<String> edit = new ArrayList<>();
    private LinearLayout Container;


    public meet() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_meet, container, false);
        Container = (LinearLayout)view.findViewById(R.id.container);

        routine activity = (routine) getActivity();
        edit = activity.getmeetInfo();

        if(edit != null){
            EditText name = (EditText) view.findViewById(R.id.meet);
            name.setText(edit.get(0));

            EditText date= (EditText) view.findViewById(R.id.date);
            date.setText(edit.get(1));

            EditText location= (EditText) view.findViewById(R.id.location);
            location.setText(edit.get(2));

            for(int i = 3; i < edit.size(); i = i + 3){
                setOnClick(edit.get(i), edit.get(i+1), edit.get(i+2));
            }

        }

        add = (ImageButton) view.findViewById(R.id.add);


        //dynamically adds new event.xlm into layout container
        add.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                setOnClick("", "", "");
            }});

        //verify text fields have values
        Button create = (Button) view.findViewById(R.id.create);
        create.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Boolean error = false;
                EditText meetName = (EditText) view.findViewById(R.id.meet);
                EditText meetDate = (EditText) view.findViewById(R.id.date);
                EditText meetLocation = (EditText) view.findViewById(R.id.location);

                if (meetName.getText().toString().trim().equalsIgnoreCase("")) {
                    meetName.setError("Enter meet name");
                    error = true;
                }

                if (meetDate.getText().toString().trim().equalsIgnoreCase("")) {
                    meetDate.setError("Enter date");
                    error = true;
                }
                if (meetLocation.getText().toString().trim().equalsIgnoreCase("")) {
                    meetLocation.setError("Enter location");
                    error = true;
                }
                for(View v: myList){
                    EditText time = (EditText) v.findViewById(R.id.time);
                    EditText events = (EditText) v.findViewById(R.id.event);
                    if (events.getText().toString().trim().equalsIgnoreCase("")) {
                        events.setError("Enter event");
                        error = true;
                    }
                    if (time.getText().toString().trim().equalsIgnoreCase("")) {
                        time.setError("Enter your distance or time");
                        error = true;
                    }
                }
                if(error == false){
                    //add client information here

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


    void setOnClick(String event, String place, String time) {
        LayoutInflater layoutInflater =
                (LayoutInflater) getActivity().getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View addView = layoutInflater.inflate(R.layout.event, null);

        EditText editEvent = (EditText) addView.findViewById(R.id.event);
        editEvent.setText(event);

        EditText editPlace = (EditText) addView.findViewById(R.id.place);
        editPlace.setText(place);

        EditText editTime = (EditText) addView.findViewById(R.id.time);
        editTime.setText(time);

        //set up spinner values
        Spinner s = (Spinner) addView.findViewById(R.id.spinner);
        String[] values =
                {"Seconds", "Minutes", "Meters", "Feet"};

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, values);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        s.setAdapter(adapter);

        //add the view
        myList.add(addView);

        //remove this view when the delete button is pressed
        ImageButton buttonRemove = (ImageButton) addView.findViewById(R.id.delete);
        buttonRemove.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                myList.remove(addView);
                adapter.notifyDataSetChanged();
                ((LinearLayout) addView.getParent()).removeView(addView);
            }
        });

        Container.addView(addView);
    }
}



