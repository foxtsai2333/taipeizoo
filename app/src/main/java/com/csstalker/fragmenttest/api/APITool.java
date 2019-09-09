package com.csstalker.fragmenttest.api;

public class APITool {

    private static APITool instance;

    public static APITool getInstance() {
        if (instance == null)
            instance = new APITool();

        return instance;
    }

    public String getZoneUrl() {
        return "https://data.taipei/opendata/datalist/apiAccess?scope=resourceAquire&rid=5a0e5fbb-72f8-41c6-908e-2fb25eff9b8a";
    }

    public String getPlanetUrl() {
        return "https://data.taipei/opendata/datalist/apiAccess?scope=resourceAquire&rid=f18de02f-b6c9-47c0-8cda-50efad621c14";
    }
}
