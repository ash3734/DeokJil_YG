package com.deokjilmate.www.deokjilmate.home.vote;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.deokjilmate.www.deokjilmate.R;
import com.deokjilmate.www.deokjilmate.application.ApplicationController;
import com.deokjilmate.www.deokjilmate.home.vote.preVote.PreData;
import com.deokjilmate.www.deokjilmate.program.Program;
import com.deokjilmate.www.deokjilmate.program.ProgramFactory;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by ash on 2017-07-15.
 */

public class PageFragment extends Fragment {

    ArrayList<PreData> mDatas;
    private int position;
    private Button button;
    private ImageView imageView;
    private TextView textViewProgramName;
    private TextView textViewPeriod;
    private TextView textViewState;
    private TextView textViewDDay;
    private TextView textViewPeriodDetail;
    private TextView textViewVoteMethod;
    private ImageView imageView2;
    private String programName;
    private Program program;


    public static PageFragment create(ArrayList<PreData> datas, int position) {

        PageFragment fragment = new PageFragment();
        Bundle args = new Bundle();
        args.putSerializable("preDatas", datas);
        args.putInt("position",position);
        fragment.setArguments(args);
        //context = this;
        return fragment;
    }

    @Override
    public void onStart() {
        super.onStart();
        mDatas = (ArrayList<PreData>) getArguments().getSerializable("preDatas");
        position = getArguments().getInt("position");
        String text;
        text = mDatas.get(position).getProgram_name();

        if(position==mDatas.size()-1) {
            text += "\n" + mDatas.get(position).getProgram_data() + " 실시간 투표";
            textViewProgramName.setText(text);
        } else{
            text += "\n" + mDatas.get(position).getProgram_data() + " 사전 투표";
            textViewProgramName.setText(text);
        }
        //진행 예정 진행중 가리기

        // 현재시간을 msec 으로 구한다.
        long now = System.currentTimeMillis();
        // 현재시간을 date 변수에 저장한다.
        Date date = new Date(now);
        // 시간을 나타냇 포맷을 정한다 ( yyyy/MM/dd 같은 형태로 변형 가능 )
        SimpleDateFormat sdfNow = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        // nowDate 변수에 값을 저장한다.
        String formatDate = sdfNow.format(date);
        Log.d("cur",formatDate);
        if(position==mDatas.size()-1){
            textViewState.setText("진행중");
        }else{
            if('7' == formatDate.indexOf(13)) {
                textViewState.setText("진행중");
            }else{
                Log.d("cur", String.valueOf(formatDate.indexOf(13)));
                textViewState.setText("진행예정");
            }
        }

        program= ProgramFactory.create(mDatas.get(position).getProgram_name());
        imageView.setImageResource(program.getImage());
        if(position==mDatas.size()-1) { //실시간 투표
            textViewVoteMethod.setText(program.getCurVoteWay());
        } else{ //사전 투표
            textViewVoteMethod.setText(program.getPreVoteWay());
        }

        //text



        //진행중 = (프로그램 시간일 경우) 진행 예정 = (프로그램 시작할 경우)
        //textViewState


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(position==mDatas.size()-1) { //실시간 투표
                    program.goCurVote(getContext(), ApplicationController.getInstance().mainResult.vote_data.singer_name);
                } else{ //사전 투표
                    program.goPreVote(getContext());
                }

                Log.d("ash3734","govote?");
                button.setText("투표 완료");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                button.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.govote_button));
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.dialog_fragment, container, false);
        textViewProgramName = (TextView) rootView.findViewById(R.id.dialog_textview_program);
        //textViewPeriod = (TextView)rootView.findViewById(R.id.dialog_textview_week);
        textViewState = (TextView)rootView.findViewById(R.id.dialog_textview_state);
        textViewPeriodDetail = (TextView)rootView.findViewById(R.id.dialog_textview_voteday);
        textViewVoteMethod = (TextView)rootView.findViewById(R.id.dialog_textview_voteway);
        button = (Button)rootView.findViewById(R.id.dialog_button_govote);
        imageView = (ImageView)rootView.findViewById(R.id.dialog_imageview);
        return rootView;
    }
}
