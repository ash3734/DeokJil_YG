package com.deokjilmate.www.deokjilmate.home.vote;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.deokjilmate.www.deokjilmate.R;
import com.deokjilmate.www.deokjilmate.application.ApplicationController;
import com.deokjilmate.www.deokjilmate.home.MainResult;
import com.deokjilmate.www.deokjilmate.home.vote.preVote.PreData;
import com.deokjilmate.www.deokjilmate.home.vote.preVote.PreRecyclerViewAdapter;
import com.deokjilmate.www.deokjilmate.program.Program;
import com.deokjilmate.www.deokjilmate.program.ProgramFactory;

import java.util.ArrayList;

/**
 * Created by ash on 2017-02-06.
 * 홈화면 첫번째 탭 투표 현황이 나온다.
 */

public class VoteFragment extends Fragment {

    TextView textViewsingerName;
    ImageView imageViewSinger;
    //RelativeLayout relativeLayoutSinger;
    ImageView imageViewBage;
    TextView textViewFanCount;
    TextView textViewVoteCount;
    ImageView imageViewCurProgram;
    TextView textViewCurProgramName;
    TextView textViewCurProgramDate;
    RecyclerView preRecyclerView;
    ArrayList<PreData> preDatas;
    TextView textViewCurNull;
    TextView textViewProcessNull;
    MainResult mainResult;
    LinearLayoutManager preLinearLayoutManager;
    PreRecyclerViewAdapter preRecyclerViewAdapter;
    Program program;
    RelativeLayout noProcessVoteRelative;

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onStart() {
        super.onStart();
        mainResult = ApplicationController.getInstance().mainResult;
        preDatas = new ArrayList<PreData>();
        preDatas = mainResult.program_data.pre_data;
        preRecyclerView.setHasFixedSize(true);
        preLinearLayoutManager = new LinearLayoutManager(getActivity());
        preLinearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        preRecyclerView.setLayoutManager(preLinearLayoutManager);
        preRecyclerViewAdapter = new PreRecyclerViewAdapter(preDatas, clickEvent);//클릭이벤트, 글라이드도 넣자
        preRecyclerView.setAdapter(preRecyclerViewAdapter);
        textViewsingerName.setText(mainResult.vote_data.singer_name);
        Glide.with(this).load(mainResult.vote_data.singer_img).into(imageViewSinger);
     /*   Glide.with(this).load(mainResult.vote_data.singer_img).diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(new ViewTarget<RelativeLayout, GlideDrawable>(relativeLayoutSinger) {

                    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                    @Override
                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                        RelativeLayout myView  = this.view;
                        myView.setBackground(resource);
                    }
                });*/
        textViewFanCount.setText("팬 | " + mainResult.vote_data.b_vote_count);
        textViewVoteCount.setText("투표 | " + mainResult.vote_data.choice_count);
        if (mainResult.vote_data.b_vote_count < 10)
            imageViewBage.setImageResource(R.drawable.badge_muggle);
        else if (10 <= mainResult.vote_data.b_vote_count && mainResult.vote_data.b_vote_count < 100)
            imageViewBage.setImageResource(R.drawable.badge_newbie);
        else if (100 <= mainResult.vote_data.b_vote_count && mainResult.vote_data.b_vote_count < 500)
            imageViewBage.setImageResource(R.drawable.badge_ilco);
        else if (500 <= mainResult.vote_data.b_vote_count && mainResult.vote_data.b_vote_count < 1000)
            imageViewBage.setImageResource(R.drawable.badge_duckwho);
        else
            imageViewBage.setImageResource(R.drawable.badge_sungduck);

        if ((mainResult.program_data.cure_data != null) && (!mainResult.program_data.pre_data.isEmpty())) {
            textViewCurProgramName.setText(mainResult.program_data.cure_data.getProgram_name());
            textViewCurProgramDate.setText(mainResult.program_data.cure_data.getProgram_data());
            //mainResult.program_data.cure_data

            program = ProgramFactory.create(mainResult.program_data.cure_data.getProgram_name());
            //// TODO: 2017-08-14 리펙토링 해야함 스멜이나는 지역
            if (mainResult.program_data
                    .cure_data.getProgram_name().equals("")) {
                textViewCurNull.setText("진행중인 투표가 없습니다.");
            } else {
                imageViewCurProgram.setImageResource(program.getImage());
            }


            imageViewCurProgram.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), DialogActivity.class);
                    intent.putExtra("curVote", true);
                    startActivity(intent);
                }
            });

        }else{
            noProcessVoteRelative.setVisibility(View.VISIBLE);
            textViewCurNull.setVisibility(View.VISIBLE);
            preRecyclerView.setVisibility(View.GONE);
        }
    }

    public View.OnClickListener clickEvent = new View.OnClickListener() {
        public void onClick(View v) {
            int itemPosition = preRecyclerView.getChildLayoutPosition(v);  //포지션 값 넘겨줘서 첫 페이지 설정 ?? 가능???
            Intent intent = new Intent(v.getContext(), DialogActivity.class);
            intent.putExtra("curPage",itemPosition);
            startActivity(intent);
        }
    };



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View frag = inflater.inflate(R.layout.home_tab_vote, container, false);
        preRecyclerView= (RecyclerView)(frag.findViewById(R.id.home_fragment_recyclerview_prevote));
        textViewsingerName = (TextView)(frag.findViewById(R.id.home_fragment_textview_singer_name));
        imageViewSinger = (ImageView)(frag.findViewById(R.id.home_fragment_imageview_singer));
       // relativeLayoutSinger = (RelativeLayout)(frag.findViewById(R.id.home_fragment_imageview_singer));
        textViewFanCount = (TextView)(frag.findViewById(R.id.home_fragment_textview_fan_count));
        textViewVoteCount = (TextView)(frag.findViewById(R.id.home_fragment_textview_vote_count));
        imageViewCurProgram = (ImageView)(frag.findViewById(R.id.home_list_imageview_cur_program));

        textViewCurProgramName = (TextView)(frag.findViewById(R.id.home_list_textview_cur_program_name));
        textViewCurProgramDate = (TextView)(frag.findViewById(R.id.home_list_textview_cur_program_date));
        textViewCurNull = (TextView)(frag.findViewById(R.id.home_fragment_textview_no_cur));
        textViewProcessNull = (TextView)(frag.findViewById(R.id.home_fragment_textview_no_process));
        imageViewBage = (ImageView)(frag.findViewById(R.id.home_fragment_imageview_bage));
        noProcessVoteRelative = (RelativeLayout)(frag.findViewById(R.id.home_fragment_no_process));
        return frag;
    }
/*    public void setLayoutSize(){
        DisplayMetrics dm = getApplicationContext().getResources().getDisplayMetrics();
        int width = dm.widthPixels;
        int height = width*2/3;
        imageViewSinger.setMinimumHeight(height);
    }*/
}
