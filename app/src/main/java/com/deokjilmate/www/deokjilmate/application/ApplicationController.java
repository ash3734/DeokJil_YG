package com.deokjilmate.www.deokjilmate.application;

import android.app.Application;

import com.deokjilmate.www.deokjilmate.MyPage.MyPageAllSingerNumbers;
import com.deokjilmate.www.deokjilmate.network.NetworkService;

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
    //// TODO: 2017-02-06 base Url 수정 
    private String baseUrl = "https://defflee.com/duckmate/";
    private int totalSingerCount = 0;



    private MyPageAllSingerNumbers myPageAllSingerNumberses;
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

}
