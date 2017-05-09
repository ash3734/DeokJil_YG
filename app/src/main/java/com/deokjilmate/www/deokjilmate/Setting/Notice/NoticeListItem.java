package com.deokjilmate.www.deokjilmate.Setting.Notice;

/**
 * Created by 김민경 on 2017-02-22.
 */

public class NoticeListItem {
    String title;
    String main;
    String time;


    public NoticeListItem(String title, String main, String time) {
        this.title = title;
        this.main = main;
        this.time = time;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMain() {
        return main;
    }

    public void setMain(String main) {
        this.main = main;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
