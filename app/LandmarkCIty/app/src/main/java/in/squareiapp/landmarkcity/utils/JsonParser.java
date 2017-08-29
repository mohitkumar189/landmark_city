package in.squareiapp.landmarkcity.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by mohit kumar on 7/26/2017.
 */

public class JsonParser {
    private final String TAG = getClass().getSimpleName();
    private JSONObject jsonObject;

    public JsonParser(String jsonResponse) {
        try {
            jsonObject = new JSONObject(jsonResponse);
            Logger.info(TAG, "string converted to json");
        } catch (JSONException e) {
            Logger.error(TAG, "Exception in conversion ::" + e);
        }
    }

    public int getSuccess() {
        int success = 0;
        try {
            success = jsonObject.getInt("success");
        } catch (JSONException e) {
            Logger.error(TAG, "Exception in success key ::" + e);
        }
        return success;
    }

    public int getError() {
        int error = 0;
        try {
            error = jsonObject.getInt("error");
        } catch (JSONException e) {
            Logger.error(TAG, "Exception in error key ::" + e);
        }
        return error;
    }

    public String getMessage() {
        String message = "";
        try {
            message = jsonObject.getString("message");
        } catch (JSONException e) {
            Logger.error(TAG, "Exception in message key ::" + e);
        }
        return message;
    }

    public String getUserType() {
        String message = "";
        try {
            message = jsonObject.getString("userType");
        } catch (JSONException e) {
            Logger.error(TAG, "Exception in message key ::" + e);
        }
        return message;
    }

    public String getRewardsPoints() {
        String points = "";
        try {
            points = jsonObject.getString("points");
        } catch (JSONException e) {
            Logger.error(TAG, "Exception in message key ::" + e);
        }
        return points;
    }

    public JSONObject getObjectData() {
        JSONObject data = null;
        try {
            data = jsonObject.getJSONObject("data");
        } catch (JSONException e) {
            Logger.error(TAG, "Exception in data  key ::" + e);
        }
        return data;
    }

    public JSONArray getArrayData() {
        JSONArray data = null;
        try {
            data = jsonObject.getJSONArray("data");
        } catch (JSONException e) {
            Logger.error(TAG, "Exception in data  key ::" + e);
        }
        return data;
    }
}
