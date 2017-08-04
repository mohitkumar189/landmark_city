package in.squareiapp.landmarkcity.activities;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import in.squareiapp.landmarkcity.R;
import in.squareiapp.landmarkcity.interfaces.AppConstants;
import in.squareiapp.landmarkcity.utils.DimensionUtils;
import in.squareiapp.landmarkcity.utils.Logger;

public abstract class BaseActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private static String TAG = "BaseActivity";
    public Activity currentActivity;
    public Bundle bundle;
    public Context context;
    public ProgressDialog pdialog;
    public Dialog customDialog;
    public Toolbar toolbar;
    private final String FONT = "fonts/avanti_regular.ttf";
    public Typeface myTypeface;

    private String[] PERMISSIONS = {Manifest.permission.ACCESS_NETWORK_STATE, Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.CALL_PHONE};
    private int PERMISSION_ALL = 1;

    protected abstract void initContext();

    protected abstract void initViews();

    protected abstract void initListners();

    protected abstract void initToolbar();

    public void startMyACtivtiy() {
        initContext();
        initViews();
        initListners();
        initToolbar();
        Logger.info(TAG, "Activity is initialized for context :: " + context);

     //   myTypeface = Typeface.createFromAsset(getAssets(), "fonts/avanti_regular.ttf");
    }

    protected void toHideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public void showHomeButton() {
        Logger.info(TAG, "showing home button");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        //    getSupportActionBar().setHomeAsUpIndicator(R.drawable.back_btn);
    }

    public void setToolbarTitle(String title) {
        Logger.info(TAG, "setting home title");
        getSupportActionBar().setTitle(title);
    }

    public void removeHomeButton() {
        Logger.info(TAG, "removing home button");
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(false);
    }

    public void setToolBar(Toolbar t) {
        setSupportActionBar(t);
    }

    public void showToast(String text, boolean isLengthLong) {
        Logger.info(TAG, "showing toast");
        int time;
        if (isLengthLong) {
            time = Toast.LENGTH_LONG;
        } else {
            time = Toast.LENGTH_SHORT;
        }
        Toast.makeText(context, text, time).show();
    }

    public void testingToast(String text, boolean isLengthLong) {
        Logger.info(TAG, "showing testing toast");
        int time;
        if (isLengthLong) {
            time = Toast.LENGTH_LONG;
        } else {
            time = Toast.LENGTH_SHORT;
        }
        Toast.makeText(currentActivity, text, time).show();
    }

    public void switchFragment(int containerId, Fragment fragment, boolean addToBackStack, boolean addOrReplace, String tag) {
        Logger.info(TAG, "switching fragment");
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        if (!addOrReplace) {
            ft.replace(containerId, fragment, tag);
        } else {
            ft.add(containerId, fragment, tag);
        }
        if (addToBackStack) {
            ft.addToBackStack(tag);
        }
        ft.commit();
    }

    public void popFragment(String tag) {
        Logger.info(TAG, "pop up fragment");
        if (getSupportFragmentManager().getBackStackEntryCount() >= 0) {
            getSupportFragmentManager().popBackStack(tag, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
    }

    public void startNewActivity(Activity activity, Class newclass) {
        Logger.info(TAG, "starting new activity ::" + newclass);
        Intent intent = new Intent(activity, newclass);
        startActivity(intent);
    }

    public void startNewActivity(Activity activity, Class newclass, Bundle bundle, boolean isResult, int requestCode, boolean animationRequired, int animationType) {
        Logger.info(TAG, "starting new activity ::" + newclass);
        Intent intent = new Intent(activity, newclass);
        if (bundle != null)

            intent.putExtras(bundle);
        if (!isResult && !animationRequired)
            startActivity(intent);
        else if (!isResult && animationRequired) {
            startActivity(intent);
            if (animationType == AppConstants.ANIMATION_SLIDE_LEFT) {
                overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_left);
            } else if (animationType == AppConstants.ANIMATION_SLIDE_UP) {
                overridePendingTransition(R.anim.anim_slide_up, R.anim.anim_stopping_exiting_activity);
            }

        } else if (isResult && animationRequired) {
            startActivityForResult(intent, requestCode);
            if (animationType == AppConstants.ANIMATION_SLIDE_LEFT) {
                overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_left);
            } else if (animationType == AppConstants.ANIMATION_SLIDE_UP) {
                overridePendingTransition(R.anim.anim_slide_up, R.anim.anim_stopping_exiting_activity);
            }
        } else
            startActivityForResult(intent, requestCode);
    }

    public void showProgressDialog(Context context, String title, String message, boolean cancelable, boolean isTitle) {
        Logger.info(TAG, "showing progress dialog");
        if (pdialog == null) {
            pdialog = new ProgressDialog(context);
        }

        if (isTitle) {
            pdialog.setTitle(title);
        }

        pdialog.setMessage(message);

        if (!cancelable) {
            pdialog.setCancelable(false);
        }

        if (!pdialog.isShowing()) {
            pdialog.show();
        }
    }

    public void cancelProgressDialog() {
        Logger.info(TAG, "cancelling progress dialog");
        pdialog.cancel();
    }

    protected Dialog createCustomeDialog(Context context, boolean isCancelableBack, boolean isCancelableoutside, View view, int height, int width) {
        Logger.info(TAG, "creating custom dialog");
        customDialog = new Dialog(context, R.style.dialogTheme);
        //  dialog.setCancelable(isCancelableBack);
        if (view.getParent() != null) {
            ViewGroup viewGroup = (ViewGroup) view.getParent();
            viewGroup.removeView(view);
        }
        customDialog.setCanceledOnTouchOutside(isCancelableoutside);
        customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        customDialog.setContentView(view);
        WindowManager.LayoutParams wmlp = customDialog.getWindow().getAttributes();
        customDialog.getWindow().setLayout(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        wmlp.gravity = Gravity.CENTER;

        customDialog.show();

        customDialog.getWindow().setLayout(DimensionUtils.toPixels(context, width), DimensionUtils.toPixels(context, height));
        customDialog.getWindow().setAttributes(wmlp);
        return customDialog;

    }

    protected void cancelCustomDialog() {
        Logger.info(TAG, "cancelling custom dialog");
        if (customDialog != null) {
            customDialog.cancel();
        }
    }

    public void askForPermission() {
        Logger.info(TAG, "asking for permissions");
        if (!hasPermissions(this, PERMISSIONS)) {
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
        }
    }

    public void hideStatusBar() {
        Logger.info(TAG, "hiding status bar");
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); // hiding status bar
    }

    public static boolean hasPermissions(Context context, String... permissions) {
        Logger.info(TAG, "checking permissions");
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }
}
