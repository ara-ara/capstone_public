
package capstone.ontrack;

import android.content.Intent;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.app.Activity;
import android.graphics.*;
import android.os.Bundle;

import com.androidplot.util.PixelUtils;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYSeries;
import com.androidplot.xy.*;

import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;
import java.util.*;
/**
 * A simple XYPlot
 */

public class graph extends AppCompatActivity {
    /*  @Override
      protected void onCreate(Bundle savedInstanceState) {
          super.onCreate(savedInstanceState);
          setContentView(R.layout.activity_graph);
          Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
          setSupportActionBar(myToolbar);
          setTitle("Graph");
          getSupportActionBar().setDisplayShowHomeEnabled(true);
          getSupportActionBar().setDisplayUseLogoEnabled(true);
      }
      */
    private XYPlot plot;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        // initialize our XYPlot reference:
        plot = (XYPlot) findViewById(R.id.plot);

        // create a couple arrays of y-values to plot:
        final String[] domainLabels = {"Apr. 1", "Apr. 3", "Apr. 16", "Apr. 21", "Apr. 28", "May 6", "May 13", "May 21", "Jun. 1", "Jun. 10"};
        Number[] series1Numbers = {2, 4, 2, 8, 4, 1, 8, 2, 1, 6};
        Number[] series2Numbers = {5, 7, 10, 5, 2, 10, 4, 2, 8, 4};

        // turn the above arrays into XYSeries':
        // (Y_VALS_ONLY means use the element index as the x value)
        XYSeries series1 = new SimpleXYSeries(
                Arrays.asList(series1Numbers), SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, "Meet Finish");
        XYSeries series2 = new SimpleXYSeries(
                Arrays.asList(series2Numbers), SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, "No. of Practices");

        // create formatters to use for drawing a series using LineAndPointRenderer
        // and configure them from xml:
        LineAndPointFormatter series1Format = new LineAndPointFormatter(Color.RED, Color.GREEN, Color.BLUE, null);


        LineAndPointFormatter series2Format = new LineAndPointFormatter(Color.RED, Color.GREEN, Color.BLUE, null);


        // add an "dash" effect to the series2 line:
        series2Format.getLinePaint().setPathEffect(new DashPathEffect(new float[] {

                // always use DP when specifying pixel sizes, to keep things consistent across devices:
                PixelUtils.dpToPix(20),
                PixelUtils.dpToPix(15)}, 0));

        // just for fun, add some smoothing to the lines:
        // see: http://androidplot.com/smooth-curves-and-androidplot/
        series1Format.setInterpolationParams(
                new CatmullRomInterpolator.Params(10, CatmullRomInterpolator.Type.Centripetal));

        series2Format.setInterpolationParams(
                new CatmullRomInterpolator.Params(10, CatmullRomInterpolator.Type.Centripetal));

        // add a new series' to the xyplot:
        plot.addSeries(series1, series1Format);
        plot.addSeries(series2, series2Format);

        plot.getGraph().getLineLabelStyle(XYGraphWidget.Edge.BOTTOM).setFormat(new Format() {
            @Override
            public StringBuffer format(Object obj, StringBuffer toAppendTo, FieldPosition pos) {
                int i = Math.round(((Number) obj).floatValue());
                return toAppendTo.append(domainLabels[i]);
            }
            @Override
            public Object parseObject(String source, ParsePosition pos) {
                return null;
            }
        });
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
    }

    //toolbar profile button pressed go to profile activity
    public void profile(View view) {
        Intent intent = new Intent(this, profile.class);
        startActivity(intent);
    }

    //toolbar home button pressed go to profile activity
    public void home(View view) {
        Intent intent = new Intent(this, home.class);
        startActivity(intent);
    }

    //toolbar settings button pressed go to profile activity
    public void settings(View view) {
        Intent intent = new Intent(this, settings.class);
        startActivity(intent);
    }
}
