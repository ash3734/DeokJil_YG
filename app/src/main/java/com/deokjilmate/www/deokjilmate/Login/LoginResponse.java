package com.deokjilmate.www.deokjilmate.Login;

/**
 * Created by 2yg on 2017. 5. 29..
 */

public class LoginResponse {
    int b_vote_count;
    int member_id;
    LoginList singer_info;

    public LoginResponse(int b_vote_count, int member_id, LoginList singer_info) {
        this.b_vote_count = b_vote_count;
        this.member_id = member_id;
        this.singer_info = singer_info;
    }
}
