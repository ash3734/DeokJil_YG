package com.deokjilmate.www.deokjilmate.Setting.Inquiry;

/**
 * Created by 김민경 on 2017-04-10.
 */

public class InquiryObject {
    String firebaseToken;
    String questions_title;
    String questions_main;
    String questions_mail;


    public InquiryObject(String firebaseToken, String questions_title, String questions_main, String questions_mail) {
        this.firebaseToken = firebaseToken;
        this.questions_title = questions_title;
        this.questions_main = questions_main;
        this.questions_mail = questions_mail;
    }
}
