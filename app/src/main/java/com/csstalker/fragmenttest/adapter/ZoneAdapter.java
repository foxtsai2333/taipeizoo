package com.csstalker.fragmenttest.adapter;

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
import com.bumptech.glide.request.RequestOptions;
import com.csstalker.fragmenttest.R;
import com.csstalker.fragmenttest.gson.object.ZoneData;
import com.csstalker.fragmenttest.gson.object.ZoneResult;

import java.util.List;


public class ZoneAdapter extends RecyclerView.Adapter<ZoneAdapter.ZoneViewHolder> {

    private Context context;
    private List<ZoneData> zoneList;
    private OnZoneItemClickListener zicListener;
    private final RequestOptions ro;

    public ZoneAdapter(Context context, List<ZoneData> zoneList) {
        this.context = context;
        this.zoneList = zoneList;
        ro = new RequestOptions().placeholder(R.drawable.placeholder_cat);
    }

    public void setZoneItemClickListener(OnZoneItemClickListener zicListener) {
        this.zicListener = zicListener;
    }

    @NonNull
    @Override
    public ZoneViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.zone_listitem, viewGroup, false);
        return new ZoneViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ZoneViewHolder holder, int position) {
        final ZoneData zone = zoneList.get(position);
        // 圖片
        String image_url = zone.img;
        if (image_url != null && !"".equals(image_url)) {
            Glide.with(context)
                    .load(image_url)
                    .apply(ro)
                    .into(holder.zoneImage);
        }
        // 標題
        holder.titleText.setText(zone.name);
        // 介紹
        holder.descText.setText(zone.info);
        // 休館資訊
        String memo = zone.memo;
        if (memo == null || "".equals(memo))
            memo = context.getString(R.string.empty_open_hour_text);
        holder.openHourText.setText(memo);
        // set click listener
        holder.itemLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (zicListener != null)
                    zicListener.onClickZone(zone);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (zoneList == null)
            return 0;
        else
            return zoneList.size();
    }

    public class ZoneViewHolder extends RecyclerView.ViewHolder {

        RelativeLayout itemLayout;
        ImageView zoneImage;
        TextView titleText, descText, openHourText;

        public ZoneViewHolder(@NonNull View itemView) {
            super(itemView);
            itemLayout = itemView.findViewById(R.id.zone_item_layout);
            zoneImage = itemView.findViewById(R.id.zone_image);
            titleText = itemView.findViewById(R.id.zone_title_text);
            descText = itemView.findViewById(R.id.zone_desc_text);
            openHourText = itemView.findViewById(R.id.zone_open_hour_desc_text);
        }
    }

}