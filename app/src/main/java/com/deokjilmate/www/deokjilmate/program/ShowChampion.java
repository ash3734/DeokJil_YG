package com.deokjilmate.www.deokjilmate.program;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.util.Log;

import com.deokjilmate.www.deokjilmate.R;
import com.deokjilmate.www.deokjilmate.application.ApplicationController;

import java.util.List;

/**
 * Created by ash on 2017-08-13.
 */

public class ShowChampion implements Program{
    public static final String name = "쇼챔피언";
    public static final String preVoteWay = "아이돌챔프 앱에서 투표";
    public static final String preVoteTime= "매주 화요일 00:00~일요일 23:59";
    public static final int iamge= R.drawable.showchampion;

    //todo 아이돌 챔프 url 찾기
    public static final String idolUrl = "com.nwz.ichampclient";
    public void goVote(Context context,String singerName){
        Context appContext = ApplicationController.getInstance().getContext();
        if (getPackageList(appContext)){
            Intent intent = appContext.getPackageManager().getLaunchIntentForPackage(idolUrl);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            appContext.startActivity(intent);
        }else{
            String url = "market://details?id=" + idolUrl;
            Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            appContext.startActivity(i);
        }

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
                if(mApps.get(i).activityInfo.packageName.startsWith(idolUrl)){
                    isExist = true;
                    Log.d("ash3734","isExist?");
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
