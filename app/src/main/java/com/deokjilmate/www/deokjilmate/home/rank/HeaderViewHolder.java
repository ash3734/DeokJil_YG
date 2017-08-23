package com.deokjilmate.www.deokjilmate.home.rank;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.deokjilmate.www.deokjilmate.R;

/**
 * Created by ash on 2017-07-29.
 */

public class HeaderViewHolder extends RecyclerView.ViewHolder {

    public ImageView imageViewSinger;
    public TextView textViewSong;
    public TextView textViewAlbum;
    public TextView textViewsingerName;

    public HeaderViewHolder(View itemView) {
        super(itemView);
        imageViewSinger = (ImageView) itemView.findViewById(R.id.home_rank_fragment_imageview_singer);
        textViewSong = (TextView) itemView.findViewById(R.id.home_rank_fragment_textview_song);
        textViewAlbum = (TextView) itemView.findViewById(R.id.home_rank_fragment_textview_album);
        textViewsingerName = (TextView) itemView.findViewById(R.id.home_rank_fragment_textview_singer);

    }

}

