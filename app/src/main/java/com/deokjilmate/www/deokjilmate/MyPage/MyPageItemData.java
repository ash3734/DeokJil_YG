package com.deokjilmate.www.deokjilmate.MyPage;

/**
 * Created by dldud on 2017-02-14.
 */

public class MyPageItemData {
    String singer_Image;
    String rank_Image;
    String singer_name;
    String vote_count;
    //서버 딴에서 받을 때는 숫자로 받고 이것을 문자열로 캐스팅해야 함.



    public MyPageItemData(String singer_Image, String singer_name, String vote_count, String rack_Image) {
        this.singer_Image = singer_Image;
        this.rank_Image = rack_Image;
        this.singer_name = singer_name;
        this.vote_count = vote_count;
    }
}
