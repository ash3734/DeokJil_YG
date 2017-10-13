package com.deokjilmate.www.deokjilmate.alarm;


import java.util.ArrayList;

public class NoticeData {

    ArrayList<ArrayList<Integer>> zero_flag;
    ArrayList<ArrayList<Integer>> one_flag;
    ArrayList<ArrayList<Integer>> two_flag;
    ArrayList<ArrayList<Integer>> three_flag;
    String today_alarm; // 오늘의 알림 on,off -> 0,1


    public NoticeData(ArrayList<ArrayList<Integer>> zero_flag, ArrayList<ArrayList<Integer>> one_flag,
                      ArrayList<ArrayList<Integer>> two_flag, ArrayList<ArrayList<Integer>> three_flag, String today_alarm) {
        this.zero_flag = zero_flag;
        this.one_flag = one_flag;
        this.two_flag = two_flag;
        this.three_flag = three_flag;
        this.today_alarm = today_alarm;
    }
}
