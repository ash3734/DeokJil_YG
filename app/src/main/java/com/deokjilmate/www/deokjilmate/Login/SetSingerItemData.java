package com.deokjilmate.www.deokjilmate.Login;

/**
 * Created by dldud on 2017-02-11.
 */

public class SetSingerItemData {


    int singer_id;
    String singer_image;



    // int singer;
    //보통 이미지의 리소스는 인트로 저장.
    String singer_name;
    int singer_most;
    //list에 있어야 할 것은 가수 이미지, 가수 이름, most설정 이미지(T/F)이 네 가지만 있으면 된다.



    public SetSingerItemData(int singer_id, String singer_image,String singer_name, int singer_most) {
        this.singer_id = singer_id;
        this.singer_image = singer_image;
        // this.singer = singer;
        this.singer_name = singer_name;
        this.singer_most = singer_most;
    }
    public int getSinger_id() {
        return singer_id;
    }
    public String getSinger_name() {
        return singer_name;
    }
}
