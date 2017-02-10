package com.deokjilmate.www.deokjilmate.home.rank;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.deokjilmate.www.deokjilmate.R;

/**
 * Created by ash on 2017-02-06.
 * 홈화면 두번재 탭 가수 랭크 현황을 표시하기로 한다.
 */

public class RankFragment extends Fragment {
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View frag = inflater.inflate(R.layout.home_tab_rank, container, false);

        return frag;
    }
}
