package com.deokjilmate.www.deokjilmate.home;

import android.widget.AbsListView;

/**
 * Created by ash on 2017-02-05.
 */

public interface ScrollTabHolder {

    void adjustScroll(int scrollHeight);

    void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount, int pagePosition);
}
