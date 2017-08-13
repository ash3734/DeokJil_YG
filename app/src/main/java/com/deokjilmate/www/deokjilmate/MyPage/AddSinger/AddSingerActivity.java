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
import com.deokjilmate.www.deokjilmate.AllSinger.AllSingerDetails;
import com.deokjilmate.www.deokjilmate.AllSinger.AllSingerRanking;
import com.deokjilmate.www.deokjilmate.MyPage.MyPageActivity;
import com.deokjilmate.www.deokjilmate.R;
import com.deokjilmate.www.deokjilmate.SingerList;
import com.deokjilmate.www.deokjilmate.application.ApplicationController;
import com.deokjilmate.www.deokjilmate.network.NetworkService;

import java.util.ArrayList;
import java.util.HashMap;

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
    //@BindView(R.id.)

    private LinearLayoutManager linearLayoutManager;
    private RequestManager requestManager;
    //private ArrayList<AddSingerItemData> setSingerItemDatas;//추천목록
    private ArrayList<AddSingerItemData> allSingerList;//전체목록
    private ArrayList<AllSingerDetails> allSingerDetails;
    private AddsingerAdapter addsingerAdapter;
    private HashMap<String, String> singerPNData;

    private NetworkService networkService;
    private SingerList singerList;


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
        singerPNData = new HashMap<>();
        allSingerList = new ArrayList<AddSingerItemData>();
        allSingerDetails = new ArrayList<AllSingerDetails>();
        //singerList = new SingerList();

        Call<AllSingerRanking> setSingerRankingCall = networkService.setSingerRanking();
        setSingerRankingCall.enqueue(new Callback<AllSingerRanking>() {
            @Override
            public void onResponse(Call<AllSingerRanking> call, Response<AllSingerRanking> response) {
                if(response.isSuccessful()) {
                    allSingerDetails = response.body().result;
                    for(int i = 0; i<allSingerDetails.size(); i++)
                    {
                        allSingerList.add(new AddSingerItemData(allSingerDetails.get(i).getSinger_id(), allSingerDetails.get(i).getSinger_img(),
                                allSingerDetails.get(i).getSinger_name(), R.drawable.meta));
                    }
                    addsingerAdapter = new AddsingerAdapter(getApplicationContext(), requestManager, allSingerList, SingerList.getList(), networkService);
                    recyclerView.setAdapter(addsingerAdapter);

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
