package com.deokjilmate.www.deokjilmate.alarm;

import java.util.ArrayList;

/**
 * Created by 김민경 on 2017-08-20.
 */

public class NoticePostData {
    String firebaseToken;
    String fcm_token;
    Integer today_alarm;
    ArrayList<Integer> zero_flag;
    ArrayList<Integer> one_flag;
    ArrayList<Integer> two_flag;
    ArrayList<Integer> three_flag;

    public NoticePostData(String firebaseToken, String fcm_token, Integer today_alarm, ArrayList<Integer> zero_flag, ArrayList<Integer> one_flag, ArrayList<Integer> two_flag, ArrayList<Integer> three_flag) {
        this.firebaseToken = firebaseToken;
        this.fcm_token = fcm_token;
        this.today_alarm = today_alarm;
        this.zero_flag = zero_flag;
        this.one_flag = one_flag;
        this.two_flag = two_flag;
        this.three_flag = three_flag;
    }
}
