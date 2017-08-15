package com.deokjilmate.www.deokjilmate;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.deokjilmate.www.deokjilmate.Login.MainLoginActivity;
import com.deokjilmate.www.deokjilmate.MyPage.MyPageActivity;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;

import io.fabric.sdk.android.Fabric;

public class SplashActivity extends AppCompatActivity {
    private static final String TWITTER_KEY = "ZPJ66hrJ8nJpMKRS297O9BgGQ ";
    private static final String TWITTER_SECRET = "Kb41OH7xsJfEZ3Rzf9rCiBLE6mz47GMtiJBdCcbuYmtIDqCLHu";

//    @BindView(R.id.Splash_background)
//    ImageView background;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.splash);
//                ButterKnife.bind(this);
//                Glide.with(this).load(R.drawable.splash).into(background);

                TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
                Fabric.with(this, new Twitter(authConfig));

                Handler handler = new Handler();
                handler.postDelayed(new Runnable(){
                    @Override
                    public void run()
                    {
                        AutoLogin();
            }
        }, 5000);
    }
    public void AutoLogin(){
        if(SharedPrefrernceController.getFirebaseToken(SplashActivity.this).equals("")){
            startActivity(new Intent(getApplicationContext(), MainLoginActivity.class));
            finish();
        }
        else{
            startActivity(new Intent(getApplicationContext(), MyPageActivity.class));
            finish();
        }
    }
    //누구나 다 아는 그런 스플래쉬
}
