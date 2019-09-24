package com.csstalker.zoo.task;

import android.os.AsyncTask;
import android.util.Log;

import com.csstalker.zoo.gson.GsonTool;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class JsonHttpTask extends AsyncTask<String, String, Object> {

    private final static String TAG = JsonHttpTask.class.getSimpleName();
    private final static int TIMEOUT = 60;

    private OkHttpClient client;
    private OnJsonTaskCompleteListener jtcListener;
    private Class<?> c;
    private RequestBody body;
    private String url;

    public JsonHttpTask(String url, Class<?> c, OnJsonTaskCompleteListener jtcListener) {
        this.c = c;
        this.url = url;
        this.jtcListener = jtcListener;
        client = new OkHttpClient.Builder()
                .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(TIMEOUT, TimeUnit.SECONDS)
                .build();
    }

    public JsonHttpTask(String url, RequestBody body, Class<?> c, OnJsonTaskCompleteListener jtcListener) {
        this.c = c;
        this.url = url;
        this.body = body;
        this.jtcListener = jtcListener;
        client = new OkHttpClient.Builder()
                .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(TIMEOUT, TimeUnit.SECONDS)
                .build();
    }

    @Override
    protected Object doInBackground(String... params) {
        try {
            Request request = getRequest(url, body);
            Response response = client.newCall(request).execute();
            String result = new String(response.body().bytes(), StandardCharsets.UTF_8);
            return GsonTool.getInstance().stringToObject(result, c);
        } catch (Exception e) {
            Log.e(TAG, "doInBackground: " + e.getMessage());
            return null;
        }
    }

    @Override
    protected void onPostExecute(Object object) {
        if (jtcListener != null)
            jtcListener.onTaskComplete(object);
    }

    private Request getRequest(String url, RequestBody body) {
        if (body == null) {
            // get
            return new Request.Builder()
                    .url(url)
                    .build();
        } else {
            // post
            return new Request.Builder()
                    .url(url)
                    .post(body)
                    .build();
        }
    }
}
