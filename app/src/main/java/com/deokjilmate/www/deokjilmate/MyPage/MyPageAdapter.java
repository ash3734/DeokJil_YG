package com.deokjilmate.www.deokjilmate.MyPage;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.RequestManager;
import com.deokjilmate.www.deokjilmate.R;

import java.util.ArrayList;

/**
 * Created by dldud on 2017-02-14.
 */

public class MyPageAdapter extends RecyclerView.Adapter<MyPageViewHolder> {
    RequestManager requestManager_singer;
    RequestManager requestManager_rank;
    ArrayList<MyPageItemData> myPageItemDatas;

    public MyPageAdapter(RequestManager requestManager_singer, RequestManager requestManager_rank, ArrayList<MyPageItemData> myPageItemDatas) {
        this.requestManager_singer = requestManager_singer;
        this.requestManager_rank = requestManager_rank;
        this.myPageItemDatas = myPageItemDatas;
    }

    @Override
    public MyPageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.mypage_recycle,parent,false);
        MyPageViewHolder mypageViewHolder=new MyPageViewHolder(itemView);
        //mypageViewHolder.nextBulView.setOnClickListener(clickEvent);
        return mypageViewHolder;
    }

    @Override
    public void onBindViewHolder(MyPageViewHolder holder, int position) {
        requestManager_singer.load(myPageItemDatas.get(position).singer_Image).into(holder.singer_Image);
        requestManager_rank.load(myPageItemDatas.get(position).rank_Image).into(holder.rank_Image);
        holder.singer_name.setText(myPageItemDatas.get(position).singer_name);
        holder.vote_count.setText(myPageItemDatas.get(position).vote_count);
    }

    @Override
    public int getItemCount() {
        return (myPageItemDatas != null) ? myPageItemDatas.size() : 0;
    }
}
