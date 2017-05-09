package com.deokjilmate.www.deokjilmate.Login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.deokjilmate.www.deokjilmate.AllSinger.AllSingerDetails;
import com.deokjilmate.www.deokjilmate.AllSinger.AllSingerRanking;
import com.deokjilmate.www.deokjilmate.R;
import com.deokjilmate.www.deokjilmate.application.ApplicationController;
import com.deokjilmate.www.deokjilmate.network.NetworkService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SetSingerActivity extends AppCompatActivity {

    @BindView(R.id.SetSinger_search)
    EditText search;

    @BindView(R.id.SetSinger_backImage)
    ImageButton backButton;

    @BindView(R.id.SetSinger_list)
    RecyclerView recyclerView;

    //RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    RequestManager requestManager;
    ArrayList<SetSingerItemData> setSingerItemDatas;//추천목록
    ArrayList<SetSingerItemData> allSingerList;//전체목록
    ArrayList<AllSingerDetails> allSingerDetails;


    SetSingerAdapter setSingerAdapter;
    HashMap<String, String> singerPNData;

    NetworkService networkService;
    String imageUrl = "https://00e9e64bac7c4aab450e3b22d212cabca5cfe7403d011279ee-apidata.googleusercontent.com/download/storage/v1/b/duckmate_1/o/new%2Fhi.png?qk=AD5uMEu5kgUv6YApI3IzolJW8OWW-pQNcREa89fmIeqQc4J4WIUao1JO4A_f2aTkJ91QyoigAf0icnspWaNZmeISXBAzxl3_4uic4kFCLjL-DSnZimW0ZXboYfnPzCW_Vtf2YwncRtzC6eiePWIJTt0o-S2w5oqFaeA3GV9eGhHpkBO04HyDYJGWsGDN2YMrXCs2l-Ds1DZsxgBZHk23B1-HdoaQFJs34fNtHa8BTaA1i9tG3WVWw7VpQx2RxrMSt2F-UowVQNA9sLcTFuOa7ROMi87qMsWCxk0OOE88-8mP_kXJEFSvONS9Ng1dvZtnIS-95OzKGNogYmZ7fgCj88nHAJK5GzNjiB561g_a_iOclET6IVeYtViHFhDiJBLIW6hVGCBIu0TyfzE15ry4TEiuaDZ8ONjLiqPf6CK345B1OMDjhw5sZcu-RnNXS7mDCOdvUiLCTQyr5LPDIG2VPiq8ZtcanyTaibX-eB5z-JZZCPzharQDDsyM4A4OiGKhNt71mFJF-tG4WYHdF3LYtz41R1BhttPiWjBH2yh19th_JjPl7hyhodF8EW5KCj_LYYiahZg8NlPnrjyKk5VmxBDnGA0R1OQkFcBAok6biq1jtmAkcU6XiwdVL0FZtoOsvgFk651C3FZMDRtRgGpDgAWTOnRMBr2P4p_YJfEsliKGRCmAB0LbByNIu3Meso_-EUJS-0mSX_KDqmB7O-C0f2xdihC4XuLTguUZNxG3RNK096gA2QFJMojPCzqZ7pYspn56iK1VaVXJ6ZHVKLYYrnLBBke7qzMaiw";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_set_singer);
        ButterKnife.bind(this);
        Glide.with(this).load(R.drawable.meta).into(backButton);
        //recyclerView = (RecyclerView) findViewById(R.id.SetSinger_list);
        recyclerView.setHasFixedSize(true);
        //recyclerView.get
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayout.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        networkService = ApplicationController.getInstance().getNetworkService();

        requestManager = Glide.with(this);
        singerPNData = new HashMap<>();


        setSingerItemDatas = new ArrayList<SetSingerItemData>();
        allSingerList = new ArrayList<SetSingerItemData>();
        allSingerDetails = new ArrayList<AllSingerDetails>();

        String[] keySet = {"투피엠","에이오에이","비원에이포","블랙핑크","씨엘씨","씨엔블루","엑소","이엑스아이디","트와이스","위너"};

        //TODO : 랭킹 가수 목록 넣기
        singerPNData.put("IU", "아이유");
        singerPNData.put("TWICE", "트와이스");
        singerPNData.put("Redvelvet", "레드벨벳");
        singerPNData.put("BigBang", "빅뱅");
        singerPNData.put("악동뮤지션", "악동뮤지션");
        singerPNData.put("EXO", "엑소");
        Call<AllSingerRanking> setSingerRankingCall = networkService.setSingerRanking();
        setSingerRankingCall.enqueue(new Callback<AllSingerRanking>() {
            @Override
            public void onResponse(Call<AllSingerRanking> call, Response<AllSingerRanking> response) {
                if(response.isSuccessful())
                {
                    allSingerDetails = response.body().result;
                    for(int i = 0; i<allSingerDetails.size(); i++)
                    {
                        setSingerItemDatas.add(new SetSingerItemData(allSingerDetails.get(i).getSinger_img(),
                                allSingerDetails.get(i).getSinger_name(), R.drawable.meta));
                        allSingerList.add(new SetSingerItemData(allSingerDetails.get(i).getSinger_img(),
                                allSingerDetails.get(i).getSinger_name(), R.drawable.meta));
                    }
                    setSingerAdapter = new SetSingerAdapter(requestManager, setSingerItemDatas, allSingerList, singerPNData);
                    recyclerView.setAdapter(setSingerAdapter);
                }
            }
            @Override
            public void onFailure(Call<AllSingerRanking> call, Throwable t) {
            }
        });

