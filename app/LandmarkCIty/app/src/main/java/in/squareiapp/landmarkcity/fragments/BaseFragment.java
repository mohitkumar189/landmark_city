package in.squareiapp.landmarkcity.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Toast;

import in.squareiapp.landmarkcity.utils.Logger;

/**
 * Created by mohit kumar on 7/26/2017.
 */

public abstract class BaseFragment extends Fragment implements View.OnClickListener {
    private final String TAG = getClass().getSimpleName();
    public ProgressDialog pdialog;
    public Activity currentActivity;
    public Context context;
    View view;
    Bundle bundle;

    public BaseFragment() {
        // Required empty public constructor
    }

    protected abstract void initViews(View view);

    protected abstract void initListners();

    protected abstract void startFromHere();


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        currentActivity = getActivity();
        context = getActivity();
        initListners();
        startFromHere();
        Logger.info(TAG, "creating fragmnet for :: " + context);
    }

    public void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public void progressDialog(Context context, String title, String message, boolean cancelable, boolean isTitle) {
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
        pdialog.cancel();
    }
}
