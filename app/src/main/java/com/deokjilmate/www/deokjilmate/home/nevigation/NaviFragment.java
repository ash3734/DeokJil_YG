package com.deokjilmate.www.deokjilmate.home.nevigation;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.deokjilmate.www.deokjilmate.MyPage.EditSinger.EditSingerActivity;
import com.deokjilmate.www.deokjilmate.Profile.EditProfileActivity;
import com.deokjilmate.www.deokjilmate.R;
import com.deokjilmate.www.deokjilmate.Setting.SettingActivity;
import com.deokjilmate.www.deokjilmate.SharedPrefrernceController;
import com.deokjilmate.www.deokjilmate.UserAllSingerData;
import com.deokjilmate.www.deokjilmate.alarm.AlarmActivity;
import com.deokjilmate.www.deokjilmate.application.ApplicationController;
import com.deokjilmate.www.deokjilmate.home.MainResult;

import java.util.ArrayList;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by ash on 2017-07-30.
 * 네비게이션 바 안에 화면
 */

public class NaviFragment extends Fragment {


    //네비게이션 부분의 위젯들
    ImageView myImage;
    TextView myNickName;
    ImageView myBadge;
    TextView singer1;
    ImageView new1;
    TextView singer2;
    ImageView new2;
    TextView singer3;
    ImageView new3;
    TextView singer4;
    ImageView new4;
    ImageView alarmBtn;
    ImageView settingBtn;
    RelativeLayout relativeLayout1;
    RelativeLayout relativeLayout2;
    RelativeLayout relativeLayout3;
    RelativeLayout relativeLayout4;
    MainResult mainResult;

    ImageView singerChange;

