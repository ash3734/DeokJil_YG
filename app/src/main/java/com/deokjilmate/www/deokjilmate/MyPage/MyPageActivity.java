package com.deokjilmate.www.deokjilmate.MyPage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.deokjilmate.www.deokjilmate.MyPage.EditSinger.EditSingerActivity;
import com.deokjilmate.www.deokjilmate.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyPageActivity extends AppCompatActivity {

    @BindView(R.id.MyPageMain_toolbar)
    ImageView toolbarImage;

    @BindView(R.id.MyPage_editSinger)
    ImageButton plusSub;

    @BindView(R.id.MyPage_backImage)
    ImageView backImage;

    @BindView(R.id.MyPage_subRecycle)
    RecyclerView subSingerrecyclerView;


    RequestManager requestManager_singer;
    RequestManager requestManager_rank;
    ArrayList<MyPageItemData> myPageItemDatas;
    //ArrayList<MyPageFootItemData> myPageFootItemData;

    MyPageFootItemData myPageFootItemData;
    MyPageHeadItemData myPageHeadItemData;

    MyPageHeaderAdapter myPageHeaderAdapter;
    LinearLayoutManager linearLayoutManager;
    MyPageAdapter myPageAdapter;

   // NoxView noxView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mypage_main);
        ButterKnife.bind(this);
        Glide.with(this).load(R.drawable.toolbar).into(toolbarImage);
        Glide.with(this).load(R.drawable.meta).into(plusSub);
        Glide.with(this).load(R.drawable.meta).into(backImage);

        requestManager_singer = Glide.with(this);
        requestManager_rank = Glide.with(this);

        subSingerrecyclerView.setHasFixedSize(true);
        //recyclerView.get
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayout.VERTICAL);
        subSingerrecyclerView.setLayoutManager(linearLayoutManager);



        //서브
        myPageItemDatas = new ArrayList<MyPageItemData>();



        myPageItemDatas.add(new MyPageItemData(R.drawable.meta, R.drawable.meta, "aaaaaaa", "1234"));
        myPageItemDatas.add(new MyPageItemData(R.drawable.meta, R.drawable.meta, "bbbbbbb", "1234"));
        myPageItemDatas.add(new MyPageItemData(R.drawable.meta, R.drawable.meta, "ccccccc", "1234"));
        myPageItemDatas.add(new MyPageItemData(R.drawable.meta, R.drawable.meta, "ddddddd", "1234"));


        myPageHeadItemData = new MyPageHeadItemData(R.drawable.meta, R.drawable.meta, "header", "123456");



       myPageAdapter = new MyPageAdapter(requestManager_singer, requestManager_rank, myPageItemDatas, myPageHeadItemData);


        subSingerrecyclerView.setAdapter(myPageAdapter);



        //TODO : 통신 이후 이 부분 바뀌어야 함.
        //메인



    }

    @OnClick(R.id.MyPage_editSinger)
    public void plusSub()
    {
        Intent intent = new Intent(getApplicationContext(), EditSingerActivity.class);
        startActivity(intent);
    }

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.AT_MOST);

        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }
}
