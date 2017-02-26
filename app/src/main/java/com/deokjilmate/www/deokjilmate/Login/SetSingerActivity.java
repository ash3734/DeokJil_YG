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
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.deokjilmate.www.deokjilmate.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SetSingerActivity extends AppCompatActivity {

    @BindView(R.id.SetSinger_topImage)
    ImageView toobarImage;

    @BindView(R.id.SetSinger_search)
    EditText search;

    @BindView(R.id.SetSinger_backImage)
    ImageButton backButton;

    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    RequestManager requestManager;
    ArrayList<SetSingerItemData> setSingerItemDatas;//추천목록
    ArrayList<SetSingerItemData> allSingerList;//전체목록
    SetSingerAdapter setSingerAdapter;
    HashMap<String, String> singerPNData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_set_singer);
        ButterKnife.bind(this);

        Glide.with(this).load(R.drawable.toolbar).into(toobarImage);
        Glide.with(this).load(R.drawable.meta).into(backButton);

        requestManager = Glide.with(this);
        singerPNData = new HashMap<>();


        setSingerItemDatas = new ArrayList<SetSingerItemData>();
        allSingerList = new ArrayList<SetSingerItemData>();
        setSingerItemDatas.add(new SetSingerItemData(R.drawable.meta, "aaaa", R.drawable.meta));
        setSingerItemDatas.add(new SetSingerItemData(R.drawable.meta, "bbbb", R.drawable.meta));
        setSingerItemDatas.add(new SetSingerItemData(R.drawable.meta, "cccc", R.drawable.meta));
        setSingerItemDatas.add(new SetSingerItemData(R.drawable.meta, "dddd", R.drawable.meta));
        setSingerItemDatas.add(new SetSingerItemData(R.drawable.meta, "eeee", R.drawable.meta));
        setSingerItemDatas.add(new SetSingerItemData(R.drawable.meta, "ffff", R.drawable.meta));

        allSingerList.add(new SetSingerItemData(R.drawable.meta, "AOA", R.drawable.meta));
        allSingerList.add(new SetSingerItemData(R.drawable.meta, "2PM", R.drawable.meta));
        allSingerList.add(new SetSingerItemData(R.drawable.meta, "EXO", R.drawable.meta));
        allSingerList.add(new SetSingerItemData(R.drawable.meta, "BLACKPINK", R.drawable.meta));
        allSingerList.add(new SetSingerItemData(R.drawable.meta, "B1A4", R.drawable.meta));
        allSingerList.add(new SetSingerItemData(R.drawable.meta, "EXID", R.drawable.meta));
        allSingerList.add(new SetSingerItemData(R.drawable.meta, "CNBLUE", R.drawable.meta));
        allSingerList.add(new SetSingerItemData(R.drawable.meta, "GOT7", R.drawable.meta));
        allSingerList.add(new SetSingerItemData(R.drawable.meta, "TWICE", R.drawable.meta));
        allSingerList.add(new SetSingerItemData(R.drawable.meta, "WINNER", R.drawable.meta));
        allSingerList.add(new SetSingerItemData(R.drawable.meta, "방탄소년단", R.drawable.meta));
        allSingerList.add(new SetSingerItemData(R.drawable.meta, "걸스데이", R.drawable.meta));
        allSingerList.add(new SetSingerItemData(R.drawable.meta, "소녀시대", R.drawable.meta));
        allSingerList.add(new SetSingerItemData(R.drawable.meta, "비투비", R.drawable.meta));
        allSingerList.add(new SetSingerItemData(R.drawable.meta, "악동뮤지션", R.drawable.meta));
        allSingerList.add(new SetSingerItemData(R.drawable.meta, "에이핑크", R.drawable.meta));



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
        singerPNData.put("2PM", "투페임");
        singerPNData.put("AOA", "에이오에이");
        singerPNData.put("B1A4", "비원에이포");
        singerPNData.put("BLACKPINK", "블랙핑크");
        singerPNData.put("CNBLUE", "씨엔블루");
        singerPNData.put("EXO", "엑소");
        singerPNData.put("EXID", "이엑스아이디");
        singerPNData.put("TWICE", "트와이스");
        singerPNData.put("WINNER", "위너");
        singerPNData.put("GOT7", "갓세븐");
        singerPNData.put("방탄소년단", "방탄소년단");
        singerPNData.put("걸스데이", "걸스데이");
        singerPNData.put("소녀시대", "소녀시대");
        singerPNData.put("비투비", "비투비");
        singerPNData.put("악동뮤지션", "악동뮤지션");
        singerPNData.put("에이핑크", "에이핑크");



        String[] keySet = {"투피엠","에이오에이","비원에이포","블랙핑크","씨엘씨","씨엔블루","엑소","이엑스아이디","트와이스","위너"};


        recyclerView = (RecyclerView) findViewById(R.id.SetSinger_list);
        recyclerView.setHasFixedSize(true);
        //recyclerView.get
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayout.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        setSingerAdapter = new SetSingerAdapter(requestManager, setSingerItemDatas, allSingerList, singerPNData, keySet);
        recyclerView.setAdapter(setSingerAdapter);

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
