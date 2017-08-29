package in.squareiapp.landmarkcity.utils;

import android.content.Context;
import android.content.SharedPreferences;

import in.squareiapp.landmarkcity.interfaces.AppConstants;

/**
 * Created by mohit kumar on 7/18/2017.
 */

public class SharedPrefUtils {

    public static final String USER_TYPE = "USER_TYPE" ;
    public static final String SOS_NUMBER = "SOS_NUMBER";
    private static SharedPrefUtils sharedPreferenceUtils;
    private static SharedPreferences sharedPreferences;
    private static Context context;
    private static final String TAG = "SharedPrefUtils";

    ////////////////////// Keys for shared preference ///////////////////////
    public static final String LOGIN_STATUS = "islogin";
    public static final String CLIENT_ID = "clientid";
    public static final String USER_NAME = "username";
    public static final String OTP_STATUS = "otp_status";

    private SharedPrefUtils() {

    }

    public static SharedPrefUtils getInstance(Context context) {
        if (sharedPreferenceUtils == null) {
            sharedPreferenceUtils = new SharedPrefUtils();
        }
        if (SharedPrefUtils.context == null) {
            SharedPrefUtils.context = context;
        }
        return sharedPreferenceUtils;
    }

    protected SharedPreferences getSharedPreferences() {
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences(AppConstants.APP_NAME, Context.MODE_PRIVATE);
        }
        Logger.debug(TAG, "returning instance for sharedPrefClass and context is:  " + context);
        return sharedPreferences;
    }

    public String getString(String key) {
        Logger.debug(TAG, "returning value for " + key);
        return getSharedPreferences().getString(key, "");
    }

    public boolean getBoolean(String key) {
        Logger.debug(TAG, "returning value for " + key);
        return getSharedPreferences().getBoolean(key, false);
    }

    public void putBoolean(String key, boolean value) {
        Logger.debug(TAG, "saving value for " + key + " value is " + value);
        getSharedPreferences().edit().putBoolean(key, value).commit();

    }

    public void putString(String key, String value) {
        Logger.debug(TAG, "saving value for " + key + " value is " + value);
        getSharedPreferences().edit().putString(key, value).commit();
    }

    public void putInteger(String key, int value) {
        Logger.debug(TAG, "saving value for " + key + " value is " + value);
        getSharedPreferences().edit().putInt(key, value).commit();
    }

    public int getInteger(String key) {
        Logger.debug(TAG, "returning value for " + key);
        return getSharedPreferences().getInt(key, 0);
    }

    public void clearAll() {
        Logger.debug(TAG, "Preferences are cleared");
        getSharedPreferences().edit().clear().commit();
    }

}
