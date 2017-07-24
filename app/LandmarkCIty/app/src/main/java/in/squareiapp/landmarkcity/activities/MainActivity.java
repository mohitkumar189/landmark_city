package in.squareiapp.landmarkcity.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import in.squareiapp.landmarkcity.R;
import in.squareiapp.landmarkcity.application.LandmarkCityApplication;

public class MainActivity extends AppCompatActivity {

    private String jsonData = "{" + "name:mohit, age:23,address:noida" + "}";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void download(View view) {

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, "http://www.parcelabler.com/", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                System.out.println(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        LandmarkCityApplication.getInstance().addToRequestQueue(jsonObjectRequest);
    }
}
