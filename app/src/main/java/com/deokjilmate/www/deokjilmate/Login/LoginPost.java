package com.deokjilmate.www.deokjilmate.Login;

/**
 * Created by 2yg on 2017. 5. 29..
 */

public class LoginPost {
    String emailOrtoken;
    String pwdOrtype;


    public LoginPost(String emailOrtoken, String pwdOrtype) {
        this.emailOrtoken = emailOrtoken;
        this.pwdOrtype = pwdOrtype;
    }
}
