package com.deokjilmate.www.deokjilmate.MyPage.EditSinger;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.deokjilmate.www.deokjilmate.MyPage.AddSinger.AddSingerActivity;
import com.deokjilmate.www.deokjilmate.MyPage.AddSinger.SingerAddPost;
import com.deokjilmate.www.deokjilmate.MyPage.AddSinger.SingerAddResponse;
import com.deokjilmate.www.deokjilmate.MyPage.MyPageActivity;
import com.deokjilmate.www.deokjilmate.MyPage.MyPageAllSingerNumbers;
import com.deokjilmate.www.deokjilmate.MyPage.MyPageSelectedSinger;
import com.deokjilmate.www.deokjilmate.R;
import com.deokjilmate.www.deokjilmate.SharedPrefrernceController;
import com.deokjilmate.www.deokjilmate.UserAllSingerData;
import com.deokjilmate.www.deokjilmate.UserAllSingerResponse;
import com.deokjilmate.www.deokjilmate.application.ApplicationController;
import com.deokjilmate.www.deokjilmate.network.NetworkService;
import com.tsengvn.typekit.TypekitContextWrapper;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditSingerActivity extends AppCompatActivity {

    @BindView(R.id.MyPage_EditSinger_backImage)
    ImageButton back;

    @BindView(R.id.MyPage_EditSinger_save)
    ImageButton save;

    @BindView(R.id.MyPage_EditSinger_recycle)
    RecyclerView recyclerView;

    @BindView(R.id.MyPage_EditSinger_addSinger)
    ImageView addSinger;

    private final String TAG = "LOG::EditSingerActivity";

    private LinearLayoutManager linearLayoutManager;
    private RequestManager requestManager;
    private RequestManager requestManagerImage;
    private ArrayList<EditSingerItemData> editSingerItemDatas;//추천목록
    private EditSingerHeadItemData editSingerHeadItemData;
    private EditSingerAdpater editSingerAdpater;
    private NetworkService networkService;
    private ArrayList<MyPageSelectedSinger> myPageEditSelectedSingers;
    private ArrayList<Integer> myAllSingerArrayN;
    private ArrayList<Integer> myAllSingerArray;
    private MyPageAllSingerNumbers myPageAllSingerNumberses;

    private ArrayList<UserAllSingerData> userAllSingerDatas;
    private String firebaseToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mypage_edit_singer);
        ButterKnife.bind(this);
        //Glide.with(this).load(R.drawable.toolbar).into(toolbarImage);
        Glide.with(this).load(R.drawable.floating_add).into(addSinger);



        networkService = ApplicationController.getInstance().getNetworkService();
        requestManager = Glide.with(this);
        requestManagerImage = Glide.with(this);

        recyclerView.setHasFixedSize(true);
        //recyclerView.get
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayout.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        editSingerItemDatas = new ArrayList<EditSingerItemData>();

        myPageAllSingerNumberses = ApplicationController.getInstance().getMyPageAllSingerNumberses();

        //userAllSingerDatas = ApplicationController.getInstance().getUserAllSingerDatas();
        userAllSingerDatas = new ArrayList<UserAllSingerData>();
        //firebaseToken = ApplicationController.getInstance().getFirebaseToken();
        firebaseToken = SharedPrefrernceController.getFirebaseToken(EditSingerActivity.this);

        //TODO : Edit의 경우 일단 모든 정보는 불러올 필요가 있음. 피엠이 말하길 수정 완료 되면 그대로 머물게 있게 하라고..
        setLists();

    }


    @OnClick(R.id.MyPage_EditSinger_save)
    public void save()
    {
        //changeLists();
        //TODO : 이 부분이 수정 후 저장하는 부분
        Thread networkThreadEdit = new NetworkThreadEdit(networkService);
       // NetworkThreadEdit networkThreadEdit = new NetworkThreadEdit(networkService);
        //NetworkThreadDelete networkThreadDelete = new NetworkThreadDelete(networkService);
        networkThreadEdit.start();
        System.out.println("대기 중..");
        try {
            networkThreadEdit.join();
            Thread.sleep(500);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("대기 완료..");

        //deleteSinger();

        finish();
        startActivity(getIntent());
        // setLists();
    }

    @OnClick(R.id.MyPage_EditSinger_backImage)
    public void clickBack()
    {
        Intent intent = new Intent(getApplicationContext(), MyPageActivity.class);
        startActivity(intent);
        finish();
    }

    @OnClick(R.id.MyPage_EditSinger_addSinger)
    public void clickAdd()
    {
        Intent intent = new Intent(getApplicationContext(), AddSingerActivity.class);
        startActivity(intent);
    }

    public void setLists(){
        Call<UserAllSingerResponse> userAllSingerResponse = networkService.userAllSinger(firebaseToken);
        userAllSingerResponse.enqueue(new Callback<UserAllSingerResponse>() {
            @Override
            public void onResponse(Call<UserAllSingerResponse> call, Response<UserAllSingerResponse> response) {
                if(response.isSuccessful()){
                    userAllSingerDatas = response.body().data;
                    ApplicationController.getInstance().setUserAllSingerDatas(userAllSingerDatas);
                    for(int i = 0; i<userAllSingerDatas.size(); i++)
                    {
                        if(i==0 && userAllSingerDatas.get(0)!=null)
                        {//myAllSingerArray.indexOf(myAllSingerArray.get(0))
                            editSingerHeadItemData = new EditSingerHeadItemData(userAllSingerDatas.get(i).getSinger_img(),
                                    userAllSingerDatas.get(i).getSinger_name(), R.drawable.chgasu_sub, R.drawable.chgasu_x);
                        }
                        else
                        {
                            editSingerItemDatas.add(new EditSingerItemData(userAllSingerDatas.get(i).getSinger_img(),
                                    userAllSingerDatas.get(i).getSinger_name(), R.drawable.chgasu_main, R.drawable.chgasu_x));
                        }
                    }
                    editSingerAdpater = new EditSingerAdpater(requestManagerImage, requestManager, editSingerItemDatas, editSingerHeadItemData);
                    recyclerView.setAdapter(editSingerAdpater);
                }
            }

            @Override
            public void onFailure(Call<UserAllSingerResponse> call, Throwable t) {

            }
        });
    }

    public void deleteSinger(){
        for(int i = 0; i<ApplicationController.getInstance().getDeleteList().size(); i++) {
            Call<Void> deleteSinger = networkService.deleteSinger(new EditSingerDelete(
                    1, ApplicationController.getInstance().getDeleteList().get(i)));

            deleteSinger.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.isSuccessful()) {
                        Log.v(TAG, "삭제 성공");
                        //해당 아이디에 맞는 애를 마이페이지에 추가
                    } else {
                        Log.v(TAG, "삭제 실패");
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Log.e(TAG, "통신 실패");
                }
            });
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }
}





