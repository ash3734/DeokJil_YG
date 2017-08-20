package com.deokjilmate.www.deokjilmate;

/**
 * Created by 2yg on 2017. 8. 16..
 */

public class UserDataSumm {
    int singer_id;
    String singer_name;
    String singer_img;

    public UserDataSumm(int singer_id, String singer_name, String singer_img) {
        this.singer_id = singer_id;
        this.singer_name = singer_name;
        this.singer_img = singer_img;
    }

    public int getSinger_id() {
        return singer_id;
    }

    public void setSinger_id(int singer_id) {
        this.singer_id = singer_id;
    }

    public String getSinger_name() {
        return singer_name;
    }

    public void setSinger_name(String singer_name) {
        this.singer_name = singer_name;
    }

    public String getSinger_img() {
        return singer_img;
    }

    public void setSinger_img(String singer_img) {
        this.singer_img = singer_img;
    }
}
