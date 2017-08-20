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
import com.deokjilmate.www.deokjilmate.UserAllSingerData;
import com.deokjilmate.www.deokjilmate.UserDataSumm;
import com.deokjilmate.www.deokjilmate.application.ApplicationController;
import com.deokjilmate.www.deokjilmate.network.NetworkService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

import retrofit2.Call;

import static android.view.View.GONE;
import static com.bumptech.glide.gifdecoder.GifHeaderParser.TAG;

/**
 * Created by dldud on 2017-02-17.
 */

public class AddsingerAdapter extends RecyclerView.Adapter<AddSingerViewHolder>{


    RequestManager requestManager;
    ArrayList<AddSingerItemData> addSingerItemDatas;//이건 추천목록
    ArrayList<AddSingerItemData> allSingerList;//이건 전체 가수 목록
    ArrayList<AddSingerItemData> searchSingerList;//실제 보여줄 것.
    HashMap<String, String> singerPNData = new HashMap<String, String>();
    NetworkService networkService;
    SetSingerNameData setSingerNameData;
    private MyPageAllSingerNumbers myPageAllSingerNumberses;
    private Context addSingerActivity;
    private int totalSingerCount;
    private int selectSingerNum;
    private String firebaseToken;
    public boolean search = false;
    private ArrayList<UserDataSumm> userDataSumms;



    public AddsingerAdapter(Context addSingerActivity, RequestManager requestManager, ArrayList<AddSingerItemData> allSingerList,
                            HashMap<String, String> singerPNData, NetworkService networkService, String firebaseToken, ArrayList<AddSingerItemData> addSingerItemDatas) {
        this.requestManager = requestManager;
        this.addSingerItemDatas = addSingerItemDatas;//이건 추천목록
        this.searchSingerList = allSingerList;
        this.allSingerList = new ArrayList<AddSingerItemData>();
        this.allSingerList.addAll(allSingerList);
        Log.v("전체", String.valueOf(this.allSingerList.size()));
        this.addSingerActivity = addSingerActivity;
        //this.singerPNData = new HashMap<String, String>();
        this.singerPNData.putAll(singerPNData);
        this.networkService = networkService;
        this.myPageAllSingerNumberses = ApplicationController.getInstance().getMyPageAllSingerNumberses();
        this.totalSingerCount = ApplicationController.getInstance().getTotalSingerCount();
        this.firebaseToken = firebaseToken;
        this.userDataSumms = new ArrayList<UserDataSumm>();
        this.userDataSumms = ApplicationController.getInstance().getUserDataSumms();
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
        if(!checkHave(allSingerList.get(position).singer_id)) {
            holder.add_singer.setImageResource(allSingerList.get(position).add_singer);
            selectSingerNum = allSingerList.get(position).singer_id;
        }
        else
            holder.add_singer.setVisibility(GONE);

        holder.add_singer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectSingerNum = allSingerList.get(position).singer_id;
                Log.v("넘버", String.valueOf(selectSingerNum));
                Log.v("넘버", String.valueOf(totalSingerCount));
                //;
                if (checkHave(selectSingerNum)) {
                    //Log.v("내 가수들", myPageAllSingerNumberses.toString());
                    Log.v("EditAdap", "이미 있음");
                    Toast.makeText(addSingerActivity.getApplicationContext(), "이미 있음", Toast.LENGTH_SHORT);
                } else {
                    if (totalSingerCount < 4) {//전체 가수 length.

                        userDataSumms.add(new UserDataSumm(allSingerList.get(position).singer_id, allSingerList.get(position).singer_name,
                                allSingerList.get(position).singer_image));
                        ApplicationController.getInstance().setUserDataSumms(userDataSumms);

                        Call<SingerAddResponse> addSinger = networkService.addSinger(new SingerAddPost(totalSingerCount,
                                selectSingerNum, firebaseToken));
                        Toast.makeText(addSingerActivity.getApplicationContext(), "추가하였습니다", Toast.LENGTH_SHORT);

//                        addSinger.enqueue(new Callback<SingerAddResponse>() {
//                            @Override
//                            public void onResponse(Call<SingerAddResponse> call, Response<SingerAddResponse> response) {
//                                if (response.isSuccessful()) {
//                                    Log.v("추가", "성공");
//                                    //해당 아이디에 맞는 애를 마이페이지에 추가
//                                } else {
//                                    Log.v("추가", "실패");
//                                }
//                            }
//
//                            @Override
//                            public void onFailure(Call<SingerAddResponse> call, Throwable t) {
//                                Log.v("추가", "통신 실패");
//
//                            }
//                        });
                    }else{
                        Log.v("EditAdap", "가수는 4명까지");
                        Toast.makeText(addSingerActivity.getApplicationContext(), "가수는 4명까지", Toast.LENGTH_SHORT);
                    }
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return (allSingerList != null) ? allSingerList.size() : 0;
    }

    public boolean checkHave(int selectNum){
       // MyPageAllSingerNumbers myPageAllSingerNumbers = ApplicationController.getInstance().getMyPageAllSingerNumberses();
        ArrayList<UserAllSingerData> userAllSingerDatas = ApplicationController.getInstance().getUserAllSingerDatas();
        for(int i = 0; i<userAllSingerDatas.size(); i++){
            if(userAllSingerDatas.get(i).getSinger_id() == selectNum)
                return true;
        }
            return false;
    }

    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());


        searchSingerList.clear();
        //입력한 데이터가 없을 경우에는 추천목록
        if (charText.length() == 0) {
            searchSingerList.addAll(addSingerItemDatas);
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
