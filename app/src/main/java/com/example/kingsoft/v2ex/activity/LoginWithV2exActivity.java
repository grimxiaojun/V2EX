package com.example.kingsoft.v2ex.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;

import com.example.kingsoft.v2ex.Model.LoginResult;
import com.example.kingsoft.v2ex.R;
import com.example.kingsoft.v2ex.Util.ApiErrorUtil;
import com.example.kingsoft.v2ex.Util.JsoupUtil;
import com.example.kingsoft.v2ex.Util.LogUtil;
import com.example.kingsoft.v2ex.Util.ToastUtil;
import com.example.kingsoft.v2ex.Util.ValidateUtil;
import com.example.kingsoft.v2ex.http.LoginService;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.OnClick;
import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

import static com.example.kingsoft.v2ex.R.id.account;

/**
 * Created by kingsoft on 2017/8/18.
 */

public class LoginWithV2exActivity extends BaseSwipeRefreshActivity {

    @Bind(account)
    AutoCompleteTextView mAccountView;

    @Bind(R.id.password)
    EditText mPasswordView;

    @Bind(R.id.login_progress)
    View mProgressView;

    @Bind(R.id.login_form)
    View mLoginFormView;

    @OnClick(R.id.sign_in_button_with_v2ex)
    public void login(){
        attemptLogin();
    }

    public static Intent getIntent(Context context) {
        Intent intent = new Intent(context, LoginWithV2exActivity.class);
        return intent;
    }



    Observer<LoginResult> observer = new Observer<LoginResult>() {
        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {
            showProgress(false);
            ToastUtil.showShort("登录失败");
            Toast.makeText(LoginWithV2exActivity.this, "登录失败", Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onNext(LoginResult items) {
            showProgress(false);
            if (ValidateUtil.isNotEmpty(items) && ValidateUtil.isNotEmpty(items.getUserId())) {
                ToastUtil.showShort("登录成功");
                Toast.makeText(LoginWithV2exActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                intent.putExtra("userName", items.getUserId());
                intent.putExtra("userAvatar", items.getUserAvatar());
                setResult(RESULT_OK,intent);
                finish();

            }else{
                Toast.makeText(LoginWithV2exActivity.this, "登录失败", Toast.LENGTH_SHORT).show();
            }
            //Toast.makeText(LoginWithV2exActivity.this, "fuckcode", Toast.LENGTH_SHORT).show();
            Log.i("fuck", "items" + items);
        }
    };

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void initViews() {

    }

    private void login(final String username, final String password) {
        Subscription subscription = LoginService.getInstance().auth().login()
                .map(new Func1<String, HashMap>() {
                    @Override
                    public HashMap call(String stringResponse) {
                        return JsoupUtil.parseUserNameAndPwd(stringResponse, username, password);
                    }
                }).flatMap(new Func1<HashMap, Observable<String>>() {
                    @Override
                    public Observable<String> call(HashMap requestMap) {
                        return LoginService.getInstance().auth().postLogin(requestMap);
                    }
                }).map(new Func1<String, LoginResult>() {
                    @Override
                    public LoginResult call(String response) {
                        String errorMsg = ApiErrorUtil.getErrorMsg(response);
                        if (errorMsg == null) {
                            return JsoupUtil.parseLoginResult(response);
                        } else {
                            LoginResult result = new LoginResult();
                            result.setMessage(errorMsg);
                            return result;
                        }
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
        addSubscription(subscription);
    }





    private void attemptLogin() {
        mAccountView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String account = mAccountView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;
//        Toast.makeText(LoginWithV2exActivity.this, "我曹！！！", Toast.LENGTH_SHORT).show();
        // Check for a valid email address.
        if (TextUtils.isEmpty(account)) {
            mAccountView.setError(getString(R.string.error_account_required));
            focusView = mAccountView;
            cancel = true;
        }

        if (TextUtils.isEmpty(password)) {
            mPasswordView.setError(getString(R.string.error_password_required));
            focusView = mPasswordView;
            cancel = true;
        }
        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            LogUtil.i("fuckcode","我曹");
            Log.i("fuckcode", "我曹");
            Toast.makeText(LoginWithV2exActivity.this, "我曹", Toast.LENGTH_SHORT).show();
            focusView.requestFocus();
        } else {
            showProgress(true);
            login(account, password);
        }
    }


    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
        }
    }



}



