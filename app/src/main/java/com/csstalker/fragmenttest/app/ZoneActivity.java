package com.csstalker.fragmenttest.app;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
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
import com.csstalker.fragmenttest.gson.object.PlanetResult;
import com.csstalker.fragmenttest.gson.object.ZoneData;
import com.csstalker.fragmenttest.image.GlideConfig;
import com.csstalker.fragmenttest.task.JsonHttpTask;
import com.csstalker.fragmenttest.task.OnJsonTaskCompleteListener;

import java.net.URLEncoder;

public class ZoneActivity extends BaseActivity implements OnPlanetItemClickListener {

    private static final String TAG = ZoneActivity.class.getSimpleName();
    private ZoneData zone;
    private String zoneStr, planetStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zone);

        // 開啟返回按鈕
        ActionBar bar = getSupportActionBar();
        bar.setDisplayHomeAsUpEnabled(true);

        // get zone object payload
        zoneStr = getIntent().getStringExtra("zone");
        if (zoneStr != null && !"".equals(zoneStr)) {
            // parsing payload to object
            zone = (ZoneData) GsonTool.getInstance()
                    .stringToObject(zoneStr, ZoneData.class);
        }

        init();
    }


    private void init() {
        // 取得該館的植物列表
        getPlanetOfZone(zone.name);
    }

    // 設定介紹跟植物列表
    private void setupFragment() {
        // 取得 fragment manager
        FragmentManager fm = getSupportFragmentManager();
        // 建立 fragment transcation
        FragmentTransaction ft = fm.beginTransaction();
        // 新增要加入的 fragment
        //ZoneFragment zoneFragment = new ZoneFragment();
        ZoneFragment zoneFragment = ZoneFragment.newInstance(zoneStr, planetStr);
        zoneFragment.setOnPlanetItemClickListener(this);
        //PlanetFragment planetFragment = new PlanetFragment();
        // 加入 fragment
        ft.add(R.id.container, zoneFragment, "zone");
        // 送出
        ft.commit();
        // 要求立即處理 pending 的作業
        fm.executePendingTransactions();
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
                planetStr = GsonTool.getInstance().objectToString(pb.result);
                setupFragment();
            }
        });
        showLoadingHint();
        task.execute();
    }

    @Override
    public void onClickPlanet(PlanetData planet) {
        // 原本會跳頁, 改用 fragment 交換
        /*
        Intent intent = new Intent(this, PlanetDetailActivity.class);
        intent.putExtra("planet", GsonTool.getInstance().objectToString(planet));
        intent.setAction(Intent.ACTION_VIEW);
        startActivity(intent);
        */
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

    @Override
    public boolean onSupportNavigateUp() {
        // click action bar back
        return true;
    }
}
