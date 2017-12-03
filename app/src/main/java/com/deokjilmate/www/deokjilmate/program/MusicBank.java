package com.deokjilmate.www.deokjilmate.program;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import com.deokjilmate.www.deokjilmate.R;

/**
 * Created by ash on 2017-08-13.
 */

public class MusicBank implements Program {
    public static final String name = "뮤직뱅크";
    public static final String preVoteWay = "KBS 국민패널 대중가요 선호도 조사(http://survey.kbs.co.kr)";
    public static final String preVoteTime= "매주 금요일 09:00~수요일 09:30";
    public static final int image = R.drawable.musicbank;


    @Override
    public void goCurVote(Context context, String singerName) {
        Log.d("ash3734","실시간투표 존재하지 않음");
    }

    @Override
    public void goPreVote(Context context) {
        Uri uri = Uri.parse("http://survey.kbs.co.kr");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        context.startActivity(intent);
    }

    @Override
    public int getImage() {
        return image;
    }

    @Override
    public String getPreVoteWay() {
        return preVoteWay;
    }

    @Override
    public String getCurVoteWay() {
        Log.d("ash3734","실시간투표 존재하지 않음");
        return null;
    }

}
