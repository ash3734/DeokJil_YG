package com.deokjilmate.www.deokjilmate.network;

import com.deokjilmate.www.deokjilmate.AllSinger.AllSingerRanking;
import com.deokjilmate.www.deokjilmate.MyPage.MyPageCheckMainSub;
import com.deokjilmate.www.deokjilmate.MyPage.MyPageSingerList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by ash on 2017-02-06.
 */

public interface NetworkService {









    //////이 밑은 YG 담당//////////
    //가수 전체 목록 불러오기
    @GET("singer/singer_rank")
    Call<AllSingerRanking> setSingerRanking();

    //내 가수들 번호 가져오기
    @GET("singer/singerbase/{member_id}")
    Call<MyPageCheckMainSub> myPageCheckMainSub(@Path("member_id") int member_id);

    //내 가수들
    @GET("singer/singercheck/{member_id}")
    Call<MyPageSingerList> myPageSingerList(@Path("member_id") int member_id);


    

}
