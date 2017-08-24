package com.deokjilmate.www.deokjilmate.Setting.Break;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.deokjilmate.www.deokjilmate.R;
import com.deokjilmate.www.deokjilmate.SharedPrefrernceController;

public class BreakActivity extends AppCompatActivity {

    Button button_break;
    String firebaseToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.break_activity);

        button_break = (Button)findViewById(R.id.button_break);
        final String USER = "user";
        firebaseToken = SharedPrefrernceController.getFirebaseToken(BreakActivity.this);

        button_break.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        BreakActivity.this);

                // 제목셋팅
                //alertDialogBuilder.setTitle("");

                // AlertDialog 셋팅
                alertDialogBuilder
                        .setMessage("삭제된 계정 정보와 개인 정보는 복구할 수 없습니다. 정말 탈퇴하시겠습니까?")
                        .setCancelable(false)
                        .setPositiveButton("확인",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(
                                            DialogInterface dialog, int id) {
                                        // TODO: sharedpreference-Token 지우고 DB에서 삭제하면 됨.
                                        SharedPreferences pref = getSharedPreferences(USER, Context.MODE_PRIVATE);
                                        SharedPreferences.Editor editor = pref.edit();
                                        editor.remove("firebaseToken");
                                        editor.commit();

                                        // 프로그램을 종료한다
                                        Toast.makeText(BreakActivity.this,"탈퇴되었습니다.",Toast.LENGTH_SHORT).show();
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
