package com.deokjilmate.www.deokjilmate.Setting.Inquiry;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by 김민경 on 2017-07-18.
 */

public class InquiryTabPagerAdapter extends FragmentStatePagerAdapter {
    //Count number of tabs
    private int tabCount;

    public InquiryTabPagerAdapter(FragmentManager fm, int tabCount) {
        super(fm);
        this.tabCount = tabCount;
    }

    @Override
    public Fragment getItem(int position) {
        // Returning the current tabs
        switch (position) {
            case 0:
                InquiryFragment tabFragment1 = new InquiryFragment();
                return tabFragment1;
            case 1:
                InquiryListFragment tabFragment2 = new InquiryListFragment();
                return tabFragment2;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}