//        setSingerItemDatas.add(new SetSingerItemData(R.drawable.meta, "aaaa", R.drawable.meta));
//        setSingerItemDatas.add(new SetSingerItemData(R.drawable.meta, "bbbb", R.drawable.meta));
//        setSingerItemDatas.add(new SetSingerItemData(R.drawable.meta, "cccc", R.drawable.meta));
//        setSingerItemDatas.add(new SetSingerItemData(R.drawable.meta, "dddd", R.drawable.meta));
//        setSingerItemDatas.add(new SetSingerItemData(R.drawable.meta, "eeee", R.drawable.meta));
//        setSingerItemDatas.add(new SetSingerItemData(R.drawable.meta, "ffff", R.drawable.meta));
//
//        allSingerList.add(new SetSingerItemData(R.drawable.meta, "AOA", R.drawable.meta));
//        allSingerList.add(new SetSingerItemData(R.drawable.meta, "2PM", R.drawable.meta));
//        allSingerList.add(new SetSingerItemData(R.drawable.meta, "EXO", R.drawable.meta));
//        allSingerList.add(new SetSingerItemData(R.drawable.meta, "BLACKPINK", R.drawable.meta));
//        allSingerList.add(new SetSingerItemData(R.drawable.meta, "B1A4", R.drawable.meta));
//        allSingerList.add(new SetSingerItemData(R.drawable.meta, "EXID", R.drawable.meta));
//        allSingerList.add(new SetSingerItemData(R.drawable.meta, "CNBLUE", R.drawable.meta));
//        allSingerList.add(new SetSingerItemData(R.drawable.meta, "GOT7", R.drawable.meta));
//        allSingerList.add(new SetSingerItemData(R.drawable.meta, "TWICE", R.drawable.meta));
//        allSingerList.add(new SetSingerItemData(R.drawable.meta, "WINNER", R.drawable.meta));
//        allSingerList.add(new SetSingerItemData(R.drawable.meta, "방탄소년단", R.drawable.meta));
//        allSingerList.add(new SetSingerItemData(R.drawable.meta, "걸스데이", R.drawable.meta));
//        allSingerList.add(new SetSingerItemData(R.drawable.meta, "소녀시대", R.drawable.meta));
//        allSingerList.add(new SetSingerItemData(R.drawable.meta, "비투비", R.drawable.meta));
//        allSingerList.add(new SetSingerItemData(R.drawable.meta, "악동뮤지션", R.drawable.meta));
//        allSingerList.add(new SetSingerItemData(R.drawable.meta, "에이핑크", R.drawable.meta));



//        singerPNData.put("투피엠", "2PM");
//        singerPNData.put("에이오에이", "AOA");
//        singerPNData.put("비원에이포", "B1A4");
//        singerPNData.put("블랙핑크", "BLACKPINK");
//        singerPNData.put("씨엔블루", "CNBLUE");
//        singerPNData.put("엑소", "EXO");
//        singerPNData.put("이엑스아이디", "EXID");
//        singerPNData.put("트와이스", "TWICE");
//        singerPNData.put("위너", "WINNER");
//        singerPNData.put("갓세븐", "GOT7");
//        singerPNData.put("2PM", "투피엠");
//        singerPNData.put("AOA", "에이오에이");
//        singerPNData.put("B1A4", "비원에이포");
//        singerPNData.put("BLACKPINK", "블랙핑크");
//        singerPNData.put("CNBLUE", "씨엔블루");
//        singerPNData.put("EXO", "엑소");
//        singerPNData.put("EXID", "이엑스아이디");
//        singerPNData.put("TWICE", "트와이스");
//        singerPNData.put("WINNER", "위너");
//        singerPNData.put("GOT7", "갓세븐");
//        singerPNData.put("방탄소년단", "방탄소년단");
//        singerPNData.put("걸스데이", "걸스데이");
//        singerPNData.put("소녀시대", "소녀시대");
//        singerPNData.put("비투비", "비투비");
//        singerPNData.put("악동뮤지션", "악동뮤지션");
//        singerPNData.put("에이핑크", "에이핑크");
//
//
//
//
//
//




        search.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                setSingerAdapter.search = true;
                String text = search.getText().toString().toLowerCase(Locale.getDefault());
                if(text.length() == 0) {
                    setSingerAdapter.search = false;
                }
                setSingerAdapter.filter(text);
            }
            public void afterTextChanged(Editable s) {
            }
        });

    }

//    @OnClick(R.id.SetSinger_search)
//    public void Search()
//    {
//
//    }

    @OnClick(R.id.SetSinger_backImage)
    public void ClickBack()
    {
        Intent intent = new Intent(getApplicationContext(), SetProfileActivity.class);
        startActivity(intent);
    }
}
