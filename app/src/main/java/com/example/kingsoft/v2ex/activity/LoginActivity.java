package com.example.kingsoft.v2ex.activity;

/**
 * Created by kingsoft on 2017/8/15.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.kingsoft.v2ex.R;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;



public class LoginActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, View.OnClickListener {

    private GoogleApiClient mGoogleApiClient;
    private SignInButton sign_in_button;
    private static int RC_SIGN_IN=10001;
    private TextView tv_1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        GoogleSignInOptions gso = new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestId()
                .build();

        mGoogleApiClient = new GoogleApiClient
                .Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

//        tv_1 = (TextView) findViewById(R.id.tv_1);
//        sign_in_button = (SignInButton) findViewById(R.id.sign_in_button);
//        sign_in_button.setSize(SignInButton.SIZE_STANDARD);
//        sign_in_button.setScopes(gso.getScopeArray());
//        sign_in_button.setOnClickListener(this);
    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    private void handleSignInResult(GoogleSignInResult result){
        if(result.isSuccess()){
            GoogleSignInAccount acct = result.getSignInAccount();
            if(acct!=null){
                //此处将信息放入侧滑栏中
                Intent intent = new Intent();
                intent.putExtra("user_name", acct.getDisplayName());
                intent.putExtra("email", acct.getEmail());
                intent.putExtra("user_icon", acct.getPhotoUrl());
                intent.putExtra("user_id", acct.getId());
                setResult(RESULT_OK, intent);
                finish();
            }
        }else{
            tv_1.setText("登录失败");
            Log.i("jason_wang", "没有成功"+result.getStatus());
        }
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//            case R.id.sign_in_button: {
//                signIn();
//                break;
//            }
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.i("jason_wang","google登录-->onConnected,bundle=="+bundle);
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.i("jason_wang","google登录-->onConnectionSuspended,i=="+i);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.i("jason_wang","google登录-->onConnectionFailed,connectionResult=="+connectionResult);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mGoogleApiClient!=null&&mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i("jason_wang", "requestCode==" + requestCode + ",resultCode==" + resultCode + ",data==" + data);
        if(requestCode==RC_SIGN_IN){
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }


}
