package com.deokjilmate.www.deokjilmate.Login;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.RequestManager;
import com.deokjilmate.www.deokjilmate.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

/**
 * Created by dldud on 2017-02-11.
 */

public class SetSingerAdapter extends RecyclerView.Adapter<SetSingerViewHolder>{

    RequestManager requestManager;
    ArrayList<SetSingerItemData> setSingerItemDatas;//이건 추천목록
    ArrayList<SetSingerItemData> allSingerList;//이건 전체 가수 목록
    ArrayList<SetSingerItemData> searchSingerList;//실제 보여줄 것.
    HashMap<String, String> singerPNData = new HashMap<String, String>();
    SetSingerNameData setSingerNameData;


    boolean search = false;
    //보여지는 것은 추천목록이고 검색하면 검색한 녀석이 보여져야 함.

    public SetSingerAdapter(RequestManager requestManager, ArrayList<SetSingerItemData> setSingerItemDatas, ArrayList<SetSingerItemData> allSingerList, HashMap<String, String> singerPNData) {
        this.requestManager = requestManager;
        this.setSingerItemDatas = setSingerItemDatas;//이건 추천목록
        this.searchSingerList = allSingerList;
        this.allSingerList = new ArrayList<SetSingerItemData>();
        this.allSingerList.addAll(allSingerList);
        Log.v("전체", String.valueOf(this.allSingerList.size()));

        //this.singerPNData = new HashMap<String, String>();
        this.singerPNData.putAll(singerPNData);

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
            Log.v("false", "false");
            requestManager.load(setSingerItemDatas.get(position).singer_image).into(holder.singer_image);
            holder.singer_name.setText(setSingerItemDatas.get(position).singer_name);
            holder.singer_most.setImageResource(setSingerItemDatas.get(position).singer_most);
        }
        else{
            Log.v("true", "true");
            //holder.singer_image.setImageResource(searchSingerList.get(position).singer_image);
            requestManager.load(searchSingerList.get(position).singer_image).into(holder.singer_image);
            holder.singer_name.setText(searchSingerList.get(position).singer_name);
            holder.singer_most.setImageResource(searchSingerList.get(position).singer_most);
        }
    }

    @Override
    public int getItemCount() {
        if(search == false) {
            return (setSingerItemDatas != null) ? setSingerItemDatas.size() : 0;

        }
        else {
            return (searchSingerList != null) ? searchSingerList.size() : 0;
        }
    }

    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());


        searchSingerList.clear();
        //입력한 데이터가 없을 경우에는 추천목록
        if (charText.length() == 0) {
            searchSingerList.addAll(setSingerItemDatas);
            search = false;
        }
        else
        {//검색하면 전체로부터 가져오기.
            Log.v("검색 중", "검색 중");
            Log.v("전체2", String.valueOf(allSingerList.size()));
            Log.v("해쉬", String.valueOf(singerPNData));

            for (int i = 0; i < allSingerList.size() ; i++)
            {
                Log.v("들어옴", "들어옴");
                Log.v("입력", charText);

                String wp = allSingerList.get(i).getSinger_name();//실제 서버단에 저장된 가수 이름.AOA
                //실질적 비교를 위해서는 에이오에이라고 치면 이것이 AOA가 되어야 함.
                //해쉬 내에서 wp를 키로하는 애들의 value가 charText를 포함.
                Log.v("첫번째 wp", wp);
                Log.v("가수", singerPNData.toString());
               // Log.v("가수", singerPNData.)
                if (wp.toLowerCase(Locale.getDefault()).contains(charText))
                {//실제 가수 이름이 charText를 갖고 있다면!.
                    searchSingerList.add(allSingerList.get(i));
                    Log.v("일치", "일치");
                    search = true;
                }
                else if(singerPNData.get(wp).contains(charText)) {
                    Log.v("불일치", "불일치");
                    searchSingerList.add(allSingerList.get(i));
                    search = true;
                }
                else {
                    Log.v("불일치", "레알 불일치");
                    //search = false;
                }
            }
        }
        //입력한 데이터가 있을 경우에는 일치하는 항목들만 찾아 출력해줍니다.
        notifyDataSetChanged();
    }
}
