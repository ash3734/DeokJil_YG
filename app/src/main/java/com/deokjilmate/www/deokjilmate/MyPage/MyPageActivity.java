package com.deokjilmate.www.deokjilmate.MyPage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.deokjilmate.www.deokjilmate.MyPage.AddSinger.AddSingerActivity;
import com.deokjilmate.www.deokjilmate.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyPageActivity extends AppCompatActivity {

    @BindView(R.id.MyPageMain_toolbar)
    ImageView toolbarImage;

    @BindView(R.id.MyPage_plusSub)
    ImageButton plusSub;

    @BindView(R.id.MyPage_mainSingerImage)
    ImageView mainImage;

    @BindView(R.id.MyPage_rankImage)
    ImageView rankImage;

    RequestManager requestManager_singer;
    RequestManager requestManager_rank;
    ArrayList<MyPageItemData> myPageItemDatas;
    LinearLayoutManager linearLayoutManager;
    RecyclerView recyclerView;
    MyPageAdapter myPageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mypage_main);
        ButterKnife.bind(this);
        Glide.with(this).load(R.drawable.toolbar).into(toolbarImage);

        requestManager_singer = Glide.with(this);
        requestManager_rank = Glide.with(this);

        recyclerView = (RecyclerView) findViewById(R.id.MyPage_subRecycle);
        recyclerView.setHasFixedSize(true);
        //recyclerView.get
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayout.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);


        final ScrollView scrollview = ((ScrollView) findViewById(R.id.MyPage_scrollView));
        scrollview.post(new Runnable() {
            @Override
            public void run() {
                scrollview.fullScroll(ScrollView.FOCUS_UP);
            }
        });


        //TODO : 통신 이후 이 부분 바뀌어야 함.
        //메인
        Glide.with(this).load(R.drawable.toolbar).into(mainImage);
        Glide.with(this).load(R.drawable.toolbar).into(rankImage);



        //서브
        myPageItemDatas = new ArrayList<MyPageItemData>();
        myPageItemDatas.add(new MyPageItemData(R.drawable.meta, R.drawable.meta, "aaaa", "1234"));
        myPageItemDatas.add(new MyPageItemData(R.drawable.meta, R.drawable.meta, "bbbb", "3456"));

        myPageAdapter = new MyPageAdapter(requestManager_singer, requestManager_rank, myPageItemDatas);

        recyclerView.setAdapter(myPageAdapter);


    }

    @OnClick(R.id.MyPage_plusSub)
    public void plusSub()
    {
        Intent intent = new Intent(getApplicationContext(), AddSingerActivity.class);
        startActivity(intent);
    }
}
