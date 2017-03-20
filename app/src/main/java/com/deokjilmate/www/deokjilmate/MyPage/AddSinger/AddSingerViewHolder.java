package com.deokjilmate.www.deokjilmate.MyPage.AddSinger;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.deokjilmate.www.deokjilmate.R;

/**
 * Created by dldud on 2017-02-17.
 */

public class AddSingerViewHolder extends RecyclerView.ViewHolder{

    TextView singer_rank;
    ImageView singer_Image;
    TextView singer_Name;
    ImageButton add_singer;

    public AddSingerViewHolder(View itemView) {
        super(itemView);
        singer_rank = (TextView)itemView.findViewById(R.id.mypage_add_singer_recycle_rank);
        singer_Image = (ImageView)itemView.findViewById(R.id.mypage_add_singer_recycle_singerImage);
        singer_Name = (TextView)itemView.findViewById(R.id.mypage_add_singer_recycle_singerName);
        add_singer = (ImageButton)itemView.findViewById(R.id.mypage_add_singer_recycle_add);

    }
}
