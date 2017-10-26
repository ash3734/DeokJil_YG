package com.deokjilmate.www.deokjilmate.Setting.Notice;

/**
 * Created by 김민경 on 2017-04-03.
 */

public class BoardNoticeData {
    public int notice_id;
    public String notice_title;
    public String notice_main;

    public BoardNoticeData(int notice_id, String notice_title, String notice_main) {
        this.notice_id = notice_id;
        this.notice_title = notice_title;
        this.notice_main = notice_main;
    }
}
