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
import com.deokjilmate.www.deokjilmate.home.vote.preVote.PreData;
import com.deokjilmate.www.deokjilmate.home.vote.preVote.PreRecyclerViewAdapter;

import java.util.ArrayList;

/**
 * Created by ash on 2017-02-06.
 * 홈화면 첫번째 탭 투표 현황이 나온다.
 */

public class VoteFragment extends Fragment {
    ArrayList<PreData> mDatas;
    RecyclerView preRecyclerView;
    LinearLayoutManager linearLayoutManager;
    PreRecyclerViewAdapter preRecyclerViewAdapter;
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View frag = inflater.inflate(R.layout.home_tab_vote, container, false);

        return frag;
    }
}
