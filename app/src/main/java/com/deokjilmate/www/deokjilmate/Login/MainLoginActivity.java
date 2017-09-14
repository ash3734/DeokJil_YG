package com.deokjilmate.www.deokjilmate.Login;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.deokjilmate.www.deokjilmate.R;
import com.tsengvn.typekit.TypekitContextWrapper;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainLoginActivity extends AppCompatActivity {

    @BindView(R.id.MainLogin_sign)
    RelativeLayout btnSign;

    @BindView(R.id.MainLogin_login)
    RelativeLayout btnLogin;

    @BindView(R.id.MainLogin_background)
    ImageView background;

    @BindView(R.id.MainLogin_loginBtn)
    ImageView mainLoginBtn;

    @BindView(R.id.MainLogin_signBtn)
    ImageView mainLoginSign;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            //Drawable background = this.getResources().getDrawable(R.drawable.gradation);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(this.getResources().getColor(R.color.statusbar));
            //window.setNavigationBarColor(this.getResources().getColor(R.color.tw__transparent));
            //window.setBackgroundDrawable(background);

        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_main_login);
        ButterKnife.bind(this);
        //Glide.with(this).load(R.drawable.splash_full).into(background);
        Glide.with(this).load(R.drawable.splash_logo).into(background);
        Glide.with(this).load(R.drawable.splash_login).into(mainLoginBtn);
        Glide.with(this).load(R.drawable.splash_signin).into(mainLoginSign);
        try {
            PackageInfo info = getPackageManager().getPackageInfo("com.deokjilmate.www.deokjilmate", PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        //logoImage = (ImageView)findViewById(R.id.MainLogin_logo);
        //Glide.with(this).load(R.drawable.output).into(logoImage);
    }

    //첫 화면에서 로그인 버튼 눌렀을 때
    @OnClick(R.id.MainLogin_login)
    public void LoginEvent()
    {
        startActivity(new Intent(getApplicationContext(), LoginSelectActivity.class));
    }

    //첫 화면에서 회원가입 버튼 눌렀을 때
    @OnClick(R.id.MainLogin_sign)
    public void SignEvent()
    {
        startActivity(new Intent(getApplicationContext(), SignActivity.class));
    }
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }
}
