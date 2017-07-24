package in.squareiapp.landmarkcity.activities.useraccesspackage;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import in.squareiapp.landmarkcity.R;
import in.squareiapp.landmarkcity.activities.BaseActivity;

public class UserRegisterActivity extends BaseActivity {
    private final String TAG = getClass().getSimpleName();

    private EditText editName, editUserName, editPassword, editUser;
    private ImageView imageSpinner, ivFacebook, ivGoogle, ivTwitter;
    private Button btnRegister;
    private Spinner spinnerUser;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register);
        startMyACtivtiy();
    }

    @Override
    protected void initContext() {
        context = UserRegisterActivity.this;
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void initListners() {

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

    }
}
