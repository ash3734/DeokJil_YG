package com.deokjilmate.www.deokjilmate.home.vote;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.deokjilmate.www.deokjilmate.home.vote.preVote.PreData;
import com.dev.sacot41.scviewpager.SCViewPagerAdapter;

import java.util.ArrayList;

/**
 * Created by ash on 2017-07-15.
 */


public class MPagerAdapter extends SCViewPagerAdapter {

    ArrayList<PreData> datas;

    MPagerAdapter(FragmentManager fragmentManager, ArrayList<PreData> datas) {
        super(fragmentManager);
        this.datas = datas;
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Fragment getItem(int position) {
        return PageFragment.create(datas,position);
    }

}
