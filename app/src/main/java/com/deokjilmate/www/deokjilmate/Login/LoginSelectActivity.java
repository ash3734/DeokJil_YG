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
//여기는 로그인 방법 결정하는 곳(구글, 페북, 트위터, 커스텀)
public class LoginSelectActivity extends AppCompatActivity {


    @BindView(R.id.LoginSelect_topImage)
    ImageView toobarImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_select);
        ButterKnife.bind(this);

    //toolbar = (Toolbar)findViewById(R.id.)
        Glide.with(this).load(R.drawable.meta).into(toobarImage);
    }

    @OnClick(R.id.LoginSelect_findPwd)
    public void FindPwdEvent()
    {
        startActivity(new Intent(getApplicationContext(), FindPwdActivity.class));
    }
}
