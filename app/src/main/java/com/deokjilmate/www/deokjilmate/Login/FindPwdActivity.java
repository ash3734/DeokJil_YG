package com.deokjilmate.www.deokjilmate.Login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.deokjilmate.www.deokjilmate.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

//여긴 비번 찾는 페이지
public class FindPwdActivity extends AppCompatActivity {


    @BindView(R.id.FindPwd_toobarImg)
    ImageView toobarImage;

    @BindView(R.id.FindPwd_backImage)
    ImageButton backButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_find_pwd);
        ButterKnife.bind(this);

        Glide.with(this).load(R.drawable.toolbar).into(toobarImage);
        Glide.with(this).load(R.drawable.meta).into(backButton);

    }

    @OnClick(R.id.FindPwd_backImage)
    public void ClickBack()
    {
        Intent intent = new Intent(getApplicationContext(), LoginSelectActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.FindPwd_check)
    public void clickCheck()
    {
        
    }


}
