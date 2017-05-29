package com.deokjilmate.www.deokjilmate.MyPage.AddSinger;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.RequestManager;
import com.deokjilmate.www.deokjilmate.Login.SetSingerNameData;
import com.deokjilmate.www.deokjilmate.MyPage.MyPageAllSingerNumbers;
import com.deokjilmate.www.deokjilmate.R;
import com.deokjilmate.www.deokjilmate.application.ApplicationController;
import com.deokjilmate.www.deokjilmate.network.NetworkService;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by dldud on 2017-02-17.
 */

public class AddsingerAdapter extends RecyclerView.Adapter<AddSingerViewHolder>{


    RequestManager requestManager;
    //  ArrayList<AddSingerItemData> setSingerItemDatas;//이건 추천목록
    ArrayList<AddSingerItemData> allSingerList;//이건 전체 가수 목록
    ArrayList<AddSingerItemData> searchSingerList;//실제 보여줄 것.
    HashMap<String, String> singerPNData = new HashMap<String, String>();
    NetworkService networkService;
    SetSingerNameData setSingerNameData;
    private ArrayList<MyPageAllSingerNumbers> myPageAllSingerNumberses;
    private Context addSingerActivity;

    private int selectSingerNum;


    public AddsingerAdapter(Context addSingerActivity, RequestManager requestManager, ArrayList<AddSingerItemData> allSingerList,
                            HashMap<String, String> singerPNData, NetworkService networkService) {
        this.requestManager = requestManager;
        //this.setSingerItemDatas = setSingerItemDatas;//이건 추천목록
        this.searchSingerList = allSingerList;
        this.allSingerList = new ArrayList<AddSingerItemData>();
        this.allSingerList.addAll(allSingerList);
        Log.v("전체", String.valueOf(this.allSingerList.size()));
        this.addSingerActivity = addSingerActivity;
        //this.singerPNData = new HashMap<String, String>();
        this.singerPNData.putAll(singerPNData);
        this.networkService = networkService;
        this.myPageAllSingerNumberses = ApplicationController.getInstance().getMyPageAllSingerNumberses();

    }

    @Override
    public AddSingerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.mypage_add_singer_recycle, parent,false);

        //화면상에 보여줄 레이아웃을 지정해주어야 함.
        AddSingerViewHolder viewHolder = new AddSingerViewHolder(itemView);
        //itemView.setOnClickListener(clickEvent);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(AddSingerViewHolder holder, final int position) {
        holder.singer_rank.setText(String.valueOf(position+1));
        requestManager.load(allSingerList.get(position).singer_image).into(holder.singer_Image);
        holder.singer_Name.setText(allSingerList.get(position).singer_name);
        holder.add_singer.setImageResource(allSingerList.get(position).add_singer);
        selectSingerNum = allSingerList.get(position).singer_id;
        Log.v("넘버", String.valueOf(selectSingerNum));
        holder.add_singer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectSingerNum = allSingerList.get(position).singer_id;

                if (ApplicationController.getInstance().getMyPageAllSingerNumberses().contains(selectSingerNum)) {
                    //Log.v("내 가수들", myPageAllSingerNumberses.toString());
                    Toast.makeText(addSingerActivity.getApplicationContext(), "이미 있음", Toast.LENGTH_SHORT);
                } else{
                    Call<SingerAddResponse> addSinger = networkService.addSinger(new SingerAddPost(selectSingerNum,
                            1, ApplicationController.getInstance().getTotalSingerCount()));
                    addSinger.enqueue(new Callback<SingerAddResponse>() {
                        @Override
                        public void onResponse(Call<SingerAddResponse> call, Response<SingerAddResponse> response) {
                            if (response.isSuccessful()) {
                                Log.v("추가", "성공");
                                //해당 아이디에 맞는 애를 마이페이지에 추가
                            } else {
                                Log.v("추가", "실패");
                            }
                        }

                        @Override
                        public void onFailure(Call<SingerAddResponse> call, Throwable t) {
                            Log.v("추가", "통신 실패");

                        }
                    });
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return (allSingerList != null) ? allSingerList.size() : 0;
    }
}
