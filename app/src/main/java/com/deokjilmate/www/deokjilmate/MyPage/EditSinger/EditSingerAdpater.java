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

public class EditSingerAdpater extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private RequestManager requestManager_singer;
    private ArrayList<EditSingerItemData> editSingerItemDatas;
    private static final int HEADER_VIEW = 1;
    private EditSingerHeadItemData editSingerHeadItemData;




    public EditSingerAdpater(RequestManager requestManager_singer, ArrayList<EditSingerItemData> editSingerItemDatas, EditSingerHeadItemData editSingerHeadItemData) {
        this.requestManager_singer = requestManager_singer;
        this.editSingerItemDatas = editSingerItemDatas;
        this.editSingerHeadItemData = editSingerHeadItemData;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView;
        if(viewType == HEADER_VIEW)
        {
            itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.mypage_edit_singer_head,parent,false);
            EditSingerHeadViewHolder editSingerHeadViewHolder = new EditSingerHeadViewHolder(itemView);
            return editSingerHeadViewHolder;
        }
        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.mypage_edit_singer_recycle, parent, false);

        EditSingerViewHolder editSingerViewHolder = new EditSingerViewHolder(itemView);
        return editSingerViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        try {
            if (holder instanceof EditSingerViewHolder) {
                EditSingerViewHolder editSingerViewHolder = (EditSingerViewHolder) holder;

                requestManager_singer.load(editSingerItemDatas.get(position-1).singer_Image).into(editSingerViewHolder.singer_Image);
                editSingerViewHolder.singer_Name.setText(editSingerItemDatas.get(position-1).singer_Name);
                editSingerViewHolder.set_Main.setImageResource(editSingerItemDatas.get(position-1).set_Main);
                editSingerViewHolder.delete_Singer.setImageResource(editSingerItemDatas.get(position-1).delete_Singer);
            } else if (holder instanceof EditSingerHeadViewHolder) {
                EditSingerHeadViewHolder editSingerHeadViewHolder = (EditSingerHeadViewHolder) holder;

                requestManager_singer.load(editSingerHeadItemData.singer_Image).into(editSingerHeadViewHolder.singer_Image);
                editSingerHeadViewHolder.singer_Name.setText(editSingerHeadItemData.singer_Name);
                editSingerHeadViewHolder.set_Sub.setImageResource(editSingerHeadItemData.set_Sub);
                editSingerHeadViewHolder.delete_Singer.setImageResource(editSingerHeadItemData.delete_Singer);
                editSingerHeadViewHolder.presentMain.setImageResource(R.drawable.meta);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return (editSingerItemDatas != null) ? editSingerItemDatas.size()+1 : 0;
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
