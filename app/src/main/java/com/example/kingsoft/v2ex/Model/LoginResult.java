package com.example.kingsoft.v2ex.Model;

/**
 * Created by kingsoft on 2017/8/18.
 */

public class LoginResult {

    private String userId;
    private String userAvatar;
    private String message;

    public LoginResult() {
    }

    public LoginResult(String userId, String userAvatar) {
        this.userId = userId;
        this.userAvatar = userAvatar;
    }


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserAvatar() {
        return userAvatar;
    }

    public void setUserAvatar(String userAvatar) {
        this.userAvatar = userAvatar;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "LoginResult{" +
                "userId='" + userId + '\'' +
                ", userAvatar='" + userAvatar + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
