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
import in.squareiapp.landmarkcity.activities.StoresListActivity;
import in.squareiapp.landmarkcity.adapters.StoreAdapter;
import in.squareiapp.landmarkcity.interfaces.CustomItemClickListener;
import in.squareiapp.landmarkcity.interfaces.NetworkResponseListener;
import in.squareiapp.landmarkcity.models.StoreData;
import in.squareiapp.landmarkcity.utils.ApiURLS;
import in.squareiapp.landmarkcity.utils.JsonParser;
import in.squareiapp.landmarkcity.utils.Logger;
import in.squareiapp.landmarkcity.utils.NetworkRequestHandler2;
import in.squareiapp.landmarkcity.utils.SharedPrefUtils;

public class StoreActivity extends BaseActivity implements CustomItemClickListener, NetworkResponseListener {
    private final String TAG = getClass().getSimpleName();
    private RecyclerView recycler_view;
    private List<StoreData> storeData;
    private StoreAdapter storeAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);
        startMyACtivtiy();
        storeData = new ArrayList<>();
        getStoresList();
    }

    @Override
    protected void initContext() {
        context = StoreActivity.this;
        currentActivity = StoreActivity.this;
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
        setToolbarTitle("Store");
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
                Intent i = new Intent(currentActivity, StoresListActivity.class);
                i.putExtra("storeid", storeData.get(position).getId());
                startActivity(i);
                break;
        }
    }

    private void getStoresList() {
        NetworkRequestHandler2 networkRequestHandler = new NetworkRequestHandler2(context, this);
        String client_id = SharedPrefUtils.getInstance(context).getString(SharedPrefUtils.CLIENT_ID);
        String url = ApiURLS.GET_STORES_CATEGORIES + "?client_id=" + client_id;
        networkRequestHandler.getStringResponse(url, ApiURLS.ApiId.GET_STORES_CATEGORIES, ApiURLS.REQUEST_GET, null, null, true);

    }

    @Override
    public void onJsonResponse(ApiURLS.ApiId apiId, JSONObject jsonObjectResponse) {

    }

    @Override
    public void onStringResponse(ApiURLS.ApiId apiId, String stringResponse) {
        Logger.info(TAG, "on response::" + stringResponse);
        if (apiId == ApiURLS.ApiId.GET_STORES_CATEGORIES) {
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
                    StoreData data = new StoreData();
                    data.setId(id);
                    data.setStoreIcon(image);
                    data.setStoreName(name);
                    storeData.add(data);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            Logger.error(TAG, "===::setting title" + jsonArray);
            storeAdapter = new StoreAdapter(this, storeData, context);
            recycler_view.setAdapter(storeAdapter);
            //  storeAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onErrorResponse(ApiURLS.ApiId apiId, String errorData, int responseCode) {

    }
}
