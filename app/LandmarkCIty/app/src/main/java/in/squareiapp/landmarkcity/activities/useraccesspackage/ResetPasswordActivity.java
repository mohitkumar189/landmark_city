package in.squareiapp.landmarkcity.activities.useraccesspackage;

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

public class ResetPasswordActivity extends BaseActivity implements NetworkResponseListener {

    private final String TAG = getClass().getSimpleName();
    private EditText editPassword, editPassword2;
    private EditText editDigit1, editDigit2, editDigit3, editDigit4;
    private Button btnReset;
    private String username;
    private TextView tvResend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        startMyACtivtiy();
        username = getIntent().getStringExtra("username");
        Logger.info(TAG, "received username::" + username);
    }

    @Override
    protected void initContext() {
        context = ResetPasswordActivity.this;
        currentActivity = ResetPasswordActivity.this;
    }

    @Override
    protected void initViews() {
        editPassword = (EditText) findViewById(R.id.editPassword);
        editPassword2 = (EditText) findViewById(R.id.editPassword2);
        editDigit1 = (EditText) findViewById(R.id.editDigit1);
        editDigit2 = (EditText) findViewById(R.id.editDigit2);
        editDigit3 = (EditText) findViewById(R.id.editDigit3);
        editDigit4 = (EditText) findViewById(R.id.editDigit4);
        tvResend = (TextView) findViewById(R.id.tvResend);
        btnReset = (Button) findViewById(R.id.btnReset);
    }

    private void continueResetPassword() {
        if (CommonUtils.isNetworkAvailable(context)) {
            HashMap<String, String> hm = new HashMap<>();

            hm.put("username", username);
            hm.put("vcode", editDigit1.getText().toString());
            hm.put("password", editPassword.getText().toString().trim());

            NetworkRequestHandler.getInstance(context, this).getStringResponse(ApiURLS.RESET_PASSWORD, ApiURLS.ApiId.RESET_PASSWORD, ApiURLS.REQUEST_POST, hm, null, true);
        } else {
            showToast(getString(R.string.network_error), false);
        }
    }

    @Override
    protected void initListners() {
        btnReset.setOnClickListener(this);
        tvResend.setOnClickListener(this);
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
            case R.id.btnReset:
                if (CommonUtils.isStringSame(editPassword.getText().toString().trim(), editPassword2.getText().toString().trim())) {
                    continueResetPassword();
                }
                break;
            case R.id.tvResend:
                continueResendOtp();
                break;
        }
    }

    private void continueResendOtp() {

        if (CommonUtils.isNetworkAvailable(context)) {
            HashMap<String, String> hm = new HashMap<>();
            //   hm.put("otp", otp);
            hm.put("client_id", username);

            NetworkRequestHandler.getInstance(context, this).getStringResponse(ApiURLS.USER_RESEND_OTP, ApiURLS.ApiId.USER_RESEND_OTP, ApiURLS.REQUEST_POST, hm, null, true);
        } else {
            showToast(getString(R.string.network_error), false);
        }
    }

    @Override
    public void onJsonResponse(ApiURLS.ApiId apiId, JSONObject jsonObjectResponse) {

    }

    @Override
    public void onStringResponse(ApiURLS.ApiId apiId, String stringResponse) {
        //////////////////////checking for the resent otp///////////////////////////
        if (apiId == ApiURLS.ApiId.USER_RESEND_OTP) {
            JsonParser jsonParser = new JsonParser(stringResponse);
            int success = jsonParser.getSuccess();
            int error = jsonParser.getError();
            String message = jsonParser.getMessage();
            if (success == 1 && error == 0) {
                showToast(message, false);

            } else {
                showToast(message, false);
            }
        }
    }

    @Override
    public void onErrorResponse(ApiURLS.ApiId apiId, String errorData, int responseCode) {

    }
}
