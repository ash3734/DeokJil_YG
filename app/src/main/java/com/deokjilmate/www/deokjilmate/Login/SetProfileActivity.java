package com.deokjilmate.www.deokjilmate.Login;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.deokjilmate.www.deokjilmate.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SetProfileActivity extends AppCompatActivity {

    @BindView(R.id.SetProfile_topImage)
    ImageView toobarImage;

    @BindView(R.id.SetProfile_profileImg)
    ImageView profileImage;

    @BindView(R.id.SetProfile_select)
    ImageView profileSelect;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_profile);
        ButterKnife.bind(this);

        Glide.with(this).load(R.drawable.output).into(toobarImage);

        Glide.with(this).load(R.drawable.output).into(profileImage);

        Glide.with(this).load(R.drawable.output).into(profileSelect);

    }
}
