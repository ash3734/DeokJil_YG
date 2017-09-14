package com.deokjilmate.www.deokjilmate.Login;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.deokjilmate.www.deokjilmate.R;
import com.deokjilmate.www.deokjilmate.application.ApplicationController;
import com.deokjilmate.www.deokjilmate.network.NetworkService;
import com.tsengvn.typekit.TypekitContextWrapper;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//여긴 비번 찾는 페이지
public class FindPwdActivity extends AppCompatActivity {


    @BindView(R.id.FindPwd_email)
    EditText findPwdbyEmail;

    private NetworkService networkService;
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
        setContentView(R.layout.login_find_pwd);
        ButterKnife.bind(this);
        networkService = ApplicationController.getInstance().getNetworkService();
      //  Glide.with(this).load(R.drawable.toolbar).into(toobarImage);
        //Glide.with(this).load(R.drawable.topbar_back).into(backButton);

    }

    @OnClick(R.id.FindPwd_backImage)
    public void ClickBack()
    {
        Intent intent = new Intent(getApplicationContext(), LoginSelectActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.FindPwd_check)
    public void clickCheck()
    {
        Call<FindPwdResponse> findPwd = networkService.findPwd(new FindPwdPost(findPwdbyEmail.getText().toString()));
        findPwd.enqueue(new Callback<FindPwdResponse>() {
            @Override
            public void onResponse(Call<FindPwdResponse> call, Response<FindPwdResponse> response) {
                Log.v("메일", findPwdbyEmail.getText().toString());
                if(response.isSuccessful()) {

                    Toast.makeText(getApplicationContext(), "메일로 전송하였습니다.", Toast.LENGTH_LONG).show();
                }
                else {

                    Toast.makeText(getApplicationContext(), "메일을 확인해주세요.", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<FindPwdResponse> call, Throwable t) {
            }
        });
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }
}
