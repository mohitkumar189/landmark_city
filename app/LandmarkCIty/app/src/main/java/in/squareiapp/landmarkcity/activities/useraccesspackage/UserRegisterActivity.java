package in.squareiapp.landmarkcity.activities.useraccesspackage;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import in.squareiapp.landmarkcity.R;
import in.squareiapp.landmarkcity.activities.BaseActivity;
import in.squareiapp.landmarkcity.interfaces.NetworkResponseListener;
import in.squareiapp.landmarkcity.utils.ApiURLS;
import in.squareiapp.landmarkcity.utils.CommonUtils;
import in.squareiapp.landmarkcity.utils.JsonParser;
import in.squareiapp.landmarkcity.utils.Logger;
import in.squareiapp.landmarkcity.utils.NetworkRequestHandler;

public class UserRegisterActivity extends BaseActivity implements AdapterView.OnItemClickListener, AdapterView.OnItemSelectedListener, NetworkResponseListener {
    private final String TAG = getClass().getSimpleName();

    private EditText editName, editUserName, editPassword, editUser;
    private ImageView imageSpinner, ivFacebook, ivGoogle, ivTwitter;
    private Button btnRegister;
    private Spinner spinnerUser;
    private Toolbar toolbar;
    private List<String> userTypes = new ArrayList<String>();
    private String userType = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register);
        startMyACtivtiy();
        initExtras();
    }

    private void initExtras() {
        userTypes.add("STUDENT");
        userTypes.add("PARENT");
        userTypes.add("VENDOR");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, userTypes);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // Dropdown Layout
        spinnerUser.setAdapter(dataAdapter);
    }

    @Override
    protected void initContext() {
        context = UserRegisterActivity.this;
        currentActivity = UserRegisterActivity.this;
    }

    @Override
    protected void initViews() {
        editName = (EditText) findViewById(R.id.editName);
        editUserName = (EditText) findViewById(R.id.editUserName);
        editPassword = (EditText) findViewById(R.id.editPassword);
        editUser = (EditText) findViewById(R.id.editUser);
        imageSpinner = (ImageView) findViewById(R.id.imageSpinner);
        //  imageSpinner.bringToFront();

        ivFacebook = (ImageView) findViewById(R.id.ivFacebook);
        ivGoogle = (ImageView) findViewById(R.id.ivGoogle);
        ivTwitter = (ImageView) findViewById(R.id.ivTwitter);

        btnRegister = (Button) findViewById(R.id.btnRegister);
     //   btnRegister.setTypeface(myTypeface);

        spinnerUser = (Spinner) findViewById(R.id.spinnerUser);
    }

    @Override
    protected void initListners() {
        btnRegister.setOnClickListener(this);
        ivFacebook.setOnClickListener(this);
        ivGoogle.setOnClickListener(this);
        ivTwitter.setOnClickListener(this);
        imageSpinner.setOnClickListener(this);
        spinnerUser.setOnItemSelectedListener(this);
    }

    @Override
    protected void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setToolBar(toolbar);
        setTitle("Registeration");
        showHomeButton();
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // startNewActivity(currentActivity, LoginActivity.class);
                finish();
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnRegister:
                isValidSignupDetails();
                break;
            case R.id.imageSpinner:
                Logger.info(TAG, "image clicked");
                spinnerUser.performClick();
                break;
            case R.id.ivFacebook:
                break;
            case R.id.ivGoogle:
                break;
            case R.id.ivTwitter:
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        editUser.setText(userTypes.get(position).toString());
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        editUser.setText(userTypes.get(position).toString());
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void isValidSignupDetails() {
        String usrName = editUserName.getText().toString();
        String password = editPassword.getText().toString();
        String first_name = editName.getText().toString();
        if (first_name.isEmpty()) {
            editName.setError(getString(R.string.error_first_name));
            editName.requestFocus();
            return;
        }
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

        continueSignup();
    }

    private void continueSignup() {
        String usrName = editUserName.getText().toString();
        String password = editPassword.getText().toString();
        String first_name = editName.getText().toString();
        if (CommonUtils.isNetworkAvailable(context)) {
            HashMap<String, String> hm = new HashMap<>();
            hm.put("name", editName.getText().toString().trim());
            hm.put("username", editUserName.getText().toString().trim());
            hm.put("password", editPassword.getText().toString().trim());
            hm.put("usertype", editUser.getText().toString().trim());

            NetworkRequestHandler.getInstance(context, this).getStringResponse(ApiURLS.USER_REGISTER_URL, ApiURLS.ApiId.USER_REGISTER_URL, ApiURLS.REQUEST_POST, hm, null, true);
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
                String apikey = jsonObject.getString("data");
                Bundle bundle = new Bundle();
                bundle.putString("apikey", apikey);
                Logger.info(TAG, "sent api key===::" + apikey);
                startNewActivity(currentActivity, OtpEnterActivity.class, bundle, false, 0, false, 0);
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
