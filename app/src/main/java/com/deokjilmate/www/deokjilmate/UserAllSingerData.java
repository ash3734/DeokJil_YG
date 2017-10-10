package com.deokjilmate.www.deokjilmate;

/**
 * Created by 2yg on 2017. 8. 7..
 */

public class UserAllSingerData {
    int singer_id;
    String singer_name;
    String song_name;
    String album_name;
    String singer_img;
    String album_img;
    int choice_count;
    String new_flag;


  //  ArrayList<Integer> alarm_array;

    public String getSinger_name() {
        return singer_name;
    }

    public int getSinger_id() {
        return singer_id;
    }

    public String getSong_name() {
        return song_name;
    }

    public String getAlbum_name() {
        return album_name;
    }

    public String getSinger_img() {
        return singer_img;
    }

    public String getAlbum_img() {
        return album_img;
    }

    public int getChoice_count() {
        return choice_count;
    }

    public String getNew_flag() {
        return new_flag;
    }

//    public ArrayList<Integer> getAlarm_array() {
//        return alarm_array;
//    }

}
