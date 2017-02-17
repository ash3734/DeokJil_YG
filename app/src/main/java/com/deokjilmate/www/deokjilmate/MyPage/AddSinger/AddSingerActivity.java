package com.deokjilmate.www.deokjilmate.MyPage.AddSinger;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.deokjilmate.www.deokjilmate.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddSingerActivity extends AppCompatActivity {

    @BindView(R.id.MyPage_AddSinger_toolbar)
    ImageView toolbarImage;

    //@BindView(R.id.)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mypage_add_singer);
        ButterKnife.bind(this);
        Glide.with(this).load(R.drawable.toolbar).into(toolbarImage);


    }
}
