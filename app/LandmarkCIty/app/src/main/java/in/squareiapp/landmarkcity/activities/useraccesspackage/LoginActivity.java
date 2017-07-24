package in.squareiapp.landmarkcity.activities.useraccesspackage;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import in.squareiapp.landmarkcity.R;
import in.squareiapp.landmarkcity.activities.BaseActivity;

public class LoginActivity extends BaseActivity {
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
                startActivity(new Intent(context, UserRegisterActivity.class));
                break;
        }
    }
}
