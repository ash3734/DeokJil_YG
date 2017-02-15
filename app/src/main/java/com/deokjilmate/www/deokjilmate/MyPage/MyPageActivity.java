package com.deokjilmate.www.deokjilmate.MyPage;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.deokjilmate.www.deokjilmate.R;

import butterknife.BindView;

public class MyPageActivity extends AppCompatActivity {

    @BindView(R.id.MyPageMain_toolbar)
    ImageView toolbarImage;

    RequestManager requestManager_singer;
    RequestManager requestManager_rank;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mypage_main);
        Glide.with(this).load(R.drawable.toolbar).into(toolbarImage);


    }
}
