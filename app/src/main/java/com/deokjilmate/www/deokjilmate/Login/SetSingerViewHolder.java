package com.deokjilmate.www.deokjilmate.Login;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.deokjilmate.www.deokjilmate.R;

/**
 * Created by dldud on 2017-02-11.
 */

public class SetSingerViewHolder extends RecyclerView.ViewHolder{

    ImageView singer_image;
    TextView singer_name;
    ImageView singer_most;
    public SetSingerViewHolder(View itemView) {
        super(itemView);
        singer_image = (ImageView)itemView.findViewById(R.id.SetSingerRecycle_singerImg);
        singer_name = (TextView)itemView.findViewById(R.id.SetSingerRecycle_singerName);
        singer_most = (ImageView)itemView.findViewById(R.id.SetSingerRecycle_most);
    }
}
