package com.deokjilmate.www.deokjilmate.MyPage.AddSinger;

/**
 * Created by 2yg on 2017. 5. 30..
 */

public class SingerAddPost {
    int singerNum;
    int singer_id;
    String firebasToken;


    public SingerAddPost(int singerNum, int singer_id, String firebasToken) {
        this.singerNum = singerNum;
        this.singer_id = singer_id;
        this.firebasToken = firebasToken;
    }
}
