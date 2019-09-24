package com.csstalker.zoo.gson.object;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PlanetResult {

    @SerializedName("results")
    public List<PlanetData> planetList;
}
