package com.deokjilmate.www.deokjilmate;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CustomDialog extends Dialog {

    @BindView(R.id.btn_left)
    TextView btn_left;

    @BindView(R.id.btn_right)
    TextView btn_right;

    @BindView(R.id.dlg_content)
    TextView content;

    private View.OnClickListener mLeftClickListener;
    private View.OnClickListener mRightClickListener;

    private boolean clickedState = false;
    private String mContent;
    private String rightText;
    private String leftText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowManager.LayoutParams lpWindow = new WindowManager.LayoutParams();
        lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        lpWindow.dimAmount = 0.8f;
        getWindow().setAttributes(lpWindow);

        setContentView(R.layout.custom_dialog);
        ButterKnife.bind(this);

        btn_left.setText(leftText);
        btn_right.setText(rightText);

        if (mLeftClickListener != null && mRightClickListener != null) {
            btn_left.setOnClickListener(mLeftClickListener);
            btn_right.setOnClickListener(mRightClickListener);
        } else if (mLeftClickListener != null
                && mRightClickListener == null) {
            btn_left.setOnClickListener(mLeftClickListener);
        } else {

        }

        content.setText(mContent);
    }
    public CustomDialog(Context context,
                                String content, View.OnClickListener leftListener,
                                View.OnClickListener rightListener, String left, String right) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.mContent = content;
        this.mLeftClickListener = leftListener;
        this.mRightClickListener = rightListener;
        this.rightText = right;
        this.leftText = left;
    }

}
