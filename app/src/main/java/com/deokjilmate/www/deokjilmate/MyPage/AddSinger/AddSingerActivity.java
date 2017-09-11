package com.deokjilmate.www.deokjilmate.MyPage.AddSinger;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.deokjilmate.www.deokjilmate.AllSinger.AllSingerDetails;
import com.deokjilmate.www.deokjilmate.AllSinger.AllSingerRanking;
import com.deokjilmate.www.deokjilmate.MyPage.EditSinger.EditSingerActivity;
import com.deokjilmate.www.deokjilmate.R;
import com.deokjilmate.www.deokjilmate.SharedPrefrernceController;
import com.deokjilmate.www.deokjilmate.SingerList;
import com.deokjilmate.www.deokjilmate.application.ApplicationController;
import com.deokjilmate.www.deokjilmate.network.NetworkService;
import com.tsengvn.typekit.TypekitContextWrapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddSingerActivity extends AppCompatActivity {


    @BindView(R.id.MyPage_AddSinger_backImage)
    ImageButton backButton;


    @BindView(R.id.MyPage_AddSinger_recycle)
    RecyclerView recyclerView;

    @BindView(R.id.MyPage_AddSinger_search)
    EditText search;

    @BindView(R.id.MyPage_AddSinger_clear)
    ImageView clear;

    @BindView(R.id.MyPage_AddSinger_favoriteText)
    TextView favorite;
    //ArrayList<AddSingerItemData> addSingerItemDatas;
    //@BindView(R.id.)

    private LinearLayoutManager linearLayoutManager;
    private RequestManager requestManager;
    //private ArrayList<AddSingerItemData> setSingerItemDatas;//추천목록
    private ArrayList<AddSingerItemData> allSingerList;//전체목록
    private ArrayList<AddSingerItemData> addSingerItemDatas;//검색 목록
    private ArrayList<AllSingerDetails> allSingerDetails;

    private AddsingerAdapter addsingerAdapter;
    private HashMap<String, String> singerPNData;

    private NetworkService networkService;
    private SingerList singerList;
    private String firebaseToken;
    private Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mypage_add_singer);
        ButterKnife.bind(this);
        Glide.with(this).load(R.drawable.topbar_back).into(backButton);

        networkService = ApplicationController.getInstance().getNetworkService();

        requestManager = Glide.with(this);
        recyclerView.setHasFixedSize(true);
        //recyclerView.get
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayout.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        singerPNData = new HashMap<>();
        allSingerList = new ArrayList<AddSingerItemData>();
        addSingerItemDatas = new ArrayList<AddSingerItemData>();
        allSingerDetails = new ArrayList<AllSingerDetails>();
        //singerList = new SingerList();
        firebaseToken = SharedPrefrernceController.getFirebaseToken(AddSingerActivity.this);
        context = this;

        Call<AllSingerRanking> setSingerRankingCall = networkService.setSingerRanking();
        setSingerRankingCall.enqueue(new Callback<AllSingerRanking>() {
            @Override
            public void onResponse(Call<AllSingerRanking> call, Response<AllSingerRanking> response) {
                if(response.isSuccessful()) {
                    allSingerDetails = response.body().data;
                    for(int i = 0; i<allSingerDetails.size(); i++)
                    {
                        allSingerList.add(new AddSingerItemData(i+1, allSingerDetails.get(i).getSinger_id(), allSingerDetails.get(i).getSinger_img(),
                                allSingerDetails.get(i).getSinger_name(), R.drawable.addgasu_add, R.drawable.my_maingasu));
                        addSingerItemDatas.add(new AddSingerItemData(i+1, allSingerDetails.get(i).getSinger_id(), allSingerDetails.get(i).getSinger_img(),
                                allSingerDetails.get(i).getSinger_name(), R.drawable.addgasu_add, R.drawable.my_maingasu));
                    }
                    addsingerAdapter = new AddsingerAdapter(getApplicationContext(), requestManager, allSingerList, SingerList.getList(),
                            networkService, firebaseToken, addSingerItemDatas, context);
                    recyclerView.setAdapter(addsingerAdapter);

                }
            }
            @Override
            public void onFailure(Call<AllSingerRanking> call, Throwable t) {

            }
        });


        search.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                addsingerAdapter.search = true;
                String text = search.getText().toString().toLowerCase(Locale.getDefault());
                if(text.length() == 0) {
                    addsingerAdapter.search = false;
                    clear.setVisibility(View.GONE);
                    favorite.setVisibility(View.VISIBLE);
                }else{
                    favorite.setVisibility(View.GONE);
                    clear.setVisibility(View.VISIBLE);
                }
                addsingerAdapter.filter(text);
            }
            public void afterTextChanged(Editable s) {
            }
        });

    }

    @OnClick(R.id.MyPage_AddSinger_backImage)
    public void ClickBack()
    {
        Intent intent = new Intent(getApplicationContext(), EditSingerActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.MyPage_AddSinger_clear)
    public void clearEdit(){
        search.setText("");
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(), EditSingerActivity.class);
        startActivity(intent);
    }


}
