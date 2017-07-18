package com.deokjilmate.www.deokjilmate.Setting.Inquiry;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.deokjilmate.www.deokjilmate.R;

/**
 * Created by 김민경 on 2017-07-18.
 */

public class InquiryListFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.inquiry_list_tab_fragment, container, false);
    }
}
