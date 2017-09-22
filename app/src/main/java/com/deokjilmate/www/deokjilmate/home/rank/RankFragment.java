package com.deokjilmate.www.deokjilmate.home.rank;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.deokjilmate.www.deokjilmate.R;
import com.deokjilmate.www.deokjilmate.application.ApplicationController;
import com.deokjilmate.www.deokjilmate.home.MainResult;

import java.util.ArrayList;

/**
 * Created by ash on 2017-02-06.
 * 홈화면 두번재 탭 가수 랭크 현황을 표시하기로 한다.
 */

public class RankFragment extends Fragment {
    RecyclerView recyclerView;
    public RequestManager mGlideRequestManager;
    ArrayList<RankData> rankDatas;
    LinearLayoutManager linearLayoutManager;
    RankAdapter rankAdapter;
    ImageView imageViewSinger;
    TextView textViewSingerName;
    TextView textViewAlbumName;
    TextView textViewSong;
    MainResult mainResult;

    @Override
    public void onStart() {
        super.onStart();
        mGlideRequestManager = Glide.with(this);
        mainResult = ApplicationController.getInstance().mainResult;
        /*textViewAlbumName.setText(mainResult.chart_data.album_name);
        textViewSingerName.setText(mainResult.vote_data.singer_name);
        textViewSong.setText(mainResult.chart_data.song_name);
        Glide.with(this).load(mainResult.vote_data.singer_img).into(imageViewSinger);*/
        rankDatas = new ArrayList<RankData>();
        boolean flag=false;
        if(mainResult.chart_data.melonchart.get(0).is_up==1)
            flag=true;
        rankDatas.add(new RankData("멜론",mainResult.chart_data.melonchart.get(0).idx,flag));

        recyclerView.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        rankAdapter = new RankAdapter(rankDatas,mGlideRequestManager);//클릭이벤트, 글라이드도 넣자
        recyclerView.setAdapter(rankAdapter);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View frag = inflater.inflate(R.layout.home_tab_rank, container, false);
        recyclerView= (RecyclerView)(frag.findViewById(R.id.home_fragment_recyclerview_rank));


        return frag;
    }
}
