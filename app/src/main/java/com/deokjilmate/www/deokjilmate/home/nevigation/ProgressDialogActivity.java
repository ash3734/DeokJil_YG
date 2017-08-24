package com.deokjilmate.www.deokjilmate.home.nevigation;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.Toast;

import com.deokjilmate.www.deokjilmate.R;
import com.deokjilmate.www.deokjilmate.SharedPrefrernceController;
import com.deokjilmate.www.deokjilmate.application.ApplicationController;
import com.deokjilmate.www.deokjilmate.home.HomeActivity;
import com.deokjilmate.www.deokjilmate.home.MainResult;
import com.deokjilmate.www.deokjilmate.network.NetworkService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProgressDialogActivity extends Activity {
    private NetworkService service;
    //// TODO: 2017-07-31 로그인시 member_id 받기
    private String firebaseToken;

    private int singer_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress_dialog);
        CheckTypesTask task = new CheckTypesTask();
        task.execute();
    }
    private class CheckTypesTask extends AsyncTask<Void, Void, Void> {

        ProgressDialog asyncDialog = new ProgressDialog(
                ProgressDialogActivity.this);

        @Override
        protected void onPreExecute() {
            asyncDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            asyncDialog.setMessage("로딩중입니다..");

            // show dialog
            asyncDialog.show();
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            try {
                for (int i = 0; i < 5; i++) {
                    //asyncDialog.setProgress(i * 30);
                    Thread.sleep(500);
                }
                //singer_id = ApplicationController.getInstance().singer_id;
                //singer_id=1;
                firebaseToken = SharedPrefrernceController.getFirebaseToken(ProgressDialogActivity.this);
                singer_id = ApplicationController.getInstance().getSinger_id();
                SharedPrefrernceController.setSelected(ProgressDialogActivity.this, singer_id);
                service = ApplicationController.getInstance().getNetworkService();
                Call<MainResult> requestMainResult = service.requestMain(firebaseToken,singer_id);

                requestMainResult.enqueue(new Callback<MainResult>() {
                    @Override
                    public void onResponse(Call<MainResult> call, Response<MainResult> response) {
                        if(response.isSuccessful()){
                            ApplicationController.getInstance().setMainResult(response.body());
                            startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                            finish();
                        }else{
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

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            asyncDialog.dismiss();
            super.onPostExecute(result);
        }
    }
}
