package com.deokjilmate.www.deokjilmate.network;

import com.deokjilmate.www.deokjilmate.AllSinger.AllSingerRanking;
import com.deokjilmate.www.deokjilmate.Login.FindPwdPost;
import com.deokjilmate.www.deokjilmate.Login.FindPwdResponse;
import com.deokjilmate.www.deokjilmate.Login.LoginPost;
import com.deokjilmate.www.deokjilmate.Login.LoginResponseResult;
import com.deokjilmate.www.deokjilmate.Login.LoginSnsPost;
import com.deokjilmate.www.deokjilmate.Login.LoginUidCheck;
import com.deokjilmate.www.deokjilmate.Login.RegisterResult;
import com.deokjilmate.www.deokjilmate.Login.RegisterSnsResult;
import com.deokjilmate.www.deokjilmate.Login.SetProfileResult;
import com.deokjilmate.www.deokjilmate.Login.SignPost;
import com.deokjilmate.www.deokjilmate.Login.SignSnsPost;
import com.deokjilmate.www.deokjilmate.Login.SnsResult;
import com.deokjilmate.www.deokjilmate.MyPage.AddSinger.SingerAddPost;
import com.deokjilmate.www.deokjilmate.MyPage.AddSinger.SingerAddResponse;
import com.deokjilmate.www.deokjilmate.MyPage.EditSinger.EditSingerDelete;
import com.deokjilmate.www.deokjilmate.MyPage.EditSinger.EditSingerDeleteResponse;
import com.deokjilmate.www.deokjilmate.MyPage.MyPageCheckMainSub;
import com.deokjilmate.www.deokjilmate.MyPage.MyPageSingerList;
import com.deokjilmate.www.deokjilmate.Setting.Break.BreakPostResult;
import com.deokjilmate.www.deokjilmate.Setting.Inquiry.InquiryObject;
import com.deokjilmate.www.deokjilmate.Setting.Inquiry.InquiryResult;
import com.deokjilmate.www.deokjilmate.Setting.Notice.BoardNotice;
import com.deokjilmate.www.deokjilmate.UserAllSingerResponse;
import com.deokjilmate.www.deokjilmate.alarm.FirstAlarmResult;
import com.deokjilmate.www.deokjilmate.alarm.NoticePostData;
import com.deokjilmate.www.deokjilmate.alarm.NoticePostResult;
import com.deokjilmate.www.deokjilmate.alarm.NoticeResult;
import com.deokjilmate.www.deokjilmate.home.MainResult;
import com.deokjilmate.www.deokjilmate.home.vote.VotePost;
import com.deokjilmate.www.deokjilmate.home.vote.VotePostResponse;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by ash on 2017-02-06.
 */

public interface NetworkService {

    //안성현
    @GET("firstpage/{firebaseToken}/{singer_id}")
    Call<MainResult> requestMain(@Path("firebaseToken") String firebaseToken, @Path("singer_id")int singer_id);

    /***********************밍구************************/
    //공지사항 불러오기
    @GET("notice")
    Call<BoardNotice> getNotice();

    //문의하기
    @POST("question")
    Call<InquiryResult> inquiryRegister(@Body InquiryObject getObject);

    //첫 알람 가수 정보들 가져오기
    @GET("alarm/input")
    Call<FirstAlarmResult> getfirstAlarm(@Query("singer_id") int singer_id);

    // 알람 가져오기
    @GET("alarm")
    Call<NoticeResult> getAlarm(@Query("firebaseToken") String firebaseToken);

    // 알람 보내기
    @POST("alarm")
    Call<NoticePostResult> postAlarm(@Body NoticePostData noticePostData);

    // 회원 탈퇴
    @HTTP(method = "DELETE", path = "memberDelete", hasBody = false)
    Call<BreakPostResult> postBreak(@Query("firebaseToken") String firebaseToken);





    //////이 밑은 YG 담당//////////
    //로그인(sns)
    @POST("login")
    Call<LoginResponseResult> login(@Body LoginPost loginPost);

    @POST("login")
    Call<LoginResponseResult> loginSns(@Body LoginSnsPost loginSnsPost);


    //회원가입(sns)
    @Multipart
    @POST("sns")
    Call<SnsResult> snsResult(
            @Part MultipartBody.Part profile_img,
            @Part("email") RequestBody email,
            @Part("snstoken") RequestBody snstoken
    );



    //회원가입 in App
//    @Multipart
//    @POST("register")
//    Call<RegisterResult> registerResult(
//            @Part("member_email") RequestBody email,
//            @Part("member_password") RequestBody passwd,
//            @Part("member_name") RequestBody member_name
//    );


   // @Multipart
   // @Multipart
   @POST("register")
   Call<RegisterResult> registerResult(
           @Body SignPost signPost
   );

    @POST("register")
    Call<RegisterSnsResult> registerResultSns(
            @Body SignSnsPost signSnsPost
    );

    //닉넴 중복 체크
//    @GET("register/{member_name}")
//    Call<SetProfileResult> setProfileResult(@Path("member_name") String member_name);

    @GET("register")
    Call<SetProfileResult> setProfileResult(@Query("member_name") String member_name);

    @GET("register/id")
    Call<LoginUidCheck> loginResult(@Query("uid") String uid);

    //비번 찾기
//    @POST("findpassword")
//    Call<FindPwdResponse> findPwd(@Body String member_email);

    @POST("findpassword")
    Call<FindPwdResponse> findPwd(@Body FindPwdPost findPwdPost);

    //가수 전체 목록 불러오기
    @GET("singer/rank")
    Call<AllSingerRanking> setSingerRanking();

    //내 가수들 번호 가져오기(메인, 서브 판별
    @GET("singer/singerbase/{member_id}")
    Call<MyPageCheckMainSub> myPageCheckMainSub(@Path("member_id") int member_id);

    //내 가수들(목록 불러오기)
    @GET("singer/singercheck/{member_id}")
    Call<MyPageSingerList> myPageSingerList(@Path("member_id") int member_id);

    //가수 추가
    @POST("singer")
    Call<SingerAddResponse> addSinger(@Body SingerAddPost singerAddPost);

    //가수 삭제
    @HTTP(method = "DELETE", path = "singer", hasBody = true)
    Call<EditSingerDeleteResponse> deleteSinger(@Body EditSingerDelete editSingerDelete);

    //singerBase + singerCheck
    @GET("singer")
    Call<UserAllSingerResponse> userAllSinger(@Query("firebaseToken") String firebaseToken);

//    @GET("/my/API/call")
//    Response getMyThing(
//            @Query("param1") String param1,
//            @Query("param2") String param2);

    @POST("vote")
    Call<VotePostResponse> userVote(@Body VotePost votePost);

}
