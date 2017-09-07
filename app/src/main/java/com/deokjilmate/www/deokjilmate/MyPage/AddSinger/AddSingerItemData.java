package com.deokjilmate.www.deokjilmate.MyPage.AddSinger;

/**
 * Created by dldud on 2017-02-17.
 */

public class AddSingerItemData {


    int singer_rank;
    int singer_id;

    String singer_image;


    // int singer;
    //보통 이미지의 리소스는 인트로 저장.
    String singer_name;
    int add_singer;



    int cancel_add;


    public AddSingerItemData(int singer_rank, int singer_id, String singer_image, String singer_name,
                             int add_singer, int cancel_add) {
        // this.singer_rank = singer_rank;
        this.singer_rank = singer_rank;
        this.singer_id = singer_id;
        this.singer_image = singer_image;
        this.singer_name = singer_name;
        this.add_singer = add_singer;
        this.cancel_add = cancel_add;
    }

    public int getSinger_id() {
        return singer_id;
    }

    public String getSinger_image() {
        return singer_image;
    }

    public String getSinger_name() {
        return singer_name;
    }

    public int getAdd_singer() {
        return add_singer;
    }
    //list에 있어야 할 것은 가수 이미지, 가수 이름, most설정 이미지(T/F)이 네 가지만 있으면 된다.
    public int getSinger_rank() {
        return singer_rank;
    }

    public int getCancel_add() {
        return cancel_add;
    }

}
