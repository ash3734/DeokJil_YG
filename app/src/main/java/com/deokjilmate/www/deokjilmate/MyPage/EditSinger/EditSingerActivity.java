package com.deokjilmate.www.deokjilmate.MyPage.EditSinger;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.deokjilmate.www.deokjilmate.CustomDialog;
import com.deokjilmate.www.deokjilmate.MyPage.AddSinger.AddSingerActivity;
import com.deokjilmate.www.deokjilmate.MyPage.AddSinger.SingerAddPost;
import com.deokjilmate.www.deokjilmate.MyPage.AddSinger.SingerAddResponse;
import com.deokjilmate.www.deokjilmate.MyPage.MyPageActivity;
import com.deokjilmate.www.deokjilmate.MyPage.MyPageAllSingerNumbers;
import com.deokjilmate.www.deokjilmate.MyPage.MyPageSelectedSinger;
import com.deokjilmate.www.deokjilmate.R;
import com.deokjilmate.www.deokjilmate.SharedPrefrernceController;
import com.deokjilmate.www.deokjilmate.UserAllSingerData;
import com.deokjilmate.www.deokjilmate.UserDataSumm;
import com.deokjilmate.www.deokjilmate.application.ApplicationController;
import com.deokjilmate.www.deokjilmate.home.MainResult;
import com.deokjilmate.www.deokjilmate.network.NetworkService;
import com.tsengvn.typekit.TypekitContextWrapper;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditSingerActivity extends AppCompatActivity {

    @BindView(R.id.MyPage_EditSinger_backImage)
    ImageButton back;

    @BindView(R.id.MyPage_EditSinger_save)
    ImageButton save;

    @BindView(R.id.MyPage_EditSinger_recycle)
    RecyclerView recyclerView;

    @BindView(R.id.MyPage_EditSinger_addSinger)
    ImageView addSinger;

    private final String TAG = "LOG::EditSingerActivity";


    private LinearLayoutManager linearLayoutManager;
    private RequestManager requestManager;
    private RequestManager requestManagerImage;
    private ArrayList<EditSingerItemData> editSingerItemDatas;//추천목록
    private EditSingerHeadItemData editSingerHeadItemData;
    private EditSingerAdpater editSingerAdpater;
    private NetworkService networkService;
    private ArrayList<MyPageSelectedSinger> myPageEditSelectedSingers;
    private ArrayList<Integer> myAllSingerArrayN;
    private ArrayList<Integer> myAllSingerArray;
    private MyPageAllSingerNumbers myPageAllSingerNumberses;
    private CustomDialog customDialog;

    private ArrayList<UserAllSingerData> userAllSingerDatas;
    private ArrayList<UserDataSumm> userDataSumms;
    private String firebaseToken;
    private int baseTotal;
    private int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mypage_edit_singer);
        ButterKnife.bind(this);
        //Glide.with(this).load(R.drawable.toolbar).into(toolbarImage);
        //Glide.with(this).load(R.drawable.floating_add).into(addSinger);



        networkService = ApplicationController.getInstance().getNetworkService();
        requestManager = Glide.with(this);
        requestManagerImage = Glide.with(this);
        baseTotal = 3;
        recyclerView.setHasFixedSize(true);
        //recyclerView.get
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayout.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        editSingerItemDatas = new ArrayList<EditSingerItemData>();

        myPageAllSingerNumberses = ApplicationController.getInstance().getMyPageAllSingerNumberses();

        //userAllSingerDatas = ApplicationController.getInstance().getUserAllSingerDatas();
        userAllSingerDatas = new ArrayList<UserAllSingerData>();
        userDataSumms = new ArrayList<UserDataSumm>();

        userAllSingerDatas = ApplicationController.getInstance().getUserAllSingerDatas();
        userDataSumms = ApplicationController.getInstance().getUserDataSumms();
        //firebaseToken = ApplicationController.getInstance().getFirebaseToken();
        firebaseToken = SharedPrefrernceController.getFirebaseToken(EditSingerActivity.this);

        //TODO : Edit의 경우 일단 모든 정보는 불러올 필요가 있음. 피엠이 말하길 수정 완료 되면 그대로 머물게 있게 하라고..
        setLists();

