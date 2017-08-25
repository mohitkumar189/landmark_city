package in.squareiapp.landmarkcity.activities.featuresactivities;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import in.squareiapp.landmarkcity.R;
import in.squareiapp.landmarkcity.activities.BaseActivity;
import in.squareiapp.landmarkcity.adapters.GreetingsAdapter;
import in.squareiapp.landmarkcity.interfaces.CustomItemClickListener;
import in.squareiapp.landmarkcity.interfaces.NetworkResponseListener;
import in.squareiapp.landmarkcity.models.GreetingData;
import in.squareiapp.landmarkcity.utils.ApiURLS;
import in.squareiapp.landmarkcity.utils.JsonParser;
import in.squareiapp.landmarkcity.utils.Logger;
import in.squareiapp.landmarkcity.utils.NetworkRequestHandler2;
import in.squareiapp.landmarkcity.utils.SharedPrefUtils;

public class GreetingsActivity extends BaseActivity implements CustomItemClickListener, NetworkResponseListener {
    private final String TAG = getClass().getSimpleName();
    private RecyclerView recycler_view;
    private List<GreetingData> greetingDatas;
    private GreetingsAdapter greetingsAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_greetings);
        startMyACtivtiy();
        greetingDatas = new ArrayList<>();
        getGreetingList();
    }

    @Override
    protected void initContext() {
        context = GreetingsActivity.this;
        currentActivity = GreetingsActivity.this;
    }

    @Override
    protected void initViews() {
        recycler_view = (RecyclerView) findViewById(R.id.recycler_view);
        recycler_view.setLayoutManager(new GridLayoutManager(context, 2));

    }

    @Override
    protected void initListners() {

    }

    @Override
    protected void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setToolBar(toolbar);
        setToolbarTitle(getString(R.string.greetings_title_text));
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

    @Override
    public void onItemClickCallback(int position, int flag) {
        switch (flag) {
            case 1:
                Intent i = new Intent(currentActivity, FriendsActivity.class);
                i.putExtra("greetingId", greetingDatas.get(position).getId());
                startActivity(i);
                break;
        }
    }

    private void getGreetingList() {
        NetworkRequestHandler2 networkRequestHandler = new NetworkRequestHandler2(context, this);
        String client_id = SharedPrefUtils.getInstance(context).getString(SharedPrefUtils.CLIENT_ID);
        String url = ApiURLS.GET_GREETINGS + "?client_id=" + client_id;
        networkRequestHandler.getStringResponse(url, ApiURLS.ApiId.GET_GREETINGS, ApiURLS.REQUEST_GET, null, null, true);

    }

    @Override
    public void onJsonResponse(ApiURLS.ApiId apiId, JSONObject jsonObjectResponse) {

    }

    @Override
    public void onStringResponse(ApiURLS.ApiId apiId, String stringResponse) {
        Logger.info(TAG, "on response::" + stringResponse);
        if (apiId == ApiURLS.ApiId.GET_GREETINGS) {
            JsonParser jsonParser = new JsonParser(stringResponse);
            int success = jsonParser.getSuccess();
            //  int error = jsonParser.getError();
            String message = jsonParser.getMessage();
            if (success == 1) {
                JSONArray jsonArray = jsonParser.getArrayData();
                setData(jsonArray);
                // JSONObject jsonObject = jsonParser.getObjectData();
                // setData(jsonObject);
                //showToast(message, false);
            } else {
                showToast(message, false);
            }
        }
    }

    private void setData(JSONArray jsonArray) {

        int length = jsonArray.length();
        if (length > 0) {
            for (int i = 0; i < length; i++) {
                try {
                    JSONObject jobj = jsonArray.getJSONObject(i);
                    String id = jobj.getString("id");
                    String name = jobj.getString("name");
                    String image = jobj.getString("image");
                    GreetingData data = new GreetingData();
                    data.setId(id);
                    data.setStoreIcon(image);
                    data.setStoreName(name);
                    greetingDatas.add(data);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            Logger.error(TAG, "===::setting title" + jsonArray);
            greetingsAdapter = new GreetingsAdapter(this, greetingDatas, context);
            recycler_view.setAdapter(greetingsAdapter);
            //  storeAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onErrorResponse(ApiURLS.ApiId apiId, String errorData, int responseCode) {

    }
}

