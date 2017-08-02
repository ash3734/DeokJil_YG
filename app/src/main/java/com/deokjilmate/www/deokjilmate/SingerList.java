package com.deokjilmate.www.deokjilmate;

import java.util.HashMap;

/**
 * Created by 2yg on 2017. 5. 22..
 */

public class SingerList {
    public static HashMap<String, String> singerPNData = new HashMap<String, String>();


    public static HashMap<String, String> getList(){
        singerPNData.put("빅뱅", "BIGBANG");
        //  singerPNData.put("악동뮤지션", "악동뮤지션");
        singerPNData.put("엑소", "EXO");
        singerPNData.put("신화", "SHINWHA");
        singerPNData.put("젝스키스", "젝스키스");
        singerPNData.put("라붐", "LABOOM");
        singerPNData.put("모모랜드", "MOMOLAND");

        //        singerPNData.put("투피엠", "2PM");
//        singerPNData.put("에이오에이", "AOA");
//        singerPNData.put("비원에이포", "B1A4");
//        singerPNData.put("블랙핑크", "BLACKPINK");
//        singerPNData.put("씨엔블루", "CNBLUE");
//        singerPNData.put("엑소", "EXO");
//        singerPNData.put("이엑스아이디", "EXID");
//        singerPNData.put("트와이스", "TWICE");
//        singerPNData.put("위너", "WINNER");
//        singerPNData.put("갓세븐", "GOT7");
//        singerPNData.put("2PM", "투피엠");
//        singerPNData.put("AOA", "에이오에이");
//        singerPNData.put("B1A4", "비원에이포");
//        singerPNData.put("BLACKPINK", "블랙핑크");
//        singerPNData.put("CNBLUE", "씨엔블루");
//        singerPNData.put("EXO", "엑소");
//        singerPNData.put("EXID", "이엑스아이디");
//        singerPNData.put("TWICE", "트와이스");
//        singerPNData.put("WINNER", "위너");
//        singerPNData.put("GOT7", "갓세븐");
//        singerPNData.put("방탄소년단", "방탄소년단");
//        singerPNData.put("걸스데이", "걸스데이");
//        singerPNData.put("소녀시대", "소녀시대");
//        singerPNData.put("비투비", "비투비");
//        singerPNData.put("악동뮤지션", "악동뮤지션");
//        singerPNData.put("에이핑크", "에이핑크");

        return singerPNData;
    }

}
