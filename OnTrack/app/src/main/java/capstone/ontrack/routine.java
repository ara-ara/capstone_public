package capstone.ontrack;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v7.widget.SearchView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.MenuItemCompat.OnActionExpandListener;
import android.support.v4.view.ViewPager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.util.ArrayList;
import java.util.List;


public class routine extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ArrayList<String> practiceInfo = new ArrayList<>();
    private ArrayList<String> meetInfo = new ArrayList<>();
    private ArrayList<List<String>> workoutInfo = new ArrayList<>();

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routine);

        toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        practiceInfo = (ArrayList<String>) getIntent().getSerializableExtra("practice");
        if(practiceInfo != null){
            viewPager.setCurrentItem(1);
        }

        meetInfo = (ArrayList<String>) getIntent().getSerializableExtra("meet");
        if(meetInfo != null){
            viewPager.setCurrentItem(0);
        }

        workoutInfo = (ArrayList<List<String>>) getIntent().getSerializableExtra("workout");
        if(workoutInfo != null){
            viewPager.setCurrentItem(2);
        }

    }

    //toolbar profile button pressed go to profile activity
    public void profile(View view) {
        Intent intent = new Intent(this, profile.class);
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

    //toolbar settings button pressed go to profile activity
    public void settings(View view) {
        Intent intent = new Intent(this, settings.class);
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

    //sets the tab view, adds the fragments and the names
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new meet(), "meet");
        adapter.addFragment(new practice(), "practice");
        adapter.addFragment(new workout(), "workout");
        viewPager.setAdapter(adapter);
    }


    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }

    }

    //send array to the proper fragment
    public ArrayList<String> getpracticeInfo(){
        return practiceInfo;
    }
    public ArrayList<String> getmeetInfo(){
        return meetInfo;
    }
    public ArrayList<List<String>> getworkoutInfo(){
        return workoutInfo;
    }
}

