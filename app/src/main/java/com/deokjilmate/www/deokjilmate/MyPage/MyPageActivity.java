package com.deokjilmate.www.deokjilmate.MyPage;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.deokjilmate.www.deokjilmate.MyPage.EditSinger.EditSingerActivity;
import com.deokjilmate.www.deokjilmate.Profile.EditProfileActivity;
import com.deokjilmate.www.deokjilmate.R;
import com.deokjilmate.www.deokjilmate.SharedPrefrernceController;
import com.deokjilmate.www.deokjilmate.UserAllSingerData;
import com.deokjilmate.www.deokjilmate.UserAllSingerResponse;
import com.deokjilmate.www.deokjilmate.UserDataSumm;
import com.deokjilmate.www.deokjilmate.application.ApplicationController;
import com.deokjilmate.www.deokjilmate.home.HomeActivity;
import com.deokjilmate.www.deokjilmate.network.NetworkService;
import com.tsengvn.typekit.TypekitContextWrapper;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyPageActivity extends AppCompatActivity {

    @BindView(R.id.MyPage_editSinger)
    ImageView plusSub;

    @BindView(R.id.mypage_main_backImage)
    ImageButton back;

    @BindView(R.id.MyPage_subRecycle)
    RecyclerView subSingerrecyclerView;

    @BindView(R.id.Mypage_main_layout)
    LinearLayout mainLayout;

    @BindView(R.id.Mypage_editProfile)
    ImageView editProfile;

    @BindView(R.id.MyPage_profileImage)
    ImageView profileImage;

    @BindView(R.id.mypage_My_TextView_Id)
    TextView mypageNickname;

    @BindView(R.id.mypage_total_vote)
    TextView mypageTotalVote;


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
    private ArrayList<UserAllSingerData> userAllSingerDatas;
    private String firebaseToken;
    private ArrayList<UserDataSumm> userDataSumms;
    private int totalVote;

    private Context context;


    //ArrayList<MyPageSingerList> myPageSingerList;


    String imageUrl = "https://00e9e64bac7c4aab450e3b22d212cabca5cfe7403d011279ee-apidata.googleusercontent.com/download/storage/v1/b/duckmate_1/o/new%2Fhi.png?qk=AD5uMEu5kgUv6YApI3IzolJW8OWW-pQNcREa89fmIeqQc4J4WIUao1JO4A_f2aTkJ91QyoigAf0icnspWaNZmeISXBAzxl3_4uic4kFCLjL-DSnZimW0ZXboYfnPzCW_Vtf2YwncRtzC6eiePWIJTt0o-S2w5oqFaeA3GV9eGhHpkBO04HyDYJGWsGDN2YMrXCs2l-Ds1DZsxgBZHk23B1-HdoaQFJs34fNtHa8BTaA1i9tG3WVWw7VpQx2RxrMSt2F-UowVQNA9sLcTFuOa7ROMi87qMsWCxk0OOE88-8mP_kXJEFSvONS9Ng1dvZtnIS-95OzKGNogYmZ7fgCj88nHAJK5GzNjiB561g_a_iOclET6IVeYtViHFhDiJBLIW6hVGCBIu0TyfzE15ry4TEiuaDZ8ONjLiqPf6CK345B1OMDjhw5sZcu-RnNXS7mDCOdvUiLCTQyr5LPDIG2VPiq8ZtcanyTaibX-eB5z-JZZCPzharQDDsyM4A4OiGKhNt71mFJF-tG4WYHdF3LYtz41R1BhttPiWjBH2yh19th_JjPl7hyhodF8EW5KCj_LYYiahZg8NlPnrjyKk5VmxBDnGA0R1OQkFcBAok6biq1jtmAkcU6XiwdVL0FZtoOsvgFk651C3FZMDRtRgGpDgAWTOnRMBr2P4p_YJfEsliKGRCmAB0LbByNIu3Meso_-EUJS-0mSX_KDqmB7O-C0f2xdihC4XuLTguUZNxG3RNK096gA2QFJMojPCzqZ7pYspn56iK1VaVXJ6ZHVKLYYrnLBBke7qzMaiw";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mypage_main);
        ButterKnife.bind(this);
        setLayoutSize();
       // Glide.with(this).load(R.drawable.toolbar).into(toolbarImage);
        Glide.with(this).load(R.drawable.topbar_chgasu).into(plusSub);
        Glide.with(this).load(R.drawable.profile_change).into(editProfile);
        context = this;
        totalVote = 2000;
        init();


       // Glide.with(this).load(R.drawable.meta).into(backImage);

        networkService = ApplicationController.getInstance().getNetworkService();

        requestManager_singer = Glide.with(this);
        requestManager_rank = Glide.with(this);

        //recyclerView.get
        userAllSingerDatas = new ArrayList<UserAllSingerData>();
        userDataSumms = new ArrayList<UserDataSumm>();
        myPageItemDatas = new ArrayList<MyPageItemData>();

        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayout.VERTICAL);
        subSingerrecyclerView.setHasFixedSize(true);
        subSingerrecyclerView.setLayoutManager(linearLayoutManager);
        mypageTotalVote.setText(String.valueOf(totalVote));


        //TODO : 맨 처음에 정보를 받아 온다면 아래 부분 바꾸어야 함 작성 당시 필요해서 적어놓긴 했음
        //TODO : 이 부분은 해당 토큰에 맞는 가수 정보 불러오는 거 근데 작동하는 지 테스트 못함
        //TODO : 그도 그럴 것이 닉넴 체크가 바뀐 것 같은데 어케 바뀐 건지 모르겠음.

