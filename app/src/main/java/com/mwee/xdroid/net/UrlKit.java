package com.mwee.xdroid.net;

public class UrlKit {

    public static final String API_BASE_URL = "http://gank.io/api/";

    public static final String ACTION_DATA_RESULT = "data/{type}/{number}/{page}";


    public static String getUrl(String action) {
        return new StringBuilder(API_BASE_URL).append(action).toString();
    }
}
