package com.deokjilmate.www.deokjilmate.MyPage;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.RequestManager;
import com.deokjilmate.www.deokjilmate.R;
import com.karumi.headerrecyclerview.HeaderRecyclerViewAdapter;

import java.util.ArrayList;

/**
 * Created by dldud on 2017-03-06.
 */

public class MyPageHeaderAdapter extends HeaderRecyclerViewAdapter<RecyclerView.ViewHolder, MyPageHeadItemData, MyPageItemData, MyPageFootItemData> {


    private static final String LOG_TAG = MyPageHeaderAdapter.class.getSimpleName();

    RequestManager requestManager_singer;
    RequestManager requestManager_rank;
    ArrayList<MyPageItemData> myPageItemDatas;
    //ArrayList<MyPageFootItemData> myPageFootItemData;

    MyPageFootItemData myPageFootItemData;
    MyPageHeadItemData myPageHeadItemData;
    public MyPageHeaderAdapter(RequestManager requestManager_singer, RequestManager requestManager_rank, ArrayList<MyPageItemData> myPageItemDatas, MyPageFootItemData myPageFootItemData, MyPageHeadItemData myPageHeadItemData) {
        this.requestManager_singer = requestManager_singer;
        this.requestManager_rank = requestManager_rank;
        this.myPageItemDatas = myPageItemDatas;
        this.myPageHeadItemData = myPageHeadItemData;
        this.myPageFootItemData = myPageFootItemData;
        Log.v("길이", String.valueOf(myPageItemDatas.size()));
    }

    @Override
    protected RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = getLayoutInflater(parent);
        View headerView = inflater.inflate(R.layout.mypage_head, parent, false);
        Log.v("뷰생성1", "뷰생성1");

        return new MyPageHeadViewHolder(headerView);
    }

    @Override
    protected RecyclerView.ViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = getLayoutInflater(parent);
        Log.v("_뷰 생성", "_뷰 생성");

        View characterView = inflater.inflate(R.layout.mypage_recycle, parent, false);
        Log.v("뷰 생성", "뷰 생성");
        return new MyPageViewHolder(characterView);
    }

    @Override
    protected RecyclerView.ViewHolder onCreateFooterViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = getLayoutInflater(parent);
        View footerView = inflater.inflate(R.layout.mypage_head, parent, false);
        Log.v("뷰생성2", "뷰생성2");

        return new MyPageFootViewHolder(footerView);
    }





    @Override protected void onBindHeaderViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyPageHeadViewHolder viewHolder = (MyPageHeadViewHolder) holder;

        Log.v("뷰 들어옴1", "뷰 들어옴1");
        viewHolder.singer_Image.setImageResource(myPageHeadItemData.singer_Image);
        viewHolder.rank_Image.setImageResource(myPageHeadItemData.rank_Image);
        viewHolder.singer_name.setText(myPageHeadItemData.singer_name);
        viewHolder.vote_count.setText(myPageHeadItemData.vote_count);
    }

    @Override protected void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyPageViewHolder viewHolder = (MyPageViewHolder) holder;
        Log.v("뷰 들어옴", String.valueOf(myPageItemDatas.size()));

//        viewHolder.singer_Image.setImageResource(myPageItemDatas.get(position).singer_Image);
//        viewHolder.rank_Image.setImageResource(myPageItemDatas.get(position).rank_Image);

        requestManager_singer.load(myPageItemDatas.get(position).singer_Image).into(viewHolder.singer_Image);
        requestManager_rank.load(myPageItemDatas.get(position).rank_Image).into(viewHolder.rank_Image);
        viewHolder.singer_name.setText(myPageItemDatas.get(position).singer_name);
        viewHolder.vote_count.setText(myPageItemDatas.get(position).vote_count);
    }

    @Override protected void onBindFooterViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyPageFootViewHolder viewHolder = (MyPageFootViewHolder) holder;

        Log.v("뷰 들어옴2", "뷰 들어옴2");

//        viewHolder.singer_Image.setImageResource(myPageFootItemData.singer_Image);
//        viewHolder.rank_Image.setImageResource(myPageFootItemData.rank_Image);
//        viewHolder.singer_name.setText(myPageFootItemData.singer_name);
//        viewHolder.vote_count.setText(myPageFootItemData.vote_count);
    }

    @Override protected void onHeaderViewRecycled(RecyclerView.ViewHolder holder) {
        Log.v(LOG_TAG, "onHeaderViewRecycled(RecyclerView.ViewHolder holder)");
    }

    @Override protected void onItemViewRecycled(RecyclerView.ViewHolder holder) {
        Log.v(LOG_TAG, "onItemViewRecycled(RecyclerView.ViewHolder holder)");
    }

    @Override protected void onFooterViewRecycled(RecyclerView.ViewHolder holder) {
        Log.v(LOG_TAG, "onFooterViewRecycled(RecyclerView.ViewHolder holder)");
    }

    private LayoutInflater getLayoutInflater(ViewGroup parent) {
        return LayoutInflater.from(parent.getContext());
    }
    @Override
    public int getItemCount() {
        return (myPageItemDatas != null) ? myPageItemDatas.size() : 0;
    }


}
