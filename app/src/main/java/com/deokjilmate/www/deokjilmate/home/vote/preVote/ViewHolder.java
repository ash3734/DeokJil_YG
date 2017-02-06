package com.deokjilmate.www.deokjilmate.home.vote.preVote;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.deokjilmate.www.deokjilmate.R;

/**
 * Created by ash on 2017-02-07.
 * 사전 투표 리사이클러 뷰 뷰홀더
 */

public class ViewHolder extends RecyclerView.ViewHolder{
    TextView textViewProgramName;
    ImageView imageViewProgram;
    public ViewHolder(View itemView) {
        super(itemView);
        textViewProgramName = (TextView) itemView.findViewById(R.id.home_list_textview_program);
        imageViewProgram = (ImageView) itemView.findViewById(R.id.home_list_imageview_program);
    }
}
