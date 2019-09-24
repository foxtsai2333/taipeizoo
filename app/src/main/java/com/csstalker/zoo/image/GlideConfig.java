package com.csstalker.zoo.image;

import com.bumptech.glide.request.RequestOptions;
import com.csstalker.zoo.R;

public class GlideConfig {

    private static GlideConfig instance;
    private final RequestOptions ro;

    private GlideConfig() {
        ro = new RequestOptions().placeholder(R.drawable.placeholder_cat);
    }

    public static GlideConfig getInstance() {
        if (instance == null)
            instance = new GlideConfig();

        return instance;
    }

    public RequestOptions getGlideOption() {
        return ro;
    }


}
