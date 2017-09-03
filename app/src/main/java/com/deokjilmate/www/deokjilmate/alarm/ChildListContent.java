package com.deokjilmate.www.deokjilmate.alarm;

/**
 * Created by 김민경 on 2017-09-03.
 */

public class ChildListContent {
    String mp_name;
    boolean state;

    public ChildListContent(String mp_name, boolean state) {
        this.mp_name = mp_name;
        this.state = state;
    }


    public String getMp_name() {
        return mp_name;
    }

    public void setMp_name(String mp_name) {
        this.mp_name = mp_name;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }
}
