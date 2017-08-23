package com.deokjilmate.www.deokjilmate.application;

import android.app.Application;
import android.content.Context;

import com.deokjilmate.www.deokjilmate.MyPage.MyPageAllSingerNumbers;
import com.deokjilmate.www.deokjilmate.UserAllSingerData;
import com.deokjilmate.www.deokjilmate.UserDataSumm;
import com.deokjilmate.www.deokjilmate.home.MainResult;
import com.deokjilmate.www.deokjilmate.network.NetworkService;
import com.tsengvn.typekit.Typekit;

import java.util.ArrayList;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ash on 2017-02-06.
 * 싱글톤 패턴을 위한 어플리케이션을 상속받은 클래스
 * 네트워크, 폰트 적용
 */

public class ApplicationController extends Application{

    private static ApplicationController instance;

    private NetworkService networkService;
    private Context context;
    public String singerName = "빅스";
    public int singer_id=3;

    public MainResult mainResult;

    public void setContext(Context context) {
        this.context = context;
    }
    //// TODO: 2017-02-06 base Url 수정
    private String baseUrl = "https://35.199.171.150/duckmate/android/";
    private int totalSingerCount = 0;
    private ArrayList<Integer> deleteList;

    private MyPageAllSingerNumbers myPageAllSingerNumberses;
    private ArrayList<UserAllSingerData> userAllSingerDatas;



    private ArrayList<UserDataSumm> userDataSumms;
    private int most;


    private String firebaseToken;

    public NetworkService getNetworkService() {
        return networkService;
    }
    //public
    public static ApplicationController getInstance() {
        return instance;
    }

    public void buildNetwork() {
        Retrofit.Builder builder = new Retrofit.Builder();
        Retrofit retrofit = builder
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        networkService = retrofit.create(NetworkService.class);

    }
    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;

        Typekit.getInstance().addNormal(Typekit.createFromAsset(this,"NotoSansCJKkr-Regular.otf"))
                .addBold(Typekit.createFromAsset(this, "NotoSansCJKkr-Bold.otf"));

        buildNetwork();
    }
    public int getTotalSingerCount() {
        return totalSingerCount;
    }

    public void setTotalSingerCount(int totalSingerCount) {
        this.totalSingerCount = totalSingerCount;
    }

    public MyPageAllSingerNumbers getMyPageAllSingerNumberses() {
        return myPageAllSingerNumberses;
    }

    public void setMyPageAllSingerNumberses(MyPageAllSingerNumbers myPageAllSingerNumberses) {
        this.myPageAllSingerNumberses = myPageAllSingerNumberses;
    }

    public ArrayList<Integer> getDeleteList() {
        return deleteList;
    }

    public void setDeleteList(ArrayList<Integer> deleteList) {
        this.deleteList = deleteList;
    }
    public int getMost() {
        return most;
    }

    public void setMost(int most) {
        this.most = most;
    }

    public ArrayList<UserAllSingerData> getUserAllSingerDatas() {
        return userAllSingerDatas;
    }

    public void setUserAllSingerDatas(ArrayList<UserAllSingerData> userAllSingerDatas) {
        this.userAllSingerDatas = userAllSingerDatas;
    }


    public String getFirebaseToken() {
        return firebaseToken;
    }

    public void setFirebaseToken(String firebaseToken) {
        this.firebaseToken = firebaseToken;
    }
    public ArrayList<UserDataSumm> getUserDataSumms() {
        return userDataSumms;
    }

    public void setUserDataSumms(ArrayList<UserDataSumm> userDataSumms) {
        this.userDataSumms = userDataSumms;
    }
}
