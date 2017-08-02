package com.deokjilmate.www.deokjilmate.MyPage.EditSinger;

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
import com.deokjilmate.www.deokjilmate.MyPage.MyPageActivity;
import com.deokjilmate.www.deokjilmate.MyPage.MyPageAllSingerNumbers;
import com.deokjilmate.www.deokjilmate.MyPage.MyPageSelectedSinger;
import com.deokjilmate.www.deokjilmate.MyPage.MyPageSingerList;
import com.deokjilmate.www.deokjilmate.R;
import com.deokjilmate.www.deokjilmate.application.ApplicationController;
import com.deokjilmate.www.deokjilmate.network.NetworkService;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mypage_edit_singer);
        ButterKnife.bind(this);
        //Glide.with(this).load(R.drawable.toolbar).into(toolbarImage);
        Glide.with(this).load(R.drawable.meta).into(addSinger);

        networkService = ApplicationController.getInstance().getNetworkService();
        requestManager = Glide.with(this);
        requestManagerImage = Glide.with(this);

        recyclerView.setHasFixedSize(true);
        //recyclerView.get
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayout.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        editSingerItemDatas = new ArrayList<EditSingerItemData>();
        myAllSingerArray = new ArrayList<>();
        myAllSingerArrayN = new ArrayList<>();
        myAllSingerArray.clear();
        myAllSingerArrayN.clear();
        myPageAllSingerNumberses = ApplicationController.getInstance().getMyPageAllSingerNumberses();


        myAllSingerArray.add(myPageAllSingerNumberses.getSingerb_id());
        myAllSingerArray.add(myPageAllSingerNumberses.getSinger0_id());
        myAllSingerArray.add(myPageAllSingerNumberses.getSinger1_id());
        myAllSingerArray.add(myPageAllSingerNumberses.getSinger2_id());


        setLists();

    }


    @OnClick(R.id.MyPage_EditSinger_save)
    public void save()
    {
        //changeLists();

        NetworkThreadEdit networkThreadEdit = new NetworkThreadEdit(networkService);
        NetworkThreadDelete networkThreadDelete = new NetworkThreadDelete(networkService);
        networkThreadEdit.start();
        networkThreadDelete.start();

        try {
            networkThreadEdit.join();
            networkThreadDelete.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


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
        Call<MyPageSingerList> myPageSingerList = networkService.myPageSingerList(1);
        myPageSingerList.enqueue(new Callback<MyPageSingerList>() {
            @Override
            public void onResponse(Call<MyPageSingerList> call, Response<MyPageSingerList> response) {
                if(response.isSuccessful())
                {
                    myPageEditSelectedSingers = response.body().result;
                    //Log.v("MyPage", String.valueOf(myPageSelectedSingers.size()));

                    for(int i = 0; i<myPageEditSelectedSingers.size(); i++){
                        myAllSingerArrayN.add(myPageEditSelectedSingers.get(i).getSinger_id());
                    }

                    for(int i = 0; i<myPageEditSelectedSingers.size(); i++)
                    {
                        if(i==0)
                        {
                            editSingerHeadItemData = new EditSingerHeadItemData(myPageEditSelectedSingers.get(myAllSingerArrayN.indexOf(myAllSingerArray.get(0))).getSinger_img(),
                                    myPageEditSelectedSingers.get(myAllSingerArrayN.indexOf(myAllSingerArray.get(0))).getSinger_name(), R.drawable.chgasu_sub, R.drawable.chgasu_x);
                        }
                        else
                        {
                            editSingerItemDatas.add(new EditSingerItemData(myPageEditSelectedSingers.get(myAllSingerArrayN.indexOf(myAllSingerArray.get(i))).getSinger_img(),
                                    myPageEditSelectedSingers.get(myAllSingerArrayN.indexOf(myAllSingerArray.get(i))).getSinger_name(), R.drawable.chgasu_main, R.drawable.chgasu_x));

                        }
                    }
                    editSingerAdpater = new EditSingerAdpater(requestManagerImage, requestManager, editSingerItemDatas, editSingerHeadItemData);
                    recyclerView.setAdapter(editSingerAdpater);

                }
            }

            @Override
            public void onFailure(Call<MyPageSingerList> call, Throwable t) {

            }
        });
    }
}





class NetworkThreadEdit extends Thread{
    private NetworkService networkService;

    public NetworkThreadEdit(NetworkService networkService){
        this.networkService = networkService;
    }

    public void run(){
        int singerb_id = ApplicationController.getInstance().getMyPageAllSingerNumberses().getSingerb_id();
        int singer0_id = ApplicationController.getInstance().getMyPageAllSingerNumberses().getSinger0_id();
        int singer1_id = ApplicationController.getInstance().getMyPageAllSingerNumberses().getSinger1_id();
        int singer2_id = ApplicationController.getInstance().getMyPageAllSingerNumberses().getSinger2_id();


        Call<Void> addSinger = networkService.addSinger(new SingerAddPost(singerb_id,
                1, 0));
        addSinger.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Log.v("추가", "성공");
                    //해당 아이디에 맞는 애를 마이페이지에 추가
                } else {
                    Log.v("추가", "실패");
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.v("추가", "통신 실패");
            }
        });

        Call<Void> addSinger1 = networkService.addSinger(new SingerAddPost(singer0_id,
                1, 1));
        addSinger1.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Log.v("추가", "성공");
                    //해당 아이디에 맞는 애를 마이페이지에 추가
                } else {
                    Log.v("추가", "실패");
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.v("추가", "통신 실패");
            }
        });

        Call<Void> addSinger2 = networkService.addSinger(new SingerAddPost(singer1_id,
                1, 2));
        addSinger2.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Log.v("추가", "성공");
                    //해당 아이디에 맞는 애를 마이페이지에 추가
                } else {
                    Log.v("추가", "실패");
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.v("추가", "통신 실패");
            }
        });

        Call<Void> addSinger3 = networkService.addSinger(new SingerAddPost(singer2_id,
                1, 3));
        addSinger3.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Log.v("추가", "성공");
                    //해당 아이디에 맞는 애를 마이페이지에 추가
                } else {
                    Log.v("추가", "실패");
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.v("추가", "통신 실패");
            }
        });
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
