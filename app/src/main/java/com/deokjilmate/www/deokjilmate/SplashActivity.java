package com.deokjilmate.www.deokjilmate;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.widget.TextView;
import android.widget.Toast;

import com.deokjilmate.www.deokjilmate.Login.MainLoginActivity;
import com.deokjilmate.www.deokjilmate.application.ApplicationController;
import com.deokjilmate.www.deokjilmate.home.HomeActivity;
import com.deokjilmate.www.deokjilmate.home.MainResult;
import com.deokjilmate.www.deokjilmate.network.NetworkService;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;

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

    @BindView(R.id.splash_gasa)
    TextView textViewGasa;
//    @BindView(R.id.Splash_background)
//    ImageView background;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.splash);
                ButterKnife.bind(this);
//                Glide.with(this).load(R.drawable.splash).into(background);
//        try {
//            PackageInfo info = getPackageManager().getPackageInfo("com.deokjilmate.www.deokjilmate", PackageManager.GET_SIGNATURES);
//            for (Signature signature : info.signatures) {
//                MessageDigest md = MessageDigest.getInstance("SHA");
//                md.update(signature.toByteArray());
//                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
//            }
//        } catch (PackageManager.NameNotFoundException e) {
//            e.printStackTrace();
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        }


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
            startActivity(new Intent(getApplicationContext(), MainLoginActivity.class));
            finish();
        }
        else{
            ApplicationController.getInstance().setSinger_id(SharedPrefrernceController.getSelected(SplashActivity.this));
            setHomeData(SharedPrefrernceController.getFirebaseToken(SplashActivity.this),
                    SharedPrefrernceController.getSelected(SplashActivity.this));
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
            public void onResponse(Call<MainResult> call, Response<MainResult> response) {
                if(response.body().result){
                    ApplicationController.getInstance().setMainResult(response.body());
                    startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                    finish();

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
}
