package com.deokjilmate.www.deokjilmate.program;

import android.content.Context;

/**
 * Created by ash on 2017-08-16.
 */

public interface Program {
    public void goCurVote(Context context, String singerName);
    public void goPreVote(Context context);
    public int getImage();
    public String getPreVoteWay();
    public String getCurVoteWay();
}
