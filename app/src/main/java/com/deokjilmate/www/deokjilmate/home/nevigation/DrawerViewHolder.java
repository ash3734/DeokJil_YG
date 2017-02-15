package com.deokjilmate.www.deokjilmate.home.nevigation;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.deokjilmate.www.deokjilmate.R;

/**
 * Created by ash on 2017-02-09.
 */

public class DrawerViewHolder extends RecyclerView.ViewHolder {
    TextView textView;

    public DrawerViewHolder(View itemView) {
        super(itemView);
        textView = (TextView) itemView.findViewById(R.id.home_drawer_singername);
    }
}
