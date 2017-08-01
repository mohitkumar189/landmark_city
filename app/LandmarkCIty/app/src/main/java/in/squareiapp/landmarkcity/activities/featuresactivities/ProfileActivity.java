package in.squareiapp.landmarkcity.activities.featuresactivities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import in.squareiapp.landmarkcity.R;
import in.squareiapp.landmarkcity.activities.BaseActivity;
import in.squareiapp.landmarkcity.interfaces.NetworkResponseListener;
import in.squareiapp.landmarkcity.utils.ApiURLS;
import in.squareiapp.landmarkcity.utils.CommonUtils;
import in.squareiapp.landmarkcity.utils.JsonParser;
import in.squareiapp.landmarkcity.utils.NetworkRequestHandler2;
import in.squareiapp.landmarkcity.utils.SharedPrefUtils;

import static in.squareiapp.landmarkcity.utils.ApiURLS.ApiId.GET_PROFILE;

public class ProfileActivity extends BaseActivity implements NetworkResponseListener {
    private final String TAG = getClass().getSimpleName();
    private EditText editUserName, editUserEmail, editUserAddress, editLandmark, editZipcode, editState, editCountry, editUserCity, editUserMobile, editGender;
    private Button btnSubmit;
    private ImageView ivBack, ivProfile;
    private String clientid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        startMyACtivtiy();
        Intent intent = getIntent();
        if (intent.getExtras() != null)
            clientid = intent.getStringExtra("clientid");
        getProfileData();
    }

    @Override
    protected void initContext() {
        context = ProfileActivity.this;
        currentActivity = ProfileActivity.this;
    }

    @Override
    protected void initViews() {
        editUserName = (EditText) findViewById(R.id.editUserName);
        editUserEmail = (EditText) findViewById(R.id.editUserEmail);
        editUserAddress = (EditText) findViewById(R.id.editUserAddress);
        editLandmark = (EditText) findViewById(R.id.editLandmark);
        editZipcode = (EditText) findViewById(R.id.editZipcode);
        editState = (EditText) findViewById(R.id.editState);
        editCountry = (EditText) findViewById(R.id.editCountry);
        editUserCity = (EditText) findViewById(R.id.editUserCity);
        editUserMobile = (EditText) findViewById(R.id.editUserMobile);
        editGender = (EditText) findViewById(R.id.editGender);

        btnSubmit = (Button) findViewById(R.id.btnSubmit);
        ivBack = (ImageView) findViewById(R.id.ivBack);
        ivProfile = (ImageView) findViewById(R.id.ivProfile);
    }

    @Override
    protected void initListners() {

    }

    @Override
    protected void initToolbar() {
/*        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setToolBar(toolbar);
        setToolbarTitle("Profile");
        showHomeButton();
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });*/
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }

    @Override
    public void onClick(View v) {

    }

    private void getProfileData() {
        NetworkRequestHandler2 networkRequestHandler = new NetworkRequestHandler2(context, this);

        String client_id = SharedPrefUtils.getInstance(context).getString(SharedPrefUtils.CLIENT_ID);

        String url = ApiURLS.GET_PROFILE + "?client_id=" + client_id + "&id=" + clientid;
        networkRequestHandler.getStringResponse(url, GET_PROFILE, ApiURLS.REQUEST_GET, null, null, true);

    }

    @Override
    public void onJsonResponse(ApiURLS.ApiId apiId, JSONObject jsonObjectResponse) {

    }

    @Override
    public void onStringResponse(ApiURLS.ApiId apiId, String stringResponse) {
        if (apiId == GET_PROFILE) {
            JsonParser jsonParser = new JsonParser(stringResponse);
            int success = jsonParser.getSuccess();
            //  int error = jsonParser.getError();
            String message = jsonParser.getMessage();
            if (success == 1) {
                JSONObject jsonObject = jsonParser.getObjectData();
                setData(jsonObject);
   /*             try {
                    String id = jsonObject.getString("id");
                    String name = jsonObject.getString("name");
                    String userid = jsonObject.getString("userid");
                    String address = jsonObject.getString("address");
                    String landmark = jsonObject.getString("landmark");
                    String city = jsonObject.getString("city");
                    String state = jsonObject.getString("state");
                    String country = jsonObject.getString("country");
                    String zipcode = jsonObject.getString("zipcode");
                    String email = jsonObject.getString("email");
                    String gender = jsonObject.getString("gender");
                    String matrialstatus = jsonObject.getString("matrialstatus");
                    String phone = jsonObject.getString("phone");
                    String profilepic = jsonObject.getString("profilepic");
                    String prof_status = jsonObject.getString("prof_status");
                    String usertype = jsonObject.getString("usertype");
                    String mobile = jsonObject.getString("mobile");

                } catch (JSONException e) {
                    e.printStackTrace();
                }*/
            } else {
                showToast(message, false);
            }
        }
    }

    @Override
    public void onErrorResponse(ApiURLS.ApiId apiId, String errorData, int responseCode) {

    }

    private void setData(JSONObject jsonObject) {
        try {
            String id = jsonObject.getString("id");
            String name = jsonObject.getString("name");
            String userid = jsonObject.getString("userid");
            String address = jsonObject.getString("address");
            String landmark = jsonObject.getString("landmark");
            String city = jsonObject.getString("city");
            String state = jsonObject.getString("state");
            String country = jsonObject.getString("country");
            String zipcode = jsonObject.getString("zipcode");
            String email = jsonObject.getString("email");
            String gender = jsonObject.getString("gender");
            String matrialstatus = jsonObject.getString("matrialstatus");
            String phone = jsonObject.getString("phone");
            String profilepic = jsonObject.getString("profilepic");
            String prof_status = jsonObject.getString("prof_status");
            String usertype = jsonObject.getString("usertype");
            String mobile = jsonObject.getString("mobile");

            if (CommonUtils.isValidString(name))
                editUserName.setText(name);
            if (CommonUtils.isValidString(email))
                editUserEmail.setText(email);
            if (CommonUtils.isValidString(address))
                editUserAddress.setText(address);
            if (CommonUtils.isValidString(landmark))
                editLandmark.setText(landmark);
            if (CommonUtils.isValidString(zipcode))
                editZipcode.setText(zipcode);
            if (CommonUtils.isValidString(state))
                editState.setText(state);
            if (CommonUtils.isValidString(country))
                editCountry.setText(country);
            if (CommonUtils.isValidString(city))
                editUserCity.setText(city);
            if (CommonUtils.isValidString(mobile))
                editUserMobile.setText(mobile);
            if (CommonUtils.isValidString(gender))
                editGender.setText(gender);

            if (CommonUtils.isValidString(profilepic))
                Picasso.with(context).load(profilepic).fit().into(ivProfile);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
