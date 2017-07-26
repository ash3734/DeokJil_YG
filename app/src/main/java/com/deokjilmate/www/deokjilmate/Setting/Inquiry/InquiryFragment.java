package com.deokjilmate.www.deokjilmate.Setting.Inquiry;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.deokjilmate.www.deokjilmate.R;
import com.deokjilmate.www.deokjilmate.application.ApplicationController;
import com.deokjilmate.www.deokjilmate.network.NetworkService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by 김민경 on 2017-07-18.
 */

public class InquiryFragment extends Fragment {

    private static final String TAG = "InquiryActivity";
    Button send_inquiry;
    Button show_token;

    NetworkService service;

    EditText title;
    EditText main;
    EditText mail;

    int member_id;
    String questions_title;
    String questions_main;
    String questions_mail;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.inquiry_tab_fragment, container, false);

        service = ApplicationController.getInstance().getNetworkService();

       // title = (EditText)view.findViewById(R.id.questions_title);
        main = (EditText)view.findViewById(R.id.questions_main);
        mail = (EditText)view.findViewById(R.id.questions_mail);

        questions_title = "하이루";
        /*
        show_token = (Button)view.findViewById(show_token);
        show_token.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String token = FirebaseInstanceId.getInstance().getToken();
                Log.d(TAG,"Token: "+token);
                //Toast.makeText(InquiryActivity.this,token,Toast.LENGTH_SHORT).show();
            }
        });
*/
        send_inquiry = (Button)view.findViewById(R.id.send_inquiry);
        send_inquiry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("다이얼로그","onClick들어옴");
                final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());

                // AlertDialog 셋팅
                alertDialogBuilder
                        .setMessage("문의사항을 제출하시겠습니까?")
                        .setCancelable(false)
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {



                                member_id = 7;
                                questions_title = title.getText().toString();
                                questions_main = main.getText().toString();
                                questions_mail = mail.getText().toString();




                                ///////////////////////////////////////////////////////
                                Call<InquiryResult> inquiryRegister = service.inquiryRegister(new InquiryObject(member_id,questions_title,questions_main,questions_mail));
                                inquiryRegister.enqueue(new Callback<InquiryResult>() {
                                    @Override
                                    public void onResponse(Call<InquiryResult> call, Response<InquiryResult> response) {
                                        if(response.isSuccessful()) {
                                            if (response.body().result.equals("success")) {

                                                Log.d("밍구밍구","등록성공!");

                                                //Toast.makeText(getApplicationContext(), "성공", Toast.LENGTH_SHORT).show();
                                                //finish();
                                                AlertDialog.Builder alertDialogBuilder2 = new AlertDialog.Builder(getActivity());

                                                alertDialogBuilder2
                                                        .setMessage("답변은 3~5일 내에 메일로 전송됩니다.\n" +
                                                                "문의해주셔서 감사합니다.")
                                                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
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
                                        }
                                        else{
                                            Log.d("밍구밍구","등록실패!");
                                            Toast.makeText(getApplicationContext(),"등록실패",Toast.LENGTH_SHORT).show();
                                        }

                                    }
                                    @Override
                                    public void onFailure(Call<InquiryResult> call, Throwable t) {
                                        Toast.makeText(getApplicationContext(),"실패",Toast.LENGTH_SHORT).show();
                                    }
                                });


//                   AlertDialog.Builder alertDialogBuilder2 = new AlertDialog.Builder(InquiryActivity.this);
//
//                                alertDialogBuilder2
//                                        .setMessage("답변은 3~5일 내에 메일로 전송됩니다.\n" +
//                                                "문의해주셔서 감사합니다.")
//                                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
//                                            public void onClick(
//                                                    DialogInterface dialog, int id) {
//                                                dialog.cancel();
//                                            }
//                                        });
//
//                                // 다이얼로그 생성
//                                AlertDialog alertDialog2 = alertDialogBuilder2.create();
//
//                                // 다이얼로그 보여주기
//                                alertDialog2.show();


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
        return inflater.inflate(R.layout.inquiry_tab_fragment, container, false);

    }
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.inquiry_tab_fragment);
//
//
//
//
//    }





}
