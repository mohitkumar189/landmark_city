package in.squareiapp.landmarkcity.activities.featuresactivities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

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
    private TextView tvUserStatus, editUserMobile, editUserAddress, tvUserName, editUserEmail;
    private ImageView ivBack, ivProfile;
    private String clientid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_profile_activity);
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
        editUserAddress = (TextView) findViewById(R.id.editUserAddress);
        editUserMobile = (TextView) findViewById(R.id.editUserMobile);
        tvUserName = (TextView) findViewById(R.id.tvUserName);
        tvUserStatus = (TextView) findViewById(R.id.tvUserStatus);
        editUserEmail = (TextView) findViewById(R.id.editUserEmail);
        ivBack = (ImageView) findViewById(R.id.ivBack);
        ivProfile = (ImageView) findViewById(R.id.ivProfile);
    }

    @Override
    protected void initListners() {
        ivBack.setOnClickListener(this);
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
        switch (v.getId()) {
            case R.id.ivBack:
                finish();
                break;
        }
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
                tvUserName.setText(name);
            if (CommonUtils.isValidString(email))
                editUserEmail.setText(email);
            if (CommonUtils.isValidString(address))
                editUserAddress.setText(address);
            if (CommonUtils.isValidString(prof_status))
                tvUserStatus.setText(prof_status);

/*            if (CommonUtils.isValidString(country))
                editCountry.setText(country);
            if (CommonUtils.isValidString(city))
                editUserCity.setText(city);*/
            if (CommonUtils.isValidString(mobile))
                editUserMobile.setText(mobile);


            if (CommonUtils.isValidString(profilepic))
                Picasso.with(context).load(profilepic).fit().into(ivProfile);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
