package capstone.ontrack;

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

import java.util.ArrayList;
import java.util.List;

public class profile extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
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

    //set up the tab views and their names
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new profileMeet(), "Meet");
        adapter.addFragment(new profilePractice(), "Practice");
        adapter.addFragment(new profileWorkout(), "Workout");
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
}
