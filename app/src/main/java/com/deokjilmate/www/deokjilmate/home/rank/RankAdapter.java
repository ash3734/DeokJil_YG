package com.deokjilmate.www.deokjilmate.home.rank;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.RequestManager;
import com.deokjilmate.www.deokjilmate.R;
import com.deokjilmate.www.deokjilmate.application.ApplicationController;
import com.deokjilmate.www.deokjilmate.home.MainResult;

import java.util.ArrayList;

/**
 * Created by ash on 2017-07-26.
 */

public class RankAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    ArrayList<RankData> itemDatas;
    private static int TYPE_HEADER=1;
    private static int TYPE_ITEM=2;
    MainResult mainResult;
    private RequestManager mRequestManager;


    public RankAdapter(ArrayList<RankData> itemDatas,RequestManager mRequestManager) {
        this.itemDatas=itemDatas;
        this.mRequestManager = mRequestManager;
        mainResult = ApplicationController.getInstance().mainResult;
    }
    //리싸이클러 뷰 레이아웃 적용
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType ==TYPE_HEADER){
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.rank_header, parent,false);
            return new HeaderViewHolder(itemView);
        }else if(viewType == TYPE_ITEM){
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_rank_listitem, parent,false);
            Log.d("ash3734","here1");
            return new RankViewHolder(itemView);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof HeaderViewHolder){
            final HeaderViewHolder headerHolder = (HeaderViewHolder) holder;
            mRequestManager.load(mainResult.chart_data.album_img).into(headerHolder.imageViewSinger);
            headerHolder.textViewsingerName.setText(mainResult.vote_data.singer_name);
            headerHolder.textViewSong.setText(mainResult.chart_data.song_name);
            headerHolder.textViewAlbum.setText(mainResult.chart_data.album_name);

        }else if(holder instanceof RankViewHolder){
            final RankViewHolder rankHolder = (RankViewHolder) holder;
            rankHolder.textViewrank.setText(itemDatas.get(position-1).rank);
            if (itemDatas.get(position-1).isUp==1) {
                rankHolder.imageViewIsup.setImageResource(R.drawable.chart_up);
            }else if(itemDatas.get(position-1).isUp==0){
                rankHolder.imageViewIsup.setImageResource(R.drawable.chart_stay);
            } else
                rankHolder.imageViewIsup.setImageResource(R.drawable.chart_down);
            if (itemDatas.get(position-1).chartName.equals("멜론"))
                rankHolder.imageViewChartName.setImageResource(R.drawable.chart_melon);
            else if(itemDatas.get(position-1).chartName.equals("지니"))
                rankHolder.imageViewChartName.setImageResource(R.drawable.chart_genie);
            else if(itemDatas.get(position-1).chartName.equals("벅스"))
                rankHolder.imageViewChartName.setImageResource(R.drawable.chart_bugs);
            else if(itemDatas.get(position-1).chartName.equals("엠넷"))
                rankHolder.imageViewChartName.setImageResource(R.drawable.chart_mnet);
            else if(itemDatas.get(position-1).chartName.equals("네이버"))
                rankHolder.imageViewChartName.setImageResource(R.drawable.chart_naver);
            else if(itemDatas.get(position-1).chartName.equals("소리바다"))
                rankHolder.imageViewChartName.setImageResource(R.drawable.chart_soribada);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(position==0){
            return TYPE_HEADER;
        }
        Log.d("ash3734","here2");
        return TYPE_ITEM;
    }

    //현재 위치 계산
    @Override
    public int getItemCount() {
        return (itemDatas != null) ? itemDatas.size()+1 : 0;
    }

}
