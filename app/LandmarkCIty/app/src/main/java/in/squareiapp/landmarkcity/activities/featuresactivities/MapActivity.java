package in.squareiapp.landmarkcity.activities.featuresactivities;

import android.Manifest;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import in.squareiapp.landmarkcity.R;
import in.squareiapp.landmarkcity.activities.BaseActivity;
import in.squareiapp.landmarkcity.adapters.StoreListHorizontalAdapter;
import in.squareiapp.landmarkcity.interfaces.CustomItemClickListener;
import in.squareiapp.landmarkcity.interfaces.NetworkResponseListener;
import in.squareiapp.landmarkcity.models.StoreData;
import in.squareiapp.landmarkcity.utils.ApiURLS;
import in.squareiapp.landmarkcity.utils.JsonParser;
import in.squareiapp.landmarkcity.utils.Logger;
import in.squareiapp.landmarkcity.utils.NetworkRequestHandler2;
import in.squareiapp.landmarkcity.utils.SharedPrefUtils;

public class MapActivity extends BaseActivity implements OnMapReadyCallback, NetworkResponseListener, CustomItemClickListener {

    private final String TAG = getClass().getSimpleName();
    private SupportMapFragment supportMapFragment = null;
    private GoogleMap googleMap;
    private RecyclerView recycler_view;
    private List<StoreData> storeData;
    private List<StoreData> storeListData;
    private StoreListHorizontalAdapter storeAdapter;
    private boolean a = false;
    private LatLng receivedLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        startMyACtivtiy();
        Intent intent = getIntent();
        storeData = new ArrayList<>();
        storeListData = new ArrayList<>();
        if (!a)
            getStoresList();
        a = true;
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            //   doMySearch(query);
        }

        if (intent.getExtras() != null) {
            Logger.info(TAG, "LOCATION intent is found");
            Bundle bundle = intent.getExtras();
            if (bundle.containsKey("storeLocation")) {
                Logger.info(TAG, bundle.getString("storeLocation"));
                String[] loc = bundle.getString("storeLocation").split(",");
                receivedLocation = new LatLng(Double.parseDouble(loc[0]), Double.parseDouble(loc[1]));


            } else {
                Logger.info(TAG, "LOCATION does not exists");
            }
        }

    }

    @Override
    protected void initContext() {
        context = MapActivity.this;
        currentActivity = MapActivity.this;
    }

    @Override
    protected void initViews() {
        try {
            supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
            supportMapFragment.getMapAsync(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
        recycler_view = (RecyclerView) findViewById(R.id.recycler_view);
        recycler_view.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
    }

    @Override
    protected void initListners() {

    }

    @Override
    protected void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setToolBar(toolbar);
        setToolbarTitle("Landmark City");
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
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        readyMap();
    }

    private void getStoresList() {
        NetworkRequestHandler2 networkRequestHandler = new NetworkRequestHandler2(context, this);
        String client_id = SharedPrefUtils.getInstance(context).getString(SharedPrefUtils.CLIENT_ID);
        String url = ApiURLS.GET_STORES_CATEGORIES + "?client_id=" + client_id;
        networkRequestHandler.getStringResponse(url, ApiURLS.ApiId.GET_STORES_CATEGORIES, ApiURLS.REQUEST_GET, null, null, true);

    }

    private void readyMap() {
        double lat = 25.2004747;
        double lng = 75.8294157;
        LatLng currentLocation = new LatLng(lat, lng);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        googleMap.setMyLocationEnabled(true);
        googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 15));

        //  googleMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(context, R.raw.styler));

        drawLandmarkCity();
    }

    private void drawLandmarkCity() {
        googleMap.addPolyline(new PolylineOptions().geodesic(true)
                .add(new LatLng(25.199229, 75.832438))
                .add(new LatLng(25.202888, 75.835890))
                .add(new LatLng(25.206917, 75.830608))
                .add(new LatLng(25.201077, 75.827896))
                .add(new LatLng(25.197992, 75.824986))
                .add(new LatLng(25.199229, 75.832438)).width(7)
                .color(Color.RED));
        if (receivedLocation != null) {
            googleMap.addMarker(new MarkerOptions().position(receivedLocation).title(""));

            //  googleMap.addMarker(receivedLocation);
        }
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
            } else {
                showToast(message, false);
            }
        } else if (apiId == ApiURLS.ApiId.GET_STORES) {
            JsonParser jsonParser = new JsonParser(stringResponse);
            int success = jsonParser.getSuccess();
            int error = jsonParser.getError();
            String message = jsonParser.getMessage();
            if (success == 1 && error == 0) {
                JSONArray jsonArray = jsonParser.getArrayData();
                setStoresOnMap(jsonArray);
            } else {
                showToast(message, false);
            }
        } else if (apiId == ApiURLS.ApiId.SEARCH_STORES) {
            JsonParser jsonParser = new JsonParser(stringResponse);
            int success = jsonParser.getSuccess();
            int error = jsonParser.getError();
            String message = jsonParser.getMessage();
            if (success == 1 && error == 0) {
                JSONArray jsonArray = jsonParser.getArrayData();
                setStoresOnMap(jsonArray);
            } else {
                showToast(message, false);
            }
        }
    }

    private void setStoresOnMap(JSONArray jsonArray) {
        Logger.info(TAG, ":: calling setStoresOnMap()");
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
                    String loc = jobj.getString("loc");
                    String icon = jobj.getString("icon");
                    String storeType = jobj.getString("btype");
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
                    data.setLocation(loc);
                    data.setSvgIcon(icon);
                    data.setStoreType(storeType);
                    storeListData.add(data);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            settingStroresOnMap(storeListData);
            //    Logger.error(TAG, "===::setting title" + jsonArray);

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.map_menu, menu);

        final MenuItem searchItem = menu.findItem(R.id.action_search);

        SearchManager searchManager = (SearchManager) context.getSystemService(Context.SEARCH_SERVICE);

        SearchView searchView = null;
        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
        }
        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(currentActivity.getComponentName()));
        }
        final SearchView finalSearchView = searchView;
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (!finalSearchView.isIconified()) {
                    finalSearchView.setIconified(true);
                    showToast(query, true);
                    getStoresSearch(query);
                }
                searchItem.collapseActionView();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    private void settingStroresOnMap(List<StoreData> storeListData) {
        Logger.info(TAG, ":: calling settingStroresOnMap()");
        int listSize = storeListData.size();
        for (int i = 0; i < listSize; i++) {
            Logger.info(TAG, ":: List size " + storeListData.get(i));
            String[] loc = storeListData.get(i).getLocation().split(",");
            LatLng location = new LatLng(Double.parseDouble(loc[0]), Double.parseDouble(loc[1]));

            switch (storeListData.get(i).getStoreType()) {
                case "1":
                    //  googleMap.addMarker(new MarkerOptions().position(location).title(storeListData.get(i).getBname()).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_restaurant_svg)));
                    googleMap.addMarker(new MarkerOptions().position(location).title(storeListData.get(i).getBname()).icon(BitmapDescriptorFactory.fromBitmap(getBitmapFromVectorDrawable(R.drawable.ic_restaurant_svg))));
                    break;
                case "2":
                    googleMap.addMarker(new MarkerOptions().position(location).title(storeListData.get(i).getBname()).icon(BitmapDescriptorFactory.fromBitmap(getBitmapFromVectorDrawable(R.drawable.ic_medical_store_svg))));
                    //   googleMap.addMarker(new MarkerOptions().position(location).title(storeListData.get(i).getBname()).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_medical_store_svg)));
                    break;
                case "3":
                    googleMap.addMarker(new MarkerOptions().position(location).title(storeListData.get(i).getBname()).icon(BitmapDescriptorFactory.fromBitmap(getBitmapFromVectorDrawable(R.drawable.ic_laundary_svg))));
                    //  googleMap.addMarker(new MarkerOptions().position(location).title(storeListData.get(i).getBname()).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_laundary_svg)));
                    break;
                case "4":
                    googleMap.addMarker(new MarkerOptions().position(location).title(storeListData.get(i).getBname()).icon(BitmapDescriptorFactory.fromBitmap(getBitmapFromVectorDrawable(R.drawable.ic_mobile_shop_svg))));
                    //   googleMap.addMarker(new MarkerOptions().position(location).title(storeListData.get(i).getBname()).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_mobile_shop_svg)));
                    break;
                case "5":
                    googleMap.addMarker(new MarkerOptions().position(location).title(storeListData.get(i).getBname()).icon(BitmapDescriptorFactory.fromBitmap(getBitmapFromVectorDrawable(R.drawable.ic_stationary_svg))));
                    // googleMap.addMarker(new MarkerOptions().position(location).title(storeListData.get(i).getBname()).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_stationary_svg)));
                    break;
                case "6":
                    googleMap.addMarker(new MarkerOptions().position(location).title(storeListData.get(i).getBname()).icon(BitmapDescriptorFactory.fromBitmap(getBitmapFromVectorDrawable(R.drawable.ic_clothes_svg))));
                    //   googleMap.addMarker(new MarkerOptions().position(location).title(storeListData.get(i).getBname()).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_clothes_svg)));
                    break;
            }
            //  Logger.info(TAG, ":: setting location::" + loc);
        }
    }

    @Override
    public void onErrorResponse(ApiURLS.ApiId apiId, String errorData, int responseCode) {

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
            //   Logger.error(TAG, "===::setting title" + jsonArray);
            storeAdapter = new StoreListHorizontalAdapter(this, storeData, context);
            recycler_view.setAdapter(storeAdapter);
            //  storeAdapter.notifyDataSetChanged();
        }
    }

    private void getStoresList(String storeId) {
        NetworkRequestHandler2 networkRequestHandler = new NetworkRequestHandler2(context, this);
        String client_id = SharedPrefUtils.getInstance(context).getString(SharedPrefUtils.CLIENT_ID);
        String url = ApiURLS.GET_STORES + "?client_id=" + client_id + "&type=" + storeId;
        networkRequestHandler.getStringResponse(url, ApiURLS.ApiId.GET_STORES, ApiURLS.REQUEST_GET, null, null, true);
    }

    private void getStoresSearch(String key) {
        NetworkRequestHandler2 networkRequestHandler = new NetworkRequestHandler2(context, this);
        String client_id = SharedPrefUtils.getInstance(context).getString(SharedPrefUtils.CLIENT_ID);
        String url = ApiURLS.SEARCH_STORES + "?client_id=" + client_id + "&q=" + key;
        networkRequestHandler.getStringResponse(url, ApiURLS.ApiId.SEARCH_STORES, ApiURLS.REQUEST_GET, null, null, true);
    }

    @Override
    public void onItemClickCallback(int position, int flag) {
        switch (flag) {
            case 1:
                getStoresList(storeData.get(position).getId());
      /*
      Intent i = new Intent(currentActivity, StoresListActivity.class);
                i.putExtra("storeid", storeData.get(position).getId());
                startActivity(i);*/
                break;
        }
    }

    public Bitmap getBitmapFromVectorDrawable(int drawableId) {
        Drawable drawable = ContextCompat.getDrawable(context, drawableId);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            drawable = (DrawableCompat.wrap(drawable)).mutate();
        }

        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }
}
