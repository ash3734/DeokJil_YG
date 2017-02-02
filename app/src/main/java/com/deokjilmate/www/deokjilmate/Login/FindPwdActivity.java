package com.deokjilmate.www.deokjilmate.Login;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.deokjilmate.www.deokjilmate.R;

import butterknife.BindView;
import butterknife.ButterKnife;

//여긴 비번 찾는 페이지
public class FindPwdActivity extends AppCompatActivity {

    @BindView(R.id.FindPwd_toobarImg)
    ImageView toobarImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_pwd);
        ButterKnife.bind(this);

        Glide.with(this).load(R.drawable.meta).into(toobarImage);
    }
}
