package com.deokjilmate.www.deokjilmate.Login;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.deokjilmate.www.deokjilmate.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SetSingerActivity extends AppCompatActivity {

    @BindView(R.id.SetSinger_topImage)
    ImageView toobarImage;

    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    RequestManager requestManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_singer);
        ButterKnife.bind(this);

        Glide.with(this).load(R.drawable.toolbar).into(toobarImage);

    }
}
