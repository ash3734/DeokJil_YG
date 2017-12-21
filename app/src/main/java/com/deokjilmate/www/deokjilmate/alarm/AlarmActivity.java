
package com.deokjilmate.www.deokjilmate.alarm;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.deokjilmate.www.deokjilmate.R;
import com.deokjilmate.www.deokjilmate.SharedPrefrernceController;
import com.deokjilmate.www.deokjilmate.UserDataSumm;
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
    private ArrayList<Integer> alarmArray = null;

    private ArrayList<ChildListContent> mChildListContent = null;
    private ArrayList<ChildListContent> mChildListContent2 = null;
    private ArrayList<ChildListContent> mChildListContent3 = null;
    private ArrayList<ChildListContent> mChildListContent4 = null;
    private HashMap<String,ArrayList<ChildListContent>> mChildList = null;
    private ArrayList<UserDataSumm> userDataSumms = null;

    private ProgressDialog progressDialog;


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
    String todayAlarmState;

    String firebaseToken;
    String firebaseToken2;
    String fcmToken;
    int flagCount;

    // 처음으로 알람을 받아올 때
    boolean isFirstAlarm;
    ArrayList<Integer> singerID = null;
    int getFirstAlarmCount = 0;

    ImageView alarm_today_info;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarm_activity);

        if (Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            //Drawable background = this.getResources().getDrawable(R.drawable.gradation);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(this.getResources().getColor(R.color.statusbar));
            //window.setNavigationBarColor(this.getResources().getColor(R.color.tw__transparent));
            //window.setBackgroundDrawable(background);

        }

        /***초기화 하는 부분***/
        todayAlarmState = "0";
        c = this;
        v = this;
        flagCount= 0; // flag 배열 개수 구분 하기 위해
        firebaseToken = SharedPrefrernceController.getFirebaseToken(AlarmActivity.this);
        firebaseToken2 = "qwerty";
        isFirstAlarm = SharedPrefrernceController.getAlarm(AlarmActivity.this);
        fcmToken = FirebaseInstanceId.getInstance().getToken();
        userDataSumms = new ArrayList<UserDataSumm>();
        userDataSumms = ApplicationController.getInstance().getUserDataSumms();

        singerID = new ArrayList<Integer>();
        alarmArray = new ArrayList<Integer>();
        progressDialog = new ProgressDialog(this);
        makeDialog("잠시만 기다려주세요.");

