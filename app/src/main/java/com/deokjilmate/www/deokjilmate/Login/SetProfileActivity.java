package com.deokjilmate.www.deokjilmate.Login;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.deokjilmate.www.deokjilmate.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SetProfileActivity extends AppCompatActivity {

    @BindView(R.id.SetProfile_topImage)
    ImageView toobarImage;

    @BindView(R.id.SetProfile_profileImage)
    ImageView profileImage;

    @BindView(R.id.SetProfile_select)
    ImageButton profileSelect;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_set_profile);
        ButterKnife.bind(this);

        Glide.with(this).load(R.drawable.output).into(toobarImage);

        Glide.with(this).load(R.drawable.profile_default).into(profileImage);

        Glide.with(this).load(R.drawable.profilesetting_photo).into(profileSelect);
    }
    @OnClick(R.id.SetProfile_select)
    public void ProfileSet()
    {

    }
}
