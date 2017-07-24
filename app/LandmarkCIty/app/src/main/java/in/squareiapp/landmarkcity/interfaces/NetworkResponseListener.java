package in.squareiapp.landmarkcity.interfaces;

import org.json.JSONObject;

import in.squareiapp.landmarkcity.utils.ApiURLS;

/**
 * Created by mohit kumar on 7/24/2017.
 */

public interface NetworkResponseListener {

    void onJsonResponse(ApiURLS.ApiId apiId, JSONObject jsonObjectResponse);

    void onStringResponse(ApiURLS.ApiId apiId, String stringResponse);

    void onErrorResponse(ApiURLS.ApiId apiId, String errorData, int responseCode);

}
