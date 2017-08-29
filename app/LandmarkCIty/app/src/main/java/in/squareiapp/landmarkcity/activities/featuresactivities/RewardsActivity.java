package in.squareiapp.landmarkcity.activities.featuresactivities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import in.squareiapp.landmarkcity.R;
import in.squareiapp.landmarkcity.activities.BaseActivity;
import in.squareiapp.landmarkcity.adapters.RewardsAdapter;
import in.squareiapp.landmarkcity.interfaces.CustomItemClickListener;
import in.squareiapp.landmarkcity.interfaces.NetworkResponseListener;
import in.squareiapp.landmarkcity.models.FriendsData;
import in.squareiapp.landmarkcity.models.RewardsPoints;
import in.squareiapp.landmarkcity.utils.ApiURLS;
import in.squareiapp.landmarkcity.utils.CommonUtils;
import in.squareiapp.landmarkcity.utils.JsonParser;
import in.squareiapp.landmarkcity.utils.Logger;
import in.squareiapp.landmarkcity.utils.NetworkRequestHandler;
import in.squareiapp.landmarkcity.utils.NetworkRequestHandler2;
import in.squareiapp.landmarkcity.utils.SharedPrefUtils;

public class RewardsActivity extends BaseActivity implements NetworkResponseListener, CustomItemClickListener {
    private final String TAG = getClass().getSimpleName();
    private ListView listView;
    private List<RewardsPoints> rewardsPointses;
    private RewardsAdapter rewardsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rewards);
        startMyACtivtiy();
        getRewardPoints();
    }

    @Override
    protected void initContext() {
        context = RewardsActivity.this;
        currentActivity = RewardsActivity.this;
        rewardsPointses = new ArrayList<>();

    }

    @Override
    protected void initViews() {
        listView = (ListView) findViewById(R.id.rewardsListView);
    }

    @Override
    protected void initListners() {

    }

    @Override
    protected void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setToolBar(toolbar);
        setToolbarTitle("Rewards Point");
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



    }

    private void getRewardPoints() {
        NetworkRequestHandler2 networkRequestHandler = new NetworkRequestHandler2(context, this);

        String client_id = SharedPrefUtils.getInstance(context).getString(SharedPrefUtils.CLIENT_ID);

        String url = ApiURLS.GET_REWARDS_POINTS + "?client_id=" + client_id;
        networkRequestHandler.getStringResponse(url, ApiURLS.ApiId.GET_REWARDS_POINTS, ApiURLS.REQUEST_GET, null, null, true);

    }


    @Override
    public void onJsonResponse(ApiURLS.ApiId apiId, JSONObject jsonObjectResponse) {

    }

    @Override
    public void onStringResponse(ApiURLS.ApiId apiId, String stringResponse) {
        Logger.info(TAG, "on response::" + stringResponse);
        if (apiId == ApiURLS.ApiId.GET_REWARDS_POINTS) {
            JsonParser jsonParser = new JsonParser(stringResponse);
            int success = jsonParser.getSuccess();
            ((TextView)findViewById(R.id.textViewPoints)).setText("Total Points " + jsonParser.getRewardsPoints() );
            String message = jsonParser.getMessage();
            if (success == 1) {
                JSONArray jsonArray = jsonParser.getArrayData();
                setFriendsData(jsonArray);
            } else {
                showToast(message, false);
            }
        }
    }

    @Override
    public void onErrorResponse(ApiURLS.ApiId apiId, String errorData, int responseCode) {

    }

    private void setFriendsData(JSONArray jsonArray) {
        Logger.info(TAG, "setting friends data::");
        int length = jsonArray.length();
        if (length > 0) {
            for (int i = 0; i < length; i++) {
                try {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    RewardsPoints rewardsPoints = new RewardsPoints();
                    rewardsPoints.setId(jsonObject.getInt("id"));
                    rewardsPoints.setBillAmount(jsonObject.getString("bill_amt"));
                    rewardsPoints.setDate(jsonObject.getString("created"));
                    rewardsPoints.setNote(jsonObject.getString("note"));
                    rewardsPoints.setPoint(jsonObject.getString("point"));
                   rewardsPointses.add(rewardsPoints);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            rewardsAdapter = new RewardsAdapter(rewardsPointses, context);
            listView.setAdapter(rewardsAdapter);
      //      recyclerFriends.setAdapter(friendsAdapter);
       //     friendsAdapter.notifyDataSetChanged();
        }
    }

    private void setSuggestionsData(JSONArray jsonArray) {
        int length = jsonArray.length();
        if (length > 0) {
            for (int i = 0; i < length; i++) {
                try {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    FriendsData friendsData = new FriendsData();
                    friendsData.setUserid(jsonObject.getString("userid"));
                    friendsData.setName(jsonObject.getString("name"));
                    friendsData.setProf_status(jsonObject.getString("prof_status"));
                    friendsData.setProfilePic(jsonObject.getString("profilePic"));
         //           suggestions.add(friendsData);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    @Override
    public void onItemClickCallback(int position, int flag) {

    }

    private void sendFriendRequest(String user) {
        if (CommonUtils.isNetworkAvailable(context)) {
            HashMap<String, String> hm = new HashMap<>();
            hm.put("fid", user);
            hm.put("client_id", SharedPrefUtils.getInstance(context).getString(SharedPrefUtils.CLIENT_ID));
            NetworkRequestHandler.getInstance(context, this).getStringResponse(ApiURLS.SEND_FRIEND_REQUEST, ApiURLS.ApiId.SEND_FRIEND_REQUEST, ApiURLS.REQUEST_POST, hm, null, true);
        } else {
            showToast(getString(R.string.network_error), false);
        }
    }
}
