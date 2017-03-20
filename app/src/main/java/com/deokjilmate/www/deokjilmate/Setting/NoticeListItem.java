package com.deokjilmate.www.deokjilmate.Setting;

/**
 * Created by 김민경 on 2017-02-22.
 */

public class NoticeListItem {
    String title;
    int icon;

    public NoticeListItem(String title, int icon) {
        this.title = title;
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }
}
