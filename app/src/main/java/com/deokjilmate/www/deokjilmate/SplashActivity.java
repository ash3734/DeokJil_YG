package com.deokjilmate.www.deokjilmate;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.deokjilmate.www.deokjilmate.Login.MainLoginActivity;
import com.deokjilmate.www.deokjilmate.application.ApplicationController;
import com.deokjilmate.www.deokjilmate.home.HomeActivity;
import com.deokjilmate.www.deokjilmate.home.MainResult;
import com.deokjilmate.www.deokjilmate.network.NetworkService;
import com.tsengvn.typekit.TypekitContextWrapper;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;

import java.util.ArrayList;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.fabric.sdk.android.Fabric;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashActivity extends AppCompatActivity {
    private static final String TWITTER_KEY = "ZPJ66hrJ8nJpMKRS297O9BgGQ ";
    private static final String TWITTER_SECRET = "Kb41OH7xsJfEZ3Rzf9rCiBLE6mz47GMtiJBdCcbuYmtIDqCLHu";
    private NetworkService networkService;

    private ArrayList<UserAllSingerData> userAllSingerDatas;
    private String firebaseToken;
    private ArrayList<UserDataSumm> userDataSumms;


    @BindView(R.id.splash_gasa)
    TextView textViewGasa;
//    @BindView(R.id.Splash_background)
//    ImageView background;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);

        }

                super.onCreate(savedInstanceState);
                setContentView(R.layout.splash);
                ButterKnife.bind(this);

                TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
                Fabric.with(this, new Twitter(authConfig));
                Random random = new Random();


                String[] gasa = getResources().getStringArray(R.array.lyric);

//                 Handler handler = new Handler();
//                 handler.postDelayed(new Runnable(){
//                     @Override
//                     public void run()
//                     {

//                         //AutoLogin();

//                 startActivity(new Intent(getApplicationContext(), InquiryActivity.class));
//                 finish();


                textViewGasa.setText(gasa[random.nextInt(gasa.length)]);
                ApplicationController.getInstance().setMost(SharedPrefrernceController.getMost(SplashActivity.this));
                AutoLogin();
//                Handler handler = new Handler();
//                handler.postDelayed(new Runnable(){
//                    @Override
//                    public void run()
//                    {
//
//                        AutoLogin();
//
////                startActivity(new Intent(getApplicationContext(), MainLoginActivity.class));
////                finish();
//
//            }
//        }, 5000);
    }
    public void AutoLogin(){
        if(SharedPrefrernceController.getFirebaseToken(SplashActivity.this).equals("")){

            Handler handler = new Handler();
                handler.postDelayed(new Runnable(){
                    @Override
                    public void run()
                    {

                        startActivity(new Intent(getApplicationContext(), MainLoginActivity.class));
                        finish();

//                startActivity(new Intent(getApplicationContext(), MainLoginActivity.class));
//                finish();

            }
        }, 3000);


        }
        else{
//            startActivity(new Intent(getApplicationContext(), FindPwdActivity.class));
//            finish();

            ApplicationController.getInstance().setSinger_id(SharedPrefrernceController.getSelected(SplashActivity.this));
            setHomeData(SharedPrefrernceController.getFirebaseToken(SplashActivity.this),
                    SharedPrefrernceController.getMost(SplashActivity.this));

            //startActivity(new Intent(getApplicationContext(), MyPageActivity.class));

        }
    }
    //누구나 다 아는 그런 스플래쉬

    public void setHomeData(final String firebaseToken, int singer_id){
        networkService = ApplicationController.getInstance().getNetworkService();
        Log.v("토큰", firebaseToken);
        Log.v("아이디", String.valueOf(singer_id));
        Call<MainResult> requestMainResult = networkService.requestMain(firebaseToken,singer_id);

        requestMainResult.enqueue(new Callback<MainResult>() {
            @Override
            public void onResponse(Call<MainResult> call, final Response<MainResult> response) {
                if(response.body().result){
                    ApplicationController.getInstance().setMainResult(response.body());
                    SharedPrefrernceController.setUserNickname(SplashActivity.this, response.body().nevi_data.member_name);
                    getMySingerDatas();

                }else{
                    Log.v("여기로 뜸", "여기로 뜸");
                    Toast toast = Toast.makeText(getApplicationContext(), response.message(), Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    finish();
                }
            }

            @Override
            public void onFailure(Call<MainResult> call, Throwable t) {
                Toast toast = Toast.makeText(getApplicationContext(), "네트워크 상태를 확인하세요", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
        });
    }

    //여기까지가 8/24에 작업한 것

    public void getMySingerDatas(){

        userAllSingerDatas = new ArrayList<UserAllSingerData>();
        userDataSumms = new ArrayList<UserDataSumm>();

        final Call<UserAllSingerResponse> userAllSingerResponse = networkService.userAllSinger(SharedPrefrernceController.getFirebaseToken(SplashActivity.this));
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

                    for(int i = 0; i<userAllSingerDatas.size(); i++)
                    {
                        if(i==0 && userAllSingerDatas.get(0)!=null)
                        {//myAllSingerArray.indexOf(myAllSingerArray.get(0))
                            Log.v("MyPage", "메인 들어옴");
                            ApplicationController.getInstance().setMost(userAllSingerDatas.get(i).getSinger_id());
                            SharedPrefrernceController.setMost(SplashActivity.this, userAllSingerDatas.get(i).getSinger_id());
                        }
                        userDataSumms.add(new UserDataSumm(userAllSingerDatas.get(i).getSinger_id(), userAllSingerDatas.get(i).getSinger_name(),
                                userAllSingerDatas.get(i).getSinger_img()));
                    }
                    Log.v("MyPage", "이제 어댑터로");
                    ApplicationController.getInstance().setUserDataSumms(userDataSumms);

                    if(userDataSumms.size() == userAllSingerDatas.size()){
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable(){
                            @Override
                            public void run()
                            {
                                SharedPrefrernceController.setLoginType(SplashActivity.this, "l");
                                ApplicationController.getInstance().setLoginState("l");
                                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                                finish();
                            }
                        }, 3000);

                    }
                } else{
                    Toast.makeText(SplashActivity.this, "정보 불러오는 데에 실패하였습니다", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<UserAllSingerResponse> call, Throwable t) {

            }
        });
    }
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }
}
