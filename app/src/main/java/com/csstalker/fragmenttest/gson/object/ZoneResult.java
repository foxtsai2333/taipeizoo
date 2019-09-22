package com.csstalker.fragmenttest.gson.object;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ZoneResult {

    @SerializedName("limit")
    public int limit;

    @SerializedName("offset")
    public int offset;

    @SerializedName("count")
    public int count;

    @SerializedName("sort")
    public String sort;

    @SerializedName("results")
    public List<ZoneData> zoneList;
}