    private ArrayList<UserAllSingerData> userAllSingerDatas;

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onStart() {
        super.onStart();
        mainResult = new MainResult();
        mainResult = ApplicationController.getInstance().getMainResult();

        //네비게이션바 필요할지 몰라서 살려둠
        userAllSingerDatas = new ArrayList<UserAllSingerData>();
        userAllSingerDatas = ApplicationController.getInstance().getUserAllSingerDatas();
        //네비게이션바
        //NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        //navigationView.setNavigationItemSelectedListener(this);
        if (mainResult.nevi_data.member_img != null)
            Glide.with(getActivity()).load(mainResult.nevi_data.member_img).into(myImage);
        if (mainResult.nevi_data.member_name != null)
            myNickName.setText(mainResult.nevi_data.member_name);
        //myBadge = 벳지는 현재 놔둠
        //

        //내코드 이고 영규가 다시 만든 코드는 밑에 일딴 살려둠
  /*      //가수 등록에 따라 출력하기
        if(mainResult.nevi_data.singer.size()==1) {
            singer1.setText(mainResult.nevi_data.singer.get(0).singer_name);
            new1.setImageResource(R.drawable.menu_new);
            relativeLayout1.setBackgroundColor(getContext().getResources().getColor(R.color.navi_singer_bg));
        } else if(mainResult.nevi_data.singer.size()==2){
            singer1.setText(mainResult.nevi_data.singer.get(0).singer_name);
            new1.setImageResource(R.drawable.menu_new);
            singer2.setText(mainResult.nevi_data.singer.get(1).singer_name);
            new2.setImageResource(R.drawable.menu_new);
            relativeLayout1.setBackgroundColor(getContext().getResources().getColor(R.color.navi_singer_bg));
            relativeLayout2.setBackgroundColor(getContext().getResources().getColor(R.color.navi_singer_bg));
        }else if(mainResult.nevi_data.singer.size()==3){
            singer1.setText(mainResult.nevi_data.singer.get(0).singer_name);
            new1.setImageResource(R.drawable.menu_new);
            singer2.setText(mainResult.nevi_data.singer.get(1).singer_name);
            new2.setImageResource(R.drawable.menu_new);
            singer3.setText(mainResult.nevi_data.singer.get(2).singer_name);
            new3.setImageResource(R.drawable.menu_new);
            relativeLayout1.setBackgroundColor(getContext().getResources().getColor(R.color.navi_singer_bg));
            relativeLayout2.setBackgroundColor(getContext().getResources().getColor(R.color.navi_singer_bg));
            relativeLayout3.setBackgroundColor(getContext().getResources().getColor(R.color.navi_singer_bg));
        }else if(mainResult.nevi_data.singer.size()==4){
            singer1.setText(mainResult.nevi_data.singer.get(0).singer_name);
            new1.setImageResource(R.drawable.menu_new);
            singer2.setText(mainResult.nevi_data.singer.get(1).singer_name);
            new2.setImageResource(R.drawable.menu_new);
            singer3.setText(mainResult.nevi_data.singer.get(2).singer_name);
            new3.setImageResource(R.drawable.menu_new);
            singer4.setText(mainResult.nevi_data.singer.get(3).singer_name);
            new4.setImageResource(R.drawable.menu_new);
            relativeLayout1.setBackgroundColor(getContext().getResources().getColor(R.color.navi_singer_bg));
            relativeLayout2.setBackgroundColor(getContext().getResources().getColor(R.color.navi_singer_bg));
            relativeLayout3.setBackgroundColor(getContext().getResources().getColor(R.color.navi_singer_bg));
            relativeLayout4.setBackgroundColor(getContext().getResources().getColor(R.color.navi_singer_bg));
        }*/

//        if(mainResult.nevi_data.member_img!=null)
//        Glide.with(getActivity()).load(mainResult.nevi_data.member_img).into(myImage);
//        if(mainResult.nevi_data.member_name!=null)
//            myNickName.setText(mainResult.nevi_data.member_name);

        init();
        //myBadge =
//        switch (mainResult.nevi_data.singer.size()) {
//            case 4:
//                if (mainResult.nevi_data.singer.get(3).singer_name != null)
//                    singer4.setText(mainResult.nevi_data.singer.get(3).singer_name);
//                if (mainResult.nevi_data.singer.get(3).new_flag != null)
//                    new4.setImageResource(R.drawable.menu_new);
//                relativeLayout4.setBackgroundColor(getContext().getResources().getColor(R.color.navi_singer_bg));
//            case 3:
//                Log.v("3", "3");
//                if (mainResult.nevi_data.singer.get(2).singer_name != null)
//                    singer3.setText(mainResult.nevi_data.singer.get(2).singer_name);
//                if (mainResult.nevi_data.singer.get(2).new_flag != null)
//                    new3.setImageResource(R.drawable.menu_new);
//                relativeLayout3.setBackgroundColor(getContext().getResources().getColor(R.color.navi_singer_bg));
//            case 2:
//                if (mainResult.nevi_data.singer.get(1).singer_name != null)
//                    singer2.setText(mainResult.nevi_data.singer.get(1).singer_name);
//                if (mainResult.nevi_data.singer.get(1).new_flag != null)
//                    new2.setImageResource(R.drawable.menu_new);
//                relativeLayout2.setBackgroundColor(getContext().getResources().getColor(R.color.navi_singer_bg));
//            case 1:
//                if (mainResult.nevi_data.singer.get(0).singer_name != null)
//                    singer1.setText(mainResult.nevi_data.singer.get(0).singer_name);
//                if (mainResult.nevi_data.singer.get(0).new_flag != null)
//                    new1.setImageResource(R.drawable.menu_new);
//                relativeLayout1.setBackgroundColor(getContext().getResources().getColor(R.color.navi_singer_bg));
//                break;
//            default:
//                break;
//        }

        //myBadge =
        if (ApplicationController.getInstance().getLoginState().equals("s")) {
            switch (mainResult.nevi_data.singer.size()) {
                case 4:
                    if (mainResult.nevi_data.singer.get(3).singer_name != null)
                        singer4.setText(mainResult.nevi_data.singer.get(3).singer_name);
                    if (mainResult.nevi_data.singer.get(3).new_flag != null)
                        new4.setImageResource(R.drawable.menu_new);
                    relativeLayout4.setBackgroundColor(getContext().getResources().getColor(R.color.navi_singer_bg));
                case 3:
                    Log.v("3", "3");
                    if (mainResult.nevi_data.singer.get(2).singer_name != null)
                        singer3.setText(mainResult.nevi_data.singer.get(2).singer_name);
                    if (mainResult.nevi_data.singer.get(2).new_flag != null)
                        new3.setImageResource(R.drawable.menu_new);
                    relativeLayout3.setBackgroundColor(getContext().getResources().getColor(R.color.navi_singer_bg));
                case 2:
                    if (mainResult.nevi_data.singer.get(1).singer_name != null)
                        singer2.setText(mainResult.nevi_data.singer.get(1).singer_name);
                    if (mainResult.nevi_data.singer.get(1).new_flag != null)
                        new2.setImageResource(R.drawable.menu_new);
                    relativeLayout2.setBackgroundColor(getContext().getResources().getColor(R.color.navi_singer_bg));
                case 1:
                    if (mainResult.nevi_data.singer.get(0).singer_name != null)
                        singer1.setText(mainResult.nevi_data.singer.get(0).singer_name);
                    if (mainResult.nevi_data.singer.get(0).new_flag != null)
                        new1.setImageResource(R.drawable.menu_new);
                    relativeLayout1.setBackgroundColor(getContext().getResources().getColor(R.color.navi_singer_bg));
                    break;
                default:
                    break;
            }
        } else {
            switch (userAllSingerDatas.size()) {
                case 4:
                    if (userAllSingerDatas.get(3).getSinger_name() != null)
                        singer4.setText(userAllSingerDatas.get(3).getSinger_name());
                    if (userAllSingerDatas.get(3).getNew_flag() != null)
                        new4.setImageResource(R.drawable.menu_new);
                    relativeLayout4.setBackgroundColor(getContext().getResources().getColor(R.color.navi_singer_bg));
                case 3:
                    Log.v("3", "3");
                    if (userAllSingerDatas.get(2).getSinger_name() != null)
                        singer3.setText(userAllSingerDatas.get(2).getSinger_name());
                    if (userAllSingerDatas.get(2).getNew_flag() != null)
                        new3.setImageResource(R.drawable.menu_new);
                    relativeLayout3.setBackgroundColor(getContext().getResources().getColor(R.color.navi_singer_bg));
                case 2:
                    if (userAllSingerDatas.get(1).getSinger_name() != null)
                        singer2.setText(userAllSingerDatas.get(1).getSinger_name());
                    if (userAllSingerDatas.get(1).getNew_flag() != null)
                        new2.setImageResource(R.drawable.menu_new);
                    relativeLayout2.setBackgroundColor(getContext().getResources().getColor(R.color.navi_singer_bg));
                case 1:
                    if (userAllSingerDatas.get(0).getSinger_name() != null)
                        singer1.setText(userAllSingerDatas.get(0).getSinger_name());
                    if (userAllSingerDatas.get(0).getNew_flag() != null)
                        new1.setImageResource(R.drawable.menu_new);
                    relativeLayout1.setBackgroundColor(getContext().getResources().getColor(R.color.navi_singer_bg));
                    break;
                default:
                    break;
            }
        }


//        if(mainResult.nevi_data.singer.get(0).singer_name!=null)
//            singer1.setText(mainResult.nevi_data.singer.get(0).singer_name);
//        if(mainResult.nevi_data.singer.get(0).new_flag!=null)
//            new1.setImageResource(R.drawable.menu_new);
//        if(mainResult.nevi_data.singer.get(1).singer_name!=null)
//            singer2.setText(mainResult.nevi_data.singer.get(1).singer_name);
//        if(mainResult.nevi_data.singer.get(1).new_flag!=null)
//            new2.setImageResource(R.drawable.menu_new);
//        if(mainResult.nevi_data.singer.get(2).singer_name!=null)
//            singer3.setText(mainResult.nevi_data.singer.get(2).singer_name);
//        if(mainResult.nevi_data.singer.get(2).new_flag!=null)
//            new3.setImageResource(R.drawable.menu_new);
//        if(mainResult.nevi_data.singer.get(3).singer_name!=null)
//            singer4.setText(mainResult.nevi_data.singer.get(3).singer_name);
//        if(mainResult.nevi_data.singer.get(3).new_flag!=null)
//            new4.setImageResource(R.drawable.menu_new);

        relativeLayout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApplicationController.getInstance().setSinger_id(ApplicationController.getInstance().getMost());
                //SharedPrefrernceController.get
                //SharedPrefrernceController.setSelected(getActivity(), SharedPrefrernceController.getMost(getActivity()));

                startActivity(new Intent(getActivity(), ProgressDialogActivity.class));
                getActivity().finish();


                //drawer.closeDrawer(GravityCompat.START);
            }
        });
        relativeLayout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ApplicationController.getInstance().singer_id = 2;
                if (ApplicationController.getInstance().getUserDataSumms().size() >= 2) {
                    ApplicationController.getInstance().setSinger_id(ApplicationController.getInstance().getUserDataSumms().get(1).getSinger_id());
                    startActivity(new Intent(getActivity(), ProgressDialogActivity.class));
                    getActivity().finish();
                }

                //drawer.closeDrawer(GravityCompat.START);
            }
        });
        relativeLayout3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ApplicationController.getInstance().singer_id = 3;
                if (ApplicationController.getInstance().getUserDataSumms().size() >= 3) {
                    ApplicationController.getInstance().setSinger_id(ApplicationController.getInstance().getUserDataSumms().get(2).getSinger_id());
                    startActivity(new Intent(getActivity(), ProgressDialogActivity.class));
                    getActivity().finish();
                }

                //drawer.closeDrawer(GravityCompat.START);
            }
        });
        //이부분 이용해서 추가하기
        relativeLayout4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ApplicationController.getInstance().singer_id = 4;
                if (ApplicationController.getInstance().getUserDataSumms().size() >= 4) {
                    ApplicationController.getInstance().setSinger_id(ApplicationController.getInstance().getUserDataSumms().get(3).getSinger_id());
                    startActivity(new Intent(getActivity(), ProgressDialogActivity.class));
                    getActivity().finish();
                }

                //drawer.closeDrawer(GravityCompat.START);
            }
        });

        myBadge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), EditProfileActivity.class));
            }
        });

        singerChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), EditSingerActivity.class));
                ApplicationController.getInstance().setFromHome(true);
            }
        });

        settingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), SettingActivity.class));

            }
        });

        alarmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), AlarmActivity.class));

            }
        });

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View frag = inflater.inflate(R.layout.navi_fragment, container, false);
        //네비게이션바 xml묶기
        myImage = (ImageView) frag.findViewById(R.id.drawer_myimage);
        myNickName = (TextView) frag.findViewById(R.id.drawer_nickname);
        myBadge = (ImageView) frag.findViewById(R.id.drawer_badge);
        singer1 = (TextView) frag.findViewById(R.id.drawer_singer_1);
        new1 = (ImageView) frag.findViewById(R.id.drawer_new_1);
        singer2 = (TextView) frag.findViewById(R.id.drawer_singer_2);
        new2 = (ImageView) frag.findViewById(R.id.drawer_new_2);
        singer3 = (TextView) frag.findViewById(R.id.drawer_singer_3);
        new3 = (ImageView) frag.findViewById(R.id.drawer_new_3);
        singer4 = (TextView) frag.findViewById(R.id.drawer_singer_4);
        new4 = (ImageView) frag.findViewById(R.id.drawer_new_4);
        //allVoteBtn = (ImageView)frag.findViewById(R.id.drawer_all_vote_btn);
        alarmBtn = (ImageView) frag.findViewById(R.id.drawer_alarm_btn);
        settingBtn = (ImageView) frag.findViewById(R.id.drawer_setting_btn);

        relativeLayout1 = (RelativeLayout) frag.findViewById(R.id.drawer_layout_1);
        relativeLayout2 = (RelativeLayout) frag.findViewById(R.id.drawer_layout_2);
        relativeLayout3 = (RelativeLayout) frag.findViewById(R.id.drawer_layout_3);
        relativeLayout4 = (RelativeLayout) frag.findViewById(R.id.drawer_layout_4);

        singerChange = (ImageView) frag.findViewById(R.id.drawer_go_singer_change);

        return frag;
    }

    public void init() {
        Uri data;
        // data = ApplicationController.getInstance().getProfile_uri();
        data = Uri.parse(SharedPrefrernceController.getUserImage(getActivity()));

        //  data = SharedPrefrernceController.getUserImage(getActivity());
        Glide.with(myImage.getContext())
                .load(data)
                .bitmapTransform(new CropCircleTransformation(getActivity().getApplicationContext()))
                .into(myImage);

        String name = SharedPrefrernceController.getUserNickname(getActivity());
        myNickName.setText(name);

    }

}
