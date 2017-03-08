package com.deokjilmate.www.deokjilmate.MyPage;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.deokjilmate.www.deokjilmate.R;

/**
 * Created by dldud on 2017-03-06.
 */

public class MyPageHeadViewHolder extends RecyclerView.ViewHolder{

    ImageView singer_Image;
    ImageView rank_Image;
    TextView singer_name;
    TextView vote_count;

    public MyPageHeadViewHolder(View itemView) {
        super(itemView);
        singer_Image = (ImageView)itemView.findViewById(R.id.MyPageHead_SingerImage);
        rank_Image = (ImageView)itemView.findViewById(R.id.MyPageHead_rank);
        singer_name = (TextView) itemView.findViewById(R.id.MyPageHead_singerName);
        vote_count = (TextView)itemView.findViewById(R.id.MyPageHead_voteCount);
    }
}
