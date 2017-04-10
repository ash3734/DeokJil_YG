package com.deokjilmate.www.deokjilmate.Setting.Notice;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.deokjilmate.www.deokjilmate.R;
import com.deokjilmate.www.deokjilmate.application.ApplicationController;
import com.deokjilmate.www.deokjilmate.network.NetworkService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NoticeActivity extends AppCompatActivity {

    private ArrayList<String> mGroupList = null;
    private ArrayList<ArrayList<String>> mChildList = null;
    private ArrayList<String> mChildListContent = null;
    private ExpandableListView mListView;

    private NoticeAdapter mBaseExpandableAdapter = null;

    NetworkService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notice_activity);


        mListView = (ExpandableListView) findViewById(R.id.elv_list);
        mGroupList = new ArrayList<String>();
        mChildList = new ArrayList<ArrayList<String>>();
        mChildListContent = new ArrayList<String>();


        service = ApplicationController.getInstance().getNetworkService();


        Call<BoardNotice> getNotice = service.getNotice();
        getNotice.enqueue(new Callback<BoardNotice>() {

            @Override
            public void onResponse(Call<BoardNotice> call, Response<BoardNotice> response) {

                if(response.isSuccessful()){
                    for(BoardNoticeData notice : response.body().result){
                        mGroupList.add(notice.notice_title+" "+notice.notice_time);
                    }

                    for(BoardNoticeData notice : response.body().result){
                        mChildListContent.add(notice.notice_main);
                        mChildList.add(mChildListContent);
                    }

                }

            }
            @Override
            public void onFailure(Call<BoardNotice> call, Throwable t) {

            }
        });



//        mChildList.add(mChildListContent);
//        mChildList.add(mChildListContent);
//        mChildList.add(mChildListContent);


        mBaseExpandableAdapter = new NoticeAdapter(this,mGroupList,mChildList);
        mListView.setAdapter(mBaseExpandableAdapter);

        // 그룹 클릭 했을 경우 이벤트
        mListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {
                Toast.makeText(getApplicationContext(), "g click = " + groupPosition,
                        Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        // 차일드 클릭 했을 경우 이벤트
        mListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                Toast.makeText(getApplicationContext(), "c click = " + childPosition,
                        Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        // 그룹이 닫힐 경우 이벤트
        mListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {
                Toast.makeText(getApplicationContext(), "g Collapse = " + groupPosition,
                        Toast.LENGTH_SHORT).show();
            }
        });

        // 그룹이 열릴 경우 이벤트
        mListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                Toast.makeText(getApplicationContext(), "g Expand = " + groupPosition,
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    /*
     * Layout

    private ExpandableListView mListView;

    private void setLayout(){
        mListView = (ExpandableListView) findViewById(R.id.elv_list);
    }
    */


}
