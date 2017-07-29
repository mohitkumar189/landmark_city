package in.squareiapp.landmarkcity.utils;

import android.app.ProgressDialog;
import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import in.squareiapp.landmarkcity.application.LandmarkCityApplication;
import in.squareiapp.landmarkcity.interfaces.NetworkResponseListener;

/**
 * Created by mohitattri on 7/28/17.
 */

public class NetworkRequestHandler2 {
    private static final String TAG = "NetworkRequestHandler";
    private NetworkResponseListener networkResponseListener;
    private ProgressDialog progressDialog;
    private String dialogMessage = "Please wait...";

    public NetworkRequestHandler2(Context context, NetworkResponseListener networkResponseListener) {
        Logger.info(TAG, "object is created for ::" + networkResponseListener);
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(context);
            progressDialog.setMessage(dialogMessage);
            progressDialog.setCancelable(true);
        }
        this.networkResponseListener = networkResponseListener;
    }

    public void getStringResponse(final String requestUrl, final ApiURLS.ApiId apiId, int requestMethod, final Map<String, String> postParams, final Map<String, String> headerParams, boolean isProgressDialog) {
        Logger.info(TAG, "====>Network Call<===:: " + requestUrl + " :: request Method==> " + requestMethod);

        if (isProgressDialog)
            showProgressDialog(progressDialog);

        StringRequest stringRequest = new StringRequest(requestMethod, requestUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Logger.info(TAG, "==>Response API<==:: " + requestUrl + " ===> " + response);
                dismissProgressDiialog(progressDialog);
                try {
                    if (response != null) {
                        if (networkResponseListener != null) {
                            Logger.debug(TAG, "==>calling response listener<==" + networkResponseListener);
                            networkResponseListener.onStringResponse(apiId, response);
                        } else {
                            Logger.debug(TAG, "==>response listener is null<==");
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Logger.info(TAG, "==>Error API<==:: " + requestUrl);

                dismissProgressDiialog(progressDialog);
                String messageBody = null; // message received in the error
                int responseCode = 0;
                if (error != null) {
                    if (error.networkResponse != null) {
                        responseCode = error.networkResponse.statusCode; // to get response code
                        if (error.networkResponse.data != null) {
                            try {
                                messageBody = new String(error.networkResponse.data, "UTF-8");
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }
                            if (networkResponseListener != null) {
                                Logger.debug(TAG, "==>calling error listener<==");
                                networkResponseListener.onErrorResponse(apiId, messageBody, responseCode);
                            } else {
                                Logger.debug(TAG, "==>error listener is null<==");
                            }
                        } else {
                            if (networkResponseListener != null) {
                                Logger.debug(TAG, "==>calling error listener<==");
                                networkResponseListener.onErrorResponse(apiId, messageBody, responseCode);
                            } else {
                                Logger.debug(TAG, "==>error listener is null<==");
                            }
                        }
                    }
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                if (postParams != null) {
                    Logger.info(TAG, "===>url parameters<== :: " + postParams.toString());
                    params.putAll(postParams);
                }
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                if (headerParams != null) {
                    Logger.info(TAG, "===>headers parameters<== :: " + headerParams.toString());
                    params.putAll(headerParams);
                }
                return params;
            }
        };
        LandmarkCityApplication.getInstance().addToRequestQueue(stringRequest);
    }

    private void dismissProgressDiialog(ProgressDialog pd) {
        if (pd != null && pd.isShowing()) {
            Logger.debug(TAG, "==>Dismissing progress dialog<==");
            pd.cancel();
            progressDialog = null;
        }
    }

    private void showProgressDialog(ProgressDialog pd) {
        if (pd != null && !pd.isShowing()) {
            Logger.debug(TAG, "==>Showing progress dialog<==");
            pd.show();
        }
    }
}
