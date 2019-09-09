package com.csstalker.fragmenttest.gson.object;

import com.google.gson.annotations.SerializedName;

public class PlanetData {

    // 圖片 (如果有的話)
    @SerializedName("F_Pic01_URL")
    public String img;

    // 中文名
    @SerializedName("F_Name_Ch")
    public String chineseName;

    // 英文名
    @SerializedName("F_Name_En")
    public String englishName;

    // 別名
    @SerializedName("F_AlsoKnown")
    public String alsoKnown;

    // 簡介
    @SerializedName("F_Brief")
    public String brief;

    // 辨認方式
    @SerializedName("F_Feature")
    public String feature;

    // 功能性
    @SerializedName("F_Function＆Application")
    public String funtional;

    // 更新日期
    @SerializedName("F_Update")
    public String updateDate;

}
