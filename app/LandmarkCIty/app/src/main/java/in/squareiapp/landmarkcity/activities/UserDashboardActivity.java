package in.squareiapp.landmarkcity.activities;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.HashMap;

import in.squareiapp.landmarkcity.R;
import in.squareiapp.landmarkcity.activities.featuresactivities.FriendsActivity;
import in.squareiapp.landmarkcity.activities.featuresactivities.GreetingsActivity;
import in.squareiapp.landmarkcity.activities.featuresactivities.MapActivity;
import in.squareiapp.landmarkcity.activities.featuresactivities.MusicActivity;
import in.squareiapp.landmarkcity.activities.featuresactivities.RewardsActivity;
import in.squareiapp.landmarkcity.activities.featuresactivities.SettingActivity;
import in.squareiapp.landmarkcity.activities.featuresactivities.ShareLocationActivity;
import in.squareiapp.landmarkcity.activities.featuresactivities.StoreActivity;
import in.squareiapp.landmarkcity.activities.featuresactivities.UserChatActivity;
import in.squareiapp.landmarkcity.activities.featuresactivities.UserProfileActivity;
import in.squareiapp.landmarkcity.adapters.ViewPagerAdapter;
import in.squareiapp.landmarkcity.fragments.HomeFragment;
import in.squareiapp.landmarkcity.fragments.NewsFragment;
import in.squareiapp.landmarkcity.fragments.NoticeFragment;
import in.squareiapp.landmarkcity.fragments.UpdatesFragment;
import in.squareiapp.landmarkcity.interfaces.NetworkResponseListener;
import in.squareiapp.landmarkcity.utils.ApiURLS;
import in.squareiapp.landmarkcity.utils.CommonUtils;
import in.squareiapp.landmarkcity.utils.GPSTracker;
import in.squareiapp.landmarkcity.utils.JsonParser;
import in.squareiapp.landmarkcity.utils.Logger;
import in.squareiapp.landmarkcity.utils.NetworkRequestHandler;
import in.squareiapp.landmarkcity.utils.SharedPrefUtils;

