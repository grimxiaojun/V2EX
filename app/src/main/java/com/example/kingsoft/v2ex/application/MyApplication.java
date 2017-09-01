package com.example.kingsoft.v2ex.application;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import com.example.kingsoft.v2ex.Model.SharedPreferencesUtils;
import com.example.kingsoft.v2ex.Model.User;
import com.example.kingsoft.v2ex.Util.ToastUtil;

/**
 * Created by kingsoft on 2017/8/18.
 */

public class MyApplication extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();

        context = getApplicationContext();
        ToastUtil.init(this);
        SharedPreferencesUtils.init(this);
        User.init();
    }

    public static Context getContextObject() {
        return context;
    }



}
