package com.deokjilmate.www.deokjilmate.Setting;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.deokjilmate.www.deokjilmate.R;

import java.util.ArrayList;

public class SettingActivity extends AppCompatActivity {


    ArrayList<SettingListItem> datas= new ArrayList<SettingListItem>();
    ListView listview;
    Button logoutBtn;
    Intent intent_inquiry;
    Intent intent_notice;
    SharedPreferences.Editor editor;
    SharedPreferences logout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_activity);
        datas.add( new SettingListItem("공지사항",R.drawable.aoa));
        datas.add( new SettingListItem("문의하기",R.drawable.aoa));
        datas.add( new SettingListItem("버전정보                                                1.0.0",R.drawable.aoa));
        datas.add( new SettingListItem("약관 및 정책",R.drawable.aoa));
        datas.add( new SettingListItem("탈퇴하기",R.drawable.aoa));

        listview= (ListView)findViewById(R.id.basicListview);
        SettingAdapter adapter= new SettingAdapter( getLayoutInflater() , datas);
        logoutBtn = (Button)findViewById(R.id.setting_logout);
        listview.setAdapter(adapter);

        intent_inquiry = new Intent(this,InquiryActivity.class);
        intent_notice = new Intent(this,NoticeActivity.class);

        // sharedpreferences 설정
        logout = getSharedPreferences("loginState",MODE_PRIVATE);
        editor = logout.edit();

        AdapterView.OnItemClickListener listener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                switch(position){
                    case 0:
                        startActivity(intent_notice);
                        break;
                    case 1:
                        startActivity(intent_inquiry);
                        break;
                    case 2:
                        notice();
                        break;
                }
            }
        };
        listview.setOnItemClickListener(listener);


        // 로그아웃 버튼 눌렀을시
        Button.OnClickListener logoutClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor.putBoolean("loginState",false);
            }
        };
        logoutBtn.setOnClickListener(logoutClickListener);
    }

    public void notice(){
        Toast toast = Toast.makeText(this, "아직 개발 ㄴㄴ.", Toast.LENGTH_SHORT);
        toast.show();
    }
}
