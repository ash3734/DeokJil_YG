package com.deokjilmate.www.deokjilmate.Login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.deokjilmate.www.deokjilmate.R;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.fabric.sdk.android.Fabric;

public class MainLoginActivity extends AppCompatActivity {

    @BindView(R.id.MainLogin_logo)
    ImageView logoImage;
    private static final String TWITTER_KEY = "ZPJ66hrJ8nJpMKRS297O9BgGQ ";
    private static final String TWITTER_SECRET = "Kb41OH7xsJfEZ3Rzf9rCiBLE6mz47GMtiJBdCcbuYmtIDqCLHu";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_main_login);
        ButterKnife.bind(this);

        //logoImage = (ImageView)findViewById(R.id.MainLogin_logo);
        Glide.with(this).load(R.drawable.output).into(logoImage);
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig));


    }

    //첫 화면에서 로그인 버튼 눌렀을 때
    @OnClick(R.id.MainLogin_login)
    public void LoginEvent()
    {
        startActivity(new Intent(getApplicationContext(), LoginSelectActivity.class));
    }

    //첫 화면에서 회원가입 버튼 눌렀을 때
    @OnClick(R.id.MainLogin_sign)
    public void SignEvent()
    {
        startActivity(new Intent(getApplicationContext(), SignActivity.class));
    }
}
