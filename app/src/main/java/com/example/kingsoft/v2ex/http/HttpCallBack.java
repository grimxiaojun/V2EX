package com.example.kingsoft.v2ex.http;



/**
 * Created by kingsoft on 2017/8/14.
 */

public interface HttpCallBack {

    void onSuccess(Object data);

    void onFailure(String message);

}
