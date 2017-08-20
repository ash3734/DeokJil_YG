package com.deokjilmate.www.deokjilmate.alarm;


import java.util.ArrayList;

public class NoticeData {

    public String firebaseToken;
    //public int today_alarm; // 오늘의 알림 on,off -> 0,1
    //public int member_id;
    public ArrayList<Integer> zero_flag;
    public ArrayList<Integer> one_flag;
    public ArrayList<Integer> two_flag;
    public ArrayList<Integer> three_flag;


    public NoticeData(ArrayList<Integer> zero_flag, ArrayList<Integer> one_flag, ArrayList<Integer> two_flag, ArrayList<Integer> three_flag) {
        this.zero_flag = zero_flag;
        this.one_flag = one_flag;
        this.two_flag = two_flag;
        this.three_flag = three_flag;
    }
}
