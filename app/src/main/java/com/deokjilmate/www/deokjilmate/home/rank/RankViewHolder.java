package com.deokjilmate.www.deokjilmate.home.rank;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.deokjilmate.www.deokjilmate.R;

/**
 * Created by ash on 2017-02-07.
 * 사전 투표 리사이클러 뷰 뷰홀더
 */

public class RankViewHolder extends RecyclerView.ViewHolder{

    public static TextView textViewrank;
    public static ImageView imageViewChartName;
    public static ImageView imageViewIsup;
    public RankViewHolder(View itemView) {
        super(itemView);

            textViewrank = (TextView)itemView.findViewById(R.id.home_list_textview_rank);
            imageViewChartName = (ImageView)itemView.findViewById(R.id.home_list_iamgeview_chart);
            imageViewIsup = (ImageView)itemView.findViewById(R.id.home_list_iamgeview_updown);


    }
}
