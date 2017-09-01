package com.deokjilmate.www.deokjilmate.MyPage;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ImageView;

import com.deokjilmate.www.deokjilmate.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RankDialog extends Dialog {

    @BindView(R.id.Ranking_close_dialog)
    ImageView closeDialog;

    public RankDialog(Context context) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowManager.LayoutParams lpWindow = new WindowManager.LayoutParams();
        lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        lpWindow.dimAmount = 0.8f;
        getWindow().setAttributes(lpWindow);

        setContentView(R.layout.mypage_rank);
        ButterKnife.bind(this);

    }

    @OnClick(R.id.Ranking_close_dialog)
    public void close(){
        this.dismiss();
    }
}
