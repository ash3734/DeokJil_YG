package com.deokjilmate.www.deokjilmate.program;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.util.Log;

import com.deokjilmate.www.deokjilmate.R;

import java.util.List;

/**
 * Created by ash on 2017-08-13.
 */

public class Ingigayo implements Program{
    public static final String name = "인기가요";
    public static final String preVoteWay = "멜론 아지톡 앱에서 투표\n";
    public static final String preVoteTime= "매주 월요일 12:00~토요일 23:59";
    public static final int image = R.drawable.ingigayo;
    //todo 이지톡 url 찾기
    public static final String melonUrl = "com.iloen.aztalk";
    @Override
    public void goCurVote(Context context, String singerName) {
        Log.d("ash3734","실시간투표 존재하지 않음");
    }

    @Override
    public void goPreVote(Context context) {
        if (getPackageList(context)){
            Intent intent = context.getPackageManager().getLaunchIntentForPackage(melonUrl);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }else{
            String url = "market://details?id=" + melonUrl;
            Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            context.startActivity(i);
        }
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

    public boolean getPackageList(Context context) {
        boolean isExist = false;

        PackageManager pkgMgr = context.getPackageManager();
        List<ResolveInfo> mApps;
        Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        mApps = pkgMgr.queryIntentActivities(mainIntent, 0);

        try {
            for (int i = 0; i < mApps.size(); i++) {
                if(mApps.get(i).activityInfo.packageName.startsWith(melonUrl)){
                    isExist = true;
                    break;
                }
            }
        }
        catch (Exception e) {
            isExist = false;
        }
        return isExist;
    }

}