public class UserDashboardActivity extends BaseActivity implements TabLayout.OnTabSelectedListener, NetworkResponseListener {
    private NavigationView nav_view;
    private RelativeLayout fragmentContainer;
    private TabLayout tabs;
    private ViewPager viewpager;
    private DrawerLayout drawer_layout;
    private TextView toolbarTitle;
    private ImageView ivSOS;
    private GPSTracker gps;
    private final String TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_dashboard);
        startMyACtivtiy();
    }

    private String getStringLocation() {
        gps = new GPSTracker(UserDashboardActivity.this);

        // check if GPS enabled
        if (gps.canGetLocation()) {

            double latitude = gps.getLatitude();
            double longitude = gps.getLongitude();

            // \n is for new line
            Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
            return "" + latitude + "," + longitude;
        } else {
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings
            gps.showSettingsAlert();
            return "";
        }
    }

    @Override
    protected void initContext() {
        context = UserDashboardActivity.this;
        currentActivity = UserDashboardActivity.this;
    }

    @Override
    protected void initViews() {
        drawer_layout = (DrawerLayout) findViewById(R.id.drawer_layout);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(false);
        nav_view = (NavigationView) findViewById(R.id.nav_view);
        fragmentContainer = (RelativeLayout) findViewById(R.id.fragmentContainer);
        tabs = (TabLayout) findViewById(R.id.tabs);
        viewpager = (ViewPager) findViewById(R.id.viewpager);
        toolbarTitle = (TextView) findViewById(R.id.toolbarTitle);
        ivSOS = (ImageView) findViewById(R.id.ivSOS);
        tabs.setupWithViewPager(viewpager);
        setupViewPager();
        createTabIcons();
        tabs.addOnTabSelectedListener(this);
    }

    @Override
    protected void initListners() {
        nav_view.setItemIconTintList(null);
        nav_view.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer_layout.addDrawerListener(toggle);
        //toolbar.setNavigationIcon(R.drawable.menu);
        toggle.setDrawerIndicatorEnabled(false);
        toggle.setHomeAsUpIndicator(R.drawable.ic_menu_hover_svg);
        toggle.syncState();
        toggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
                    drawer_layout.closeDrawer(GravityCompat.START);
                } else {
                    drawer_layout.openDrawer(GravityCompat.START);
                }
            }
        });
        ivSOS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });
    }

    @Override
    protected void initToolbar() {
        // setTitle(R.string.app_name);
        toolbarTitle.setText(R.string.app_name);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_profile:
                drawer_layout.closeDrawer(nav_view);
                startNewActivity(currentActivity, UserProfileActivity.class);
                break;
            case R.id.nav_friend:
                drawer_layout.closeDrawer(nav_view);
                startNewActivity(currentActivity, FriendsActivity.class);
                break;
            case R.id.nav_chat:
                drawer_layout.closeDrawer(nav_view);
                startNewActivity(currentActivity, UserChatActivity.class);
                break;
            case R.id.nav_music:
                drawer_layout.closeDrawer(nav_view);
                startNewActivity(currentActivity, MusicActivity.class);
                break;
            case R.id.nav_map:
                drawer_layout.closeDrawer(nav_view);
                startNewActivity(currentActivity, MapActivity.class);
                break;
            case R.id.nav_rewards:
                drawer_layout.closeDrawer(nav_view);
                startNewActivity(currentActivity, RewardsActivity.class);
                break;
            case R.id.nav_store:
                drawer_layout.closeDrawer(nav_view);
                startNewActivity(currentActivity, StoreActivity.class);
                break;
            case R.id.nav_greetings:
                drawer_layout.closeDrawer(nav_view);
                startNewActivity(currentActivity, GreetingsActivity.class);
                break;
            case R.id.nav_share_location:
                drawer_layout.closeDrawer(nav_view);
                startNewActivity(currentActivity, ShareLocationActivity.class);
                break;
            case R.id.nav_setting:
                drawer_layout.closeDrawer(nav_view);
                startNewActivity(currentActivity, SettingActivity.class);
                break;
        }
        return true;
    }

    @Override
    public void onClick(View v) {
/*        toggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                } else {
                    drawer.openDrawer(GravityCompat.START);
                }
            }
        });*/
    }


    private void setupViewPager() {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new HomeFragment(), "HOME");
        adapter.addFragment(new NewsFragment(), "NEWS");
        adapter.addFragment(new UpdatesFragment(), "UPDATES");
        adapter.addFragment(new NoticeFragment(), "NOTICE");
        viewpager.setAdapter(adapter);
    }

    private void createTabIcons() {

        TextView tabOne = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabOne.setText("Home");
        tabOne.setTextColor(getResources().getColor(R.color.yellow));
        tabOne.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_home_hover_svg, 0, 0);
        tabs.getTabAt(0).setCustomView(tabOne);

        TextView tabTwo = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabTwo.setText("News");
        tabTwo.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_news_svg, 0, 0);
        tabs.getTabAt(1).setCustomView(tabTwo);

        TextView tabThree = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabThree.setText("Updates");
        tabThree.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_update_svg, 0, 0);
        tabs.getTabAt(2).setCustomView(tabThree);

        TextView tabFour = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabFour.setText("Notice");
        tabFour.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_notice_svg, 0, 0);
        tabs.getTabAt(3).setCustomView(tabFour);
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        View view;
        TextView tv;
        switch (tab.getPosition()) {
            case 0:
                view = tabs.getTabAt(tab.getPosition()).getCustomView();
                tv = (TextView) view.findViewById(R.id.tabText);
                tv.setTextColor(getResources().getColor(R.color.yellow));
                tv.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_home_hover_svg, 0, 0);
                break;
            case 1:
                view = tabs.getTabAt(tab.getPosition()).getCustomView();
                tv = (TextView) view.findViewById(R.id.tabText);
                tv.setTextColor(getResources().getColor(R.color.yellow));
                tv.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_news_hover_svg, 0, 0);
                break;
            case 2:
                view = tabs.getTabAt(tab.getPosition()).getCustomView();
                tv = (TextView) view.findViewById(R.id.tabText);
                tv.setTextColor(getResources().getColor(R.color.yellow));
                tv.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_update_hover_svg, 0, 0);
                break;
            case 3:
                view = tabs.getTabAt(tab.getPosition()).getCustomView();
                tv = (TextView) view.findViewById(R.id.tabText);
                tv.setTextColor(getResources().getColor(R.color.yellow));
                tv.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_notice_hover_svg, 0, 0);
                break;
        }
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {
        View view;
        TextView tv;
        switch (tab.getPosition()) {
            case 0:
                view = tabs.getTabAt(tab.getPosition()).getCustomView();
                tv = (TextView) view.findViewById(R.id.tabText);
                tv.setTextColor(getResources().getColor(R.color.white));
                tv.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_home_svg, 0, 0);
                break;
            case 1:
                view = tabs.getTabAt(tab.getPosition()).getCustomView();
                tv = (TextView) view.findViewById(R.id.tabText);
                tv.setTextColor(getResources().getColor(R.color.white));
                tv.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_news_svg, 0, 0);
                break;
            case 2:
                view = tabs.getTabAt(tab.getPosition()).getCustomView();
                tv = (TextView) view.findViewById(R.id.tabText);
                tv.setTextColor(getResources().getColor(R.color.white));
                tv.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_update_svg, 0, 0);
                break;
            case 3:
                view = tabs.getTabAt(tab.getPosition()).getCustomView();
                tv = (TextView) view.findViewById(R.id.tabText);
                tv.setTextColor(getResources().getColor(R.color.white));
                tv.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_notice_svg, 0, 0);
                break;
        }
    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {
        switch (tab.getPosition()) {
            case 0:
                break;
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
        }
    }

    public void showDialog() {
        final Dialog dialog = new Dialog(currentActivity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.test);

        TextView tvHealth = (TextView) dialog.findViewById(R.id.tvHealth);
        TextView tvSecurity = (TextView) dialog.findViewById(R.id.tvSecurity);

        tvHealth.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + "123456789"));//change the number
                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                startActivity(callIntent);
                dialog.dismiss();
                String location = getStringLocation();
                if (!location.equals(""))
                    continueToSos("0", location);
            }
        });
        tvSecurity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + "123456789"));//change the number
                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                startActivity(callIntent);
                dialog.dismiss();
                String location = getStringLocation();
                if (!location.equals(""))
                    continueToSos("0", location);
            }
        });
        dialog.show();
    }


    private void continueToSos(String type, String location) {
        if (CommonUtils.isNetworkAvailable(context)) {

            HashMap<String, String> hm = new HashMap<>();

            hm.put("type", type);
            hm.put("location", location);
            hm.put("client_id", SharedPrefUtils.getInstance(context).getString(SharedPrefUtils.CLIENT_ID));

            NetworkRequestHandler.getInstance(context, this).getStringResponse(ApiURLS.SEND_SOS, ApiURLS.ApiId.SEND_SOS, ApiURLS.REQUEST_POST, hm, null, true);
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
        if (apiId == ApiURLS.ApiId.SEND_SOS) {
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
