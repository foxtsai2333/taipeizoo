package com.csstalker.zoo.gson.object;

import com.google.gson.annotations.SerializedName;

public class ZoneData {

    // 圖片
    @SerializedName("E_Pic_URL")
    public String img;

    // 館名
    @SerializedName("E_Name")
    public String name;

    // 簡介
    @SerializedName("E_Info")
    public String info;

    // 分區
    @SerializedName("E_Category")
    public String category;

    // 休館資料 (空白顯示 "無休館資料")
    @SerializedName("E_Memo")
    public String memo;

    // 官網網址
    @SerializedName("E_URL")
    public String webUrl;

}
