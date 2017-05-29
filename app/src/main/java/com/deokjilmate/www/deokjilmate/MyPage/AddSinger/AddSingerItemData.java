package com.deokjilmate.www.deokjilmate.MyPage.AddSinger;

/**
 * Created by dldud on 2017-02-17.
 */

public class AddSingerItemData {
    String singer_rank;
    String singer_image;


    // int singer;
    //보통 이미지의 리소스는 인트로 저장.
    String singer_name;
    String add_singer;

    public AddSingerItemData(String singer_rank, String singer_image, String singer_name, String add_singer) {
        this.singer_rank = singer_rank;
        this.singer_image = singer_image;
        this.singer_name = singer_name;
        this.add_singer = add_singer;
    }
    //list에 있어야 할 것은 가수 이미지, 가수 이름, most설정 이미지(T/F)이 네 가지만 있으면 된다.


}
