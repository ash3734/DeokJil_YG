package com.deokjilmate.www.deokjilmate.alarm;

/**
 * Created by 김민경 on 2017-08-20.
 */

public class NoticePostData {

    String firebaseToken;
    String fcm_token;
    String today_alarm;
    String zero_flag;
    String one_flag;
    String two_flag;
    String three_flag;
    public NoticePostData(String firebaseToken, String fcm_token, String today_alarm, String zero_flag, String one_flag, String two_flag, String three_flag) {
        this.firebaseToken = firebaseToken;
        this.fcm_token = fcm_token;
        this.today_alarm = today_alarm;
        this.zero_flag = zero_flag;
        this.one_flag = one_flag;
        this.two_flag = two_flag;
        this.three_flag = three_flag;
    }

}
