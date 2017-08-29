package in.squareiapp.landmarkcity.activities.featuresactivities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.HashMap;

import in.squareiapp.landmarkcity.R;
import in.squareiapp.landmarkcity.activities.BaseActivity;
import in.squareiapp.landmarkcity.activities.UserDashboardActivity;
import in.squareiapp.landmarkcity.interfaces.CustomItemClickListener;
import in.squareiapp.landmarkcity.interfaces.NetworkResponseListener;
import in.squareiapp.landmarkcity.utils.ApiURLS;
import in.squareiapp.landmarkcity.utils.AppConstants;
import in.squareiapp.landmarkcity.utils.CommonUtils;
import in.squareiapp.landmarkcity.utils.Logger;
import in.squareiapp.landmarkcity.utils.NetworkRequestHandler;
import in.squareiapp.landmarkcity.utils.SharedPrefUtils;

import static in.squareiapp.landmarkcity.R.id.btnSend;


public class SendGreetingActivity extends BaseActivity implements NetworkResponseListener, CustomItemClickListener {
    private final String TAG = getClass().getSimpleName();
    private int pos = -1;
    private String imageUrl;
    private ImageView imageUrlView;
    private Button sendGreetingButton;
    private EditText editMessage1;
    private String FriendsName;
    private Button btnSendGreetings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_greeting_attach);
        startMyACtivtiy();

        if (getIntent().getStringExtra("greetingImage").length() > 5) {

            imageUrl = getIntent().getStringExtra(AppConstants.GREETING_IMAGE);
        }
    }

    @Override
    protected void initContext() {
        context = SendGreetingActivity.this;
        currentActivity = SendGreetingActivity.this;

    }

    @Override
    protected void initViews() {
        imageUrlView = (ImageView) findViewById(R.id.imageView);
        sendGreetingButton = (Button) findViewById(btnSend);
        editMessage1 = (EditText) findViewById(R.id.editMessage);

        if (getIntent().getExtras() != null) {
            FriendsName = getIntent().getStringExtra("FriendsName");
            Picasso.with(context).load(getIntent().getStringExtra(AppConstants.GREETING_IMAGE)).fit().into(imageUrlView);
        }
    }

    @Override
    protected void initListners() {
        sendGreetingButton.setOnClickListener(this);
    }

    @Override
    protected void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setToolBar(toolbar);
        setToolbarTitle("Send Greeting to " + FriendsName);
        showHomeButton();
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setToolBar(toolbar);
        setToolbarTitle("Send Greeting to " + FriendsName);
        showHomeButton();
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             onBackPressed();
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

            case R.id.btnSend:
                sendGreetings();
                break;
        }
    }

    private void sendGreetings() {

            if (CommonUtils.isNetworkAvailable(context)) {
                HashMap<String, String> hm = new HashMap<>();
                hm.put("msg", editMessage1.getText().toString() );
                hm.put("url", imageUrl);
                hm.put("title", getString(R.string.greetings_title_text) );
                hm.put("fid",getIntent().getStringExtra(AppConstants.FRIEND_ID) );
                hm.put(AppConstants.CLIENT_ID, SharedPrefUtils.getInstance(context).getString(SharedPrefUtils.CLIENT_ID));

                NetworkRequestHandler.getInstance(context, this).getStringResponse(ApiURLS.SEND_GREETINGS, ApiURLS.ApiId.SEND_GREETINGS, ApiURLS.REQUEST_POST, hm, null, true);
            } else {
                showToast(getString(R.string.network_error), false);
            }
    }

    @Override
    public void onJsonResponse(ApiURLS.ApiId apiId, JSONObject jsonObjectResponse) {

    }

    @Override
    public void onStringResponse(ApiURLS.ApiId apiId, String stringResponse) {
      Logger.info(TAG, "on response::" + stringResponse);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //Do something after 100ms
                Toast.makeText(context, R.string.greetings_sent_msg, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent( currentActivity, UserDashboardActivity.class);
                startActivity(intent);
                finish();
            }
        }, 2000);



    }

    @Override
    public void onErrorResponse(ApiURLS.ApiId apiId, String errorData, int responseCode) {
        Toast.makeText(this, errorData, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onItemClickCallback(int position, int flag) {

    }
}
