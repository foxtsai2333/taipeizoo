package com.csstalker.fragmenttest.app;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.csstalker.fragmenttest.R;
import com.csstalker.fragmenttest.adapter.OnZoneItemClickListener;
import com.csstalker.fragmenttest.adapter.ZoneAdapter;
import com.csstalker.fragmenttest.api.APITool;
import com.csstalker.fragmenttest.gson.GsonTool;
import com.csstalker.fragmenttest.gson.object.ZoneBase;
import com.csstalker.fragmenttest.gson.object.ZoneData;
import com.csstalker.fragmenttest.task.JsonHttpTask;
import com.csstalker.fragmenttest.task.OnJsonTaskCompleteListener;
import com.google.gson.Gson;

public class HomeActivity extends BaseActivity implements OnZoneItemClickListener,
        NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = HomeActivity.class.getSimpleName();

    // view
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private RecyclerView zoneRecyclerView;

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
                dismissLoadingHint();
                ZoneBase zb = (ZoneBase) object;
                Log.d(TAG, "onTaskComplete: " + zb.result.zoneList.size());
                if (zb.result.zoneList != null) {
                    ZoneAdapter adapter = new ZoneAdapter(HomeActivity.this, zb.result.zoneList);
                    zoneRecyclerView.setLayoutManager(new LinearLayoutManager(HomeActivity.this));
                    zoneRecyclerView.setHasFixedSize(true);
                    zoneRecyclerView.setAdapter(adapter);
                    adapter.setOnZoneItemClickListener(HomeActivity.this);
                }
            }
        });
        showLoadingHint();
        task.execute();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
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
