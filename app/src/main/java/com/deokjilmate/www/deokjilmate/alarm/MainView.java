package com.deokjilmate.www.deokjilmate.alarm;

/**
 * Created by 김민경 on 2017-02-03.
 */

public interface MainView {
    void updateStateCheck(String sname,String voteName, boolean state);
    void requestName(String todayAlarmState, String zero_flag, String one_flag,
                     String two_flag, String three_flag);

    void updateNetwork();
}
