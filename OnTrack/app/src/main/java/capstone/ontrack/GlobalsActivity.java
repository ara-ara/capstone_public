package capstone.ontrack;

import android.app.Application;

/**
 * Created by Andy Aceto on 11/4/2016.
 *
 * Used to store variables that will be shared
 * across activities
 *
 */

public class GlobalsActivity extends Application {

    private String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String newId) {
        this.userId = newId;
    }
}
