package com.deokjilmate.www.deokjilmate.home.vote.preVote;

/**
 * Created by ash on 2017-02-07.
 */

public class PreData {
    String programName;
    String programImageUrl;

    public PreData(String programName, String programImageUrl) {
        this.programName = programName;
        this.programImageUrl = programImageUrl;
    }

    public String getProgramName() {
        return programName;
    }

    public void setProgramName(String programName) {
        this.programName = programName;
    }

    public String getProgramImageUrl() {
        return programImageUrl;
    }

    public void setProgramImageUrl(String programImageUrl) {
        this.programImageUrl = programImageUrl;
    }
}
