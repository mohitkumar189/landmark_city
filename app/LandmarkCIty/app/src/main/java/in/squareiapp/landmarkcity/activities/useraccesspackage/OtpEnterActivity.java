package in.squareiapp.landmarkcity.activities.useraccesspackage;

import android.app.Service;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

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

public class OtpEnterActivity extends BaseActivity implements View.OnFocusChangeListener, View.OnKeyListener, TextWatcher, NetworkResponseListener, View.OnTouchListener {
    private final String TAG = getClass().getSimpleName();

    private EditText editDigit1, editDigit2, editDigit3, editDigit4, editDigitHidden;
    private Button btnVerify;
    private TextView tvResend;
    private String apikey;
    private ScrollView parentLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_enter);
        //  setContentView((new MainLayout(this, null)));
        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            if (bundle.containsKey("apikey")) {
                apikey = bundle.getString("apikey");
                Logger.info(TAG, "received api key===::" + apikey);
            }
        }

        SharedPrefUtils.getInstance(context).putBoolean(SharedPrefUtils.OTP_STATUS, true);
        startMyACtivtiy();
        // Bundle b=getIntent().getBundleExtra("");
    }

    @Override
    protected void initContext() {
        context = OtpEnterActivity.this;
        currentActivity = OtpEnterActivity.this;
    }

    @Override
    protected void initViews() {
        editDigit1 = (EditText) findViewById(R.id.editDigit1);
        editDigit2 = (EditText) findViewById(R.id.editDigit2);
        editDigit3 = (EditText) findViewById(R.id.editDigit3);
        editDigit4 = (EditText) findViewById(R.id.editDigit4);
        editDigitHidden = (EditText) findViewById(R.id.editDigitHidden);
        parentLayout = (ScrollView)findViewById(R.id.parentLayout);

        btnVerify = (Button) findViewById(R.id.btnVerify);
        tvResend = (TextView) findViewById(R.id.tvResend);
        // btnVerify.setTypeface(myTypeface);
    }

    @Override
    protected void initListners() {
        btnVerify.setOnClickListener(this);
        tvResend.setOnClickListener(this);
        parentLayout.setOnTouchListener(this);
        //  setPINListeners();
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
            case R.id.btnVerify:
                if (editDigit1.getText().length() == 0) {
                    Toast.makeText(this, R.string.otp_error, Toast.LENGTH_SHORT).show();
                } else {
                    continueVerifyOtp();
                }
                break;
            case R.id.tvResend:
                if (editDigit1.getText().length() == 0 ) {
                    Toast.makeText(this, R.string.otp_error, Toast.LENGTH_SHORT).show();
                } else {
                    continueResendOtp();
                }

                break;
        }
    }

    private void continueVerifyOtp() {

        if (CommonUtils.isNetworkAvailable(context)) {
            HashMap<String, String> hm = new HashMap<>();
            hm.put("otp", editDigit1.getText().toString().trim());
            hm.put("client_id", SharedPrefUtils.getInstance(context).getString(SharedPrefUtils.CLIENT_ID));

            NetworkRequestHandler.getInstance(context, this).getStringResponse(ApiURLS.USER_VERIFY_OTP, ApiURLS.ApiId.USER_VERIFY_OTP, ApiURLS.REQUEST_POST, hm, null, true);
        } else {
            showToast(getString(R.string.network_error), false);
        }
    }

    private void continueResendOtp() {

        if (CommonUtils.isNetworkAvailable(context)) {
            HashMap<String, String> hm = new HashMap<>();
            //   hm.put("otp", otp);
            hm.put("client_id", SharedPrefUtils.getInstance(context).getString(SharedPrefUtils.CLIENT_ID));

            NetworkRequestHandler.getInstance(context, this).getStringResponse(ApiURLS.USER_RESEND_OTP, ApiURLS.ApiId.USER_RESEND_OTP, ApiURLS.REQUEST_POST, hm, null, true);
        } else {
            showToast(getString(R.string.network_error), false);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

        if (s.length() == 0) {
            setFocusedPinBackground(editDigit1);
            editDigit1.setText("");
        } else if (s.length() == 1) {
            setFocusedPinBackground(editDigit2);
            editDigit1.setText(s.charAt(0) + "");
            editDigit2.setText("");
            editDigit3.setText("");
            editDigit4.setText("");
        } else if (s.length() == 2) {
            setFocusedPinBackground(editDigit3);
            editDigit2.setText(s.charAt(1) + "");
            editDigit3.setText("");
            editDigit4.setText("");
        } else if (s.length() == 3) {
            setFocusedPinBackground(editDigit4);
            editDigit3.setText(s.charAt(2) + "");
            editDigit4.setText("");
        } else if (s.length() == 4) {
            //  setDefaultPinBackground(editDigit4);
            editDigit4.setText(s.charAt(3) + "");
            hideSoftKeyboard(editDigit4);
        }
    }

    private void setPINListeners() {
        editDigit1.setOnFocusChangeListener(this);
        editDigit2.setOnFocusChangeListener(this);
        editDigit3.setOnFocusChangeListener(this);
        editDigit4.setOnFocusChangeListener(this);
        editDigitHidden.setOnFocusChangeListener(this);

        editDigit1.setOnKeyListener(this);
        editDigit2.setOnKeyListener(this);
        editDigit3.setOnKeyListener(this);
        editDigit4.setOnKeyListener(this);
        editDigitHidden.setOnKeyListener(this);
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        final int id = v.getId();
        switch (id) {
            case R.id.editDigit1:
                if (hasFocus) {
                    setFocus(editDigitHidden);
                    showSoftKeyboard(editDigitHidden);
                }
                break;

            case R.id.editDigit2:
                if (hasFocus) {
                    setFocus(editDigitHidden);
                    showSoftKeyboard(editDigitHidden);
                }
                break;

            case R.id.editDigit3:
                if (hasFocus) {
                    setFocus(editDigitHidden);
                    showSoftKeyboard(editDigitHidden);
                }
                break;

            case R.id.editDigit4:
                if (hasFocus) {
                    setFocus(editDigitHidden);
                    showSoftKeyboard(editDigitHidden);
                }
                break;

            default:
                break;
        }
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            final int id = v.getId();
            switch (id) {
                case R.id.editDigitHidden:
                    if (keyCode == KeyEvent.KEYCODE_DEL) {
                        if (editDigitHidden.getText().length() == 4)
                            editDigit4.setText("");
                        else if (editDigitHidden.getText().length() == 3)
                            editDigit3.setText("");
                        else if (editDigitHidden.getText().length() == 2)
                            editDigit2.setText("");
                        else if (editDigitHidden.getText().length() == 1)
                            editDigit1.setText("");

                        if (editDigitHidden.length() > 0)
                            editDigitHidden.setText(editDigitHidden.getText().subSequence(0, editDigitHidden.length() - 1));
                        return true;
                    }
                    break;
                default:
                    return false;
            }
        }
        return false;
    }

    private static void setFocus(EditText editText) {
        if (editText == null)
            return;

        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();
    }

    public void showSoftKeyboard(EditText editText) {
        if (editText == null)
            return;

        InputMethodManager imm = (InputMethodManager) getSystemService(Service.INPUT_METHOD_SERVICE);
        imm.showSoftInput(editText, 0);
    }

    public void hideSoftKeyboard(EditText editText) {
        if (editText == null)
            return;

        InputMethodManager imm = (InputMethodManager) getSystemService(Service.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }

    private void setFocusedPinBackground(EditText editText) {

        // editText.requestFocus();
        //  setViewBackground(editText, getResources().getDrawable(R.drawable.textfield_focused_holo_light));
    }

    @SuppressWarnings("deprecation")
    public void setViewBackground(View view, Drawable background) {
        if (view == null || background == null)
            return;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            view.setBackground(background);
        } else {
            view.setBackgroundDrawable(background);
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
        //////////////////////checking for the entered otp///////////////////////////
        else if (apiId == ApiURLS.ApiId.USER_VERIFY_OTP) {
            JsonParser jsonParser = new JsonParser(stringResponse);
            int success = jsonParser.getSuccess();
            int error = jsonParser.getError();
            String message = jsonParser.getMessage();
            if (success == 1 && error == 0) {
                showToast(message, false);
                SharedPrefUtils.getInstance(context).putString(SharedPrefUtils.CLIENT_ID, apikey);
                SharedPrefUtils.getInstance(context).putBoolean(SharedPrefUtils.LOGIN_STATUS, true);
                SharedPrefUtils.getInstance(context).putBoolean(SharedPrefUtils.OTP_STATUS, false);
                startNewActivity(currentActivity, UserDashboardActivity.class);
            } else {
                showToast(message, false);
            }

        }
        Logger.error(TAG, "" + stringResponse);
    }

    @Override
    public void onErrorResponse(ApiURLS.ApiId apiId, String errorData, int responseCode) {
        Logger.error(TAG, "" + errorData);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        toHideKeyboard();
        return false;
    }


    public class MainLayout extends LinearLayout {

        public MainLayout(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            inflater.inflate(R.layout.activity_otp_enter, this);
        }

        @Override
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            final int proposedHeight = MeasureSpec.getSize(heightMeasureSpec);
            final int actualHeight = getHeight();

            Logger.debug(TAG, "proposed: " + proposedHeight + ", actual: " + actualHeight);

            if (actualHeight >= proposedHeight) {
                // Keyboard is shown
                if (editDigitHidden.length() == 0)
                    setFocusedPinBackground(editDigit1);
                else {
                    // setDefaultPinBackground(editDigit1);
                }
            }
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }

}
