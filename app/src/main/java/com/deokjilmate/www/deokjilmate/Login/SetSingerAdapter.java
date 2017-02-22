package com.deokjilmate.www.deokjilmate.Login;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.RequestManager;
import com.deokjilmate.www.deokjilmate.R;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by dldud on 2017-02-11.
 */

public class SetSingerAdapter extends RecyclerView.Adapter<SetSingerViewHolder>{

    RequestManager requestManager;
    ArrayList<SetSingerItemData> setSingerItemDatas;//이건 추천목록
    ArrayList<SetSingerItemData> allSingerList;//이건 전체 가수 목록
    ArrayList<SetSingerItemData> searchSingerList;//실제 보여줄 것.

    boolean search = false;
    //보여지는 것은 추천목록이고 검색하면 검색한 녀석이 보여져야 함.

    public SetSingerAdapter(RequestManager requestManager, ArrayList<SetSingerItemData> setSingerItemDatas, ArrayList<SetSingerItemData> allSingerList) {
        this.requestManager = requestManager;
        this.setSingerItemDatas = setSingerItemDatas;//이건 추천목록

        this.allSingerList = allSingerList;
        this.searchSingerList = allSingerList;

    }

    @Override
    public SetSingerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.login_setsinger_recycle, parent,false);
        //화면상에 보여줄 레이아웃을 지정해주어야 함.
        SetSingerViewHolder viewHolder = new SetSingerViewHolder(itemView);
        //itemView.setOnClickListener(clickEvent);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(SetSingerViewHolder holder, int position) {
        if(search == false) {
            holder.singer_image.setImageResource(setSingerItemDatas.get(position).singer_image);
            holder.singer_name.setText(setSingerItemDatas.get(position).singer_name);
            holder.singer_most.setImageResource(setSingerItemDatas.get(position).singer_most);
        }
        else{
            holder.singer_image.setImageResource(searchSingerList.get(position).singer_image);
            holder.singer_name.setText(searchSingerList.get(position).singer_name);
            holder.singer_most.setImageResource(searchSingerList.get(position).singer_most);
        }
    }

    @Override
    public int getItemCount() {
        if(search == false)
            return (setSingerItemDatas != null) ? setSingerItemDatas.size() : 0;
        else
            return (searchSingerList != null) ? searchSingerList.size() : 0;
    }

    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());

        //먼저 arSrc객체를 비워줍니다.
        searchSingerList.clear();

        //입력한 데이터가 없을 경우에는 추천목록
        if (charText.length() == 0)
            searchSingerList.addAll(setSingerItemDatas);
        else
        {//검색하면 전체로부터 가져오기.
            for (int i = 0; i < allSingerList.size() ; i++)
            {
                String wp = allSingerList.get(i).getSinger_name();

                if (wp.toLowerCase(Locale.getDefault()).contains(charText))
                {
                    searchSingerList.add(allSingerList.get(i));
                }
            }
        }
        //입력한 데이터가 있을 경우에는 일치하는 항목들만 찾아 출력해줍니다.
        notifyDataSetChanged();
    }
}
