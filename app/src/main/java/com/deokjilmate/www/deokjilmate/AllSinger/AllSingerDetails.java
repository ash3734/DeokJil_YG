package com.deokjilmate.www.deokjilmate.AllSinger;

/**
 * Created by 2yg on 2017. 3. 24..
 */

public class AllSingerDetails {
    public int getSinger_id() {
        return singer_id;
    }

    public String getSinger_img() {
        return singer_img;
    }

    public String getSinger_name() {
        return singer_name;
    }

    int singer_id;
    String singer_img;
    String singer_name;

    public AllSingerDetails(int singer_id, String singer_img, String singer_name) {
        this.singer_id = singer_id;
        this.singer_img = singer_img;
        this.singer_name = singer_name;
    }
}
