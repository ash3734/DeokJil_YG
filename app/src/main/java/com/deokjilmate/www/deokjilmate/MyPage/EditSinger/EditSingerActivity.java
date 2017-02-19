package com.deokjilmate.www.deokjilmate.MyPage.EditSinger;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.deokjilmate.www.deokjilmate.MyPage.AddSinger.AddSingerActivity;
import com.deokjilmate.www.deokjilmate.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EditSingerActivity extends AppCompatActivity {

    @BindView(R.id.Sign_topImage)
    ImageView toolbarImage;



    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    RequestManager requestManager;
    ArrayList<EditSingerItemData> editSingerItemDatas;//추천목록
    EditSingerAdpater editSingerAdpater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mypage_edit_singer);
        ButterKnife.bind(this);
        Glide.with(this).load(R.drawable.toolbar).into(toolbarImage);

        requestManager = Glide.with(this);

        editSingerItemDatas = new ArrayList<EditSingerItemData>();


        editSingerItemDatas.add(new EditSingerItemData(R.drawable.meta, "aaaa", R.drawable.meta, R.drawable.meta));
        editSingerItemDatas.add(new EditSingerItemData(R.drawable.meta, "bbbb", R.drawable.meta, R.drawable.meta));


        recyclerView = (RecyclerView) findViewById(R.id.SetSinger_list);
        recyclerView.setHasFixedSize(true);
        //recyclerView.get
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayout.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        editSingerAdpater = new EditSingerAdpater(requestManager, editSingerItemDatas);
        recyclerView.setAdapter(editSingerAdpater);
    }

    @OnClick(R.id.MyPage_EditSinger_addSinger)
    public void clickAdd()
    {
        Intent intent = new Intent(getApplicationContext(), AddSingerActivity.class);
        startActivity(intent);
    }
}
