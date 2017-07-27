package in.squareiapp.landmarkcity.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import in.squareiapp.landmarkcity.R;
import in.squareiapp.landmarkcity.adapters.UsersPostsAdapter;
import in.squareiapp.landmarkcity.interfaces.CustomItemClickListener;
import in.squareiapp.landmarkcity.interfaces.NetworkResponseListener;
import in.squareiapp.landmarkcity.models.UsersPostsData;
import in.squareiapp.landmarkcity.utils.ApiURLS;
import in.squareiapp.landmarkcity.utils.JsonParser;
import in.squareiapp.landmarkcity.utils.Logger;
import in.squareiapp.landmarkcity.utils.NetworkRequestHandler;
import in.squareiapp.landmarkcity.utils.SharedPrefUtils;

/**
 * Created by mohit kumar on 7/26/2017.
 */
public class HomeFragment extends BaseFragment implements NetworkResponseListener, CustomItemClickListener {
    private final String TAG = getClass().getSimpleName();
    private RecyclerView recycler_view;
    private List<UsersPostsData> usersPostsData;
    private UsersPostsAdapter usersPostsAdapter;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    protected void initViews(View view) {
        recycler_view = (RecyclerView) view.findViewById(R.id.recycler_view);
        recycler_view.setLayoutManager(new LinearLayoutManager(context));

    }

    @Override
    protected void initListners() {

    }

    @Override
    protected void startFromHere() {
        usersPostsData = new ArrayList<>();
        getPostsData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onClick(View v) {

    }

    private void getPostsData() {
        String client_id = SharedPrefUtils.getInstance(context).getString(SharedPrefUtils.CLIENT_ID);

        String url = ApiURLS.USERS_POST + "?client_id=" + client_id;
        NetworkRequestHandler.getInstance(context, this).getStringResponse(url, ApiURLS.ApiId.USERS_POST, ApiURLS.REQUEST_GET, null, null, true);
    }

    @Override
    public void onJsonResponse(ApiURLS.ApiId apiId, JSONObject jsonObjectResponse) {

    }

    private int likePosition = -1;

    @Override
    public void onStringResponse(ApiURLS.ApiId apiId, String stringResponse) {
        Logger.error(TAG, "" + stringResponse);

        if (apiId == ApiURLS.ApiId.USERS_POST) {
            JsonParser jsonParser = new JsonParser(stringResponse);
            int success = jsonParser.getSuccess();
            //  int error = jsonParser.getError();
            String message = jsonParser.getMessage();
            if (success == 1) {
                JSONArray jsonArray = jsonParser.getArrayData();
                setData(jsonArray);
            } else {
                showToast(context, message);
            }
        } else if (apiId == ApiURLS.ApiId.POST_LIKE) {
            JsonParser jsonParser = new JsonParser(stringResponse);
            int success = jsonParser.getSuccess();
            //  int error = jsonParser.getError();
            String message = jsonParser.getMessage();
            if (success == 1) {
                if (likePosition >= 0) {
                    usersPostsData.get(likePosition).setLiked(1);
                    usersPostsAdapter.notifyDataSetChanged();
                }
                // JSONArray jsonArray = jsonParser.getArrayData();
                // setData(jsonArray);
                showToast(context, message);
            } else {
                showToast(context, message);
            }
        }
    }

    @Override
    public void onErrorResponse(ApiURLS.ApiId apiId, String errorData, int responseCode) {
        Logger.error(TAG, "" + errorData);
    }

    private void setData(JSONArray jsonArray) {
        Logger.info(TAG, "=============setting data to home fragmnet");
        int length = jsonArray.length();
        for (int i = 0; i < length; i++) {
            try {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                UsersPostsData userdata = new UsersPostsData();
                userdata.setId(jsonObject.getString("id"));
                userdata.setTitle(jsonObject.getString("title"));
                userdata.setPostURL(jsonObject.getString("postURL"));
                userdata.setDescription(jsonObject.getString("description"));
                userdata.setPost_type(jsonObject.getString("post_type"));
                userdata.setUser_id(jsonObject.getString("user_id"));
                userdata.setPosted_on(jsonObject.getString("posted_on"));
                userdata.setLikes(jsonObject.getString("likes"));
                userdata.setComments(jsonObject.getString("comments"));
                userdata.setUpdated_on(jsonObject.getString("updated_on"));
                userdata.setPostedBy(jsonObject.getString("postedBy"));
                userdata.setProfilepic(jsonObject.getString("profilepic"));
                userdata.setCommented(jsonObject.getInt("commented"));
                userdata.setLiked(jsonObject.getInt("liked"));

                usersPostsData.add(userdata);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        usersPostsAdapter = new UsersPostsAdapter(this, usersPostsData, context);
        recycler_view.setAdapter(usersPostsAdapter);
    }

    @Override
    public void onItemClickCallback(int position, int flag) {
        Logger.info(TAG, "clicked on:: " + position + " flag is:: " + flag);
        switch (flag) {
            case 1:
                break;
            case 2:
                likePosition = position;
                continuePostLike(position);

                break;
            case 3:
                break;
            case 4:
                break;
        }
    }

    private void continuePostLike(int position) {
        String client_id = SharedPrefUtils.getInstance(context).getString(SharedPrefUtils.CLIENT_ID);

        HashMap<String, String> m = new HashMap<>();
        m.put("postid", usersPostsData.get(position).getId());
        m.put("client_id", client_id);

        NetworkRequestHandler.getInstance(context, this).getStringResponse(ApiURLS.POST_LIKE, ApiURLS.ApiId.POST_LIKE, ApiURLS.REQUEST_POST, m, null, false);
    }
}
