package com.deokjilmate.www.deokjilmate.MyPage;

/**
 * Created by 2yg on 2017. 3. 24..
 */

public class MyPageAllSingerNumbers {
    public int getSingerb_id() {
        return singerb_id;
    }

    public void setSingerb_id(int singerb_id) {
        this.singerb_id = singerb_id;
    }

    public int getSinger0_id() {
        return singer0_id;
    }

    public void setSinger0_id(int singer0_id) {
        this.singer0_id = singer0_id;
    }

    public int getSinger1_id() {
        return singer1_id;
    }

    public void setSinger1_id(int singer1_id) {
        this.singer1_id = singer1_id;
    }

    public int getSinger2_id() {
        return singer2_id;
    }

    public void setSinger2_id(int singer2_id) {
        this.singer2_id = singer2_id;
    }

    public int getSinger3_id() {
        return singer3_id;
    }

    public void setSinger3_id(int singer3_id) {
        this.singer3_id = singer3_id;
    }

    int singerb_id;
    int singer0_id;
    int singer1_id;
    int singer2_id;
    int singer3_id;
    //순서대로 메인 서브1~4

    public MyPageAllSingerNumbers(int singerb_id, int singer0_id, int singer1_id, int singer2_id, int singer3_id) {
        this.singerb_id = singerb_id;
        this.singer0_id = singer0_id;
        this.singer1_id = singer1_id;
        this.singer2_id = singer2_id;
        this.singer3_id = singer3_id;
    }



}
