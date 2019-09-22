package com.csstalker.fragmenttest;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.widget.CircularProgressDrawable;
import android.util.Log;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.csstalker.fragmenttest.api.APITool;
import com.csstalker.fragmenttest.app.ContainerActivity;
import com.csstalker.fragmenttest.gson.object.PlanetBase;
import com.csstalker.fragmenttest.gson.object.ZoneBase;
import com.csstalker.fragmenttest.task.JsonHttpTask;
import com.csstalker.fragmenttest.task.OnJsonTaskCompleteListener;

import java.net.URLEncoder;

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

        } else if (id == R.id.app_version) {

        }

        // 點擊後關閉抽屜
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void onClickZoo(View v) {
        JsonHttpTask task = new JsonHttpTask(APITool.getInstance().getZoneUrl(), ZoneBase.class, new OnJsonTaskCompleteListener() {
            @Override
            public void onTaskComplete(Object object) {
                dismissLoadingHint();
                ZoneBase zb = (ZoneBase) object;
                Log.d(TAG, "onTaskComplete: " + zb.result.zoneList.size());
            }
        });
        showLoadingHint();
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
                dismissLoadingHint();
                PlanetBase pb = (PlanetBase) object;
                Log.d(TAG, "onTaskComplete: " + pb.result.planetList.size());
            }
        });
        showLoadingHint();
        task.execute();
    }

    private ProgressDialog loadingHint;

    private void showLoadingHint() {
        if (!isLoadingHintShowing())
            loadingHint = ProgressDialog.show(this, "", getString(R.string.loading), true);
    }

    private void dismissLoadingHint() {
        if (isLoadingHintShowing())
            loadingHint.dismiss();
    }

    private boolean isLoadingHintShowing() {
        if (loadingHint != null && loadingHint.isShowing())
            return true;
        else
            return false;
    }

    public void toContainer(View view) {
        Intent intent = new Intent(this, ContainerActivity.class);
        startActivity(intent);
    }
}
