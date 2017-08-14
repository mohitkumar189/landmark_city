package in.squareiapp.landmarkcity.activities.featuresactivities;

<<<<<<< HEAD
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import in.squareiapp.landmarkcity.R;

public class FriendsActivity extends AppCompatActivity {
=======
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import in.squareiapp.landmarkcity.R;
import in.squareiapp.landmarkcity.activities.BaseActivity;
import in.squareiapp.landmarkcity.adapters.FriendSuggestionAdapter;
import in.squareiapp.landmarkcity.adapters.FriendsAdapter;
import in.squareiapp.landmarkcity.interfaces.CustomItemClickListener;
import in.squareiapp.landmarkcity.interfaces.NetworkResponseListener;
import in.squareiapp.landmarkcity.models.FriendsData;
import in.squareiapp.landmarkcity.utils.ApiURLS;
import in.squareiapp.landmarkcity.utils.CommonUtils;
import in.squareiapp.landmarkcity.utils.JsonParser;
import in.squareiapp.landmarkcity.utils.Logger;
import in.squareiapp.landmarkcity.utils.NetworkRequestHandler;
import in.squareiapp.landmarkcity.utils.NetworkRequestHandler2;
import in.squareiapp.landmarkcity.utils.SharedPrefUtils;

public class FriendsActivity extends BaseActivity implements NetworkResponseListener, CustomItemClickListener {
    private final String TAG = getClass().getSimpleName();
    private RecyclerView recyclerFriends, recyclerSuggestions;
    private List<FriendsData> friends;
    private List<FriendsData> suggestions;
    private FriendsAdapter friendsAdapter;
    private FriendSuggestionAdapter suggestionAdapter;
    private int pos = -1;
>>>>>>> 62a698070e38b72a4dce0c565da48437d02b1377

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);
<<<<<<< HEAD
=======
        startMyACtivtiy();
        getFriendsList();
        getFriendsSuggestions();
    }

    @Override
    protected void initContext() {
        context = FriendsActivity.this;
        currentActivity = FriendsActivity.this;
        friends = new ArrayList<>();
        suggestions = new ArrayList<>();
        friendsAdapter = new FriendsAdapter(this, friends, context);
        suggestionAdapter = new FriendSuggestionAdapter(this, suggestions, context);
    }

    @Override
    protected void initViews() {
        recyclerFriends = (RecyclerView) findViewById(R.id.recyclerFriends);
        recyclerSuggestions = (RecyclerView) findViewById(R.id.recyclerSuggestions);
        recyclerFriends.setLayoutManager(new LinearLayoutManager(context));
        recyclerSuggestions.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
    }

    @Override
    protected void initListners() {

    }

    @Override
    protected void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setToolBar(toolbar);
        setToolbarTitle("Friends");
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

    private void getFriendsList() {
        NetworkRequestHandler2 networkRequestHandler = new NetworkRequestHandler2(context, this);

        String client_id = SharedPrefUtils.getInstance(context).getString(SharedPrefUtils.CLIENT_ID);

        String url = ApiURLS.GET_FRIENDS + "?client_id=" + client_id;
        networkRequestHandler.getStringResponse(url, ApiURLS.ApiId.GET_FRIENDS, ApiURLS.REQUEST_GET, null, null, true);

    }

    private void getFriendsSuggestions() {
        NetworkRequestHandler2 networkRequestHandler = new NetworkRequestHandler2(context, this);

        String client_id = SharedPrefUtils.getInstance(context).getString(SharedPrefUtils.CLIENT_ID);

        String url = ApiURLS.GET_FRIENDS_SUGGESTIONS + "?client_id=" + client_id;
        networkRequestHandler.getStringResponse(url, ApiURLS.ApiId.GET_FRIENDS_SUGGESTIONS, ApiURLS.REQUEST_GET, null, null, true);

    }

    @Override
    public void onJsonResponse(ApiURLS.ApiId apiId, JSONObject jsonObjectResponse) {

    }

    @Override
    public void onStringResponse(ApiURLS.ApiId apiId, String stringResponse) {
        Logger.info(TAG, "on response::" + stringResponse);
        if (apiId == ApiURLS.ApiId.GET_FRIENDS) {
            JsonParser jsonParser = new JsonParser(stringResponse);
            int success = jsonParser.getSuccess();
            //  int error = jsonParser.getError();
            String message = jsonParser.getMessage();
            if (success == 1) {
                JSONArray jsonArray = jsonParser.getArrayData();
                setFriendsData(jsonArray);
            } else {
                showToast(message, false);
            }
        } else if (apiId == ApiURLS.ApiId.GET_FRIENDS_SUGGESTIONS) {
            JsonParser jsonParser = new JsonParser(stringResponse);
            int success = jsonParser.getSuccess();
            //  int error = jsonParser.getError();
            String message = jsonParser.getMessage();
            if (success == 1) {
                JSONArray jsonArray = jsonParser.getArrayData();
                setSuggestionsData(jsonArray);

            } else {
                showToast(message, false);
            }
        } else if (apiId == ApiURLS.ApiId.SEND_FRIEND_REQUEST) {
            JsonParser jsonParser = new JsonParser(stringResponse);
            int success = jsonParser.getSuccess();
            //  int error = jsonParser.getError();
            String message = jsonParser.getMessage();
            if (success == 1) {
                showToast(message, false);
                suggestions.remove(pos);
                suggestionAdapter.notifyDataSetChanged();
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
                    FriendsData friendsData = new FriendsData();
                    friendsData.setUserid(jsonObject.getString("userid"));
                    friendsData.setName(jsonObject.getString("name"));
                    friendsData.setProf_status(jsonObject.getString("prof_status"));
                    friendsData.setProfilePic(jsonObject.getString("profilePic"));
                    friends.add(friendsData);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            recyclerFriends.setAdapter(friendsAdapter);
            friendsAdapter.notifyDataSetChanged();
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
                    suggestions.add(friendsData);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            recyclerSuggestions.setAdapter(suggestionAdapter);
            suggestionAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onItemClickCallback(int position, int flag) {
        Intent intent = new Intent(currentActivity, ProfileActivity.class);
        switch (flag) {
            case 1:
                intent.putExtra("clientid", friends.get(position).getUserid());
                startActivity(intent);
                break;
            case 10:
                intent.putExtra("clientid", suggestions.get(position).getUserid());
                startActivity(intent);
                break;
            case 20:
                sendFriendRequest(suggestions.get(position).getUserid());
                pos = position;
                break;
            case 30:
                // sendFriendRequest(suggestions.get(position).getUserid());
                pos = position;
                suggestions.remove(position);
                suggestionAdapter.notifyDataSetChanged();
                break;
        }
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
>>>>>>> 62a698070e38b72a4dce0c565da48437d02b1377
    }
}
