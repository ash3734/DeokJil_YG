package com.deokjilmate.www.deokjilmate.MyPage.AddSinger;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.deokjilmate.www.deokjilmate.AllSinger.AllSingerRanking;
import com.deokjilmate.www.deokjilmate.MyPage.MyPageActivity;
import com.deokjilmate.www.deokjilmate.R;
import com.deokjilmate.www.deokjilmate.application.ApplicationController;
import com.deokjilmate.www.deokjilmate.network.NetworkService;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddSingerActivity extends AppCompatActivity {

    @BindView(R.id.MyPage_AddSinger_toolbar)
    ImageView toolbarImage;

    @BindView(R.id.MyPage_AddSinger_backImage)
    ImageButton backButton;


    @BindView(R.id.MyPage_AddSinger_recycle)
    RecyclerView recyclerView;

    ArrayList<AddSingerItemData> addSingerItemDatas;

    LinearLayoutManager linearLayoutManager;
    RequestManager requestManager;

    NetworkService networkService;
    //@BindView(R.id.)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mypage_add_singer);
        ButterKnife.bind(this);
        Glide.with(this).load(R.drawable.toolbar).into(toolbarImage);
        Glide.with(this).load(R.drawable.meta).into(backButton);

        networkService = ApplicationController.getInstance().getNetworkService();

        requestManager = Glide.with(this);
        recyclerView.setHasFixedSize(true);
        //recyclerView.get
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayout.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);


        Call<AllSingerRanking> setSingerRankingCall = networkService.setSingerRanking();
        setSingerRankingCall.enqueue(new Callback<AllSingerRanking>() {
            @Override
            public void onResponse(Call<AllSingerRanking> call, Response<AllSingerRanking> response) {
                if(response.isSuccessful()) {

                }
            }
            @Override
            public void onFailure(Call<AllSingerRanking> call, Throwable t) {

            }
        });


    }

    @OnClick(R.id.MyPage_AddSinger_backImage)
    public void ClickBack()
    {
        Intent intent = new Intent(getApplicationContext(), MyPageActivity.class);
        startActivity(intent);
    }
}
