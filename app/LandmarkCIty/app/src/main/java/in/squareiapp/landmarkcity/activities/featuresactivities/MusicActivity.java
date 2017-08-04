package in.squareiapp.landmarkcity.activities.featuresactivities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.jlubecki.soundcloud.webapi.android.SoundCloudAPI;
import com.jlubecki.soundcloud.webapi.android.SoundCloudService;
import com.jlubecki.soundcloud.webapi.android.models.Track;

import in.squareiapp.landmarkcity.R;
import in.squareiapp.landmarkcity.activities.BaseActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MusicActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);
        startMyACtivtiy();
        initSoundCloud();
    }

    @Override
    protected void initContext() {
        context = MusicActivity.this;
        currentActivity = MusicActivity.this;
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void initListners() {

    }

    @Override
    protected void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setToolBar(toolbar);
        setToolbarTitle("Music");
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

    private void initSoundCloud() {
        SoundCloudAPI api = new SoundCloudAPI("ineXHpr9wuJrqcT1NcRIyeUnUg1HqYzk");
        SoundCloudService soundcloud = api.getService();

        soundcloud.getTrack("trackId").enqueue(new Callback<Track>() {
            @Override
            public void onResponse(Call<Track> call, Response<Track> response) {
                Track track = response.body();

                if (track != null) {
                } else {
                }
            }

            @Override
            public void onFailure(Call<Track> call, Throwable t) {
            }
        });
    }
}
