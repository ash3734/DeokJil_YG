package com.deokjilmate.www.deokjilmate.MyPage.AddSinger;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.deokjilmate.www.deokjilmate.MyPage.MyPageActivity;
import com.deokjilmate.www.deokjilmate.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddSingerActivity extends AppCompatActivity {

    @BindView(R.id.MyPage_AddSinger_toolbar)
    ImageView toolbarImage;

    @BindView(R.id.MyPage_AddSinger_backImage)
    ImageButton backButton;

    //@BindView(R.id.)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mypage_add_singer);
        ButterKnife.bind(this);
        Glide.with(this).load(R.drawable.toolbar).into(toolbarImage);
        Glide.with(this).load(R.drawable.meta).into(backButton);


    }

    @OnClick(R.id.MyPage_AddSinger_backImage)
    public void ClickBack()
    {
        Intent intent = new Intent(getApplicationContext(), MyPageActivity.class);
        startActivity(intent);
    }
}
