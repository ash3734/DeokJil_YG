package com.deokjilmate.www.deokjilmate.home.rank;

/**
 * Created by ash on 2017-07-26.
 */

public class RankData {
    String chartName;
    String rank;
    int isUp;

    public RankData(String chartName, String rank, int isUp) {
        this.chartName = chartName;
        this.rank = rank;
        this.isUp = isUp;
    }
}
