package in.squareiapp.landmarkcity.activities.useraccesspackage;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONObject;

import java.util.HashMap;

import in.squareiapp.landmarkcity.R;
import in.squareiapp.landmarkcity.activities.BaseActivity;
import in.squareiapp.landmarkcity.interfaces.NetworkResponseListener;
import in.squareiapp.landmarkcity.utils.ApiURLS;
import in.squareiapp.landmarkcity.utils.CommonUtils;
import in.squareiapp.landmarkcity.utils.JsonParser;
import in.squareiapp.landmarkcity.utils.Logger;
import in.squareiapp.landmarkcity.utils.NetworkRequestHandler;

public class ForgotPasswordActivity extends BaseActivity implements NetworkResponseListener {
    private final String TAG = getClass().getSimpleName();
    private EditText editUserName;
    private Button btnResetPassword;
    private TextView textLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        startMyACtivtiy();
    }

    @Override
    protected void initContext() {
        context = ForgotPasswordActivity.this;
        currentActivity = ForgotPasswordActivity.this;
    }

    @Override
    protected void initViews() {
        editUserName = (EditText) findViewById(R.id.editUserName);
        btnResetPassword = (Button) findViewById(R.id.btnResetPassword);
        textLogin = (TextView) findViewById(R.id.textLogin);
    }

    @Override
    protected void initListners() {
        textLogin.setOnClickListener(this);
        btnResetPassword.setOnClickListener(this);
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
            case R.id.textLogin:
                finish();
                break;
            case R.id.btnResetPassword:
                verifyInputDetails();
                break;
        }
    }

    private void verifyInputDetails() {
        String usrName = editUserName.getText().toString();

        if (!CommonUtils.isEmailValid(usrName)) {
            editUserName.setError(getString(R.string.error_invalid_user_name));
            editUserName.requestFocus();
            return;
        }
        continueResetPassword();
    }

    private void continueResetPassword() {
        if (CommonUtils.isNetworkAvailable(context)) {
            HashMap<String, String> hm = new HashMap<>();

            hm.put("username", editUserName.getText().toString().trim());

            NetworkRequestHandler.getInstance(context, this).getStringResponse(ApiURLS.USER_RESET_PASSWORD, ApiURLS.ApiId.USER_RESET_PASSWORD, ApiURLS.REQUEST_POST, hm, null, true);
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
            Intent intent = new Intent(currentActivity, ResetPasswordActivity.class);
            intent.putExtra("username", editUserName.getText().toString().trim());
            Logger.info(TAG, "sending username::" + editUserName.getText().toString().trim());
            startActivity(intent);
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
