package com.csstalker.fragmenttest.app;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.csstalker.fragmenttest.R;

import javax.security.auth.login.LoginException;

public class BaseActivity extends AppCompatActivity {

    private static final String TAG = BaseActivity.class.getSimpleName();

    // view
    private ProgressDialog loadingHint;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    public void showLoadingHint() {
        if (!isLoadingHintShowing())
            loadingHint = ProgressDialog.show(this, "", getString(R.string.loading), true);
    }

    public void dismissLoadingHint() {
        if (isLoadingHintShowing())
            loadingHint.dismiss();
    }

    private boolean isLoadingHintShowing() {
        return loadingHint != null && loadingHint.isShowing();
    }

    public String getVersionName() {
        Context context = getApplicationContext();
        PackageManager packageManager = context.getPackageManager();
        String packageName = context.getPackageName();
        String versionName = "unknown";
        try {
            versionName = packageManager.getPackageInfo(packageName, 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, "getVersionName:" + getVersionName());
        }
        return versionName;
    }

}
