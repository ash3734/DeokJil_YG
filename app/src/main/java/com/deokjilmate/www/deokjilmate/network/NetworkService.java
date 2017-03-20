package com.deokjilmate.www.deokjilmate.network;

import com.deokjilmate.www.deokjilmate.Login.SetSingerRanking;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by ash on 2017-02-06.
 */

public interface NetworkService {









    //////이 밑은 YG 담당//////////
    //가수 전체 목록 불러오기
    @GET("/singer/singer_rank")
    Call<SetSingerRanking> setSingerRanking();

    

}
