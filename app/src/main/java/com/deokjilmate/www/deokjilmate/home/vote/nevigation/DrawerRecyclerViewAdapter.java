package com.deokjilmate.www.deokjilmate.home.vote.nevigation;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.deokjilmate.www.deokjilmate.R;

import java.util.ArrayList;

/**
 * Created by ash on 2017-02-09.
 */

public class DrawerRecyclerViewAdapter extends RecyclerView.Adapter<DrawerViewHolder>{
    ArrayList<MySingerData> itemDatas;

    public DrawerRecyclerViewAdapter(ArrayList<MySingerData> itemDatas) {
        this.itemDatas=itemDatas;

    }
    //리싸이클러 뷰 레이아웃 적용
    @Override
    public DrawerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.drawer_listitem, parent,false);
        DrawerViewHolder viewHolder = new DrawerViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(DrawerViewHolder holder, int position) {

        holder.textView.setText(itemDatas.get(position).getSingerName());
    }

    //현재 위치 계산
    @Override
    public int getItemCount() {
        return (itemDatas != null) ? itemDatas.size() : 0;
    }

}
