package in.squareiapp.landmarkcity.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.HashMap;

import in.squareiapp.landmarkcity.interfaces.NetworkResponseListener;
import in.squareiapp.landmarkcity.utils.ApiURLS;
import in.squareiapp.landmarkcity.utils.AppConstants;
import in.squareiapp.landmarkcity.utils.CommonUtils;
import in.squareiapp.landmarkcity.utils.GPSTracker;
import in.squareiapp.landmarkcity.utils.NetworkRequestHandler;
import in.squareiapp.landmarkcity.utils.SharedPrefUtils;

/**
 * Created by mohit kumar on 8/29/2017.
 */

public class LocationService extends Service implements NetworkResponseListener {
    private Context context;
    private final String TAG =  this.getClass().getSimpleName() ;



    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Let it continue running until it is stopped.
        context = this;
        sendLocation();
        return START_STICKY;
    }

    private void sendLocation() {
        GPSTracker gps = new GPSTracker(LocationService.this);

        if (CommonUtils.isNetworkAvailable(context)) {
            HashMap<String, String> hm = new HashMap<>();
            hm.put(AppConstants.LATITUDE, String.valueOf(gps.getLatitude()));
            hm.put(AppConstants.LONGITUDE, String.valueOf(gps.getLongitude()));
            hm.put(AppConstants.CLIENT_ID , SharedPrefUtils.getInstance(context).getString(SharedPrefUtils.CLIENT_ID));

            NetworkRequestHandler.getInstance(context, this).getStringResponse(ApiURLS.SHARE_LOCATION, ApiURLS.ApiId.SHARE_LOCATION, ApiURLS.REQUEST_POST, hm, null, true);
        } else {
            //showToast(getString(R.string.network_error), false);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "Service Destroyed", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onJsonResponse(ApiURLS.ApiId apiId, JSONObject jsonObjectResponse) {

    }

    @Override
    public void onStringResponse(ApiURLS.ApiId apiId, String stringResponse) {
        Log.d(this.TAG, "onStringResponse: ");

    }

    @Override
    public void onErrorResponse(ApiURLS.ApiId apiId, String errorData, int responseCode) {
        Log.d(this.TAG, "onerrorsend: ");

    }
}