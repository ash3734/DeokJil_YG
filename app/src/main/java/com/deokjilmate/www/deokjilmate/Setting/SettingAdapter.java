package com.deokjilmate.www.deokjilmate.Setting;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.deokjilmate.www.deokjilmate.R;

import java.util.ArrayList;

/**
 * Created by 김민경 on 2017-02-11.
 */

public class SettingAdapter extends BaseAdapter{

    ArrayList<SettingListItem> datas;
    LayoutInflater inflater;

    public SettingAdapter(LayoutInflater inflater, ArrayList<SettingListItem> datas) {
        this.datas= datas;
        this.inflater= inflater;
    }


    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if( view==null){
            view= inflater.inflate(R.layout.setting_item, null);
        }

        TextView title= (TextView)view.findViewById(R.id.textView);
        ImageView icon = (ImageView)view.findViewById(R.id.button);

        title.setText(datas.get(i).getTitle() );
        icon.setImageResource(datas.get(i).getIcon());

        return view;
    }
}