//        SharedPreferences FirstAlarm = getSharedPreferences("FirstAlarm", MODE_PRIVATE);
//        SharedPreferences.Editor editor = FirstAlarm.edit();
//        editor.putString("FirstAlarm", "true"); //First라는 key값으로 infoFirst 데이터를 저장한다.
//        editor.commit(); //완료한다.
//        isFirstAlarm = FirstAlarm.getString("FirstAlarm", "false");


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
                        .setMessage("하루에 한번 '진행 중인 투표 확인' 알림을 보내드립니다.")
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

        for (UserDataSumm data : userDataSumms) {
            Log.d("가수명확인", String.valueOf(data.getSinger_id()));
            singerID.add(data.getSinger_id());
            mGroupList.add(data.getSinger_name());
        }

        if(isFirstAlarm){
            Log.d("myTag","첫번째 알람이다!");

                        for(int logcount=0;logcount<singerID.size();logcount++){
                            Log.d("singer이름확인",String.valueOf(mGroupList.get(logcount)));
                        }
                        getAlarmSequence();
//                        for(int i = 0; i<singerID.size(); i++){
//                            Log.d("씽어아이디배열확인",String.valueOf(singerID.get(i)));
//
//                            Log.v("순번2", String.valueOf(i));
//                            int tempSingerID = singerID.get(i);
//                            final Call<FirstAlarmResult> getFirstAlarm = service.getfirstAlarm(tempSingerID);
//                            getFirstAlarm.enqueue(new Callback<FirstAlarmResult>(){
//                                @Override
//                                public void onResponse(Call<FirstAlarmResult> call, Response<FirstAlarmResult> response) {
//                                if(response.isSuccessful()){
//                                    Log.d("알람첫배열받기", "hhi onrespose들어옴");
//                                    String temp = response.body().alarm_array;
//                                    if(temp!=null)
//                                        Log.d("배열로 받아보자", response.body().alarm_array);
//                                    else
//                                        Log.d("배열로 받아보자", "aa");
//
//                                    //TODO: 09여기로
//                                    changeToIntegerArray(response.body().alarm_array);
//
//                                    Log.d("알람사이즈", String.valueOf(alarmArray));
//                                    for (int logcount = 0; logcount < alarmArray.size(); logcount++) {
//                                        Log.d("알람배열확인", String.valueOf(alarmArray.get(logcount)));
//                                    }
//
//                                    Log.v("순번", String.valueOf(getFirstAlarmCount));
//
//
//                                    switch (getFirstAlarmCount) {
//                                        case 0:
//                                            Log.d("onResponse반응?", "반응함");
//
//                                            mChildListContent = new ArrayList<ChildListContent>();
//                                            for (int x = 0; x < alarmArray.size(); x++) {
//                                                if (x == 0 && alarmArray.contains(x))
//                                                    mChildListContent.add(new ChildListContent("엠카운트다운", true));
//                                                else if (x == 0 && !alarmArray.contains(x))
//                                                    mChildListContent.add(new ChildListContent("엠카운트다운", false));
//                                                else if (x == 1 && alarmArray.contains(x))
//                                                    mChildListContent.add(new ChildListContent("쇼! 음악중심", true));
//                                                else if (x == 1 && !alarmArray.contains(x))
//                                                    mChildListContent.add(new ChildListContent("쇼! 음악중심", false));
//                                                else if (x == 2 && alarmArray.contains(x))
//                                                    mChildListContent.add(new ChildListContent("더 쇼", true));
//                                                else if (x == 2 && !alarmArray.contains(x))
//                                                    mChildListContent.add(new ChildListContent("더 쇼", false));
//                                            }
//                                            mChildList.put(mGroupList.get(getFirstAlarmCount), mChildListContent);
//                                            break;
//
//                                        case 1:
//                                            mChildListContent2 = new ArrayList<ChildListContent>();
//                                            for (int x = 0; x < alarmArray.size(); x++) {
//                                                if (x == 0 && alarmArray.contains(x))
//                                                    mChildListContent2.add(new ChildListContent("엠카운트다운", true));
//                                                else if (x == 0 && !alarmArray.contains(x))
//                                                    mChildListContent2.add(new ChildListContent("엠카운트다운", false));
//                                                else if (x == 1 && alarmArray.contains(x))
//                                                    mChildListContent2.add(new ChildListContent("쇼! 음악중심", true));
//                                                else if (x == 1 && !alarmArray.contains(x))
//                                                    mChildListContent2.add(new ChildListContent("쇼! 음악중심", false));
//                                                else if (x == 2 && alarmArray.contains(x))
//                                                    mChildListContent2.add(new ChildListContent("더 쇼", true));
//                                                else if (x == 2 && !alarmArray.contains(x))
//                                                    mChildListContent2.add(new ChildListContent("더 쇼", false));
//                                            }
//                                            mChildList.put(mGroupList.get(getFirstAlarmCount), mChildListContent2);
//                                            break;
//
//                                        case 2:
//                                            mChildListContent3 = new ArrayList<ChildListContent>();
//                                            for (int x = 0; x < alarmArray.size(); x++) {
//                                                if (x == 0 && alarmArray.contains(x))
//                                                    mChildListContent3.add(new ChildListContent("엠카운트다운", true));
//                                                else if (x == 0 && !alarmArray.contains(x))
//                                                    mChildListContent3.add(new ChildListContent("엠카운트다운", false));
//                                                else if (x == 1 && alarmArray.contains(x))
//                                                    mChildListContent3.add(new ChildListContent("쇼! 음악중심", true));
//                                                else if (x == 1 && !alarmArray.contains(x))
//                                                    mChildListContent3.add(new ChildListContent("쇼! 음악중심", false));
//                                                else if (x == 2 && alarmArray.contains(x))
//                                                    mChildListContent3.add(new ChildListContent("더 쇼", true));
//                                                else if (x == 2 && !alarmArray.contains(x))
//                                                    mChildListContent3.add(new ChildListContent("더 쇼", false));
//                                            }
//                                            mChildList.put(mGroupList.get(getFirstAlarmCount), mChildListContent3);
//                                            break;
//
//                                        case 3:
//                                            mChildListContent4 = new ArrayList<ChildListContent>();
//                                            for (int x = 0; x < alarmArray.size(); x++) {
//                                                if (x == 0 && alarmArray.contains(x))
//                                                    mChildListContent4.add(new ChildListContent("엠카운트다운", true));
//                                                else if (x == 0 && !alarmArray.contains(x))
//                                                    mChildListContent4.add(new ChildListContent("엠카운트다운", false));
//                                                else if (x == 1 && alarmArray.contains(x))
//                                                    mChildListContent4.add(new ChildListContent("쇼! 음악중심", true));
//                                                else if (x == 1 && !alarmArray.contains(x))
//                                                    mChildListContent4.add(new ChildListContent("쇼! 음악중심", false));
//                                                else if (x == 2 && alarmArray.contains(x))
//                                                    mChildListContent4.add(new ChildListContent("더 쇼", true));
//                                                else if (x == 2 && !alarmArray.contains(x))
//                                                    mChildListContent4.add(new ChildListContent("더 쇼", false));
//                                            }
//                                            Log.v("pre", mChildList.toString());
//                                            mChildList.put(mGroupList.get(getFirstAlarmCount), mChildListContent4);
//                                            break;
//
//                                        default:
//                                            break;
//                                    }
//                                    getFirstAlarmCount++;
//                                }
//                            }
//
//                                @Override
//                                public void onFailure(Call<FirstAlarmResult> call, Throwable t) {
//                                    Log.d("알람첫배열받기","no...onfail들어옴");
//                                }
//                            });
//
//                        }
                        isFirstAlarm = false;
                        SharedPrefrernceController.setAlarm(AlarmActivity.this, isFirstAlarm);
                        mBaseExpandableAdapter = new AlarmAdapter(c, mGroupList, mChildList, v);
                        mListView.setAdapter(mBaseExpandableAdapter);

                        for(int logcount=0;logcount<singerID.size();logcount++){
                            for(int logcount2=0;logcount2<mChildList.size();logcount2++){
                                Log.d("singer방송확인",String.valueOf(mChildList.get(logcount).get(logcount2).getMp_name()));
                            }
                        }

        }
        else {
            Log.d("myTag", "첫번째 알람이 아님!!");

            Call<NoticeResult> getAlarm = service.getAlarm(firebaseToken);
            getAlarm.enqueue(new Callback<NoticeResult>() {
                @Override
                public void onResponse(Call<NoticeResult> call, Response<NoticeResult> response) {

                    Log.d("알람가져오기", String.valueOf(response.isSuccessful()));

                    if (response.isSuccessful()) {
                        Log.d("알람", "가져오기 성공");
                        Log.d("firebase", firebaseToken);
                        todayAlarmState = response.body().data.today_alarm;

                        Log.d("todayAlarm",todayAlarmState);


                        if (todayAlarmState!=null) {
                            if (todayAlarmState.equals("1"))
                                todaySwitch.setChecked(true);
                            else todaySwitch.setChecked(false);
                        }

                        for (flagCount = 0; flagCount < mGroupList.size(); flagCount++) {
                            switch (flagCount) {
                                case 0: // zero_flag
                                    mChildListContent = new ArrayList<ChildListContent>();
                                    if(response.body().data.zero_flag==null){
//                                            mChildListContent.add(new ChildListContent("출연 방송이 없습니다", false));
                                        mChildListContent.add(new ChildListContent("엠카운트다운", false));
                                        mChildListContent.add(new ChildListContent("쇼! 음악중심", false));
                                        mChildListContent.add(new ChildListContent("더 쇼", false));
                                    }else if(response.body().data.zero_flag.isEmpty()){
//                                        mChildListContent.add(new ChildListContent("출연 방송이 없습니다", false));
                                        mChildListContent.add(new ChildListContent("엠카운트다운", false));
                                        mChildListContent.add(new ChildListContent("쇼! 음악중심", false));
                                        mChildListContent.add(new ChildListContent("더 쇼", false));
                                    }
                                    else {
                                        for (int j = 0; j <= 2; j++) {
                                            if (j == 0 && response.body().data.zero_flag.get(0).contains(j))
                                                mChildListContent.add(new ChildListContent("엠카운트다운", true));
                                            else if (j == 0 && !response.body().data.zero_flag.get(0).contains(j))
                                                mChildListContent.add(new ChildListContent("엠카운트다운", false));
                                            else if (j == 1 && response.body().data.zero_flag.get(0).contains(j))
                                                mChildListContent.add(new ChildListContent("쇼! 음악중심", true));
                                            else if (j == 1 && !response.body().data.zero_flag.get(0).contains(j))
                                                mChildListContent.add(new ChildListContent("쇼! 음악중심", false));
                                            else if (j == 2 && response.body().data.zero_flag.get(0).contains(j))
                                                mChildListContent.add(new ChildListContent("더 쇼", true));
                                            else if (j == 2 && !response.body().data.zero_flag.get(0).contains(j))
                                                mChildListContent.add(new ChildListContent("더 쇼", false));
                                        }
                                    }
                                    mChildList.put(mGroupList.get(flagCount), mChildListContent);
                                    break;

                                case 1: // one_flag
                                    mChildListContent2 = new ArrayList<ChildListContent>();
                                    if(response.body().data.one_flag==null){
//                                        mChildListContent2.add(new ChildListContent("출연 방송이 없습니다", false));
                                        mChildListContent.add(new ChildListContent("엠카운트다운", false));
                                        mChildListContent.add(new ChildListContent("쇼! 음악중심", false));
                                        mChildListContent.add(new ChildListContent("더 쇼", false));
                                    }else if(response.body().data.one_flag.isEmpty()){
//                                        mChildListContent2.add(new ChildListContent("출연 방송이 없습니다", false));
                                        mChildListContent.add(new ChildListContent("엠카운트다운", false));
                                        mChildListContent.add(new ChildListContent("쇼! 음악중심", false));
                                        mChildListContent.add(new ChildListContent("더 쇼", false));
                                    }
                                    else {
                                        for (int j = 0; j <= 2; j++) {
                                            if (j == 0 && response.body().data.one_flag.get(0).contains(j))
                                                mChildListContent2.add(new ChildListContent("엠카운트다운", true));
                                            else if (j == 0 && !response.body().data.one_flag.get(0).contains(j))
                                                mChildListContent2.add(new ChildListContent("엠카운트다운", false));
                                            else if (j == 1 && response.body().data.one_flag.get(0).contains(j))
                                                mChildListContent2.add(new ChildListContent("쇼! 음악중심", true));
                                            else if (j == 1 && !response.body().data.one_flag.get(0).contains(j))
                                                mChildListContent2.add(new ChildListContent("쇼! 음악중심", false));
                                            else if (j == 2 && response.body().data.one_flag.get(0).contains(j))
                                                mChildListContent2.add(new ChildListContent("더 쇼", true));
                                            else if (j == 2 && !response.body().data.one_flag.get(0).contains(j))
                                                mChildListContent2.add(new ChildListContent("더 쇼", false));
                                        }
                                    }
                                    mChildList.put(mGroupList.get(flagCount), mChildListContent2);
                                    break;

                                case 2: // two_flag
                                    mChildListContent3 = new ArrayList<ChildListContent>();
                                    if(response.body().data.two_flag==null){
//                                        mChildListContent3.add(new ChildListContent("출연 방송이 없습니다", false));
                                        mChildListContent.add(new ChildListContent("엠카운트다운", false));
                                        mChildListContent.add(new ChildListContent("쇼! 음악중심", false));
                                        mChildListContent.add(new ChildListContent("더 쇼", false));
                                    }else if(response.body().data.two_flag.isEmpty()){
//                                        mChildListContent3.add(new ChildListContent("출연 방송이 없습니다", false));
                                        mChildListContent.add(new ChildListContent("엠카운트다운", false));
                                        mChildListContent.add(new ChildListContent("쇼! 음악중심", false));
                                        mChildListContent.add(new ChildListContent("더 쇼", false));
                                    }
                                    else {
                                        for (int j = 0; j <= 2; j++) {
                                            if (j == 0 && response.body().data.two_flag.get(0).contains(j))
                                                mChildListContent3.add(new ChildListContent("엠카운트다운", true));
                                            else if (j == 0 && !response.body().data.two_flag.get(0).contains(j))
                                                mChildListContent3.add(new ChildListContent("엠카운트다운", false));
                                            else if (j == 1 && response.body().data.two_flag.get(0).contains(j))
                                                mChildListContent3.add(new ChildListContent("쇼! 음악중심", true));
                                            else if (j == 1 && !response.body().data.two_flag.get(0).contains(j))
                                                mChildListContent3.add(new ChildListContent("쇼! 음악중심", false));
                                            else if (j == 2 && response.body().data.two_flag.get(0).contains(j))
                                                mChildListContent3.add(new ChildListContent("더 쇼", true));
                                            else if (j == 2 && !response.body().data.two_flag.get(0).contains(j))
                                                mChildListContent3.add(new ChildListContent("더 쇼", false));
                                        }
                                    }
                                    mChildList.put(mGroupList.get(flagCount), mChildListContent3);
                                    break;

                                case 3: // three_flag
                                    mChildListContent4 = new ArrayList<ChildListContent>();
                                    if(response.body().data.three_flag==null){
//                                        mChildListContent4.add(new ChildListContent("출연 방송이 없습니다", false));
                                        mChildListContent.add(new ChildListContent("엠카운트다운", false));
                                        mChildListContent.add(new ChildListContent("쇼! 음악중심", false));
                                        mChildListContent.add(new ChildListContent("더 쇼", false));
                                    }else if(response.body().data.three_flag.isEmpty()){
//                                        mChildListContent4.add(new ChildListContent("출연 방송이 없습니다", false));
                                        mChildListContent.add(new ChildListContent("엠카운트다운", false));
                                        mChildListContent.add(new ChildListContent("쇼! 음악중심", false));
                                        mChildListContent.add(new ChildListContent("더 쇼", false));
                                    }
                                    else {
                                        for (int j = 0; j <= 2; j++) {
                                            if (j == 0 && response.body().data.three_flag.get(0).contains(j))
                                                mChildListContent4.add(new ChildListContent("엠카운트다운", true));
                                            else if (j == 0 && !response.body().data.three_flag.get(0).contains(j))
                                                mChildListContent4.add(new ChildListContent("엠카운트다운", false));
                                            else if (j == 1 && response.body().data.three_flag.get(0).contains(j))
                                                mChildListContent4.add(new ChildListContent("쇼! 음악중심", true));
                                            else if (j == 1 && !response.body().data.three_flag.get(0).contains(j))
                                                mChildListContent4.add(new ChildListContent("쇼! 음악중심", false));
                                            else if (j == 2 && response.body().data.three_flag.get(0).contains(j))
                                                mChildListContent4.add(new ChildListContent("더 쇼", true));
                                            else if (j == 2 && !response.body().data.three_flag.get(0).contains(j))
                                                mChildListContent4.add(new ChildListContent("더 쇼", false));
                                        }
                                    }
                                    mChildList.put(mGroupList.get(flagCount), mChildListContent4);
                                    break;

                                default:
                                    break;
                            }
                        }

                        mBaseExpandableAdapter = new AlarmAdapter(c, mGroupList, mChildList, v);
                        mListView.setAdapter(mBaseExpandableAdapter);

                    }
                    progressDialog.dismiss();
                }
                @Override
                public void onFailure(Call<NoticeResult> call, Throwable t) {
                    Log.d("getAlarm통신연결", "실패");
                }
            });

        }



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
    public void updateStateCheck(String sname, String voteName, boolean state, boolean todayState) {
        Log.d("AlarmAct", "upDate");

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

        if(todayState) todayAlarmState="1";
        else todayAlarmState="0";

        updateNetwork();
    }

    @Override
    public void updateNetwork() {

        zero_flag.clear();
        one_flag.clear();
        two_flag.clear();
        three_flag.clear();
        Log.d("AlarmAct", "updateNetwork");

        String zeroF = "";
        String oneF = "";
        String twoF = "";
        String threeF = "";

        for(int m=0; m<mGroupList.size(); m++){
            switch(m){
                case 0:
                    //그룹의 첫 번째.
                    for(int z=0;z<mChildList.get(mGroupList.get(m)).size();z++){
                        Log.v("AlarmAct", mChildList.get(mGroupList.get(m)).get(z).getMp_name());
                        if(mChildList.get(mGroupList.get(m)).get(z).state) {
                            zero_flag.add(z);
                        }
                        else continue;
                    }
                    zeroF = arrayToString(zero_flag);
                    break;

                case 1:
                    for(int z=0;z<mChildList.get(mGroupList.get(m)).size();z++){
                        if(mChildList.get(mGroupList.get(m)).get(z).state) one_flag.add(z);
                        else continue;
                    }
                    oneF = arrayToString(one_flag);
                    break;
                case 2:
                    for(int z=0;z<mChildList.get(mGroupList.get(m)).size();z++){
                        if(mChildList.get(mGroupList.get(m)).get(z).state) two_flag.add(z);
                        else continue;
                    }
                    twoF = arrayToString(two_flag);
                    break;
                case 3:
                    for(int z=0;z<mChildList.get(mGroupList.get(m)).size();z++){
                        if(mChildList.get(mGroupList.get(m)).get(z).state) three_flag.add(z);
                        else continue;
                    }
                    threeF = arrayToString(three_flag);
                    break;
                default:
                    break;

            }
        }

        requestName(todayAlarmState,zeroF,oneF,twoF,threeF);

    }


    @Override
    public void requestName(String todayAlarmState, String zero_flag, String one_flag, String two_flag, String three_flag) {


        retrofit2.Call<NoticePostResult> postAlarm = service.postAlarm(new NoticePostData(firebaseToken,fcmToken,todayAlarmState,zero_flag,one_flag,two_flag,three_flag));
        postAlarm.enqueue(new Callback<NoticePostResult>() {
            @Override
            public void onResponse(retrofit2.Call<NoticePostResult> call, Response<NoticePostResult> response) {
                if (response.isSuccessful()) {
//                    Toast.makeText(getApplicationContext(), "성공", Toast.LENGTH_SHORT).show();
                    Log.d("fcmtokenCheck",fcmToken);
                    Log.d("firebasetokenCheck",firebaseToken);

                } else {
//                    Toast.makeText(getApplicationContext(), "등록실패", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(retrofit2.Call<NoticePostResult> call, Throwable t) {
//                Toast.makeText(getApplicationContext(), "실패", Toast.LENGTH_SHORT).show();
            }
        });
   }

   public void changeToIntegerArray(String arrayString){
       alarmArray.clear();
       if(arrayString!=null) {
           String tempString1 = "";
           tempString1 = arrayString.replace('[', ' ');
           String tempString2 = tempString1.replace(']', ' ');

           for (int i = 0; i < tempString2.split(", ").length; i++) {
               //int temp = tempString2.split(",")[i].charAt(0);
               if (i == 0) {
                   int temp = tempString2.split(" ")[1].split(",")[0].charAt(0) - 48;
                   alarmArray.add(temp);
               } else {
                   int temp = tempString2.split(", ")[i].charAt(0) - 48;
                   alarmArray.add(temp);
               }
           }
       }else{
           alarmArray.add(-1);
       }
       Log.v("알람 배열", alarmArray.toString());
   }

   public void getAlarmSequence(){

       final Call<FirstAlarmResult> getFirstAlarm = service.getfirstAlarm(singerID.get(getFirstAlarmCount));
       getFirstAlarm.enqueue(new Callback<FirstAlarmResult>(){
           @Override
           public void onResponse(Call<FirstAlarmResult> call, Response<FirstAlarmResult> response) {
               if(response.isSuccessful()){
                   Log.d("알람첫배열받기", "hhi onrespose들어옴");
                   String temp = response.body().alarm_array;

                   //TODO: 09여기로
                   changeToIntegerArray(response.body().alarm_array);

                   Log.d("알람사이즈", String.valueOf(alarmArray));
                   for (int logcount = 0; logcount < alarmArray.size(); logcount++) {
                       Log.d("알람배열확인", String.valueOf(alarmArray.get(logcount)));
                   }

                   Log.v("순번", String.valueOf(getFirstAlarmCount));

                   todaySwitch.setChecked(true); // 첫 알람 받아올 때 무조건 오늘의 알림은 true로 되어있게!

                   switch (getFirstAlarmCount) {
                       case 0:
                           Log.d("onResponse반응?", "반응함");

                           mChildListContent = new ArrayList<ChildListContent>();
                           if(alarmArray.get(0)==-1){
//                               mChildListContent.add(new ChildListContent("출연 방송이 없습니다", false));
                               mChildListContent.add(new ChildListContent("엠카운트다운", false));
                               mChildListContent.add(new ChildListContent("쇼! 음악중심", false));
                               mChildListContent.add(new ChildListContent("더 쇼", false));

                           }else {
                               for (int x = 0; x < alarmArray.size(); x++) {
                                   if (x == 0 && alarmArray.contains(x))
                                       mChildListContent.add(new ChildListContent("엠카운트다운", true));
                                   else if (x == 0 && !alarmArray.contains(x))
                                       mChildListContent.add(new ChildListContent("엠카운트다운", false));
                                   else if (x == 1 && alarmArray.contains(x))
                                       mChildListContent.add(new ChildListContent("쇼! 음악중심", true));
                                   else if (x == 1 && !alarmArray.contains(x))
                                       mChildListContent.add(new ChildListContent("쇼! 음악중심", false));
                                   else if (x == 2 && alarmArray.contains(x))
                                       mChildListContent.add(new ChildListContent("더 쇼", true));
                                   else if (x == 2 && !alarmArray.contains(x))
                                       mChildListContent.add(new ChildListContent("더 쇼", false));
                               }
                           }
                           mChildList.put(mGroupList.get(getFirstAlarmCount), mChildListContent);
                           Log.v("pre", mChildList.toString());

                           break;

                       case 1:
                           mChildListContent2 = new ArrayList<ChildListContent>();
                           if(alarmArray.get(0)==-1){
//                               mChildListContent2.add(new ChildListContent("출연 방송이 없습니다", false));
                               mChildListContent.add(new ChildListContent("엠카운트다운", false));
                               mChildListContent.add(new ChildListContent("쇼! 음악중심", false));
                               mChildListContent.add(new ChildListContent("더 쇼", false));
                           }else {
                               for (int x = 0; x < alarmArray.size(); x++) {
                                   if (x == 0 && alarmArray.contains(x))
                                       mChildListContent2.add(new ChildListContent("엠카운트다운", true));
                                   else if (x == 0 && !alarmArray.contains(x))
                                       mChildListContent2.add(new ChildListContent("엠카운트다운", false));
                                   else if (x == 1 && alarmArray.contains(x))
                                       mChildListContent2.add(new ChildListContent("쇼! 음악중심", true));
                                   else if (x == 1 && !alarmArray.contains(x))
                                       mChildListContent2.add(new ChildListContent("쇼! 음악중심", false));
                                   else if (x == 2 && alarmArray.contains(x))
                                       mChildListContent2.add(new ChildListContent("더 쇼", true));
                                   else if (x == 2 && !alarmArray.contains(x))
                                       mChildListContent2.add(new ChildListContent("더 쇼", false));
                               }
                           }
                           mChildList.put(mGroupList.get(getFirstAlarmCount), mChildListContent2);
                           Log.v("pre", mChildList.toString());

                           break;

                       case 2:
                           mChildListContent3 = new ArrayList<ChildListContent>();
                           if(alarmArray.get(0)==-1){
//                               mChildListContent3.add(new ChildListContent("출연 방송이 없습니다", false));
                               mChildListContent.add(new ChildListContent("엠카운트다운", false));
                               mChildListContent.add(new ChildListContent("쇼! 음악중심", false));
                               mChildListContent.add(new ChildListContent("더 쇼", false));
                           }
                           else {
                               for (int x = 0; x < alarmArray.size(); x++) {
                                   if (x == 0 && alarmArray.contains(x))
                                       mChildListContent3.add(new ChildListContent("엠카운트다운", true));
                                   else if (x == 0 && !alarmArray.contains(x))
                                       mChildListContent3.add(new ChildListContent("엠카운트다운", false));
                                   else if (x == 1 && alarmArray.contains(x))
                                       mChildListContent3.add(new ChildListContent("쇼! 음악중심", true));
                                   else if (x == 1 && !alarmArray.contains(x))
                                       mChildListContent3.add(new ChildListContent("쇼! 음악중심", false));
                                   else if (x == 2 && alarmArray.contains(x))
                                       mChildListContent3.add(new ChildListContent("더 쇼", true));
                                   else if (x == 2 && !alarmArray.contains(x))
                                       mChildListContent3.add(new ChildListContent("더 쇼", false));
                               }
                           }
                           mChildList.put(mGroupList.get(getFirstAlarmCount), mChildListContent3);
                           Log.v("pre", mChildList.toString());

                           break;

                       case 3:
                           mChildListContent4 = new ArrayList<ChildListContent>();
                           if(alarmArray.get(0)==-1){
//                               mChildListContent4.add(new ChildListContent("출연 방송이 없습니다", false));
                               mChildListContent.add(new ChildListContent("엠카운트다운", false));
                               mChildListContent.add(new ChildListContent("쇼! 음악중심", false));
                               mChildListContent.add(new ChildListContent("더 쇼", false));
                           }else {
                               for (int x = 0; x < alarmArray.size(); x++) {
                                   if (x == 0 && alarmArray.contains(x))
                                       mChildListContent4.add(new ChildListContent("엠카운트다운", true));
                                   else if (x == 0 && !alarmArray.contains(x))
                                       mChildListContent4.add(new ChildListContent("엠카운트다운", false));
                                   else if (x == 1 && alarmArray.contains(x))
                                       mChildListContent4.add(new ChildListContent("쇼! 음악중심", true));
                                   else if (x == 1 && !alarmArray.contains(x))
                                       mChildListContent4.add(new ChildListContent("쇼! 음악중심", false));
                                   else if (x == 2 && alarmArray.contains(x))
                                       mChildListContent4.add(new ChildListContent("더 쇼", true));
                                   else if (x == 2 && !alarmArray.contains(x))
                                       mChildListContent4.add(new ChildListContent("더 쇼", false));
                               }
                           }
                           mChildList.put(mGroupList.get(getFirstAlarmCount), mChildListContent4);
                           Log.v("pre", mChildList.toString());

                           break;

                       default:
                           break;
                   }
                   getFirstAlarmCount++;
                   if(getFirstAlarmCount == singerID.size()){
                       progressDialog.dismiss();
                       return;
                   }else
                       getAlarmSequence();
               }
           }

           @Override
           public void onFailure(Call<FirstAlarmResult> call, Throwable t) {
               Log.d("알람첫배열받기","no...onfail들어옴");
           }
       });
   }

    public void makeDialog(String message) {
        if (progressDialog != null && !progressDialog.isShowing()) {
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setMessage(message);
            progressDialog.setCancelable(false);
            progressDialog.show();
        }
    }

    public String arrayToString(ArrayList<Integer> array){
        String returnString = "";
        if(array == null)
            returnString = "";
        else {
            if (array.isEmpty())
                returnString = "";
            else {
                returnString = "[";
                for (int i = 0; i < array.size(); i++) {
                    returnString += array.get(i).toString();
                    if (i < (array.size() - 1))
                        returnString += ",";
                }
                returnString += "]";
            }
        }
        return returnString;
    }
}
