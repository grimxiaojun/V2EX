package com.example.kingsoft.v2ex.Model;

import com.example.kingsoft.v2ex.http.LoginService;

/**
 * Created by kingsoft on 2017/8/18.
 */

public class User {

    private static User currentUser;

    public String message;

    public String userId;

    public String userAvatar;

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void init(){
        if (null == currentUser && !"".equals((String)SharedPreferencesUtils.getParam("user_id" , ""))){
            User v2exUser = new User();
            v2exUser.userId = (String) SharedPreferencesUtils.getParam("user_id" , "");
            v2exUser.userAvatar = (String) SharedPreferencesUtils.getParam("user_avatar" , "");
            v2exUser.message = (String) SharedPreferencesUtils.getParam("message" , "");
            currentUser = v2exUser;
        }
    }

    public static void saveCurrentUser(LoginResult loginResult){
        SharedPreferencesUtils.setParam("user_id" , loginResult.getUserId());
        SharedPreferencesUtils.setParam("user_avatar" , loginResult.getUserAvatar());
        SharedPreferencesUtils.setParam("message" , loginResult.getMessage());
        User v2exUser = new User();
        v2exUser.userId = loginResult.getUserId();
        v2exUser.userAvatar = loginResult.getUserAvatar();
        v2exUser.message = loginResult.getMessage();
        currentUser = v2exUser;
    }

    public static void logout(){
        SharedPreferencesUtils.clear();
        currentUser = null;

    }
}
