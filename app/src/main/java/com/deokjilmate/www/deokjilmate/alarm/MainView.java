package com.deokjilmate.www.deokjilmate.alarm;

import java.util.ArrayList;

/**
 * Created by 김민경 on 2017-02-03.
 */

public interface MainView {
    void updateStateCheck(String sname,String voteName, boolean state);
    void requestName(int todayAlarmState,ArrayList<Integer> zero_flag, ArrayList<Integer> one_flag, ArrayList<Integer> two_flag, ArrayList<Integer> three_flag);

    void updateNetwork();
}
