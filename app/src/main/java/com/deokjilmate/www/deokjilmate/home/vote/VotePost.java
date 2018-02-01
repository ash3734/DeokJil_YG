package com.deokjilmate.www.deokjilmate.home.vote;

/**
 * Created by 2yg on 2018. 2. 1..
 */

public class VotePost {
    String firebaseToken;
    String singerNum;

    public VotePost(String firebaseToken, String singerNum){
        this.firebaseToken = firebaseToken;
        this.singerNum = singerNum;
    }
}
