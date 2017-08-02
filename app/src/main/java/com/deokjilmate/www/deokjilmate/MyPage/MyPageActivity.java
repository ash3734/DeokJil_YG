package com.deokjilmate.www.deokjilmate.MyPage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.deokjilmate.www.deokjilmate.MyPage.EditSinger.EditSingerActivity;
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

public class MyPageActivity extends AppCompatActivity {

    @BindView(R.id.MyPage_editSinger)
    ImageButton plusSub;

    @BindView(R.id.mypage_main_backImage)
    ImageButton back;

    @BindView(R.id.MyPage_subRecycle)
    RecyclerView subSingerrecyclerView;

    @BindView(R.id.Mypage_main_layout)
    LinearLayout mainLayout;

    @BindView(R.id.Mypage_editProfile)
    ImageView editProfile;


    private RequestManager requestManager_singer;
    private RequestManager requestManager_rank;
    private ArrayList<MyPageItemData> myPageItemDatas;

    private MyPageHeadItemData myPageHeadItemData;

    private LinearLayoutManager linearLayoutManager;
    private MyPageAdapter myPageAdapter;
    private NetworkService networkService;

    // NoxView noxView;

    private int member_id = 0;

    private MyPageCheckMainSub myPageCheckMainSub;
    private MyPageAllSingerNumbers myPageAllSingerNumberses;
    private ArrayList<MyPageSelectedSinger> myPageSelectedSingers;
    private ArrayList<Integer> myAllSingerArray;
    private ArrayList<Integer> myAllSingerArrayN;

    //ArrayList<MyPageSingerList> myPageSingerList;




