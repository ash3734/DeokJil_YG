package com.deokjilmate.www.deokjilmate.MyPage.EditSinger;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.deokjilmate.www.deokjilmate.R;

/**
 * Created by dldud on 2017-02-19.
 */

public class EditSingerViewHolder extends RecyclerView.ViewHolder{
    ImageView singer_Image;
    TextView singer_Name;
    ImageView set_Main;
    ImageView delete_Singer;

    public EditSingerViewHolder(View itemView) {
        super(itemView);
        singer_Image = (ImageView)itemView.findViewById(R.id.mypage_edit_singer_recycle_subImage);
        singer_Name = (TextView) itemView.findViewById(R.id.mypage_edit_singer_recycle_subName);
        set_Main = (ImageView) itemView.findViewById(R.id.mypage_edit_singer_recycle_setMain);
        delete_Singer = (ImageView) itemView.findViewById(R.id.mypage_edit_singer_recycle_delete);
    }
}
