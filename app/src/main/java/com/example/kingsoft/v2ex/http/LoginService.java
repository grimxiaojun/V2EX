package com.example.kingsoft.v2ex.http;

import com.example.kingsoft.v2ex.Constants.ServerUrl;
import com.example.kingsoft.v2ex.Util.CustomCookieJar;
import com.example.kingsoft.v2ex.Util.PersistentCookieStore;
import com.example.kingsoft.v2ex.Util.StringConverter;
import com.example.kingsoft.v2ex.application.MyApplication;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by kingsoft on 2017/8/18.
 */

public class LoginService {

    private static LoginService mInstance;
    private Gson mGson;
    private OkHttpClient mClient;
    private UserApi mUserApi;
    private PersistentCookieStore mPersistentCookieStore;

    public static LoginService getInstance() {
        if (mInstance == null) {
            mInstance = new LoginService();
        }
        return mInstance;
    }

    public PersistentCookieStore getCookieStore(){
        return mPersistentCookieStore;
    }

    public LoginService() {

        mPersistentCookieStore = new PersistentCookieStore(MyApplication.getContextObject());
        mClient = new OkHttpClient.Builder()
                .cookieJar(new CustomCookieJar(new CookieManager(mPersistentCookieStore, CookiePolicy.ACCEPT_ALL)))
                .readTimeout(12, TimeUnit.SECONDS)
                .addInterceptor(htmlInterceptor)
                .build();

        mGson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .serializeNulls()
                .create();
    }

    public boolean clearCookies() {
        return mPersistentCookieStore.removeAll();
    }

    public UserApi auth() {
        if (mUserApi == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(ServerUrl.V2EX_BASE_URL)
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .addConverterFactory(new StringConverter())
                    .addConverterFactory(GsonConverterFactory.create(mGson))
                    .client(mClient)
                    .build();
            mUserApi = retrofit.create(UserApi.class);
        }
        return mUserApi;
    }

    //这个interceptor添加顺序要在loggingInterceptor之前，否则无效
    private Interceptor htmlInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request newRequest = chain.request().newBuilder()
                    .addHeader("Origin", "https://www.v2ex.com")
                    .addHeader("Content-Type", "application/x-www-form-urlencoded")
                    .build();
            return chain.proceed(newRequest);
        }
    };
}