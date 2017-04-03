package com.deokjilmate.www.deokjilmate.MyPage;

/**
 * Created by 2yg on 2017. 3. 24..
 */

public class MyPageSelectedSinger {
    public int getSinger_id() {
        return singer_id;
    }

    public String getSinger_name() {
        return singer_name;
    }

    public String getSinger_img() {
        return singer_img;
    }

    public int getChoice_count() {
        return choice_count;
    }

    int singer_id;
    String singer_name;
    String singer_img;
    int choice_count;

    public MyPageSelectedSinger(int singer_id, String singer_name, String singer_img, int choice_count) {
        this.singer_id = singer_id;
        this.singer_name = singer_name;
        this.singer_img = singer_img;
        this.choice_count = choice_count;
    }
}
