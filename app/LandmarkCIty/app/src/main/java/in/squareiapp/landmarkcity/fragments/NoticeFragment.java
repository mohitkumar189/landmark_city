package in.squareiapp.landmarkcity.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import in.squareiapp.landmarkcity.R;
import in.squareiapp.landmarkcity.adapters.UsersNoticeAdapter;
import in.squareiapp.landmarkcity.interfaces.CustomItemClickListener;
import in.squareiapp.landmarkcity.interfaces.NetworkResponseListener;
import in.squareiapp.landmarkcity.models.UsersPostsData;
import in.squareiapp.landmarkcity.utils.ApiURLS;
import in.squareiapp.landmarkcity.utils.JsonParser;
import in.squareiapp.landmarkcity.utils.NetworkRequestHandler;
import in.squareiapp.landmarkcity.utils.SharedPrefUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class NoticeFragment extends BaseFragment implements NetworkResponseListener, CustomItemClickListener {

    private final String TAG = getClass().getSimpleName();
    private RecyclerView recycler_view;
    private List<UsersPostsData> usersPostsData;
    private UsersNoticeAdapter usersPostsAdapter;

    public NoticeFragment() {
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
        getNoticeData();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_notice, container, false);
    }

    @Override
    public void onClick(View v) {

    }

    private void getNoticeData() {
        String client_id = SharedPrefUtils.getInstance(context).getString(SharedPrefUtils.CLIENT_ID);

        String url = ApiURLS.GET_NOTICE + "?client_id=" + client_id;
        NetworkRequestHandler.getInstance(context, this).getStringResponse(url, ApiURLS.ApiId.GET_NOTICE, ApiURLS.REQUEST_GET, null, null, true);

    }

    @Override
    public void onJsonResponse(ApiURLS.ApiId apiId, JSONObject jsonObjectResponse) {

    }

    @Override
    public void onStringResponse(ApiURLS.ApiId apiId, String stringResponse) {
        if (apiId == ApiURLS.ApiId.GET_NOTICE) {
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
        }
    }

    private void setData(JSONArray jsonArray) {
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
               /* userdata.setCommented(jsonObject.getInt("commented"));
                userdata.setLiked(jsonObject.getInt("liked"));*/
                usersPostsData.add(userdata);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        usersPostsAdapter = new UsersNoticeAdapter(this, usersPostsData, context);
        recycler_view.setAdapter(usersPostsAdapter);
    }

    @Override
    public void onErrorResponse(ApiURLS.ApiId apiId, String errorData, int responseCode) {

    }

    @Override
    public void onItemClickCallback(int position, int flag) {
        switch (flag) {
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                break;
            case 5:
                break;
        }
    }
}
