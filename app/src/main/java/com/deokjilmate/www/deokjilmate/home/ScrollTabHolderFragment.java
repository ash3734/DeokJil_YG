package com.deokjilmate.www.deokjilmate.home;

import android.support.v4.app.Fragment;

/**
 * Created by ash on 2017-02-05.
 */

public abstract class ScrollTabHolderFragment extends Fragment implements ScrollTabHolder {

    protected ScrollTabHolder mScrollTabHolder;

    public void setScrollTabHolder(ScrollTabHolder scrollTabHolder) {
        mScrollTabHolder = scrollTabHolder;
    }
}
