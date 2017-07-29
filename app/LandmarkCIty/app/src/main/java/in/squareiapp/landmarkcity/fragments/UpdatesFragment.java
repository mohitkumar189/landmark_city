package in.squareiapp.landmarkcity.fragments;

import android.os.Bundle;
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
import in.squareiapp.landmarkcity.adapters.UpdatesAdapter;
import in.squareiapp.landmarkcity.interfaces.CustomItemClickListener;
import in.squareiapp.landmarkcity.interfaces.NetworkResponseListener;
import in.squareiapp.landmarkcity.models.UpdatesData;
import in.squareiapp.landmarkcity.utils.ApiURLS;
import in.squareiapp.landmarkcity.utils.JsonParser;
import in.squareiapp.landmarkcity.utils.Logger;
import in.squareiapp.landmarkcity.utils.NetworkRequestHandler2;
import in.squareiapp.landmarkcity.utils.SharedPrefUtils;


public class UpdatesFragment extends BaseFragment implements NetworkResponseListener, CustomItemClickListener {
    private final String TAG = getClass().getSimpleName();
    private RecyclerView recycler_view;
    private List<UpdatesData> updatesData;
    private UpdatesAdapter updatesAdapter;

    public UpdatesFragment() {
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
        updatesData = new ArrayList<>();
        getNewsData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_updates, container, false);
    }

    @Override
    public void onClick(View v) {

    }

    private void getNewsData() {
        NetworkRequestHandler2 networkRequestHandler = new NetworkRequestHandler2(context, this);

        String client_id = SharedPrefUtils.getInstance(context).getString(SharedPrefUtils.CLIENT_ID);

        String url = ApiURLS.GET_UPDATES + "?client_id=" + client_id;
        //   NetworkRequestHandler.getInstance(context, this).getStringResponse(url, ApiURLS.ApiId.GET_NEWS, ApiURLS.REQUEST_GET, null, null, false);
        networkRequestHandler.getStringResponse(url, ApiURLS.ApiId.GET_UPDATES, ApiURLS.REQUEST_GET, null, null, false);

    }

    @Override
    public void onJsonResponse(ApiURLS.ApiId apiId, JSONObject jsonObjectResponse) {

    }

    @Override
    public void onStringResponse(ApiURLS.ApiId apiId, String stringResponse) {
        Logger.info(TAG, "on response::" + stringResponse);
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

    @Override
    public void onErrorResponse(ApiURLS.ApiId apiId, String errorData, int responseCode) {

    }

    private void setData(JSONArray jsonArray) {
        Logger.info(TAG, "=============setting data to update fragmnet");
        int length = jsonArray.length();
        for (int i = 0; i < length; i++) {
            try {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                UpdatesData userdata = new UpdatesData();
                userdata.setId(jsonObject.getString("id"));
                userdata.setTitle(jsonObject.getString("title"));
                userdata.setPostURL(jsonObject.getString("postURL"));
                userdata.setDescription(jsonObject.getString("description"));
                userdata.setPost_type(jsonObject.getString("post_type"));
                userdata.setPostedBy(jsonObject.getString("postedBy"));
                updatesData.add(userdata);
             //   Logger.info(TAG, "data added::" + updatesData);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        updatesAdapter = new UpdatesAdapter(this, updatesData, context);
        recycler_view.setAdapter(updatesAdapter);
    }

    @Override
    public void onItemClickCallback(int position, int flag) {

    }
}
