package com.csstalker.fragmenttest;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import com.csstalker.fragmenttest.api.APITool;
import com.csstalker.fragmenttest.gson.object.PlanetBase;
import com.csstalker.fragmenttest.gson.object.ZoneBase;
import com.csstalker.fragmenttest.gson.object.ZoneData;
import com.csstalker.fragmenttest.gson.object.ZoneResult;
import com.csstalker.fragmenttest.task.JsonHttpTask;
import com.csstalker.fragmenttest.task.OnJsonTaskCompleteListener;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import kotlin.text.Charsets;
import okhttp3.FormBody;
import okhttp3.RequestBody;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    // view
    private DrawerLayout drawer;
    private Toolbar toolbar;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findviews();
        setlistener();
    }

    private void findviews() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
    }

    private void setlistener() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
    }


    @Override
    public void onBackPressed() {
        // 攔截 back 按鍵, 如果目前 drawer 是開的, 先把 drawer 關起來
        // 反之則直接執行原本 back 動作

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_tools) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        // 點擊後關閉抽屜
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void onClickZoo(View v) {
        JsonHttpTask task = new JsonHttpTask(APITool.getInstance().getZoneUrl(), ZoneBase.class, new OnJsonTaskCompleteListener() {
            @Override
            public void onTaskComplete(Object object) {
                ZoneBase zb = (ZoneBase) object;
                Log.d(TAG, "onTaskComplete: " + zb.result.zoneList.size());
            }
        });
        task.execute();
    }

    public void onClickPlanet(View v) {
        String url = APITool.getInstance().getPlanetUrl();

        try {
            url = url + "&q=" + URLEncoder.encode("兩棲爬蟲動物館", "UTF-8");
        } catch (Exception e) {
            Log.e(TAG, "onClickPlanet: " + e.getMessage());
        }


        JsonHttpTask task = new JsonHttpTask(url, PlanetBase.class, new OnJsonTaskCompleteListener() {
            @Override
            public void onTaskComplete(Object object) {
                PlanetBase pb = (PlanetBase) object;
                Log.d(TAG, "onTaskComplete: " + pb.result.planetList.size());
            }
        });
        task.execute();
    }
}
