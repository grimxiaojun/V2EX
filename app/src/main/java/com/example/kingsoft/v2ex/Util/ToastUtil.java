package com.example.kingsoft.v2ex.Util;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by kingsoft on 2017/8/18.
 */

public class ToastUtil {

    private static Context mContext;
    public static void init(Context context)
    {
        mContext = context;
    }

    public static void showShort(CharSequence message) {
        if (ValidateUtil.isEmpty(mContext) || ValidateUtil.isEmpty(message)) return;
        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
    }

    public static void showShort(int messageRes) {
        if (ValidateUtil.isEmpty(mContext) || ValidateUtil.isEmpty(messageRes)) return;
        Toast.makeText(mContext, messageRes, Toast.LENGTH_SHORT).show();
    }
}
