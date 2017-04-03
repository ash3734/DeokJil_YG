package com.deokjilmate.www.deokjilmate.MyPage;

/**
 * Created by dldud on 2017-02-14.
 */

public class MyPageItemData {
    String singer_Image;
    int rank_Image;
    String singer_name;
    int choice_count;
    //서버 딴에서 받을 때는 숫자로 받고 이것을 문자열로 캐스팅해야 함.


    //이미지들은 나중에 string으로 변경할 것.
    public MyPageItemData(String singer_Image, int rack_Image, String singer_name, int choice_count) {
        this.singer_Image = singer_Image;
        this.rank_Image = rack_Image;
        this.singer_name = singer_name;
        this.choice_count = choice_count;
    }
}
