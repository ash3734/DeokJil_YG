package com.deokjilmate.www.deokjilmate.MyPage.EditSinger;

/**
 * Created by dldud on 2017-02-19.
 */

public class EditSingerItemData {
    String singer_Image;//TODO : 나중에 타입 바꾸기.
    String singer_Name;
    int set_Main;
    int delete_Singer;


    public EditSingerItemData(String singer_Image, String singer_Name,  int set_Main, int delete_Singer) {
        this.delete_Singer = delete_Singer;
        this.set_Main = set_Main;
        this.singer_Image = singer_Image;
        this.singer_Name = singer_Name;
    }
}
