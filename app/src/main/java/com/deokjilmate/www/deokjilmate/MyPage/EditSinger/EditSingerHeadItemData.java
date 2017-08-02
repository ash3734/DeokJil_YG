package com.deokjilmate.www.deokjilmate.MyPage.EditSinger;

/**
 * Created by 2yg on 2017. 3. 15..
 */

public class EditSingerHeadItemData {
    String singer_Image;//TODO : 나중에 타입 바꾸기.(Strin으로)
    String singer_Name;
    int set_Sub;
    int delete_Singer;


    public EditSingerHeadItemData(String singer_Image, String singer_Name,  int set_Sub, int delete_Singer) {
        this.delete_Singer = delete_Singer;
        this.set_Sub = set_Sub;
        this.singer_Image = singer_Image;
        this.singer_Name = singer_Name;
    }
}
