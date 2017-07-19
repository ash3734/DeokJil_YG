package com.deokjilmate.www.deokjilmate.Setting.Inquiry;

/**
 * Created by 김민경 on 2017-04-10.
 */

public class InquiryObject {
    int member_id;
    String questions_title;
    String questions_main;
    String questions_mail;


    public InquiryObject(int member_id, String questions_title, String questions_main, String questions_mail) {
        this.member_id = member_id;
        this.questions_title = questions_title;
        this.questions_main = questions_main;
        this.questions_mail = questions_mail;
    }
}
