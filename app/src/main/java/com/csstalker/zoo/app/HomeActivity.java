package com.csstalker.zoo.app;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.csstalker.zoo.R;
import com.csstalker.zoo.adapter.OnZoneItemClickListener;
import com.csstalker.zoo.adapter.ZoneAdapter;
import com.csstalker.zoo.api.APITool;
import com.csstalker.zoo.gson.GsonTool;
import com.csstalker.zoo.gson.object.ZoneBase;
import com.csstalker.zoo.gson.object.ZoneData;
import com.csstalker.zoo.task.JsonHttpTask;
import com.csstalker.zoo.task.OnJsonTaskCompleteListener;

public class HomeActivity extends BaseActivity implements OnZoneItemClickListener,
        NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = HomeActivity.class.getSimpleName();

    // view
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private RecyclerView zoneRecyclerView;
    private TextView emptyText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        findviews();
        setlistener();
        init();
    }

    private void findviews() {
        // 設定 toolbar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        zoneRecyclerView = findViewById(R.id.zone_recyclerview);
        emptyText = findViewById(R.id.empty_text);
    }

    private void setlistener() {
        // 讓 toolbar 上的標題按鈕跟抽屜開關同步作動
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        // 設定選單項目點選的 listener
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void init() {
        // 拿動物園資料
        JsonHttpTask task = new JsonHttpTask(APITool.getInstance().getZoneUrl(), ZoneBase.class, new OnJsonTaskCompleteListener() {
            @Override
            public void onTaskComplete(Object object) {
                Log.d(TAG, "onTaskComplete");
                dismissLoadingHint();
                ZoneBase zb = (ZoneBase) object;
                try {
                    if (zb != null && zb.result.zoneList.size() > 0) {
                        Log.d(TAG, "zone list size = " + zb.result.zoneList.size());
                        emptyText.setVisibility(View.GONE);
                        ZoneAdapter adapter = new ZoneAdapter(HomeActivity.this, zb.result.zoneList);
                        zoneRecyclerView.setLayoutManager(new LinearLayoutManager(HomeActivity.this));
                        zoneRecyclerView.setHasFixedSize(true);
                        zoneRecyclerView.setAdapter(adapter);
                        adapter.setOnZoneItemClickListener(HomeActivity.this);
                    } else {
                        // 空白
                        emptyText.setVisibility(View.VISIBLE);
                    }
                } catch (Exception e) {
                    Log.e(TAG, "onTaskComplete: " + e.getMessage());
                }

            }
        });
        showLoadingHint();
        task.execute();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_version_info:
                // 顯示版本資訊
                Toast.makeText(this, getVersionName(), Toast.LENGTH_LONG).show();
                return true;
        }
        return false;
    }

    @Override
    public void onClickZone(ZoneData zone) {
        Log.d(TAG, "onClickZone: " + zone.name);
        Intent intent = new Intent(this, ZoneActivity.class);
        intent.setAction(Intent.ACTION_VIEW);
        intent.putExtra("zone", GsonTool.getInstance().objectToString(zone));
        startActivity(intent);
    }
}
