package in.squareiapp.landmarkcity.activities;

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
import java.util.List;

import in.squareiapp.landmarkcity.R;
import in.squareiapp.landmarkcity.adapters.StoreListADapter;
import in.squareiapp.landmarkcity.interfaces.CustomItemClickListener;
import in.squareiapp.landmarkcity.interfaces.NetworkResponseListener;
import in.squareiapp.landmarkcity.models.StoreData;
import in.squareiapp.landmarkcity.utils.ApiURLS;
import in.squareiapp.landmarkcity.utils.JsonParser;
import in.squareiapp.landmarkcity.utils.Logger;
import in.squareiapp.landmarkcity.utils.NetworkRequestHandler2;
import in.squareiapp.landmarkcity.utils.SharedPrefUtils;

public class StoresListActivity extends BaseActivity implements NetworkResponseListener, CustomItemClickListener {
    private final String TAG = getClass().getSimpleName();
    private RecyclerView recycler_view;
    private List<StoreData> storeData;
    private StoreListADapter storeAdapter;
    private String storeId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stores_list);
        startMyACtivtiy();
        Intent intent = getIntent();
        if (intent.getExtras() != null)
            storeId = intent.getStringExtra("storeid");
        storeData = new ArrayList<>();
        getStoresList();
    }

    @Override
    protected void initContext() {
        context = StoresListActivity.this;
        currentActivity = StoresListActivity.this;
    }

    @Override
    protected void initViews() {
        recycler_view = (RecyclerView) findViewById(R.id.recycler_view);
        recycler_view.setLayoutManager(new LinearLayoutManager(context));
    }

    @Override
    protected void initListners() {

    }

    @Override
    protected void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setToolBar(toolbar);
        setToolbarTitle("Stores");
        showHomeButton();
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void getStoresList() {
        NetworkRequestHandler2 networkRequestHandler = new NetworkRequestHandler2(context, this);
        String client_id = SharedPrefUtils.getInstance(context).getString(SharedPrefUtils.CLIENT_ID);
        String url = ApiURLS.GET_STORES + "?client_id=" + client_id + "&type=" + storeId;
        networkRequestHandler.getStringResponse(url, ApiURLS.ApiId.GET_STORES, ApiURLS.REQUEST_GET, null, null, true);

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onJsonResponse(ApiURLS.ApiId apiId, JSONObject jsonObjectResponse) {

    }

    @Override
    public void onStringResponse(ApiURLS.ApiId apiId, String stringResponse) {
        Logger.info(TAG, "on response::" + stringResponse);
        if (apiId == ApiURLS.ApiId.GET_STORES) {
            JsonParser jsonParser = new JsonParser(stringResponse);
            int success = jsonParser.getSuccess();
            int error = jsonParser.getError();
            String message = jsonParser.getMessage();
            if (success == 1 && error == 0) {
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
                    String name = jobj.getString("bname");
                    String image = jobj.getString("profilepic");
                    String owner = jobj.getString("owner");
                    String address = jobj.getString("address");
                    String timing = jobj.getString("timing");
                    String phone = jobj.getString("phone");
                    String images = jobj.getString("images");
                    String note = jobj.getString("note");
                    StoreData data = new StoreData();
                    data.setId(id);
                    data.setStoreIcon(image);
                    data.setStoreName(name);
                    data.setOwner(owner);
                    data.setAddress(address);
                    data.setTiming(timing);
                    data.setPhone(phone);
                    data.setImages(images);
                    data.setNote(note);
                    storeData.add(data);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            Logger.error(TAG, "===::setting title" + jsonArray);
            storeAdapter = new StoreListADapter(this, storeData, context);
            recycler_view.setAdapter(storeAdapter);
            //  storeAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onErrorResponse(ApiURLS.ApiId apiId, String errorData, int responseCode) {

    }

    @Override
    public void onItemClickCallback(int position, int flag) {

    }
}