class NetworkThreadEdit extends Thread{
    private NetworkService networkService;

    public NetworkThreadEdit(NetworkService networkService){
        this.networkService = networkService;
    }

    public void run(){
        ArrayList<UserAllSingerData> userAllSingerDatas = ApplicationController.getInstance().getUserAllSingerDatas();
        String firebaseToken = ApplicationController.getInstance().getFirebaseToken();
        for(int i = 0; i<userAllSingerDatas.size(); i++)
        {
            Call<SingerAddResponse> addSinger = networkService.addSinger(new SingerAddPost(i,
                    userAllSingerDatas.get(i).getSinger_id(), firebaseToken));
            addSinger.enqueue(new Callback<SingerAddResponse>() {
                @Override
                public void onResponse(Call<SingerAddResponse> call, Response<SingerAddResponse> response) {
                    if (response.isSuccessful()) {
                        Log.v("추가", "성공0");
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
}


class NetworkThreadDelete extends Thread{

    private NetworkService networkService;

    public NetworkThreadDelete(NetworkService networkService){
        this.networkService = networkService;
    }

    public void run(){
        for(int i = 0; i<ApplicationController.getInstance().getDeleteList().size(); i++) {
            Call<Void> deleteSinger = networkService.deleteSinger(new EditSingerDelete(
                    1, ApplicationController.getInstance().getDeleteList().get(i)));

            deleteSinger.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.isSuccessful()) {
                        Log.v("삭제", "성공");
                        //해당 아이디에 맞는 애를 마이페이지에 추가
                    } else {
                        Log.v("삭제", "실패");
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Log.v("삭제", "통신 실패");
                }
            });
        }
    }
}
