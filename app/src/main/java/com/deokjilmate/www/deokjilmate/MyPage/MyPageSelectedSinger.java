package com.deokjilmate.www.deokjilmate.MyPage;

/**
 * Created by 2yg on 2017. 3. 24..
 */

public class MyPageSelectedSinger {
    int singer_id;
    String singer_name;
    String singer_img;
    String choice_count;

    public MyPageSelectedSinger(int singer_id, String singer_name, String singer_img, String choice_count) {
        this.singer_id = singer_id;
        this.singer_name = singer_name;
        this.singer_img = singer_img;
        this.choice_count = choice_count;
    }
}
