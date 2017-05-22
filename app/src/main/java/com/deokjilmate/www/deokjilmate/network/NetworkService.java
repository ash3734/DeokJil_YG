package com.deokjilmate.www.deokjilmate.network;

import com.deokjilmate.www.deokjilmate.AllSinger.AllSingerRanking;
import com.deokjilmate.www.deokjilmate.Login.RegisterResult;
import com.deokjilmate.www.deokjilmate.Login.SetProfileResult;
import com.deokjilmate.www.deokjilmate.Login.SnsResult;
import com.deokjilmate.www.deokjilmate.Login.TempBody;
import com.deokjilmate.www.deokjilmate.MyPage.MyPageCheckMainSub;
import com.deokjilmate.www.deokjilmate.MyPage.MyPageSingerList;
import com.deokjilmate.www.deokjilmate.Setting.Inquiry.InquiryObject;
import com.deokjilmate.www.deokjilmate.Setting.Inquiry.InquiryResult;
import com.deokjilmate.www.deokjilmate.Setting.Notice.BoardNotice;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

/**
 * Created by ash on 2017-02-06.
 */

public interface NetworkService {

    /***********************밍구************************/
    //공지사항 불러오기
    @GET("/notice")
    Call<BoardNotice> getNotice();


    //////이 밑은 YG 담당//////////
    //회원가입(sns)
    @Multipart
    @POST("sns")
    Call<SnsResult> snsResult(
            @Part MultipartBody.Part profile_img,
            @Part("email") RequestBody email,
            @Part("snstoken") RequestBody snstoken
    );

    //문의하기
    @POST("/question/send")
    Call<InquiryResult> inquiryResult(@Body InquiryObject getObject);



    //회원가입 in App
//    @Multipart
//    @POST("register")
//    Call<RegisterResult> registerResult(
//            @Part("member_email") RequestBody email,
//            @Part("member_password") RequestBody passwd,
//            @Part("member_name") RequestBody member_name
//    );


   // @Multipart
    @POST("register")
    Call<RegisterResult> registerResult(
            @Body TempBody tempBody
            );

    //닉넴 중복 체크
    @GET("register/{member_name}")
    Call<SetProfileResult> setProfileResult(@Path("member_name") String member_name);




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
