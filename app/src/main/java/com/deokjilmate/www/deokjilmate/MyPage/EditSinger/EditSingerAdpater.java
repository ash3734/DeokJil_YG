package com.deokjilmate.www.deokjilmate.MyPage.EditSinger;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.RequestManager;
import com.deokjilmate.www.deokjilmate.R;

import java.util.ArrayList;

/**
 * Created by dldud on 2017-02-19.
 */

public class EditSingerAdpater extends RecyclerView.Adapter<EditSingerViewHolder> {
    RequestManager requestManager;
    ArrayList<EditSingerItemData> editSingerItemDatas;



    public EditSingerAdpater(RequestManager requestManager, ArrayList<EditSingerItemData> editSingerItemDatas) {
        this.requestManager = requestManager;
        this.editSingerItemDatas = editSingerItemDatas;
    }

    @Override
    public EditSingerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.mypage_edit_singer_recycle, parent,false);
        //화면상에 보여줄 레이아웃을 지정해주어야 함.
        EditSingerViewHolder viewHolder = new EditSingerViewHolder(itemView);
        //itemView.setOnClickListener(clickEvent);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(EditSingerViewHolder holder, int position) {


    }

    @Override
    public int getItemCount() {
        return (editSingerItemDatas != null) ? editSingerItemDatas.size() : 0;
    }
}
