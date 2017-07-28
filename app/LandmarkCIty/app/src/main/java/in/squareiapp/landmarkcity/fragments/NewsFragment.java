package in.squareiapp.landmarkcity.fragments;


import android.content.Context;
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
import in.squareiapp.landmarkcity.adapters.NewsAdapter;
import in.squareiapp.landmarkcity.interfaces.CustomItemClickListener;
import in.squareiapp.landmarkcity.interfaces.NetworkResponseListener;
import in.squareiapp.landmarkcity.models.NewsData;
import in.squareiapp.landmarkcity.utils.ApiURLS;
import in.squareiapp.landmarkcity.utils.JsonParser;
import in.squareiapp.landmarkcity.utils.NetworkRequestHandler2;
import in.squareiapp.landmarkcity.utils.SharedPrefUtils;


public class NewsFragment extends BaseFragment implements NetworkResponseListener, CustomItemClickListener {
    private final String TAG = getClass().getSimpleName();
    private RecyclerView recycler_view;
    private List<NewsData> newsDataList;
    private NewsAdapter newsAdapter;

    public NewsFragment() {
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
        newsDataList = new ArrayList<>();
        getNewsData();
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_news, container, false);
    }

    private void getNewsData() {
        NetworkRequestHandler2 networkRequestHandler = new NetworkRequestHandler2(context, this);

        String client_id = SharedPrefUtils.getInstance(context).getString(SharedPrefUtils.CLIENT_ID);

        String url = ApiURLS.GET_NEWS + "?client_id=" + client_id;
        //   NetworkRequestHandler.getInstance(context, this).getStringResponse(url, ApiURLS.ApiId.GET_NEWS, ApiURLS.REQUEST_GET, null, null, false);
        networkRequestHandler.getStringResponse(url, ApiURLS.ApiId.GET_NEWS, ApiURLS.REQUEST_GET, null, null, false);

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onItemClickCallback(int position, int flag) {

    }

    @Override
    public void onJsonResponse(ApiURLS.ApiId apiId, JSONObject jsonObjectResponse) {

    }

    @Override
    public void onStringResponse(ApiURLS.ApiId apiId, String stringResponse) {
        if (apiId == ApiURLS.ApiId.GET_NEWS) {
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
                NewsData newsData = new NewsData();

                newsData.setTitle(jsonObject.getString("title"));
                newsData.setLink(jsonObject.getString("link"));
                newsData.setImage(jsonObject.getString("image"));
                newsData.setSite_title(jsonObject.getString("site_title"));
                newsData.setStory(jsonObject.getString("story"));

                newsDataList.add(newsData);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        newsAdapter = new NewsAdapter(this, newsDataList, context);
        recycler_view.setAdapter(newsAdapter);

    }

    @Override
    public void onErrorResponse(ApiURLS.ApiId apiId, String errorData, int responseCode) {

    }
}
