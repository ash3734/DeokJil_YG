package com.deokjilmate.www.deokjilmate.Login;

/**
 * Created by 2yg on 2017. 5. 15..
 */

public class TempBody {
    String member_email;
    String member_passwd;
    String member_name;

    public TempBody(String member_email, String member_passwd, String member_name) {
        this.member_email = member_email;
        this.member_passwd = member_passwd;
        this.member_name = member_name;
    }
}
