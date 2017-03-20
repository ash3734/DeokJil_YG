package com.deokjilmate.www.deokjilmate.Login;

import java.util.HashMap;

/**
 * Created by dldud on 2017-02-26.
 */

public class SetSingerNameData {
    HashMap<String, String> singerPNData = new HashMap<>();
    //발음이 key, 실제 이름이 value.
    public SetSingerNameData(HashMap<String, String> singerPNData)
    {
        this.singerPNData = singerPNData;
    }
    public HashMap<String, String> saveData()
    {
        singerPNData.put("투피엠", "2PM");
        singerPNData.put("에이오에이", "AOA");
        singerPNData.put("비원에이포", "B1A4");
        singerPNData.put("블랙핑크", "BLACKPINK");
        singerPNData.put("씨엘씨", "CLC");
        singerPNData.put("씨엔블루", "CNBLUE");
        singerPNData.put("엑소", "EXO");
        singerPNData.put("이엑스아이디", "EXID");
        singerPNData.put("트와이스", "TWICE");
        singerPNData.put("위너", "WINNER");


        return singerPNData;
    }


}
