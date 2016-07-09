package ua.dp.vedanta.ticketchecker;

import android.app.Application;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;

/**
 * Created by Stanislav Volnyansky on 09.07.16.
 */
public class ThisApplication extends Application {

    private Tracker mTracker;
    private static ThisApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance=this;
    }

    /**
     * Gets the default {@link Tracker} for this {@link Application}.
     * @return tracker
     */
    synchronized public Tracker getDefaultTracker() {
        if (mTracker == null) {
            GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
            mTracker = analytics.newTracker("Tracker");
        }
        return mTracker;
    }
    public static Tracker getTracker(){
        return instance.getDefaultTracker();
    }
}
