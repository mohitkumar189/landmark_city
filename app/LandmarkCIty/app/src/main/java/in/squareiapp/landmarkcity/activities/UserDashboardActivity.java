package in.squareiapp.landmarkcity.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import in.squareiapp.landmarkcity.R;
import in.squareiapp.landmarkcity.activities.featuresactivities.FriendsActivity;
import in.squareiapp.landmarkcity.activities.featuresactivities.GreetingsActivity;
import in.squareiapp.landmarkcity.activities.featuresactivities.MapActivity;
import in.squareiapp.landmarkcity.activities.featuresactivities.MusicActivity;
import in.squareiapp.landmarkcity.activities.featuresactivities.RewardsActivity;
import in.squareiapp.landmarkcity.activities.featuresactivities.SettingActivity;
import in.squareiapp.landmarkcity.activities.featuresactivities.ShareLocationActivity;
import in.squareiapp.landmarkcity.activities.featuresactivities.StoreActivity;
import in.squareiapp.landmarkcity.activities.featuresactivities.UserProfileActivity;
import in.squareiapp.landmarkcity.adapters.ViewPagerAdapter;
import in.squareiapp.landmarkcity.fragments.HomeFragment;
import in.squareiapp.landmarkcity.fragments.NewsFragment;
import in.squareiapp.landmarkcity.fragments.NoticeFragment;
import in.squareiapp.landmarkcity.fragments.UpdatesFragment;

public class UserDashboardActivity extends BaseActivity implements TabLayout.OnTabSelectedListener {
    private NavigationView nav_view;
    private RelativeLayout fragmentContainer;
    private TabLayout tabs;
    private ViewPager viewpager;
    private DrawerLayout drawer_layout;
    private TextView toolbarTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_dashboard);
        startMyACtivtiy();
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
                startNewActivity(currentActivity, UserProfileActivity.class);
                break;
            case R.id.nav_chat:
                drawer_layout.closeDrawer(nav_view);
                startNewActivity(currentActivity, FriendsActivity.class);
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
}
