package com.deokjilmate.www.deokjilmate.MyPage.AddSinger;

/**
 * Created by 2yg on 2017. 5. 30..
 */

public class SingerAddResponse {
    String data;
    String message;
    boolean result;


    public SingerAddResponse(String data, String message, boolean result) {
        this.data = data;
        this.message = message;
        this.result = result;
    }
}