//        CustomDialog customDialog = new CustomDialog(this);
//        customDialog.setOnShowListener(new DialogInterface.OnShowListener() {
//            @Override
//            public void onShow(DialogInterface dialogInterface) {
//                Log.v("EditActi", "보여줌");
//            }
//        });
//        customDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
//            @Override
//            public void onDismiss(DialogInterface dialogInterface) {
//                Log.v("EditActi", "사라짐");
//
//            }
//        });
//        customDialog.show();
//        if(customDialog.isClickedState()){
//            Log.v("EditActi", "확인 누름");
//            //customDialog.dismiss();
//        }else{
//            Log.v("EditActi", "취소 누름");
//
//        }



    }

    public void deleteLists(){
            final ArrayList<UserDataSumm> userDataSumms = ApplicationController.getInstance().getUserDataSumms();
            Log.v("토큰", firebaseToken);
            Log.v("전체 길이", String.valueOf(userDataSumms.size()));
            final int listLength = userDataSumms.size();
            baseTotal = 3;
            count = 0;
            if(listLength<4) {
                for (int i = 3; i > (3 - (4 - listLength)); i--) {
                    Call<EditSingerDeleteResponse> deleteSinger = networkService.deleteSinger(new EditSingerDelete(
                            firebaseToken, baseTotal));
                    Log.v("삭제된 num", String.valueOf(baseTotal));

                    //i는 3이 들어 옴.
                    deleteSinger.enqueue(new Callback<EditSingerDeleteResponse>() {
                        @Override
                        public void onResponse(Call<EditSingerDeleteResponse> call, Response<EditSingerDeleteResponse> response) {
                            if (response.body().result) {

                                Log.v("삭제", "삭제 성공");
                                Log.v("삭제된 num1", String.valueOf(baseTotal));

                                Log.v("베이스", String.valueOf(baseTotal));
                                count++;
                                Log.v("카운트", String.valueOf(count));
                                Log.v("카운트베이스", String.valueOf(count+baseTotal));
                                //해당 아이디에 맞는 애를 마이페이지에 추가
                                if(baseTotal+count==3){
                                    Log.v("길이", String.valueOf(listLength));
                                    Log.v("들어옴", "들어옴");
                                    addLists();

                                }

                            } else {
                                Log.v("삭제", "삭제 실패");
                            }
                        }

                        @Override
                        public void onFailure(Call<EditSingerDeleteResponse> call, Throwable t) {
                            Log.e("삭제", "통신 실패");
                        }
                    });
                    baseTotal--;
                }
            }
            else{
                addLists();
            }
    }

    public void addLists(){
        count = 0;
        final ArrayList<UserDataSumm> userDataSumms = ApplicationController.getInstance().getUserDataSumms();
        for(int i = 0; i<userDataSumms.size(); i++)
        {
            Call<SingerAddResponse> addSinger = networkService.addSinger(new SingerAddPost(i,
                    userDataSumms.get(i).getSinger_id(), firebaseToken));
            Log.v(String.valueOf(i)+"번째", String.valueOf(userDataSumms.get(i).getSinger_id()));
            addSinger.enqueue(new Callback<SingerAddResponse>() {
                @Override
                public void onResponse(Call<SingerAddResponse> call, Response<SingerAddResponse> response) {
                    if (response.body().result) {
                        Log.v("추가", "성공");
                        count++;
                        if(count == userDataSumms.size()){
                            setHomeData(firebaseToken, SharedPrefrernceController.getSelected(EditSingerActivity.this));
                            Toast.makeText(getApplicationContext(), "수정 완료", Toast.LENGTH_LONG).show();
                        }
                        //해당 아이디에 맞는 애를 마이페이지에 추가
                    } else {
                        Log.v("추가", "실패");
                    }
                }
                @Override
                public void onFailure(Call<SingerAddResponse> call, Throwable t) {
                    Log.v("추가", "통신 실패");
                }
            });
        }
    }

    @OnClick(R.id.MyPage_EditSinger_save)
    public void save()
    {

        //changeLists();
        //TODO : 이 부분이 수정 후 저장하는 부분
       // NetworkThreadEdit networkThreadEdit = new NetworkThreadEdit(networkService);
//        NetworkThreadDelete networkThreadDelete = new NetworkThreadDelete(networkService, firebaseToken);
//        //deleteSinger();
//
//        networkThreadDelete.start();
//        Log.v("EditAct", "수정중...");
//        try {
//            networkThreadDelete.join(500);
//
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//        NetworkThreadEdit networkThreadEdit = new NetworkThreadEdit(networkService, firebaseToken);
//        networkThreadEdit.start();
//
//        Log.v("EditAct", "수정 완료...");
          deleteLists();

//        finish();
//        startActivity(getIntent());
        // setLists();
    }

    @OnClick(R.id.MyPage_EditSinger_backImage)
    public void clickBack()
    {
        String content = "만약 저장을 누르지 않으시면 원래 상태로 돌아갑니다 \n돌아가시겠습니까";
        customDialog = new CustomDialog(this, content, leftListener, rightListener);
        customDialog.show();

    }

    private View.OnClickListener leftListener = new View.OnClickListener() {
        public void onClick(View v) {
            //startActivity(new Intent(getApplicationContext(), HomeActivity.class));
            Intent intent = new Intent(getApplicationContext(), MyPageActivity.class);
            startActivity(intent);
            finish();
            customDialog.dismiss();
            //setSingerActivity.SetComplete(ApplicationController.getInstance().getNumberSingerSet().get(temp_name));

        }
    };

    private View.OnClickListener rightListener = new View.OnClickListener() {
        public void onClick(View v) {
            //arSrc.remove(position);
            //arSrc.add(position, new ItemData(temp_singer, temp_name, R.drawable.popup_heart));

            // SingerAdapter adapter = new SingerAdapter(dataSet, clickEvent, dataSet2);
            //recyclerView.setAdapter(SingerAdapter.this);
            customDialog.dismiss();
            // mCustomDialog.
        }
    };

    @OnClick(R.id.MyPage_EditSinger_addSinger)
    public void clickAdd()
    {
        Intent intent = new Intent(getApplicationContext(), AddSingerActivity.class);
        startActivity(intent);
    }

    public void setLists(){
        for(int i = 0; i<userDataSumms.size(); i++)
        {
            if(i==0 && userDataSumms.get(0)!=null)
            {//myAllSingerArray.indexOf(myAllSingerArray.get(0))
                editSingerHeadItemData = new EditSingerHeadItemData(userDataSumms.get(i).getSinger_img(),
                        userDataSumms.get(i).getSinger_name(), R.drawable.chgasu_sub, R.drawable.chgasu_x);
            }
            else
            {
                editSingerItemDatas.add(new EditSingerItemData(userDataSumms.get(i).getSinger_img(),
                        userDataSumms.get(i).getSinger_name(), R.drawable.chgasu_main, R.drawable.chgasu_x));
            }
        }
        editSingerAdpater = new EditSingerAdpater(requestManagerImage, requestManager, editSingerItemDatas, editSingerHeadItemData);
        recyclerView.setAdapter(editSingerAdpater);
    }

    public void deleteSinger(){
//        for(int i = 0; i<ApplicationController.getInstance().getDeleteList().size(); i++) {
//            Call<Void> deleteSinger = networkService.deleteSinger(new EditSingerDelete(
//                    1, firebaseToken));
//
//            deleteSinger.enqueue(new Callback<Void>() {
//                @Override
//                public void onResponse(Call<Void> call, Response<Void> response) {
//                    if (response.isSuccessful()) {
//                        Log.v(TAG, "삭제 성공");
//                        //해당 아이디에 맞는 애를 마이페이지에 추가
//                    } else {
//                        Log.v(TAG, "삭제 실패");
//                    }
//                }
//
//                @Override
//                public void onFailure(Call<Void> call, Throwable t) {
//                    Log.e(TAG, "통신 실패");
//                }
//            });
//        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }

    public void setHomeData(final String firebaseToken, int singer_id){
        Call<MainResult> requestMainResult = networkService.requestMain(firebaseToken,singer_id);

        requestMainResult.enqueue(new Callback<MainResult>() {
            @Override
            public void onResponse(Call<MainResult> call, Response<MainResult> response) {
                if(response.body().result){
                    Log.v("EditActivity", "퍼스트");
                    ApplicationController.getInstance().setMainResult(response.body());

                }else{
                    Log.v("여기로 뜸", "여기로 뜸");
                    Toast toast = Toast.makeText(getApplicationContext(), response.message(), Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
            }

            @Override
            public void onFailure(Call<MainResult> call, Throwable t) {
                Toast toast = Toast.makeText(getApplicationContext(), "네트워크 상태를 확인하세요", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
        });
    }

}