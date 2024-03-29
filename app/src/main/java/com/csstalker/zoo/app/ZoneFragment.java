package com.csstalker.zoo.app;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.csstalker.zoo.R;
import com.csstalker.zoo.adapter.OnPlanetItemClickListener;
import com.csstalker.zoo.adapter.PlanetAdapter;
import com.csstalker.zoo.gson.GsonTool;
import com.csstalker.zoo.gson.object.PlanetData;
import com.csstalker.zoo.gson.object.PlanetResult;
import com.csstalker.zoo.gson.object.ZoneData;
import com.csstalker.zoo.image.GlideConfig;
import com.csstalker.zoo.utils.Utils;

public class ZoneFragment extends Fragment implements OnPlanetItemClickListener {

    private static final String TAG = ZoneFragment.class.getSimpleName();

    private static final String ARG_ZONE = "ARG_ZONE";
    private static final String ARG_PLANET = "ARG_PLANET";

    // view
    private ImageView zoneImage;
    private TextView descText;
    private TextView openHourText;
    private TextView zoneTypeText;
    private TextView webText;
    private RecyclerView planetRecyclerView;
    private TextView planetEmptyText;

    private OnPlanetItemClickListener picListener;

    // misc
    private ZoneData zone;
    private PlanetResult planetResult;

    public ZoneFragment() {
        // Required empty public constructor
    }

    // 用來在 fragment 生成時帶入所需資料
    public static ZoneFragment newInstance(String zoneStr, String planetStr) {
        ZoneFragment fragment = new ZoneFragment();
        Bundle args = new Bundle();
        args.putString(ARG_ZONE, zoneStr);
        args.putString(ARG_PLANET, planetStr);
        fragment.setArguments(args);
        return fragment;
    }

    public void setOnPlanetItemClickListener(OnPlanetItemClickListener picListener) {
        this.picListener = picListener;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            String zoneStr = getArguments().getString(ARG_ZONE);
            String planetStr = getArguments().getString(ARG_PLANET);
            zone = (ZoneData) GsonTool.getInstance().stringToObject(zoneStr, ZoneData.class);
            planetResult = (PlanetResult) GsonTool.getInstance().stringToObject(planetStr, PlanetResult.class);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView");
        // inflate layout
        View itemView = inflater.inflate(R.layout.fragment_zone, container, false);
        // findview
        zoneImage = itemView.findViewById(R.id.zone_image);
        descText = itemView.findViewById(R.id.zone_desc_text);
        openHourText = itemView.findViewById(R.id.zone_open_hour_desc_text);
        zoneTypeText = itemView.findViewById(R.id.zone_type_text);
        webText = itemView.findViewById(R.id.zone_web_text);
        planetRecyclerView = itemView.findViewById(R.id.planet_recyclerview);
        planetEmptyText = itemView.findViewById(R.id.zone_planet_empty_text);

        setZoneData();
        setPlanetData();

        return itemView;
    }

    private void setZoneData() {
        if (zone == null) {
            Toast.makeText(getContext(), "something wrong...", Toast.LENGTH_LONG).show();
        } else {
            // 設定標題
            updateTitle(Utils.getInstance().checkDisplayText(zone.name));
            // 設定該館的資訊
            // 圖片
            String imageUrl = zone.img;
            if (imageUrl != null && !"".equals(imageUrl)) {
                Glide.with(this)
                        .load(imageUrl)
                        .apply(GlideConfig.getInstance().getGlideOption())
                        .into(zoneImage);
            }
            // 說明
            descText.setText(zone.info);
            // 休館資訊
            String memo = zone.memo;
            if (memo == null || "".equals(memo))
                memo = getString(R.string.empty_open_hour_text);
            openHourText.setText(memo);
            // 分區
            zoneTypeText.setText(Utils.getInstance().checkDisplayText(zone.category));
        }
    }

    private void setPlanetData() {
        // 設定 adapter
        if (planetResult == null
                || planetResult.planetList == null
                || planetResult.planetList.size() == 0) {
            planetEmptyText.setVisibility(View.VISIBLE);
        } else {
            planetEmptyText.setVisibility(View.GONE);
            PlanetAdapter adapter = new PlanetAdapter(getContext(), planetResult.planetList);
            planetRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            planetRecyclerView.setHasFixedSize(true);
            planetRecyclerView.setNestedScrollingEnabled(false);
            planetRecyclerView.setAdapter(adapter);
            adapter.setOnClickPlanetItemListener(this);
        }
    }

    @Override
    public void onClickPlanet(PlanetData planet) {
        if (picListener != null)
            picListener.onClickPlanet(planet);
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
