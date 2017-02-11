package com.deokjilmate.www.deokjilmate.Setting;

import android.view.LayoutInflater;

import java.util.ArrayList;

/**
 * Created by 김민경 on 2017-02-11.
 */

public class SettingAdapter {

    ArrayList<SettingListItem> datas;
    LayoutInflater inflater;

    public SettingAdapter(LayoutInflater inflater, ArrayList<SettingListItem> datas) {
        this.datas= datas;
        this.inflater= inflater;
    }




}