//        userAllSingerDatas = ApplicationController.getInstance().getUserAllSingerDatas();
//        for(int i = 0; i<userAllSingerDatas.size(); i++)
//        {
//            if(i==0 && userAllSingerDatas.get(0)!=null)
//            {
//                Log.v("MyPage", "메인 들어옴");
//                myPageHeadItemData = new MyPageHeadItemData(userAllSingerDatas.get(i).getSinger_img(),
//                        myRank(), userAllSingerDatas.get(i).getSinger_name(),
//                        userAllSingerDatas.get(i).getChoice_count());
//                //ApplicationController.getInstance().setMost(userAllSingerDatas.get(i).getSinger_id());
//                //SharedPrefrernceController.setMost(MyPageActivity.this, userAllSingerDatas.get(i).getSinger_id());
//            }
//            else
//            {
//                myPageItemDatas.add(new MyPageItemData(userAllSingerDatas.get(i).getSinger_img(),
//                        myRank(), userAllSingerDatas.get(i).getSinger_name(),
//                        userAllSingerDatas.get(i).getChoice_count()));
//            }
//            userDataSumms.add(new UserDataSumm(userAllSingerDatas.get(i).getSinger_id(), userAllSingerDatas.get(i).getSinger_name(),
//                    userAllSingerDatas.get(i).getSinger_img()));
//        }
//        Log.v("MyPage", "이제 어댑터로");
//        ApplicationController.getInstance().setUserDataSumms(userDataSumms);
//        myPageAdapter = new MyPageAdapter(requestManager_singer, requestManager_rank, myPageItemDatas, myPageHeadItemData, context);
//        subSingerrecyclerView.setAdapter(myPageAdapter);

        firebaseToken = SharedPrefrernceController.getFirebaseToken(MyPageActivity.this);
        Log.v("MyPage", firebaseToken);
        final Call<UserAllSingerResponse> userAllSingerResponse = networkService.userAllSinger(firebaseToken);
        userAllSingerResponse.enqueue(new Callback<UserAllSingerResponse>() {
            @Override
            public void onResponse(Call<UserAllSingerResponse> call, Response<UserAllSingerResponse> response) {
                if(response.body().result){
                    userAllSingerDatas = response.body().data;
                    int count = userAllSingerDatas.size();
                    ApplicationController.getInstance().setTotalSingerCount(count);
                    ApplicationController.getInstance().setUserAllSingerDatas(userAllSingerDatas);
                    Log.v("MyPage", userAllSingerDatas.get(0).getSinger_name());
                    Log.v("MyPage", "전체 = " + String.valueOf(userAllSingerDatas.size()));
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    for(int i = 0; i<userAllSingerDatas.size(); i++)
                    {
                        if(i==0 && userAllSingerDatas.get(0)!=null)
                        {//myAllSingerArray.indexOf(myAllSingerArray.get(0))
                                Log.v("MyPage", "메인 들어옴");
                                myPageHeadItemData = new MyPageHeadItemData(userAllSingerDatas.get(i).getSinger_img(),
                                        myRank(), userAllSingerDatas.get(i).getSinger_name(),
                                        userAllSingerDatas.get(i).getChoice_count());
                                ApplicationController.getInstance().setMost(userAllSingerDatas.get(i).getSinger_id());
                                SharedPrefrernceController.setMost(MyPageActivity.this, userAllSingerDatas.get(i).getSinger_id());
                        }
                        else
                        {
                                myPageItemDatas.add(new MyPageItemData(userAllSingerDatas.get(i).getSinger_img(),
                                        myRank(), userAllSingerDatas.get(i).getSinger_name(),
                                        userAllSingerDatas.get(i).getChoice_count()));
                        }
                        userDataSumms.add(new UserDataSumm(userAllSingerDatas.get(i).getSinger_id(), userAllSingerDatas.get(i).getSinger_name(),
                                userAllSingerDatas.get(i).getSinger_img()));
                    }
                    Log.v("MyPage", "이제 어댑터로");
                    ApplicationController.getInstance().setUserDataSumms(userDataSumms);
                    myPageAdapter = new MyPageAdapter(requestManager_singer, requestManager_rank, myPageItemDatas, myPageHeadItemData, context);
                    subSingerrecyclerView.setAdapter(myPageAdapter);
                } else{
                    Toast.makeText(MyPageActivity.this, "정보 불러오는 데에 실패하였습니다", Toast.LENGTH_LONG);
                }
            }

            @Override
            public void onFailure(Call<UserAllSingerResponse> call, Throwable t) {

            }
        });
    }

    @OnClick(R.id.mypage_main_backImage)
    public void back(){

        //TODO : 홈으로
        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
        startActivity(intent);
        finish();
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

    @OnClick(R.id.MyPage_profileImage)
    public void reset(){
        finish();
        startActivity(getIntent());
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }

    public void init(){
        Uri data;
        data = Uri.parse(SharedPrefrernceController.getUserImage(this));
        Glide.with(profileImage.getContext())
                .load(data)
                .bitmapTransform(new CropCircleTransformation(getApplicationContext()))
                .into(profileImage);

        String nickname;
        nickname = SharedPrefrernceController.getUserNickname(this);
        mypageNickname.setText(nickname);
    }

    @OnClick(R.id.Mypage_editProfile)
    public void toEditProfile(){
        Intent intent = new Intent(getApplicationContext(), EditProfileActivity.class);
        startActivity(intent);
    }

    public int myRank(){
        if(totalVote<10){
            return R.drawable.badge_muggle;
        } else if(totalVote>=10 && totalVote<100){
            return R.drawable.badge_newbie;
        } else if(totalVote>=100 && totalVote<500){
            return R.drawable.badge_ilco;

        } else if(totalVote>=500 && totalVote <1000){
            return R.drawable.badge_duckwho;

        } else{
            return R.drawable.badge_sungduck;
        }
    }

}
