package com.deokjilmate.www.deokjilmate.Setting.Notice;

/**
 * Created by 김민경 on 2017-04-03.
 */

public class BoardNoticeData {
    String notice_title;
    String notice_main;
    String notice_time;

    public BoardNoticeData(String notice_title, String notice_main, String notice_time) {
        this.notice_title = notice_title;
        this.notice_main = notice_main;
        this.notice_time = notice_time;
    }
}