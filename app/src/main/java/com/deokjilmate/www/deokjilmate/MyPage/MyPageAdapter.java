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

public class MyPageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    RequestManager requestManager_singer;
    RequestManager requestManager_rank;
    ArrayList<MyPageItemData> myPageItemDatas;
    MyPageHeadItemData myPageHeadItemData;

    private static final int HEADER_VIEW = 1;

    public MyPageAdapter(RequestManager requestManager_singer, RequestManager requestManager_rank, ArrayList<MyPageItemData> myPageItemDatas, MyPageHeadItemData myPageHeadItemData) {
        this.requestManager_singer = requestManager_singer;
        this.requestManager_rank = requestManager_rank;
        this.myPageItemDatas = myPageItemDatas;
        this.myPageHeadItemData = myPageHeadItemData;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;
        if(viewType == HEADER_VIEW)
        {
            itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.mypage_head,parent,false);
            MyPageHeadViewHolder myPageHeadViewHolder = new MyPageHeadViewHolder(itemView);
            return myPageHeadViewHolder;
        }
            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.mypage_recycle, parent, false);

            MyPageViewHolder myPageViewHolder = new MyPageViewHolder(itemView);
            return myPageViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        try {
            if (holder instanceof MyPageViewHolder) {
                MyPageViewHolder myPageViewHolder = (MyPageViewHolder) holder;
                requestManager_singer.load(myPageItemDatas.get(position-1).singer_Image).into(myPageViewHolder.singer_Image);
                requestManager_rank.load(myPageItemDatas.get(position-1).rank_Image).into(myPageViewHolder.rank_Image);
                myPageViewHolder.singer_name.setText(myPageItemDatas.get(position-1).singer_name);
                myPageViewHolder.vote_count.setText(String.valueOf(myPageItemDatas.get(position-1).choice_count));

            } else if (holder instanceof MyPageHeadViewHolder) {
                MyPageHeadViewHolder myPageHeadViewHolder = (MyPageHeadViewHolder) holder;
                requestManager_singer.load(myPageHeadItemData.singer_Image).into(myPageHeadViewHolder.singer_Image);
                requestManager_rank.load(myPageHeadItemData.rank_Image).into(myPageHeadViewHolder.rank_Image);
              //  myPageHeadViewHolder.rank_Image.setImageResource(myPageHeadItemData.rank_Image);
                myPageHeadViewHolder.singer_name.setText(myPageHeadItemData.singer_name);
                myPageHeadViewHolder.vote_count.setText(String.valueOf(myPageHeadItemData.choice_count));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return (myPageItemDatas != null) ? myPageItemDatas.size()+1 : 0;

    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            // This is where we'll add footer.
            return HEADER_VIEW;
        }
            return super.getItemViewType(position);
    }
}
