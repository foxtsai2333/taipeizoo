package com.csstalker.zoo.gson;

import android.util.Log;

import com.google.gson.Gson;

public class GsonTool {
    private static final String TAG = GsonTool.class.getSimpleName();
    private static GsonTool instance;

    public static GsonTool getInstance() {
        if (instance == null)
            instance = new GsonTool();

        return instance;
    }

    public String objectToString(Object object) {
        try {
            Gson gson = new Gson();
            String json = gson.toJson(object);
            Log.d(TAG, "objectToString: " + json);
            return json;
        } catch (Exception e) {
            Log.e(TAG, "objectToString: " + e.getMessage());
            return null;
        }
    }

    public Object stringToObject(String json, Class<?> c) {
        try {
            Gson gson = new Gson();
            return gson.fromJson(json, c);
        } catch (Exception e) {
            Log.e(TAG, "stringToObject: " + e.getMessage());
            return null;
        }
    }

}
