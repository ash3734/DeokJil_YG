package com.deokjilmate.www.deokjilmate;

/**
 * Created by 2yg on 2017. 8. 23..
 */

public class UserSingerList {
    public int getSinger_id() {
        return singer_id;
    }

    public void setSinger_id(int singer_id) {
        this.singer_id = singer_id;
    }

    private int singer_id;

    public UserSingerList(int singer_id) {
        this.singer_id = singer_id;
    }
}
