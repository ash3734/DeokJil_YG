package com.deokjilmate.www.deokjilmate.Setting.Inquiry;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.deokjilmate.www.deokjilmate.R;
import com.deokjilmate.www.deokjilmate.network.NetworkService;

public class InquiryActivity extends AppCompatActivity {


    Button send_inquiry;
    NetworkService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inquiry_activity);


        send_inquiry = (Button)findViewById(R.id.send_inquiry);
        send_inquiry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(InquiryActivity.this);

                // AlertDialog 셋팅
                alertDialogBuilder
                        .setMessage("문의사항을 제출하시겠습니까?")
                        .setCancelable(false)
                        .setNeutralButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                /*

                                ///////////////////////////////////////////////////////
                                retrofit2.Call<InquiryResult> inquiryResult = service.inquiryResult(new InquiryObject(member_id,questions_title,questions_main,questions_mail));
                                requestRegister.enqueue(new Callback<RegisterResult>() {
                                    @Override
                                    public void onResponse(retrofit2.Call<RegisterResult> call, Response<RegisterResult> response) {
                                        if(response.isSuccessful()){
                                            if(response.body().result.equals("create")){
                                                Toast.makeText(getApplicationContext(),"성공",Toast.LENGTH_SHORT).show();
                                                finish();
                                            }
                                        } else{
                                                Toast.makeText(getApplicationContext(),"등록실패",Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    @Override
                                    public void onFailure(retrofit2.Call<RegisterResult> call, Throwable t) {
                                        Toast.makeText(getApplicationContext(),"실패",Toast.LENGTH_SHORT).show();
                                    }
                                });

                                */










                                AlertDialog.Builder alertDialogBuilder2 = new AlertDialog.Builder(InquiryActivity.this);

                                alertDialogBuilder2
                                        .setMessage("답변은 3~5일 내에 메일로 전송됩니다.\n" +
                                                "문의해주셔서 감사합니다.")
                                        .setNeutralButton("확인", new DialogInterface.OnClickListener() {
                                            public void onClick(
                                                    DialogInterface dialog, int id) {
                                                dialog.cancel();
                                            }
                                        });

                                // 다이얼로그 생성
                                AlertDialog alertDialog2 = alertDialogBuilder2.create();

                                // 다이얼로그 보여주기
                                alertDialog2.show();


                            }
                        })
                        .setNegativeButton("취소",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(
                                            DialogInterface dialog, int id) {
                                        // 다이얼로그를 취소한다
                                        dialog.cancel();
                                    }
                                });


                // 다이얼로그 생성
                AlertDialog alertDialog = alertDialogBuilder.create();

                // 다이얼로그 보여주기
                alertDialog.show();
            }
        });


    }
}
