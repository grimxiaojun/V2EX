package com.example.kingsoft.v2ex.Util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by kingsoft on 2017/8/18.
 */

public class ApiErrorUtil {
    public static String getErrorMsg(String response) {
        Pattern errorPattern = Pattern.compile("<div class=\"problem\">(.*)</div>");
        Matcher errorMatcher = errorPattern.matcher(response);
        String errorContent;
        if (errorMatcher.find()) {
            errorContent = errorMatcher.group(1).replaceAll("<[^>]+>", "");
        } else {
            errorContent = null;
        }
        return errorContent;
    }

}
