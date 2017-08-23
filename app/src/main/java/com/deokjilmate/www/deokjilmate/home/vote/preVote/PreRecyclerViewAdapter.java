package com.deokjilmate.www.deokjilmate.home.vote.preVote;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.deokjilmate.www.deokjilmate.R;

import java.util.ArrayList;

/**
 * Created by ash on 2017-02-07.
 */

public class PreRecyclerViewAdapter extends RecyclerView.Adapter<PreViewHolder>{
    ArrayList<PreData> itemDatas;
    View.OnClickListener clickEvent;

    public PreRecyclerViewAdapter(ArrayList<PreData> itemDatas,View.OnClickListener clickEvent) {
        this.itemDatas=itemDatas;
        this.clickEvent = clickEvent;
    }
    //리싸이클러 뷰 레이아웃 적용
    @Override
    public PreViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_pre_listitem, parent,false);
        PreViewHolder viewHolder = new PreViewHolder(itemView);
       itemView.setOnClickListener(clickEvent);
        return viewHolder;
    }

    //이미지를 서버에서 받아와 출력 id,interest 출력
    //현재 이미지 미구현 상태
    @Override
    public void onBindViewHolder(PreViewHolder holder, int position) {
        holder.textViewProgramName.setText(itemDatas.get(position).program_name+
                "\n"+itemDatas.get(position).program_data);
        if(itemDatas.get(position).program_name.equals("인기가요")){
            holder.imageViewProgram.setImageResource(R.drawable.ingigayo);
        }else if(itemDatas.get(position).program_name.equals("쇼챔피언")){
            holder.imageViewProgram.setImageResource(R.drawable.showchampion);
        }else if(itemDatas.get(position).program_name.equals("엠카운트다운")){
            holder.imageViewProgram.setImageResource(R.drawable.mcountdown);
        }else if(itemDatas.get(position).program_name.equals("뮤직뱅크")){
            holder.imageViewProgram.setImageResource(R.drawable.musicbank);
        }else if(itemDatas.get(position).program_name.equals("더쇼")){
            holder.imageViewProgram.setImageResource(R.drawable.theshow);
        }else{
        }
    }

    //현재 위치 계산
    @Override
    public int getItemCount() {
        return (itemDatas != null) ? itemDatas.size() : 0;
    }

}