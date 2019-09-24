package com.csstalker.zoo.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.csstalker.zoo.R;
import com.csstalker.zoo.gson.object.PlanetData;
import com.csstalker.zoo.image.GlideConfig;

import java.util.List;

public class PlanetAdapter extends RecyclerView.Adapter<PlanetAdapter.PlanetViewHolder> {

    private Context context;
    private List<PlanetData> planetList;
    private OnPlanetItemClickListener planetItemListener;

    public PlanetAdapter(Context context, List<PlanetData> planetList) {
        this.context = context;
        this.planetList = planetList;
    }

    public void setOnClickPlanetItemListener(OnPlanetItemClickListener planetItemListener) {
        this.planetItemListener = planetItemListener;
    }

    @NonNull
    @Override
    public PlanetViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.planet_listitem, viewGroup, false);
        return new PlanetViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PlanetViewHolder holder, int position) {
        final PlanetData planet = planetList.get(position);
        // 圖片
        String imageUrl = planet.img;
        if (imageUrl != null && !"".equals(imageUrl)) {
            Glide.with(context)
                    .load(imageUrl)
                    .apply(GlideConfig.getInstance().getGlideOption())
                    .into(holder.planetImage);
        }
        // 名稱
        holder.titleText.setText(planet.chineseName);
        // 說明
        holder.aliasNameText.setText(planet.brief);

        // 點擊
        holder.itemLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (planetItemListener != null)
                    planetItemListener.onClickPlanet(planet);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (planetList == null)
            return 0;
        else
            return planetList.size();
    }

    public class PlanetViewHolder extends RecyclerView.ViewHolder {

        RelativeLayout itemLayout;
        ImageView planetImage;
        TextView titleText, aliasNameText;

        public PlanetViewHolder(@NonNull View itemView) {
            super(itemView);
            itemLayout = itemView.findViewById(R.id.planet_item_layout);
            planetImage = itemView.findViewById(R.id.planet_image);
            titleText = itemView.findViewById(R.id.planet_title_text);
            aliasNameText = itemView.findViewById(R.id.planet_alias_name_text);
        }
    }

}

