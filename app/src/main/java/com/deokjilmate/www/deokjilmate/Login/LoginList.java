package com.deokjilmate.www.deokjilmate.Login;

/**
 * Created by 2yg on 2017. 5. 29..
 */

public class LoginList {
    String singer_name;
    String album_img;
    int choice_count;

    public LoginList(String singer_name, String album_img, int choice_count) {
        this.singer_name = singer_name;
        this.album_img = album_img;
        this.choice_count = choice_count;
    }
}
