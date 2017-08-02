package com.deokjilmate.www.deokjilmate.Login;

/**
 * Created by 2yg on 2017. 7. 31..
 */

public class SignPost {
    String uid;
    String member_name;
    boolean notSns;
    String member_email;
    String member_passwd;

    public SignPost(String uid, String member_name, boolean notSns, String member_email, String member_passwd) {
        this.uid = uid;
        this.member_name = member_name;
        this.notSns = notSns;
        this.member_email = member_email;
        this.member_passwd = member_passwd;
    }
}
