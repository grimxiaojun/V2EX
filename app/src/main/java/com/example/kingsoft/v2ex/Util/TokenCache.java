package com.example.kingsoft.v2ex.Util;

import com.example.kingsoft.v2ex.Model.SharedPreferencesUtils;

/**
 * Created by kingsoft on 2017/8/18.
 */

public class TokenCache {

    public static void saveToken(String token) {
        SharedPreferencesUtils.setParam("tokenCache", token);
    }

    public static String getToken() {
        return (String) SharedPreferencesUtils.getParam("tokenCache", "");
    }


    public static void clearToken() {
        SharedPreferencesUtils.setParam("tokenCache", "");
    }

}
