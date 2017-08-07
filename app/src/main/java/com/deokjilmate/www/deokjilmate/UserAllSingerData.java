package com.deokjilmate.www.deokjilmate;

/**
 * Created by 2yg on 2017. 8. 7..
 */

public class UserAllSingerData {
    int singer_id;
    String song_name;
    String album_name;
    String singer_img;
    String album_img;
    int choich_count;
    boolean new_flag;

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

    public int getChoich_count() {
        return choich_count;
    }

    public boolean isNew_flag() {
        return new_flag;
    }
}
