package com.deokjilmate.www.deokjilmate.alarm;


import java.util.ArrayList;

public class NoticeData {

    public String firebaseToken;
    public int today_alarm; // 오늘의 알림 on,off -> 0,1
    public int member_id;
    public ArrayList<Integer> theshow_flag; // 더쇼
    public ArrayList<Integer> mcount_flag; // 엠카
    public ArrayList<Integer> ingi_flag; // 인기가요
    public ArrayList<Integer> mcore_flag; // 쇼음악중심


    public NoticeData(String firebaseToken, int today_alarm, int member_id, ArrayList<Integer> theshow_flag, ArrayList<Integer> mcount_flag, ArrayList<Integer> ingi_flag, ArrayList<Integer> mcore_flag) {
        this.firebaseToken = firebaseToken;
        this.today_alarm = today_alarm;
        this.member_id = member_id;
        this.theshow_flag = theshow_flag;
        this.mcount_flag = mcount_flag;
        this.ingi_flag = ingi_flag;
        this.mcore_flag = mcore_flag;
    }
}
