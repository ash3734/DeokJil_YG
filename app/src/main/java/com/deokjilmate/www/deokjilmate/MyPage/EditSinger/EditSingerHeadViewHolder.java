package com.deokjilmate.www.deokjilmate.MyPage.EditSinger;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.deokjilmate.www.deokjilmate.R;

/**
 * Created by 2yg on 2017. 3. 15..
 */

public class EditSingerHeadViewHolder extends RecyclerView.ViewHolder{

    ImageView singer_Image;
    TextView singer_Name;
    ImageView set_Sub;
    ImageView delete_Singer;
    ImageView presentMain;




    public EditSingerHeadViewHolder(View itemView) {
        super(itemView);
        singer_Image = (ImageView)itemView.findViewById(R.id.mypage_edit_singer_head_mainImage);
        singer_Name = (TextView) itemView.findViewById(R.id.mypage_edit_singer_head_mainName);
        set_Sub = (ImageView) itemView.findViewById(R.id.mypage_edit_singer_head_toSub);
        delete_Singer = (ImageView) itemView.findViewById(R.id.mypage_edit_singer_head_delete);
        presentMain = (ImageView) itemView.findViewById(R.id.mypage_edit_singer_head_presentMain);
    }
}
