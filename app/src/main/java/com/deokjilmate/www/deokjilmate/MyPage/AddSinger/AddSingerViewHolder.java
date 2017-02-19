package com.deokjilmate.www.deokjilmate.MyPage.AddSinger;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by dldud on 2017-02-17.
 */

public class AddSingerViewHolder extends RecyclerView.ViewHolder{

    ImageView singer_Image;
    TextView singer_Name;
    ImageView set_Main;
    ImageView delete_Singer;

    public AddSingerViewHolder(View itemView) {
        super(itemView);
    }
}
