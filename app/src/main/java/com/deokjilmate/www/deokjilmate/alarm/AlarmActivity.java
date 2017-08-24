
package com.deokjilmate.www.deokjilmate.alarm;


import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.deokjilmate.www.deokjilmate.R;
import com.deokjilmate.www.deokjilmate.SharedPrefrernceController;
import com.deokjilmate.www.deokjilmate.UserAllSingerData;
import com.deokjilmate.www.deokjilmate.UserAllSingerResponse;
import com.deokjilmate.www.deokjilmate.application.ApplicationController;
import com.deokjilmate.www.deokjilmate.network.NetworkService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AlarmActivity extends AppCompatActivity {


    private ArrayList<String> reGroupList = null;
    private ArrayList<String> mGroupList = null;

    private ArrayList<ArrayList<String>> mChildList = null;
    private ArrayList<String> mChildListContent = null;
    private ArrayList<String> mChildListContent2 = null;
    private ArrayList<String> mChildListContent3 = null;
//    private ArrayList<ArrayList<ChildStateObject>> mChildList = null;
//    private ArrayList<ChildStateObject> mChildListContent = null;
//    private ArrayList<ChildStateObject> mChildListContent2 = null;
//    private ArrayList<ChildStateObject> mChildListContent3 = null;

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
   // ActionBar actionBar;
    Context c;


    String firebaseToken;

    ImageView alarm_today_info;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarm_activity);


        c = this;
        firebaseToken = SharedPrefrernceController.getFirebaseToken(AlarmActivity.this);

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

        /*
        actionBar = getSupportActionBar();

        // Custom Actionbar를 사용하기 위해 CustomEnabled을 true 시키고 필요 없는 것은 false 시킨다
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(false);            //액션바 아이콘을 업 네비게이션 형태로 표시합니다.
        actionBar.setDisplayShowTitleEnabled(false);        //액션바에 표시되는 제목의 표시유무를 설정합니다.
        actionBar.setDisplayShowHomeEnabled(false);            //홈 아이콘을 숨김처리합니다.


        //layout을 가지고 와서 actionbar에 포팅을 시킵니다.
        //View mCustomView = LayoutInflater.from(this).inflate(R.layout.layout_alarm_actionbar, null);
        //actionBar.setCustomView(mCustomView);


        // 액션바에 백그라운드 이미지를 아래처럼 입힐 수 있습니다. (drawable 폴더에 img_action_background.png 파일이 있어야 겠죠?)
        // actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.main_topbar));
        */

        s_id = 0;
        m_id = 129;

        service = ApplicationController.getInstance().getNetworkService();


        mListView = (ExpandableListView) findViewById(R.id.elv_list);
        mGroupList = new ArrayList<String>();
        mChildList = new ArrayList<ArrayList<String>>();
        mChildListContent = new ArrayList<String>();


        Call<UserAllSingerResponse> userAllSingerResponse = service.userAllSinger("asdfdsa");
        userAllSingerResponse.enqueue(new Callback<UserAllSingerResponse>() {
            @Override
            public void onResponse(Call<UserAllSingerResponse> call, Response<UserAllSingerResponse> response) {

                Log.v("firebaseToken",firebaseToken);
                Log.d("통신연결","성공");
                Log.d("result값",String.valueOf(response.body().result));

                mChildListContent.add("인기가요");
                mChildListContent.add("쇼!음악중심");
                mChildListContent.add("쇼챔피언");

                if(response.body().result){
                    for(UserAllSingerData data : response.body().data){
                        Log.d("가수명확인",data.getSinger_name());
                        mGroupList.add(data.getSinger_name());
                        mChildList.add(mChildListContent);
                    }
                }




                mBaseExpandableAdapter = new AlarmAdapter(c, mGroupList, mChildList);
                mListView.setAdapter(mBaseExpandableAdapter);
            }

            @Override
            public void onFailure(Call<UserAllSingerResponse> call, Throwable t) {
                Log.d("통신연결","실패");
            }
        });



        // TODO: firebaseToken 보내줘야 함
        Call<NoticeResult> getAlarm = service.getAlarm("asdfdsa");
        getAlarm.enqueue(new Callback<NoticeResult>() {
            @Override
            public void onResponse(Call<NoticeResult> call, Response<NoticeResult> response) {
                Log.d("getAlarm통신연결","성공");
//                Log.d("response?",String.valueOf(response.body().result));
                Log.d("response error",response.errorBody().toString());
                Log.d("response.isSuccessful",String.valueOf(response.isSuccessful()));
                if(response.isSuccessful()){
//                    for(NoticeData data : response.body().data.){
//                        Log.d("결과값 확인",String.valueOf(data));
//                    }
                    Log.d("결과값 확인",response.body().data.toString());
                }
            }

            @Override
            public void onFailure(Call<NoticeResult> call, Throwable t) {
                Log.d("getAlarm통신연결","실패");
            }
        });
        /*

        Call<NoticeResult> getDetailData = service.getDetailData(m_id);
        getDetailData.enqueue(new Callback<NoticeResult>() {

            boolean state;

            @Override
            public void onResponse(Call<NoticeResult> call, Response<NoticeResult> response) {

                if (response.isSuccessful()) {

                    for (NoticeData data : response.body().result) {
                        if (!mGroupList.contains(data.name)) {
                            mGroupList.add(data.name);
                        }
                    }


                    for (int i = 0; i < mGroupList.size(); i++) {
                        ArrayList<ChildStateObject> mChildListContent = new ArrayList<ChildStateObject>();
                        for (int j = 0; j < response.body().result.size(); j++) {
                            if (mGroupList.get(i).equals(response.body().result.get(j).name)) {
                                if (response.body().result.get(j).mp_name.equals("쇼! 챔피언") && response.body().result.get(j).notice.equals("t")) {
                                    mChildListContent.add(new ChildStateObject("쇼! 챔피언", true));
                                } else if (response.body().result.get(j).mp_name.equals("쇼! 챔피언") && response.body().result.get(j).notice.equals("f")) {
                                    mChildListContent.add(new ChildStateObject("쇼! 챔피언", false));
                                } else if (response.body().result.get(j).mp_name.equals("엠카운트다운") && response.body().result.get(j).notice.equals("t")) {
                                    mChildListContent.add(new ChildStateObject("엠카운트다운", true));
                                } else if (response.body().result.get(j).mp_name.equals("엠카운트다운") && response.body().result.get(j).notice.equals("f")) {
                                    mChildListContent.add(new ChildStateObject("엠카운트다운", false));
                                } else if (response.body().result.get(j).mp_name.equals("인기가요") && response.body().result.get(j).notice.equals("t")) {
                                    mChildListContent.add(new ChildStateObject("인기가요", true));
                                } else if (response.body().result.get(j).mp_name.equals("인기가요") && response.body().result.get(j).notice.equals("f")) {
                                    mChildListContent.add(new ChildStateObject("인기가요", false));
                                }
                            }
                        }
                        mChildList.add(mChildListContent);
                    }


                }

            }

            @Override
            public void onFailure(Call<NoticeResult> call, Throwable t) {

            }
        });

        */



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

    /*
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

        for (int j = 0; j < mChildList.get(groupIndex).size(); j++) {

            if (mChildList.get(groupIndex).get(j).name.equals(voteName)) {
                childIndex = j;
                mChildList.get(groupIndex).get(j).state = state;
                mBaseExpandableAdapter.notifyDataSetChanged();
                break;
            }
        }


        if (state)
            requestName(sname, voteName, "t");
        else
            requestName(sname, voteName, "f");

    }


*/

    /*

    @Override
    public void requestName(String sname, String name, String notice) {

        Log.i("myTag", String.valueOf(m_id));
        Log.i("myTag", String.valueOf(sname));
        Log.i("myTag", String.valueOf(name));
        Log.i("myTag", String.valueOf(notice));


        retrofit2.Call<RegisterResult> requestRegister = service.requestRegister(new NoticeObject(m_id, sname, name, notice));
        requestRegister.enqueue(new Callback<RegisterResult>() {
            @Override
            public void onResponse(retrofit2.Call<RegisterResult> call, Response<RegisterResult> response) {
                if (response.isSuccessful()) {
                    if (response.body().result.equals("create")) {
                        Toast.makeText(getApplicationContext(), "성공", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "등록실패", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(retrofit2.Call<RegisterResult> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "실패", Toast.LENGTH_SHORT).show();
            }
        });




   }

   */


}
