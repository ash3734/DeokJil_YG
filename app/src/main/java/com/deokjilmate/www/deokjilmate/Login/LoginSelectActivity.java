package com.deokjilmate.www.deokjilmate.Login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.deokjilmate.www.deokjilmate.R;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
//여기는 로그인 방법 결정하는 곳(구글, 페북, 트위터, 커스텀)
public class LoginSelectActivity extends AppCompatActivity {

    private CallbackManager callbackManager;

    @BindView(R.id.LoginSelect_topImage)
    ImageView toobarImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_select);
        ButterKnife.bind(this);
        FacebookSdk.sdkInitialize(this.getApplicationContext());
        callbackManager = CallbackManager.Factory.create();

    //toolbar = (Toolbar)findViewById(R.id.)
        Glide.with(this).load(R.drawable.meta).into(toobarImage);
    }

    @OnClick(R.id.LoginSelect_findPwd)
    public void FindPwdEvent()
    {
        startActivity(new Intent(getApplicationContext(), FindPwdActivity.class));
    }

    @OnClick(R.id.LoginSelect_facebook)
    public void LoginFacebook()//페이스북 로그인 버튼
    {
        LoginManager.getInstance().logInWithReadPermissions(LoginSelectActivity.this,
                Arrays.asList("public_profile", "email"));

        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });

    }
    @OnClick(R.id.LoginSelect_twitter)
    public void LoginTwitter()//트위터 로그인 버튼
    {

    }
    @OnClick(R.id.LoginSelect_google)
    public void LoginGoogle()//구글 로그인 버튼
    {

    }
}
