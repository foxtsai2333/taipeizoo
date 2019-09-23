package com.csstalker.fragmenttest.app;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.csstalker.fragmenttest.R;
import com.csstalker.fragmenttest.adapter.OnPlanetItemClickListener;
import com.csstalker.fragmenttest.adapter.PlanetAdapter;
import com.csstalker.fragmenttest.api.APITool;
import com.csstalker.fragmenttest.gson.GsonTool;
import com.csstalker.fragmenttest.gson.object.PlanetBase;
import com.csstalker.fragmenttest.gson.object.PlanetData;
import com.csstalker.fragmenttest.gson.object.ZoneData;
import com.csstalker.fragmenttest.image.GlideConfig;
import com.csstalker.fragmenttest.task.JsonHttpTask;
import com.csstalker.fragmenttest.task.OnJsonTaskCompleteListener;

import java.net.URLEncoder;

public class ZoneActivity extends BaseActivity implements OnPlanetItemClickListener {

    private static final String TAG = ZoneActivity.class.getSimpleName();

    // view
    private ImageView zoneImage;
    private TextView descText;
    private TextView openHourText;
    private TextView zoneTypeText;
    private TextView webText;
    private RecyclerView planetRecyclerView;

    private ZoneData zone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zone);

        // get zone object payload
        String zoneStr = getIntent().getStringExtra("zone");
        if (zoneStr != null && !"".equals(zoneStr)) {
            // parsing payload to object
            zone = (ZoneData) GsonTool.getInstance()
                    .stringToObject(zoneStr, ZoneData.class);
        }

        findviews();
        init();
    }

    private void findviews() {
        zoneImage = findViewById(R.id.zone_image);
        descText = findViewById(R.id.zone_desc_text);
        openHourText = findViewById(R.id.zone_open_hour_desc_text);
        zoneTypeText = findViewById(R.id.zone_type_text);
        webText = findViewById(R.id.zone_web_text);
        planetRecyclerView = findViewById(R.id.planet_recyclerview);
    }

    private void init() {
        if (zone == null) {
            Toast.makeText(this, "something wrong...", Toast.LENGTH_LONG).show();
        } else {
            // 設定該館的資訊
            // 圖片
            String imageUrl = zone.img;
            if (imageUrl != null && !"".equals(imageUrl)) {
                Glide.with(this)
                        .load(imageUrl)
                        .apply(GlideConfig.getInstance().getGlideOption())
                        .into(zoneImage);
            }
            // 說明
            descText.setText(zone.info);
            // 休館資訊
            String memo = zone.memo;
            if (memo == null || "".equals(memo))
                memo = getString(R.string.empty_open_hour_text);
            openHourText.setText(memo);
            // 分區
            zoneTypeText.setText(checkDisplayText(zone.category));

            // 用館名去拿其他東西
            getPlanetOfZone(checkDisplayText(zone.name));
        }
    }

    private void getPlanetOfZone(String zoneName) {
        String url = APITool.getInstance().getPlanetUrl();
        try {
            url = url + "&q=" + URLEncoder.encode(zoneName, "UTF-8");
        } catch (Exception e) {
            Log.e(TAG, "onClickPlanet: " + e.getMessage());
        }


        JsonHttpTask task = new JsonHttpTask(url, PlanetBase.class, new OnJsonTaskCompleteListener() {
            @Override
            public void onTaskComplete(Object object) {
                dismissLoadingHint();
                PlanetBase pb = (PlanetBase) object;
                Log.d(TAG, "onTaskComplete: " + pb.result.planetList.size());
                // 設定 adapter();
                PlanetAdapter adapter = new PlanetAdapter(ZoneActivity.this, pb.result.planetList);
                planetRecyclerView.setLayoutManager(new LinearLayoutManager(ZoneActivity.this));
                planetRecyclerView.setHasFixedSize(true);
                planetRecyclerView.setNestedScrollingEnabled(false);
                planetRecyclerView.setAdapter(adapter);
                adapter.setOnClickPlanetItemListener(ZoneActivity.this);
            }
        });
        showLoadingHint();
        task.execute();
    }

    @Override
    public void onClickPlanet(PlanetData planet) {
        Intent intent = new Intent(this, PlanetDetailActivity.class);
        intent.putExtra("planet", GsonTool.getInstance().objectToString(planet));
        intent.setAction(Intent.ACTION_VIEW);
        startActivity(intent);
    }

    public void onClickOpenWeb(View view) {
        if (zone != null) {
            String url = zone.webUrl;
            if (url == null) {
                Toast.makeText(this, getString(R.string.web_link_not_found), Toast.LENGTH_LONG).show();
            } else {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        }
    }
}
