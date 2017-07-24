package in.squareiapp.landmarkcity.application;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;
import android.text.TextUtils;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

/**
 * Created by mohit kumar on 7/18/2017.
 */

public class LandmarkCityApplication extends Application {
    public static String TAG = "baseApplication";
    private static LandmarkCityApplication mInstance;
    private static RequestQueue mRequestQueue;

    public static synchronized LandmarkCityApplication getInstance() {
        return mInstance;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    @Override
    public void registerOnProvideAssistDataListener(OnProvideAssistDataListener callback) {
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(this.getApplicationContext());
        }
        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> request, String tag) {
        request = settingTimeout(request);
        request.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(request);
    }

    public <T> void addToRequestQueue(Request<T> request) {
        request.setTag(TAG);
        //   request=settingTimeout(request);
        RetryPolicy retryPolicy = new DefaultRetryPolicy(
                6000,
                2,
                1000
        );
        request.setRetryPolicy(retryPolicy);
        getRequestQueue().add(request);
    }

    public Request settingTimeout(Request request) {
        request.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 50000000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 50000;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {

            }
        });

        return request;
    }

    public void cancelPendingRequest(Object tag) {
        getRequestQueue().cancelAll(tag);
    }
}
