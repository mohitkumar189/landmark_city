package in.squareiapp.landmarkcity.activities.useraccesspackage;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import in.squareiapp.landmarkcity.R;
import in.squareiapp.landmarkcity.activities.BaseActivity;
import in.squareiapp.landmarkcity.activities.UserDashboardActivity;
import in.squareiapp.landmarkcity.interfaces.NetworkResponseListener;
import in.squareiapp.landmarkcity.utils.ApiURLS;
import in.squareiapp.landmarkcity.utils.CommonUtils;
import in.squareiapp.landmarkcity.utils.JsonParser;
import in.squareiapp.landmarkcity.utils.Logger;
import in.squareiapp.landmarkcity.utils.NetworkRequestHandler;
import in.squareiapp.landmarkcity.utils.SharedPrefUtils;

public class LoginActivity extends BaseActivity implements NetworkResponseListener {
    private final String TAG = getClass().getSimpleName();

    private EditText editUserName, editPassword;
    private Button btnLogin;
    private TextView textForgotPassword, textRegister;
    private ImageView ivFacebook, ivGoogle, ivTwitter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        startMyACtivtiy();
    }

    @Override
    protected void initContext() {
        context = LoginActivity.this;
        currentActivity = LoginActivity.this;
    }

    @Override
    protected void initViews() {
        editUserName = (EditText) findViewById(R.id.editUserName);
        editPassword = (EditText) findViewById(R.id.editPassword);
        textForgotPassword = (TextView) findViewById(R.id.textForgotPassword);
        textRegister = (TextView) findViewById(R.id.textRegister);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        ivFacebook = (ImageView) findViewById(R.id.ivFacebook);
        ivGoogle = (ImageView) findViewById(R.id.ivGoogle);
        ivTwitter = (ImageView) findViewById(R.id.ivTwitter);
    }

    @Override
    protected void initListners() {
        textForgotPassword.setOnClickListener(this);
        textRegister.setOnClickListener(this);
        ivFacebook.setOnClickListener(this);
        ivGoogle.setOnClickListener(this);
        ivTwitter.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
       // btnLogin.setTypeface(myTypeface);
    }

    @Override
    protected void initToolbar() {
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.textRegister:
                startNewActivity(currentActivity, UserRegisterActivity.class);
                break;
            case R.id.textForgotPassword:
                startNewActivity(currentActivity, ForgotPasswordActivity.class);
                break;
            case R.id.btnLogin:
                isValidLoginDetails();
                //  startNewActivity(currentActivity, ForgotPasswordActivity.class);
                break;
        }
    }

    private void isValidLoginDetails() {
        String usrName = editUserName.getText().toString();
        String password = editPassword.getText().toString();

        if (!CommonUtils.isEmailValid(usrName)) {
            editUserName.setError(getString(R.string.error_invalid_user_name));
            editUserName.requestFocus();
            return;
        }
        if (password.length() < 4) {
            editPassword.setError(getString(R.string.error_invalid_password));
            editPassword.requestFocus();
            return;
        }

        continueLogin();
    }

    private void continueLogin() {
        if (CommonUtils.isNetworkAvailable(context)) {
            HashMap<String, String> hm = new HashMap<>();

            hm.put("username", editUserName.getText().toString().trim());
            hm.put("password", editPassword.getText().toString().trim());

            NetworkRequestHandler.getInstance(context, this).getStringResponse(ApiURLS.USER_LOGIN_URL, ApiURLS.ApiId.USER_LOGIN_URL, ApiURLS.REQUEST_POST, hm, null, true);
        } else {
            showToast(getString(R.string.network_error), false);
        }
    }

    @Override
    public void onJsonResponse(ApiURLS.ApiId apiId, JSONObject jsonObjectResponse) {

    }

    @Override
    public void onStringResponse(ApiURLS.ApiId apiId, String stringResponse) {
        Logger.error(TAG, "" + stringResponse);
        JsonParser jsonParser = new JsonParser(stringResponse);
        int success = jsonParser.getSuccess();
        int error = jsonParser.getError();
        String message = jsonParser.getMessage();
        if (success == 1 && error == 0) {
            JSONObject jsonObject = jsonParser.getObjectData();
            try {
                int status = jsonObject.getInt("status");
                String apikey = jsonObject.getString("apikey");
                String name = jsonObject.getString("name");
                String usertype = jsonObject.getString("usertype");
                SharedPrefUtils.getInstance(context).putString(SharedPrefUtils.USER_NAME, name);
                if (status == 0) {
                    Bundle bundle = new Bundle();
                    bundle.putString("apikey", apikey);
                    Logger.info(TAG, "sent api key===::" + apikey);
                    startNewActivity(currentActivity, OtpEnterActivity.class, bundle, false, 0, false, 0);
                    finish();
                } else {
                    Logger.info(TAG, "saving api key===::" + apikey);
                    SharedPrefUtils.getInstance(context).putString(SharedPrefUtils.CLIENT_ID, apikey);
                    SharedPrefUtils.getInstance(context).putBoolean(SharedPrefUtils.LOGIN_STATUS, true);
                    startNewActivity(currentActivity, UserDashboardActivity.class);
                    finish();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            showToast(message, false);
        } else {
            showToast(message, false);
        }
    }

    @Override
    public void onErrorResponse(ApiURLS.ApiId apiId, String errorData, int responseCode) {
        Logger.error(TAG, "" + errorData);
    }
}
