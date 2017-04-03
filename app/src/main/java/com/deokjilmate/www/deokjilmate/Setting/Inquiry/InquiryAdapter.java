package com.deokjilmate.www.deokjilmate.Setting.Inquiry;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.deokjilmate.www.deokjilmate.R;

import java.util.ArrayList;

/**
 * Created by 김민경 on 2017-04-03.
 */

public class InquiryAdapter extends BaseAdapter{
    ArrayList<InquiryListItem> datas;
    LayoutInflater inflater;

    public InquiryAdapter(LayoutInflater inflater, ArrayList<InquiryListItem> datas) {
        this.datas= datas;
        this.inflater= inflater;
    }



    @Override
    public int getCount() {
        return datas.size(); //datas의 개수를 리턴
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position);//datas의 특정 인덱스 위치 객체 리턴.
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if( convertView==null){
            convertView= inflater.inflate(R.layout.inquiry_item, null);
        }

        TextView title= (TextView)convertView.findViewById(R.id.textView);

        title.setText(datas.get(position).getTitle() );

        return convertView;
    }
}
