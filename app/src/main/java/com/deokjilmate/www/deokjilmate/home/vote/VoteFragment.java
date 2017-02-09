package com.deokjilmate.www.deokjilmate.home.vote;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.deokjilmate.www.deokjilmate.R;
import com.deokjilmate.www.deokjilmate.home.vote.curVote.CurData;
import com.deokjilmate.www.deokjilmate.home.vote.curVote.CurRecyclerViewAdapter;
import com.deokjilmate.www.deokjilmate.home.vote.preVote.PreData;
import com.deokjilmate.www.deokjilmate.home.vote.preVote.PreRecyclerViewAdapter;

import java.util.ArrayList;

import butterknife.ButterKnife;

/**
 * Created by ash on 2017-02-06.
 * 홈화면 첫번째 탭 투표 현황이 나온다.
 */

public class VoteFragment extends Fragment {

    RecyclerView curRecyclerView;
    RecyclerView preRecyclerView;
    ArrayList<PreData> preDatas;
    ArrayList<CurData> curDatas;

    LinearLayoutManager curLinearLayoutManager;
    LinearLayoutManager preLinearLayoutManager;
    PreRecyclerViewAdapter preRecyclerViewAdapter;
    CurRecyclerViewAdapter curRecyclerViewAdapter;
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onStart() {
        super.onStart();
        ButterKnife.bind(getActivity());
        preDatas = new ArrayList<PreData>();
        curDatas = new ArrayList<CurData>();
        //// TODO: 2017-02-08 레스로핏을 이용한 서버 연동해 arraylist에 담자
        preDatas.add(new PreData("뮤뱅","1"));
        preDatas.add(new PreData("인기가요","1"));
        preDatas.add(new PreData("엠카운다운","1"));
        preDatas.add(new PreData("또 뭐있지??","1"));
        preDatas.add(new PreData("글쎄???","1"));
        //각 item의 크기가 일정할 경우 고정
        preRecyclerView.setHasFixedSize(true);
        curRecyclerView.setHasFixedSize(true);

        //layoutManager 설정
        curLinearLayoutManager = new LinearLayoutManager(getActivity());
        curLinearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        preLinearLayoutManager = new LinearLayoutManager(getActivity());
        preLinearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        curRecyclerView.setLayoutManager(curLinearLayoutManager);
        preRecyclerView.setLayoutManager(preLinearLayoutManager);
        curRecyclerViewAdapter = new CurRecyclerViewAdapter(curDatas);//클릭이벤트, 글라이드도 넣자
        preRecyclerViewAdapter = new PreRecyclerViewAdapter(preDatas);//클릭이벤트, 글라이드도 넣자
        curRecyclerView.setAdapter(curRecyclerViewAdapter);
        preRecyclerView.setAdapter(preRecyclerViewAdapter);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View frag = inflater.inflate(R.layout.home_tab_vote, container, false);
        curRecyclerView= (RecyclerView)(frag.findViewById(R.id.home_fragment_recyclerview_curvote));
        preRecyclerView= (RecyclerView)(frag.findViewById(R.id.home_fragment_recyclerview_prevote));
        return frag;
    }
}
