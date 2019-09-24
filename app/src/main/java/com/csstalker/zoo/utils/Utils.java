package com.csstalker.zoo.utils;


public class Utils {

    private static Utils instance = new Utils();

    private Utils() {
    }

    public static Utils getInstance() {
        if (instance == null)
            instance = new Utils();

        return instance;
    }

    public String checkDisplayText(String text) {
        if (text == null)
            return "";
        else
            return text.trim();
    }


}
