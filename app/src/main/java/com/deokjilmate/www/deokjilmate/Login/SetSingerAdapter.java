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

    private RequestManager requestManager;
    private RequestManager requestManagerSel;
    private ArrayList<SetSingerItemData> setSingerItemDatas;//이건 추천목록
    private ArrayList<SetSingerItemData> allSingerList;//이건 전체 가수 목록
    private ArrayList<SetSingerItemData> searchSingerList;//실제 보여줄 것.
    private HashMap<String, String> singerPNData = new HashMap<String, String>();
    private SetSingerNameData setSingerNameData;
    private final String TAG = "LOG::SetSingerAdapter";

    boolean search = false;
    //보여지는 것은 추천목록이고 검색하면 검색한 녀석이 보여져야 함.

    public SetSingerAdapter(RequestManager requestManager, RequestManager requestManagerSel, ArrayList<SetSingerItemData> setSingerItemDatas, ArrayList<SetSingerItemData> allSingerList, HashMap<String, String> singerPNData) {
        this.requestManager = requestManager;
        this.setSingerItemDatas = setSingerItemDatas;//이건 추천목록
        this.searchSingerList = allSingerList;
        this.allSingerList = new ArrayList<SetSingerItemData>();
        this.allSingerList.addAll(allSingerList);
        this.requestManagerSel = requestManagerSel;
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
            Log.v(TAG, "검색 false");
            requestManager.load(setSingerItemDatas.get(position).singer_image).into(holder.singer_image);
            holder.singer_name.setText(setSingerItemDatas.get(position).singer_name);
            //holder.singer_most.setImageResource(setSingerItemDatas.get(position).singer_most);
            requestManagerSel.load(setSingerItemDatas.get(position).singer_most).into(holder.singer_most);
        }
        else{
            Log.v(TAG, "검색 true");
            //holder.singer_image.setImageResource(searchSingerList.get(position).singer_image);
            requestManager.load(searchSingerList.get(position).singer_image).into(holder.singer_image);
            holder.singer_name.setText(searchSingerList.get(position).singer_name);
            //holder.singer_most.setImageResource(searchSingerList.get(position).singer_most);
            requestManagerSel.load(setSingerItemDatas.get(position).singer_most).into(holder.singer_most);

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
            Log.v(TAG, String.valueOf(allSingerList.size()));

            for (int i = 0; i < allSingerList.size() ; i++)
            {
                Log.v(TAG, "들어옴");
                Log.v(TAG, charText);

                String wp = allSingerList.get(i).getSinger_name();//실제 서버단에 저장된 가수 이름.AOA
                //실질적 비교를 위해서는 에이오에이라고 치면 이것이 AOA가 되어야 함.
                //해쉬 내에서 wp를 키로하는 애들의 value가 charText를 포함.
                Log.v(TAG, wp);
                Log.v(TAG, singerPNData.toString());
                Log.v(TAG, singerPNData.get(wp));
               // Log.v("가수", singerPNData.)
                if (wp.toLowerCase(Locale.getDefault()).contains(charText))
                {//실제 가수 이름이 charText를 갖고 있다면!.
                    searchSingerList.add(allSingerList.get(i));
                    Log.v(TAG, "일치");
                    search = true;
                }
                else if(singerPNData.get(wp).toLowerCase(Locale.getDefault()).contains(charText)) {
                    Log.v(TAG, "불일치");
                    searchSingerList.add(allSingerList.get(i));
                    search = true;
                }
                else {
                    Log.v(TAG, "레알 불일치");
                    //search = false;
                }
            }
        }
        //입력한 데이터가 있을 경우에는 일치하는 항목들만 찾아 출력해줍니다.
        notifyDataSetChanged();
    }
}
