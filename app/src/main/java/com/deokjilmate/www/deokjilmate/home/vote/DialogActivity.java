package com.deokjilmate.www.deokjilmate.home.vote;

import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.deokjilmate.www.deokjilmate.R;
import com.deokjilmate.www.deokjilmate.application.ApplicationController;
import com.deokjilmate.www.deokjilmate.home.MainResult;
import com.deokjilmate.www.deokjilmate.home.vote.preVote.PreData;
import com.dev.sacot41.scviewpager.DotsView;
import com.dev.sacot41.scviewpager.SCViewAnimationUtil;
import com.dev.sacot41.scviewpager.SCViewPager;

import java.util.ArrayList;

/**
 * Created by ash on 2017-07-15.
 */

public class DialogActivity extends AppCompatActivity {

    private int NUM_PAGES = 3;

    private SCViewPager mViewPager;
    private MPagerAdapter mPageAdapter;
    private DotsView mDotsView;
    private ArrayList<PreData> preDatas;
    private MainResult mainResult;
    private int position;
    private Context context;
    private TextView closeTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //뭔지 모르겠지만 아마도 문자 보내느거 때문??
        ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.SEND_SMS},1);
        context = this;
        ApplicationController.getInstance().setContext(context);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_dialog);
        closeTextView = (TextView)findViewById(R.id.dialog_text_end);
        mainResult = ApplicationController.getInstance().mainResult;
        preDatas = new ArrayList<PreData>();
        PreData preData = new PreData(mainResult.program_data.cure_data.getProgram_name(),mainResult.program_data.cure_data.getProgram_data());
        preDatas = mainResult.program_data.pre_data;
        preDatas.add(preData);

        mViewPager = (SCViewPager) findViewById(R.id.viewpager_main_activity);
        mDotsView = (DotsView) findViewById(R.id.dotsview_main);
        mDotsView.setDotRessource(R.drawable.circle_full, R.drawable.circle_empty);
        mDotsView.setNumberOfPage(preDatas.size());
        mPageAdapter = new MPagerAdapter(getSupportFragmentManager(),preDatas);
        mPageAdapter.setNumberOfPage(preDatas.size());
        mPageAdapter.setFragmentBackgroundColor(R.color.theme_100);
        mViewPager.setAdapter(mPageAdapter);

        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                mDotsView.selectDot(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }


        });
        closeTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        final Point size = SCViewAnimationUtil.getDisplaySize(this);
    }
}