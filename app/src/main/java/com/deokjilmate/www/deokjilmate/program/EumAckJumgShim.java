package com.deokjilmate.www.deokjilmate.program;

import android.content.Context;
import android.telephony.SmsManager;
import android.widget.Toast;

/**
 * Created by ash on 2017-08-13.
 */

public class EumAckJumgShim implements Program{
    public static final String name = "음악중심";
    public static final String curVoteWay = "문자투표 #0505로 '후보번호(숫자)' 혹은 '가수이름'을 적어서 전송\n";
    public static final String curVoteTime = "오후 6";

    public void goVote(Context context,String singerName) {

        String phoneNum = "01030577991"; //#05050
        String message = singerName;

        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(phoneNum, null, message, null, null);

        Toast.makeText(context, "투표가 완료되었습니다.",
                Toast.LENGTH_SHORT).show();


    }
}
