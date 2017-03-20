package com.deokjilmate.www.deokjilmate.Login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.deokjilmate.www.deokjilmate.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainLoginActivity extends AppCompatActivity {

    @BindView(R.id.MainLogin_logo)
    ImageView logoImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_main_login);
        ButterKnife.bind(this);

        //logoImage = (ImageView)findViewById(R.id.MainLogin_logo);
        Glide.with(this).load(R.drawable.output).into(logoImage);



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
