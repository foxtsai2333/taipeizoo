package com.csstalker.fragmenttest.app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.csstalker.fragmenttest.R;
import com.csstalker.fragmenttest.gson.GsonTool;
import com.csstalker.fragmenttest.gson.object.PlanetData;
import com.csstalker.fragmenttest.image.GlideConfig;

public class PlanetDetailActivity extends BaseActivity {

    private static final String TAG = PlanetDetailActivity.class.getSimpleName();

    // view
    private ImageView planetImage;
    private TextView planetChineseNameText;
    private TextView planetEnglishNameText;
    private TextView planetAliasNameText;
    private TextView planetDescText;
    private TextView planetIdentifyText;
    private TextView planetFunctionalText;
    private TextView updateDateText;

    // misc
    private PlanetData planet;
    private String planetStr;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planet_detail);

        planetStr = getIntent().getStringExtra("planet");

        findviews();
        init();
    }

    private void findviews() {
        planetImage = findViewById(R.id.planet_image);
        planetChineseNameText = findViewById(R.id.planet_chinese_name_text);
        planetEnglishNameText = findViewById(R.id.planet_english_name_text);
        planetAliasNameText = findViewById(R.id.planet_alias_name_text);
        planetDescText = findViewById(R.id.planet_desc_text);
        planetIdentifyText = findViewById(R.id.planet_identify_text);
        planetFunctionalText = findViewById(R.id.planet_functional_text);
        updateDateText = findViewById(R.id.update_date_text);
    }

    private void init() {
        if (planetStr == null || "".equals(planetStr))
            Toast.makeText(this, "something wrong...", Toast.LENGTH_LONG).show();
        else {
            planet = (PlanetData) GsonTool.getInstance()
                    .stringToObject(planetStr, PlanetData.class);

            // set data
            if (planet != null) {
                // 圖片
                String imageUrl = planet.img;
                if (imageUrl != null && !"".equals(imageUrl)) {
                    Glide.with(this)
                            .load(imageUrl)
                            .apply(GlideConfig.getInstance().getGlideOption())
                            .into(planetImage);
                }
                // 中文名
                planetChineseNameText.setText(checkDisplayText(planet.chineseName));
                // 英文名
                planetEnglishNameText.setText(checkDisplayText(planet.englishName));
                // 別名
                planetAliasNameText.setText(checkDisplayText(planet.alsoKnown));
                // 簡介
                planetDescText.setText(checkDisplayText(planet.brief));
                // 辨認方式
                planetIdentifyText.setText(checkDisplayText(planet.feature));
                // 功能性
                planetFunctionalText.setText(checkDisplayText(planet.funtional));
                // 更新日期
                updateDateText.setText(checkDisplayText(planet.updateDate));
            }
        }
    }



}

