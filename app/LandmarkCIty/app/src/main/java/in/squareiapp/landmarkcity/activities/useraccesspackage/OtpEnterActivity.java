package in.squareiapp.landmarkcity.activities.useraccesspackage;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import in.squareiapp.landmarkcity.R;
import in.squareiapp.landmarkcity.activities.BaseActivity;

public class OtpEnterActivity extends BaseActivity {

    private EditText editDigit1, editDigit2, editDigit3, editDigit4;
    private Button btnVerify;
    private TextView tvResend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_enter);
    }

    @Override
    protected void initContext() {
        context = OtpEnterActivity.this;
    }

    @Override
    protected void initViews() {
        editDigit1 = (EditText) findViewById(R.id.editDigit1);
        editDigit2 = (EditText) findViewById(R.id.editDigit2);
        editDigit3 = (EditText) findViewById(R.id.editDigit3);
        editDigit4 = (EditText) findViewById(R.id.editDigit4);
        btnVerify = (Button) findViewById(R.id.btnVerify);
        tvResend = (TextView) findViewById(R.id.tvResend);
    }

    @Override
    protected void initListners() {
        btnVerify.setOnClickListener(this);
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

    }
}
