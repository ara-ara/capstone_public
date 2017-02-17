package capstone.ontrack;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

public class profilePractice extends Fragment {

    private List<String> practice = new ArrayList<>();



    public profilePractice() {
        practice.add("10/12/16"); practice.add("Thousand Throws");
        practice.add("10/31/16"); practice.add("Shot put");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //add client here and put meet information in the list of strings meet

        final View view = inflater.inflate(R.layout.fragment_profile_practice, container, false);
        final LinearLayout Container = (LinearLayout)view.findViewById(R.id.container);


        for(Integer i = 0; i < practice.size(); i = i + 2){
            LayoutInflater layoutInflater =
                    (LayoutInflater) getActivity().getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final View addView = layoutInflater.inflate(R.layout.meet_name, null);
            TextView date = (TextView) addView.findViewById(R.id.date);
            date.setText(practice.get(i));

            TextView name = (TextView) addView.findViewById(R.id.meet);
            name.setText(practice.get(i + 1));

            Button display = (Button) addView.findViewById(R.id.view);

            display.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    Intent intent = new Intent(getActivity(), displayPractice.class);
                    startActivity(intent);

                }});
            Container.addView(addView);
        }

        return view;
    }
}