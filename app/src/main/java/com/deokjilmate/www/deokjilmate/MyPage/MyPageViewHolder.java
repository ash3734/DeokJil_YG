package com.deokjilmate.www.deokjilmate.MyPage;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.deokjilmate.www.deokjilmate.R;

/**
 * Created by dldud on 2017-02-14.
 */

public class MyPageViewHolder extends RecyclerView.ViewHolder {

    ImageView singer_Image;
    ImageView rank_Image;
    TextView singer_name;
    TextView vote_count;

    public MyPageViewHolder(View itemView) {
        super(itemView);
        singer_Image = (ImageView)itemView.findViewById(R.id.MyPageRecycle_SingerImage);
        rank_Image = (ImageView)itemView.findViewById(R.id.MyPageRecycle_rank);
        singer_name = (TextView) itemView.findViewById(R.id.MyPageRecycle_singerName);
        vote_count = (TextView)itemView.findViewById(R.id.MyPageRecycle_voteCount);

    }
}
