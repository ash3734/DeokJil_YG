
package com.deokjilmate.www.deokjilmate.alarm;


import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.deokjilmate.www.deokjilmate.R;
import com.deokjilmate.www.deokjilmate.SharedPrefrernceController;
import com.deokjilmate.www.deokjilmate.UserAllSingerData;
import com.deokjilmate.www.deokjilmate.UserAllSingerResponse;
import com.deokjilmate.www.deokjilmate.application.ApplicationController;
import com.deokjilmate.www.deokjilmate.network.NetworkService;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AlarmActivity extends AppCompatActivity implements MainView{


    private ArrayList<String> reGroupList = null;
    private ArrayList<String> mGroupList = null;


    private ArrayList<ChildListContent> mChildListContent = null;
    private ArrayList<ChildListContent> mChildListContent2 = null;
    private ArrayList<ChildListContent> mChildListContent3 = null;
    private ArrayList<ChildListContent> mChildListContent4 = null;
    private HashMap<String,ArrayList<ChildListContent>> mChildList = null;

    ArrayList<Integer> zero_flag = null;
    ArrayList<Integer> one_flag = null;
    ArrayList<Integer> two_flag = null;
    ArrayList<Integer> three_flag = null;

    AlarmAdapter mBaseExpandableAdapter = null;
    private ExpandableListView mListView;

    SharedPreferences noticeInfo;
    SharedPreferences.Editor editor;

    private Switch aSwitch;
    CompoundButton childButton;
    NetworkService service;
    NetworkService noticeService;
    int m_id;
    int s_id;
    String s_name;
    String mp_name;
    String notice;
    TextView childText;
    MainView view;

    Context c;
    MainView v;
    Button show_token;
    Switch todaySwitch;
    int todayAlarmState;

    String firebaseToken;
    String firebaseToken2;
    String fcmToken;
    int flagCount;

    ImageView alarm_today_info;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarm_activity);


        /***초기화 하는 부분***/
        todayAlarmState = 0;
        c = this;
        v = this;
        flagCount= 0; // flag 배열 개수 구분 하기 위해
        firebaseToken = SharedPrefrernceController.getFirebaseToken(AlarmActivity.this);
        firebaseToken2 = "qwerty";
        fcmToken = FirebaseInstanceId.getInstance().getToken();

        Log.d("fcm",fcmToken);

        zero_flag = new ArrayList<Integer>();
        one_flag = new ArrayList<Integer>();
        two_flag = new ArrayList<Integer>();
        three_flag = new ArrayList<Integer>();

        todaySwitch = (Switch)findViewById(R.id.alarm_today);

        alarm_today_info = (ImageView) findViewById(R.id.alarm_today_info);
        alarm_today_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(AlarmActivity.this);

                // 제목셋팅
                alertDialogBuilder.setTitle("오늘의 알림");

                // AlertDialog 셋팅
                alertDialogBuilder
                        .setMessage("어쩌고 저쩌고")
                        .setCancelable(false)
                        .setNeutralButton("확인",
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

        service = ApplicationController.getInstance().getNetworkService();

        mListView = (ExpandableListView) findViewById(R.id.elv_list);
        mGroupList = new ArrayList<String>();
        mChildList = new HashMap<String,ArrayList<ChildListContent>>();


        Call<UserAllSingerResponse> userAllSingerResponse = service.userAllSinger(firebaseToken2);
        userAllSingerResponse.enqueue(new Callback<UserAllSingerResponse>() {
            @Override
            public void onResponse(Call<UserAllSingerResponse> call, Response<UserAllSingerResponse> response) {
                if(response.body().result){
                    for(UserAllSingerData data : response.body().data){
                        Log.d("가수명확인",data.getSinger_name());
                        mGroupList.add(data.getSinger_name());
                    }
                }

                Call<NoticeResult> getAlarm = service.getAlarm(firebaseToken2);
                getAlarm.enqueue(new Callback<NoticeResult>() {
                    @Override
                    public void onResponse(Call<NoticeResult> call, Response<NoticeResult> response) {

                        Log.d("알람가져오기",String.valueOf(response.isSuccessful()));

                        if(response.isSuccessful()){
                            Log.d("알람","가져오기 성공");
                            Log.d("firebase",firebaseToken);
                            todayAlarmState = response.body().data.today_alarm;

                            if(todayAlarmState==1) todaySwitch.setChecked(true);
                            else todaySwitch.setChecked(false);

                                for(flagCount=0;flagCount<mGroupList.size();flagCount++){
                                    switch (flagCount){
                                        case 0: // zero_flag
                                            mChildListContent = new ArrayList<ChildListContent>();
                                            for(int j=0;j<=2;j++){
                                                if(j==0 && response.body().data.zero_flag.contains(j))
                                                    mChildListContent.add(new ChildListContent("엠카운트다운",true));
                                                else if(j==0 && !response.body().data.zero_flag.contains(j))
                                                    mChildListContent.add(new ChildListContent("엠카운트다운",false));
                                                else if(j==1 && response.body().data.zero_flag.contains(j))
                                                    mChildListContent.add(new ChildListContent("쇼! 음악중심",true));
                                                else if(j==1 && !response.body().data.zero_flag.contains(j))
                                                    mChildListContent.add(new ChildListContent("쇼! 음악중심",false));
                                                else if(j==2 && response.body().data.zero_flag.contains(j))
                                                    mChildListContent.add(new ChildListContent("더 쇼",true));
                                                else if(j==2 && !response.body().data.zero_flag.contains(j))
                                                    mChildListContent.add(new ChildListContent("더 쇼",false));
                                            }
                                            mChildList.put(mGroupList.get(flagCount),mChildListContent);
                                            break;

                                        case 1: // one_flag
                                            mChildListContent2 = new ArrayList<ChildListContent>();
                                            for(int j=0;j<=2;j++){
                                                if(j==0 && response.body().data.one_flag.contains(j))
                                                    mChildListContent2.add(new ChildListContent("엠카운트다운",true));
                                                else if(j==0 && !response.body().data.one_flag.contains(j))
                                                    mChildListContent2.add(new ChildListContent("엠카운트다운",false));
                                                else if(j==1 && response.body().data.one_flag.contains(j))
                                                    mChildListContent2.add(new ChildListContent("쇼! 음악중심",true));
                                                else if(j==1 && !response.body().data.one_flag.contains(j))
                                                    mChildListContent2.add(new ChildListContent("쇼! 음악중심",false));
                                                else if(j==2 && response.body().data.one_flag.contains(j))
                                                    mChildListContent2.add(new ChildListContent("더 쇼",true));
                                                else if(j==2 && !response.body().data.one_flag.contains(j))
                                                    mChildListContent2.add(new ChildListContent("더 쇼",false));
                                            }
                                            mChildList.put(mGroupList.get(flagCount),mChildListContent2);
                                            break;

                                        case 2: // two_flag
                                            mChildListContent3 = new ArrayList<ChildListContent>();
                                            for(int j=0;j<=2;j++){
                                                if(j==0 && response.body().data.two_flag.contains(j))
                                                    mChildListContent3.add(new ChildListContent("엠카운트다운",true));
                                                else if(j==0 && !response.body().data.two_flag.contains(j))
                                                    mChildListContent3.add(new ChildListContent("엠카운트다운",false));
                                                else if(j==1 && response.body().data.two_flag.contains(j))
                                                    mChildListContent3.add(new ChildListContent("쇼! 음악중심",true));
                                                else if(j==1 && !response.body().data.two_flag.contains(j))
                                                    mChildListContent3.add(new ChildListContent("쇼! 음악중심",false));
                                                else if(j==2 && response.body().data.two_flag.contains(j))
                                                    mChildListContent3.add(new ChildListContent("더 쇼",true));
                                                else if(j==2 && !response.body().data.two_flag.contains(j))
                                                    mChildListContent3.add(new ChildListContent("더 쇼",false));
                                            }
                                            mChildList.put(mGroupList.get(flagCount),mChildListContent3);
                                            break;

                                        case 3: // three_flag
                                            mChildListContent4 = new ArrayList<ChildListContent>();
                                            for(int j=0;j<=2;j++){
                                                if(j==0 && response.body().data.three_flag.contains(j))
                                                    mChildListContent4.add(new ChildListContent("엠카운트다운",true));
                                                else if(j==0 && !response.body().data.three_flag.contains(j))
                                                    mChildListContent4.add(new ChildListContent("엠카운트다운",false));
                                                else if(j==1 && response.body().data.three_flag.contains(j))
                                                    mChildListContent4.add(new ChildListContent("쇼! 음악중심",true));
                                                else if(j==1 && !response.body().data.three_flag.contains(j))
                                                    mChildListContent4.add(new ChildListContent("쇼! 음악중심",false));
                                                else if(j==2 && response.body().data.three_flag.contains(j))
                                                    mChildListContent4.add(new ChildListContent("더 쇼",true));
                                                else if(j==2 && !response.body().data.three_flag.contains(j))
                                                    mChildListContent4.add(new ChildListContent("더 쇼",false));
                                            }
                                            mChildList.put(mGroupList.get(flagCount),mChildListContent4);
                                            break;

                                        default:
                                            break;
                                    }
                                }

                                mBaseExpandableAdapter = new AlarmAdapter(c, mGroupList, mChildList,v);
                                mListView.setAdapter(mBaseExpandableAdapter);

                        }
                    }

                    @Override
                    public void onFailure(Call<NoticeResult> call, Throwable t) {
                        Log.d("getAlarm통신연결","실패");
                    }
                });


            }

            @Override
            public void onFailure(Call<UserAllSingerResponse> call, Throwable t) {
                Log.d("통신연결","실패");
            }
        });



        mListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {
                return false;
            }
        });


        mListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                return false;
            }
        });

        mListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {

            }
        });

        mListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {


            }


        });


    }


    @Override
    public void updateStateCheck(String sname, String voteName, boolean state) {

        int groupIndex = 0;
        int childIndex = 0;

        for (int i = 0; i < mGroupList.size(); i++) {
            if (mGroupList.get(i).equals(sname)) {
                groupIndex = i;
                break;
            }
        }

        for (int j = 0; j < mChildList.get(mGroupList.get(groupIndex)).size(); j++) {

            if (mChildList.get(mGroupList.get(groupIndex)).get(j).getMp_name().equals(voteName)) {
                childIndex = j;
                mChildList.get(mGroupList.get(groupIndex)).get(j).state = state;
                mBaseExpandableAdapter.notifyDataSetChanged();
                break;
            }

        }

        todaySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b==true) todayAlarmState=1;
                else todayAlarmState=0;
                updateNetwork();
            }
        });

        if (state)
            updateNetwork();
        else
            updateNetwork();

    }

    @Override
    public void updateNetwork() {

        zero_flag.clear();
        one_flag.clear();
        two_flag.clear();
        three_flag.clear();

        for(int m=0; m<mGroupList.size(); m++){
            switch(m){
                case 0:
                    for(int z=0;z<=2;z++){
                        if(mChildList.get(mGroupList.get(m)).get(z).state) zero_flag.add(z);
                        else continue;
                    }
                    break;

                case 1:
                    for(int z=0;z<=2;z++){
                        if(mChildList.get(mGroupList.get(m)).get(z).state) one_flag.add(z);
                        else continue;
                    }
                    break;

                case 2:
                    for(int z=0;z<=2;z++){
                        if(mChildList.get(mGroupList.get(m)).get(z).state) two_flag.add(z);
                        else continue;
                    }
                    break;

                case 3:
                    for(int z=0;z<=2;z++){
                        if(mChildList.get(mGroupList.get(m)).get(z).state) three_flag.add(z);
                        else continue;
                    }
                    break;

                default:
                    break;

            }
        }

        requestName(todayAlarmState,zero_flag,one_flag,two_flag,three_flag);

    }


    @Override
    public void requestName(int todayAlarmState,ArrayList<Integer> zero_flag,ArrayList<Integer> one_flag,ArrayList<Integer> two_flag,ArrayList<Integer> three_flag) {

        retrofit2.Call<NoticePostResult> postAlarm = service.postAlarm(new NoticePostData(firebaseToken2,fcmToken,todayAlarmState,zero_flag,one_flag,two_flag,three_flag));
        postAlarm.enqueue(new Callback<NoticePostResult>() {
            @Override
            public void onResponse(retrofit2.Call<NoticePostResult> call, Response<NoticePostResult> response) {
                if (response.isSuccessful()) {
                        Toast.makeText(getApplicationContext(), "성공", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(getApplicationContext(), "등록실패", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(retrofit2.Call<NoticePostResult> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "실패", Toast.LENGTH_SHORT).show();
            }
        });

   }




}
