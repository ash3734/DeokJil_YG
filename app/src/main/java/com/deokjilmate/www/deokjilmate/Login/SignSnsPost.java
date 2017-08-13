package com.deokjilmate.www.deokjilmate.Login;

/**
 * Created by 2yg on 2017. 8. 12..
 */

public class SignSnsPost {
    String uid;
    String member_name;
    boolean notSns;

    public SignSnsPost(String uid, String member_name, boolean notSns) {
        this.uid = uid;
        this.member_name = member_name;
        this.notSns = notSns;
    }
}