    String imageUrl = "https://00e9e64bac7c4aab450e3b22d212cabca5cfe7403d011279ee-apidata.googleusercontent.com/download/storage/v1/b/duckmate_1/o/new%2Fhi.png?qk=AD5uMEu5kgUv6YApI3IzolJW8OWW-pQNcREa89fmIeqQc4J4WIUao1JO4A_f2aTkJ91QyoigAf0icnspWaNZmeISXBAzxl3_4uic4kFCLjL-DSnZimW0ZXboYfnPzCW_Vtf2YwncRtzC6eiePWIJTt0o-S2w5oqFaeA3GV9eGhHpkBO04HyDYJGWsGDN2YMrXCs2l-Ds1DZsxgBZHk23B1-HdoaQFJs34fNtHa8BTaA1i9tG3WVWw7VpQx2RxrMSt2F-UowVQNA9sLcTFuOa7ROMi87qMsWCxk0OOE88-8mP_kXJEFSvONS9Ng1dvZtnIS-95OzKGNogYmZ7fgCj88nHAJK5GzNjiB561g_a_iOclET6IVeYtViHFhDiJBLIW6hVGCBIu0TyfzE15ry4TEiuaDZ8ONjLiqPf6CK345B1OMDjhw5sZcu-RnNXS7mDCOdvUiLCTQyr5LPDIG2VPiq8ZtcanyTaibX-eB5z-JZZCPzharQDDsyM4A4OiGKhNt71mFJF-tG4WYHdF3LYtz41R1BhttPiWjBH2yh19th_JjPl7hyhodF8EW5KCj_LYYiahZg8NlPnrjyKk5VmxBDnGA0R1OQkFcBAok6biq1jtmAkcU6XiwdVL0FZtoOsvgFk651C3FZMDRtRgGpDgAWTOnRMBr2P4p_YJfEsliKGRCmAB0LbByNIu3Meso_-EUJS-0mSX_KDqmB7O-C0f2xdihC4XuLTguUZNxG3RNK096gA2QFJMojPCzqZ7pYspn56iK1VaVXJ6ZHVKLYYrnLBBke7qzMaiw";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mypage_main);
        ButterKnife.bind(this);
        setLayoutSize();
       // Glide.with(this).load(R.drawable.toolbar).into(toolbarImage);
        Glide.with(this).load(R.drawable.menu_chgasu).into(plusSub);
        Glide.with(this).load(R.drawable.meta).into(editProfile);
       // Glide.with(this).load(R.drawable.meta).into(backImage);

        networkService = ApplicationController.getInstance().getNetworkService();

        requestManager_singer = Glide.with(this);
        requestManager_rank = Glide.with(this);

        subSingerrecyclerView.setHasFixedSize(true);
        //recyclerView.get
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayout.VERTICAL);
        subSingerrecyclerView.setLayoutManager(linearLayoutManager);
        myAllSingerArray = new ArrayList<>();
        myAllSingerArrayN = new ArrayList<>();
        myAllSingerArray.clear();
        myAllSingerArrayN.clear();


        //myPageAllSingerNumberses = new ArrayList<MyPageAllSingerNumbers>();
        //myPageCheckMainSub = new MyPageCheckMainSub();
        //메인 서브 판별
        final Call<MyPageCheckMainSub> myPageCheckMainSub = networkService.myPageCheckMainSub(1);
        myPageCheckMainSub.enqueue(new Callback<MyPageCheckMainSub>() {
            @Override
            public void onResponse(Call<MyPageCheckMainSub> call, Response<MyPageCheckMainSub> response) {
                if(response.body().result.equals("success")) {
                    myPageAllSingerNumberses = new MyPageAllSingerNumbers(response.body().data.singerb_id, response.body().data.singer0_id, response.body().data.singer1_id,
                            response.body().data.singer2_id,response.body().data.singer3_id);

                    int count = nextCount(myPageAllSingerNumberses);

                    ApplicationController.getInstance().setTotalSingerCount(count);
                    ApplicationController.getInstance().setMyPageAllSingerNumberses(myPageAllSingerNumberses);

                    Log.v("MyPage", String.valueOf(count));
                    Log.v("MyPage", myPageAllSingerNumberses.toString());


                    myAllSingerArray.add(myPageAllSingerNumberses.getSingerb_id());
                    myAllSingerArray.add(myPageAllSingerNumberses.getSinger0_id());
                    myAllSingerArray.add(myPageAllSingerNumberses.getSinger1_id());
                    myAllSingerArray.add(myPageAllSingerNumberses.getSinger2_id());

                    setLists();

                    //전체 몇 명인지 확인.
                }
                else{
                    Toast.makeText(getApplicationContext(), "못 받음", Toast.LENGTH_LONG);
                }
            }

            @Override
            public void onFailure(Call<MyPageCheckMainSub> call, Throwable t) {

            }
        });

        //TODO : 통신 이후 이 부분 바뀌어야 함.
        //메인



    }

    @OnClick(R.id.mypage_main_backImage)
    public void back(){

        //TODO : 홈으로
//        Intent intent = new Intent(getApplicationContext(), EditSingerActivity.class);
//        startActivity(intent);
    }

    @OnClick(R.id.MyPage_editSinger)
    public void plusSub()
    {
        Intent intent = new Intent(getApplicationContext(), EditSingerActivity.class);
        startActivity(intent);
    }

    public void setLayoutSize(){
        DisplayMetrics dm = getApplicationContext().getResources().getDisplayMetrics();
        int width = dm.widthPixels;
        int height = width*2/3;
        mainLayout.setMinimumHeight(height);
    }

    public int nextCount(MyPageAllSingerNumbers myPageAllSingerNumbers){
        int count;//전체 서브가 몇 명인지
        if(myPageAllSingerNumbers.singer0_id == 0)
            count = 0;//서브 없음
        else if(myPageAllSingerNumbers.singer1_id == 0)
            count = 1;//서브 1명
        else if(myPageAllSingerNumbers.singer2_id == 0)
            count = 2;//서브 2명
        else
            count = 3;//서브 3명(풀)
        return count;
    }

    public void setLists(){
        //서브
        myPageItemDatas = new ArrayList<MyPageItemData>();

        //가수 목록 불러오기
        final Call<MyPageSingerList> myPageSingerList = networkService.myPageSingerList(1);
        myPageSingerList.enqueue(new Callback<MyPageSingerList>() {
            @Override
            public void onResponse(Call<MyPageSingerList> call, Response<MyPageSingerList> response) {
                if(response.isSuccessful())
                {
                    myPageSelectedSingers = response.body().result;
                    //myAllSingerArray1.add(myPageSelectedSingers.get);

                    for(int i = 0; i<myPageSelectedSingers.size(); i++){
                        myAllSingerArrayN.add(myPageSelectedSingers.get(i).getSinger_id());
                    }

                    for(int i = 0; i<myAllSingerArray.size(); i++)
                    {
                        if(i==0)
                        {//myAllSingerArray.indexOf(myAllSingerArray.get(0))
                            if(myAllSingerArray.get(0)!=null) {
                                myPageHeadItemData = new MyPageHeadItemData(myPageSelectedSingers.get(myAllSingerArrayN.indexOf(myAllSingerArray.get(0))).getSinger_img(),
                                        R.drawable.badge_newbie, myPageSelectedSingers.get(myAllSingerArrayN.indexOf(myAllSingerArray.get(0))).getSinger_name(),
                                        myPageSelectedSingers.get(myAllSingerArrayN.indexOf(myAllSingerArray.get(0))).getChoice_count());
                            }
                            //Log.v("MyPage", myPageSelectedSingers.get(0).getSinger_img());
                        }
                        else
                        {
                            if(myAllSingerArray.get(i)!=null) {
                                myPageItemDatas.add(new MyPageItemData(myPageSelectedSingers.get(myAllSingerArrayN.indexOf(myAllSingerArray.get(i))).getSinger_img(),
                                        R.drawable.badge_newbie, myPageSelectedSingers.get(myAllSingerArrayN.indexOf(myAllSingerArray.get(i))).getSinger_name(),
                                        myPageSelectedSingers.get(myAllSingerArrayN.indexOf(myAllSingerArray.get(i))).getChoice_count()));
                            }
                            //Log.v("MyPage", myPageSelectedSingers.get(i).getSinger_img());
                        }
                    }
                    myPageAdapter = new MyPageAdapter(requestManager_singer, requestManager_rank, myPageItemDatas, myPageHeadItemData);
                    subSingerrecyclerView.setAdapter(myPageAdapter);
                }
            }

            @Override
            public void onFailure(Call<MyPageSingerList> call, Throwable t) {

            }
        });
    }

}
