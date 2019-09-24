package com.csstalker.zoo.app;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.csstalker.zoo.R;
import com.csstalker.zoo.gson.GsonTool;
import com.csstalker.zoo.gson.object.PlanetData;
import com.csstalker.zoo.image.GlideConfig;
import com.csstalker.zoo.utils.Utils;

public class PlanetFragment extends Fragment {

    private static final String TAG = PlanetFragment.class.getSimpleName();

    private static final String ARG_PLANET = "ARG_PLANET";

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
    private String planetStr;
    private PlanetData planet;
    
    
    public PlanetFragment() {
        // Required empty public constructor
    }
    
    public static PlanetFragment newInstance(String planetStr) {
        PlanetFragment fragment = new PlanetFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PLANET, planetStr);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            planetStr = getArguments().getString(ARG_PLANET);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView");
        // inflate view
        View itemView = inflater.inflate(R.layout.fragment_planet, container, false);
        planetImage = itemView.findViewById(R.id.planet_image);
        planetChineseNameText = itemView.findViewById(R.id.planet_chinese_name_text);
        planetEnglishNameText = itemView.findViewById(R.id.planet_english_name_text);
        planetAliasNameText = itemView.findViewById(R.id.planet_alias_name_text);
        planetDescText = itemView.findViewById(R.id.planet_desc_text);
        planetIdentifyText = itemView.findViewById(R.id.planet_identify_text);
        planetFunctionalText = itemView.findViewById(R.id.planet_functional_text);
        updateDateText = itemView.findViewById(R.id.update_date_text);

        setPlanetData();

        return itemView;
    }
    
    private void setPlanetData() {
        if (planetStr == null || "".equals(planetStr))
            Toast.makeText(getContext(), "something wrong...", Toast.LENGTH_LONG).show();
        else {
            planet = (PlanetData) GsonTool.getInstance()
                    .stringToObject(planetStr, PlanetData.class);

            // set data
            if (planet != null) {
                // 顯示標題
                updateTitle(Utils.getInstance().checkDisplayText(planet.chineseName));
                // 圖片
                String imageUrl = planet.img;
                if (imageUrl != null && !"".equals(imageUrl)) {
                    Glide.with(this)
                            .load(imageUrl)
                            .apply(GlideConfig.getInstance().getGlideOption())
                            .into(planetImage);
                }
                // 中文名
                planetChineseNameText.setText(Utils.getInstance().checkDisplayText(planet.chineseName));
                // 英文名
                planetEnglishNameText.setText(Utils.getInstance().checkDisplayText(planet.englishName));
                // 別名
                planetAliasNameText.setText(Utils.getInstance().checkDisplayText(planet.alsoKnown));
                // 簡介
                planetDescText.setText(Utils.getInstance().checkDisplayText(planet.brief));
                // 辨認方式
                planetIdentifyText.setText(Utils.getInstance().checkDisplayText(planet.feature));
                // 功能性
                planetFunctionalText.setText(Utils.getInstance().checkDisplayText(planet.funtional));
                // 更新日期
                updateDateText.setText(Utils.getInstance().checkDisplayText(planet.updateDate));
            }
        }
    }

    // 設定標題
    private void updateTitle(String title) {
        try {
            getActivity().setTitle(title);
        } catch (Exception e) {
            Log.e(TAG, "updateTitle: " + e.getMessage());
        }
    }

}
