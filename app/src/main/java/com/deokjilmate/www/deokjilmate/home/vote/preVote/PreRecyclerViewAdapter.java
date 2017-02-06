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

public class PreRecyclerViewAdapter extends RecyclerView.Adapter<ViewHolder>{
    ArrayList<PreData> itemDatas;
  //  View.OnClickListener clickEvent; 추후 수정
    //RequestManager mRequestManager; 이것두
    //, View.OnClickListener clickEvent, RequestManager requestManager 밑에 파라메터로 넣자 나중에
    public PreRecyclerViewAdapter(ArrayList<PreData> itemDatas) {
        this.itemDatas=itemDatas;
        //this.clickEvent = clickEvent;
        //this.mRequestManager = requestManager;
    }
    //리싸이클러 뷰 레이아웃 적용
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_pre_listitem, parent,false);
        ViewHolder viewHolder = new ViewHolder(itemView);
      //  itemView.setOnClickListener(clickEvent);
        return viewHolder;
    }

    //이미지를 서버에서 받아와 출력 id,interest 출력
    //현재 이미지 미구현 상태
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        //mRequestManager.load(arSrc.get(position).getImageURL()).into(holder.imageView); //glide적용 미구현 사항
        //holder.textViewID.setText(arSrc.get(position).getID());
        holder.textViewProgramName.setText(itemDatas.get(position).getProgramName());
        //holder.imageViewProgram.setImageResource(R.drawable.);
    }

    //현재 위치 계산
    @Override
    public int getItemCount() {
        return (itemDatas != null) ? itemDatas.size() : 0;
    }

}