package com.deokjilmate.www.deokjilmate.alarm;

/**
 * Created by 김민경 on 2017-02-05.
 */

public class NoticeObject {
    int m_id;
    String name;
    String mp_name;
    String notice;

    public NoticeObject(int m_id, String name, String mp_name, String notice) {
        this.m_id = m_id;
        this.name = name;
        this.mp_name = mp_name;
        this.notice = notice;
    }
}
