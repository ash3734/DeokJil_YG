package com.deokjilmate.www.deokjilmate.Login;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.deokjilmate.www.deokjilmate.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SignActivity extends AppCompatActivity {

    @BindView(R.id.Sign_topImage)
    ImageView toolbarImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_sign);
        ButterKnife.bind(this);

        Glide.with(this).load(R.drawable.toolbar).into(toolbarImage);
    }
}
