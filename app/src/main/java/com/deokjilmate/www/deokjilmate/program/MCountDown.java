package com.deokjilmate.www.deokjilmate.program;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.telephony.SmsManager;
import android.widget.Toast;

import com.deokjilmate.www.deokjilmate.R;

/**
 * Created by ash on 2017-08-13.
 */

public class MCountDown implements Program{
    public static final String name = "엠카운트다운";
    public static final String curVoteWay = "온라인 투표 또는 문자투표(#2336) 가수이름 한글로";
    public static final String curVoteTime= "오후 6";
    public static final String preVoteWay = "엠카운트다운 홈페이지 에서 투표";
    public static final String preVoteTime= "매주 금요일 14:00~월요일 09:00";
    public static final int image = R.drawable.mcountdown;

    public void goVote(Context context,String singerName){
        String phoneNum = "01030577991";
        String message = singerName;

        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(phoneNum, null, message, null, null);

        Toast.makeText(context, "투표가 완료되었습니다.",
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public int getImage() {
        return image;
    }

    @Override
    public String getPreVoteWay() {
        return preVoteWay;
    }

    public void goPreVote(Context context){
        Uri uri = Uri.parse("http://mcountdown.mnet.interest.me/vote/preVote");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        context.startActivity(intent);
    }
}
