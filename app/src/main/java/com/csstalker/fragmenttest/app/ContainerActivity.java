package com.csstalker.fragmenttest.app;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.csstalker.fragmenttest.R;

public class ContainerActivity extends AppCompatActivity {

    // 實際會用來當作容器的 activity
    private static final String TAG = ContainerActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }


}
