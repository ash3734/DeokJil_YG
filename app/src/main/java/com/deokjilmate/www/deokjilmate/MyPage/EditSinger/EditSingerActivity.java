package com.deokjilmate.www.deokjilmate.MyPage.EditSinger;

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
import com.deokjilmate.www.deokjilmate.MyPage.AddSinger.AddSingerActivity;
import com.deokjilmate.www.deokjilmate.MyPage.MyPageActivity;
import com.deokjilmate.www.deokjilmate.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EditSingerActivity extends AppCompatActivity {

    @BindView(R.id.MyPage_EditSinger_backImage)
    ImageButton back;

    @BindView(R.id.MyPage_EditSinger_save)
    ImageButton save;

    @BindView(R.id.MyPage_EditSinger_recycle)
    RecyclerView recyclerView;

    @BindView(R.id.MyPage_EditSinger_addSinger)
    ImageView addSinger;



    private LinearLayoutManager linearLayoutManager;
    private RequestManager requestManager;
    private ArrayList<EditSingerItemData> editSingerItemDatas;//추천목록
    private EditSingerHeadItemData editSingerHeadItemData;
    private EditSingerAdpater editSingerAdpater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mypage_edit_singer);
        ButterKnife.bind(this);
        //Glide.with(this).load(R.drawable.toolbar).into(toolbarImage);
        Glide.with(this).load(R.drawable.meta).into(addSinger);
        //Glide.with(this).load(R.drawable.meta).into(presentMain);
        requestManager = Glide.with(this);
        recyclerView.setHasFixedSize(true);
        //recyclerView.get
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayout.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        editSingerHeadItemData = new EditSingerHeadItemData(R.drawable.meta, "메인", R.drawable.meta, R.drawable.toolbar);



        editSingerItemDatas = new ArrayList<EditSingerItemData>();
        editSingerItemDatas.add(new EditSingerItemData(R.drawable.meta, "서브1", R.drawable.toolbar, R.drawable.meta));
        editSingerItemDatas.add(new EditSingerItemData(R.drawable.meta, "서브2", R.drawable.toolbar, R.drawable.meta));
        editSingerItemDatas.add(new EditSingerItemData(R.drawable.meta, "서브3", R.drawable.toolbar, R.drawable.meta));



        editSingerAdpater = new EditSingerAdpater(requestManager, editSingerItemDatas, editSingerHeadItemData);
        recyclerView.setAdapter(editSingerAdpater);


    }


    @OnClick(R.id.MyPage_EditSinger_save)
    public void save()
    {

    }

    @OnClick(R.id.MyPage_EditSinger_backImage)
    public void clickBack()
    {
        Intent intent = new Intent(getApplicationContext(), MyPageActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.MyPage_EditSinger_addSinger)
    public void clickAdd()
    {
        Intent intent = new Intent(getApplicationContext(), AddSingerActivity.class);
        startActivity(intent);
    }
}
