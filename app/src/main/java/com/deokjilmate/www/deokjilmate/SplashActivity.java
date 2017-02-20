package com.deokjilmate.www.deokjilmate;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.deokjilmate.www.deokjilmate.Login.SetProfileActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);


        Handler handler = new Handler();
        handler.postDelayed(new Runnable(){
            @Override
            public void run()
            {
                startActivity(new Intent(getApplicationContext(), SetProfileActivity.class));
                finish();
            }
        }, 5000);
    }
    //누구나 다 아는 그런 스플래쉬
}
