package com.csstalker.fragmenttest.app;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.csstalker.fragmenttest.R;

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

}
