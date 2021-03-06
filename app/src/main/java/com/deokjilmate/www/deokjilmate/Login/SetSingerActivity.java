package com.deokjilmate.www.deokjilmate.Login;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.deokjilmate.www.deokjilmate.AllSinger.AllSingerDetails;
import com.deokjilmate.www.deokjilmate.AllSinger.AllSingerRanking;
import com.deokjilmate.www.deokjilmate.MyPage.AddSinger.SingerAddPost;
import com.deokjilmate.www.deokjilmate.MyPage.AddSinger.SingerAddResponse;
import com.deokjilmate.www.deokjilmate.R;
import com.deokjilmate.www.deokjilmate.SharedPrefrernceController;
import com.deokjilmate.www.deokjilmate.SingerList;
import com.deokjilmate.www.deokjilmate.UserAllSingerData;
import com.deokjilmate.www.deokjilmate.UserAllSingerResponse;
import com.deokjilmate.www.deokjilmate.UserDataSumm;
import com.deokjilmate.www.deokjilmate.application.ApplicationController;
import com.deokjilmate.www.deokjilmate.home.HomeActivity;
import com.deokjilmate.www.deokjilmate.home.MainResult;
import com.deokjilmate.www.deokjilmate.network.NetworkService;
import com.tsengvn.typekit.TypekitContextWrapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SetSingerActivity extends AppCompatActivity {

    @BindView(R.id.SetSinger_search)
    EditText search;

    @BindView(R.id.SetSinger_backImage)
    ImageButton backButton;

    @BindView(R.id.SetSinger_list)
    RecyclerView recyclerView;

    @BindView(R.id.Setsinger_next)
    Button next;

    @BindView(R.id.SetSinger_listText)
    RelativeLayout listText;

    //RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private RequestManager requestManager;
    private RequestManager requestManagerSel;
    private ArrayList<SetSingerItemData> setSingerItemDatas;//추천목록
    private ArrayList<SetSingerItemData> allSingerList;//전체목록
    private ArrayList<AllSingerDetails> allSingerDetails;


    private SetSingerAdapter setSingerAdapter;
    private HashMap<String, String> singerPNData;

    private NetworkService networkService;
    private String imageUrl = "https://00e9e64bac7c4aab450e3b22d212cabca5cfe7403d011279ee-apidata.googleusercontent.com/download/storage/v1/b/duckmate_1/o/new%2Fhi.png?qk=AD5uMEu5kgUv6YApI3IzolJW8OWW-pQNcREa89fmIeqQc4J4WIUao1JO4A_f2aTkJ91QyoigAf0icnspWaNZmeISXBAzxl3_4uic4kFCLjL-DSnZimW0ZXboYfnPzCW_Vtf2YwncRtzC6eiePWIJTt0o-S2w5oqFaeA3GV9eGhHpkBO04HyDYJGWsGDN2YMrXCs2l-Ds1DZsxgBZHk23B1-HdoaQFJs34fNtHa8BTaA1i9tG3WVWw7VpQx2RxrMSt2F-UowVQNA9sLcTFuOa7ROMi87qMsWCxk0OOE88-8mP_kXJEFSvONS9Ng1dvZtnIS-95OzKGNogYmZ7fgCj88nHAJK5GzNjiB561g_a_iOclET6IVeYtViHFhDiJBLIW6hVGCBIu0TyfzE15ry4TEiuaDZ8ONjLiqPf6CK345B1OMDjhw5sZcu-RnNXS7mDCOdvUiLCTQyr5LPDIG2VPiq8ZtcanyTaibX-eB5z-JZZCPzharQDDsyM4A4OiGKhNt71mFJF-tG4WYHdF3LYtz41R1BhttPiWjBH2yh19th_JjPl7hyhodF8EW5KCj_LYYiahZg8NlPnrjyKk5VmxBDnGA0R1OQkFcBAok6biq1jtmAkcU6XiwdVL0FZtoOsvgFk651C3FZMDRtRgGpDgAWTOnRMBr2P4p_YJfEsliKGRCmAB0LbByNIu3Meso_-EUJS-0mSX_KDqmB7O-C0f2xdihC4XuLTguUZNxG3RNK096gA2QFJMojPCzqZ7pYspn56iK1VaVXJ6ZHVKLYYrnLBBke7qzMaiw";

    private String member_email;
    private String member_passwd;
    private String uid;
    private boolean notSns;
    private String member_name;
    private int type;
    private String login_type;
    private ProgressDialog progressDialog;

    private ArrayList<UserAllSingerData> userAllSingerDatas;
    private ArrayList<UserDataSumm> userDataSumms;
    private ArrayList<UserDataSumm> preUserDataSumms;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            //Drawable background = this.getResources().getDrawable(R.drawable.gradation);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(this.getResources().getColor(R.color.statusbar));
            //window.setNavigationBarColor(this.getResources().getColor(R.color.tw__transparent));
            //window.setBackgroundDrawable(background);

        }


        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_set_singer);
        ButterKnife.bind(this);
        Glide.with(this).load(R.drawable.topbar_back).into(backButton);
        getValues();
        ApplicationController.getInstance().setMost(-1);
        recyclerView.setHasFixedSize(true);
        //recyclerView.get
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayout.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        networkService = ApplicationController.getInstance().getNetworkService();

        requestManager = Glide.with(this);
        requestManagerSel = Glide.with(this);
        singerPNData = new HashMap<>();


        setSingerItemDatas = new ArrayList<SetSingerItemData>();
        allSingerList = new ArrayList<SetSingerItemData>();
        allSingerDetails = new ArrayList<AllSingerDetails>();
        progressDialog = new ProgressDialog(this);

        Call<AllSingerRanking> setSingerRankingCall = networkService.setSingerRanking();
        setSingerRankingCall.enqueue(new Callback<AllSingerRanking>() {
            @Override
            public void onResponse(Call<AllSingerRanking> call, Response<AllSingerRanking> response) {
                if(response.isSuccessful())
                {
                    allSingerDetails = response.body().data;
                    for(int i = 0; i<allSingerDetails.size(); i++)
                    {
                        setSingerItemDatas.add(new SetSingerItemData(allSingerDetails.get(i).getSinger_id(), allSingerDetails.get(i).getSinger_img(),
                                allSingerDetails.get(i).getSinger_name(), R.drawable.my_maingasu));
                        allSingerList.add(new SetSingerItemData(allSingerDetails.get(i).getSinger_id(), allSingerDetails.get(i).getSinger_img(),
                                allSingerDetails.get(i).getSinger_name(), R.drawable.my_maingasu));
                    }
                    setSingerAdapter = new SetSingerAdapter(requestManager, requestManagerSel, setSingerItemDatas, allSingerList, SingerList.getList());
                    recyclerView.setAdapter(setSingerAdapter);
                }
            }
            @Override
            public void onFailure(Call<AllSingerRanking> call, Throwable t) {
            }
        });

        search.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                setSingerAdapter.search = true;
                String text = search.getText().toString().toLowerCase(Locale.getDefault());
                if(text.length() == 0) {
                    listText.setVisibility(View.VISIBLE);
                    setSingerAdapter.search = false;
                }else{
                    listText.setVisibility(View.GONE);

                }
                setSingerAdapter.filter(text);
            }
            public void afterTextChanged(Editable s) {
            }
        });
    }

    @OnClick(R.id.Setsinger_next)
    public void clickNext(){
        if(ApplicationController.getInstance().getMost()<0){
            Toast.makeText(this,"한 명의 메인 가수를 선택해주세요.", Toast.LENGTH_LONG).show();
        }else {
            if (login_type.equals("c")) {
                Call<RegisterResult> singUp = networkService.registerResult(new SignPost(uid, member_name, notSns, member_email, member_passwd));
                singUp.enqueue(new Callback<RegisterResult>() {
                    @Override
                    public void onResponse(Call<RegisterResult> call, Response<RegisterResult> response) {
                        if (response.body().result) {
                            makeDialog("잠시만 기다려주세요");
                            RegisterData registerData = response.body().data;

                            final int most = ApplicationController.getInstance().getMost();

                            SharedPrefrernceController.setMost(SetSingerActivity.this, most);
                            SharedPrefrernceController.setSelected(SetSingerActivity.this, most);

                            final String firebaseToken = response.body().data.firebasToken;
                            SharedPrefrernceController.setUserEmail(SetSingerActivity.this, member_email);
                            SharedPrefrernceController.setPasswd(SetSingerActivity.this, member_passwd);

                            SharedPrefrernceController.setFirebaseToken(SetSingerActivity.this, firebaseToken);
                            SharedPrefrernceController.setLoginType(SetSingerActivity.this, login_type);

                            Log.v("SetSinger", String.valueOf(most));
                            Call<SingerAddResponse> addSinger = networkService.addSinger(new SingerAddPost(0,
                                    most, firebaseToken));
                            addSinger.enqueue(new Callback<SingerAddResponse>() {
                                @Override
                                public void onResponse(Call<SingerAddResponse> call, Response<SingerAddResponse> response) {
                                    if (response.body().result) {
                                        Log.v("추가", "성공");
                                        //여기서 토큰 추가
                                        ApplicationController.getInstance().setFirebaseToken(firebaseToken);
                                        setHomeData(firebaseToken, most);
                                    } else {
                                        Log.v("추가", response.body().message);
                                        Toast.makeText(SetSingerActivity.this, response.body().message, Toast.LENGTH_LONG);
                                    }
                                }

                                @Override
                                public void onFailure(Call<SingerAddResponse> call, Throwable t) {
                                    Log.v("추가", "통신 실패");
                                }
                            });
                        } else {
                            Toast.makeText(getApplicationContext(), "이미 등록된 uid", Toast.LENGTH_LONG);
                        }
                    }

                    @Override
                    public void onFailure(Call<RegisterResult> call, Throwable t) {
                    }
                });
            } else {
                Call<RegisterSnsResult> singUpSns = networkService.registerResultSns(new SignSnsPost(uid, member_name, notSns));
                singUpSns.enqueue(new Callback<RegisterSnsResult>() {
                    @Override
                    public void onResponse(Call<RegisterSnsResult> call, Response<RegisterSnsResult> response) {
                        if (response.body().result) {
                            makeDialog("잠시만 기다려주세요");
                            //RegisterData registerData = response.body().data;
                            final int most = ApplicationController.getInstance().getMost();
                            SharedPrefrernceController.setMost(SetSingerActivity.this, most);
                            SharedPrefrernceController.setSelected(SetSingerActivity.this, most);

                            final String firebaseToken = response.body().data;
                            Log.v("SetSinger", firebaseToken);

                            SharedPrefrernceController.setFirebaseToken(SetSingerActivity.this, firebaseToken);
                            SharedPrefrernceController.setLoginType(SetSingerActivity.this, login_type);

                            Log.v("SetSinger", String.valueOf(most));
                            Call<SingerAddResponse> addSinger = networkService.addSinger(new SingerAddPost(0,
                                    most, firebaseToken));
                            addSinger.enqueue(new Callback<SingerAddResponse>() {
                                @Override
                                public void onResponse(Call<SingerAddResponse> call, Response<SingerAddResponse> response) {
                                    if (response.body().result) {
                                        Log.v("SetSinger", "성공");
                                        //여기서 토큰 추가
                                        ApplicationController.getInstance().setFirebaseToken(firebaseToken);
                                        setHomeData(firebaseToken, most);


//
//                                    Intent intent = new Intent(getApplicationContext(), MyPageActivity.class);
//                                    startActivity(intent);
                                    } else {
                                        Log.v("SetSinger", "실패");
                                    }
                                }

                                @Override
                                public void onFailure(Call<SingerAddResponse> call, Throwable t) {
                                    Log.v("추가", "통신 실패");
                                }
                            });
                        }
                    }

                    @Override
                    public void onFailure(Call<RegisterSnsResult> call, Throwable t) {
                    }
                });
            }
        }
    }

    @OnClick(R.id.SetSinger_backImage)
    public void ClickBack()
    {

        Intent intent = new Intent(getApplicationContext(), SetProfileActivity.class);
        switch (type){
            case 1://커스텀 로그인
                intent.putExtra("uid", uid);
                intent.putExtra("member_email", member_email);
                intent.putExtra("notSns", true);
                intent.putExtra("member_passwd", member_passwd);
                intent.putExtra("member_name", member_name);
                break;
            default://sns 로그인
                intent.putExtra("uid", uid);
                intent.putExtra("notSns", false);
                intent.putExtra("member_name", member_name);
                break;
        }
        intent.putExtra("type", type);


        startActivity(intent);
    }

    public void getValues(){
        type = getIntent().getExtras().getInt("type");
        switch (type){
            case 1:
                //notSns
                uid = getIntent().getExtras().getString("uid");
                member_email = getIntent().getExtras().getString("member_email");
                member_passwd = getIntent().getExtras().getString("member_passwd");
                member_name = getIntent().getExtras().getString("member_name");
                notSns = getIntent().getExtras().getBoolean("notSns");
                login_type = "c";//커스텀
                //true
                break;
            case 2:
                //Sns
                uid = getIntent().getExtras().getString("uid");
                member_name = getIntent().getExtras().getString("member_name");
                notSns = getIntent().getExtras().getBoolean("notSns");
                login_type= "f";//페북
                //false
                break;
            case 3:
                uid = getIntent().getExtras().getString("uid");
                member_name = getIntent().getExtras().getString("member_name");
                notSns = getIntent().getExtras().getBoolean("notSns");
                login_type= "t";//트윗
                break;
            case 4:
                uid = getIntent().getExtras().getString("uid");
                member_name = getIntent().getExtras().getString("member_name");
                notSns = getIntent().getExtras().getBoolean("notSns");
                login_type= "g";//구글
                break;
            default:
                break;
        }
        Log.v("SetSinger", uid);
    }

    public void setHomeData(final String firebaseToken, int singer_id){
        Call<MainResult> requestMainResult = networkService.requestMain(firebaseToken,singer_id);

        requestMainResult.enqueue(new Callback<MainResult>() {
            @Override
            public void onResponse(Call<MainResult> call, Response<MainResult> response) {
                if(response.isSuccessful()){
                    ApplicationController.getInstance().setMainResult(response.body());
                    SharedPrefrernceController.setLoginType(SetSingerActivity.this, "s");
                    ApplicationController.getInstance().setLoginState("s");
                    getMySingerDatas();
                    //finish();
                }else{
                    Toast toast = Toast.makeText(getApplicationContext(), response.message(), Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    finish();
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

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }

    public void makeDialog(String message) {
        if (progressDialog != null && !progressDialog.isShowing()) {
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setMessage(message);
            progressDialog.setCancelable(false);
            progressDialog.show();
            //cv_flank.setValueAnimated(fTime, 300);
//            fTime = cv_flank.getDelayMillis();
//            cv_flank.stopSpinning();
        }
    }

    public void getMySingerDatas(){

        userAllSingerDatas = new ArrayList<UserAllSingerData>();
        userDataSumms = new ArrayList<UserDataSumm>();
        preUserDataSumms = new ArrayList<UserDataSumm>();

        final Call<UserAllSingerResponse> userAllSingerResponse = networkService.userAllSinger(SharedPrefrernceController.getFirebaseToken(SetSingerActivity.this));
        userAllSingerResponse.enqueue(new Callback<UserAllSingerResponse>() {
            @Override
            public void onResponse(Call<UserAllSingerResponse> call, Response<UserAllSingerResponse> response) {
                if(response.body().result){
                    userAllSingerDatas = response.body().data;
                    int count = userAllSingerDatas.size();
                    ApplicationController.getInstance().setTotalSingerCount(count);
                    ApplicationController.getInstance().setUserAllSingerDatas(userAllSingerDatas);
                    Log.v("MyPage", userAllSingerDatas.get(0).getSinger_name());
                    Log.v("MyPage", "전체 = " + String.valueOf(userAllSingerDatas.size()));

                    for(int i = 0; i<userAllSingerDatas.size(); i++)
                    {
                        if(i==0 && userAllSingerDatas.get(0)!=null)
                        {//myAllSingerArray.indexOf(myAllSingerArray.get(0))
                            Log.v("MyPage", "메인 들어옴");
                            ApplicationController.getInstance().setMost(userAllSingerDatas.get(i).getSinger_id());
                        }
                        userDataSumms.add(new UserDataSumm(userAllSingerDatas.get(i).getSinger_id(), userAllSingerDatas.get(i).getSinger_name(),
                                userAllSingerDatas.get(i).getSinger_img()));
                    }
                    Log.v("MyPage", "이제 어댑터로");
                    preUserDataSumms.addAll(userDataSumms);
                    ApplicationController.getInstance().setUserDataSumms(userDataSumms);
                    ApplicationController.getInstance().setPreUserDataSumms(preUserDataSumms);

                    if(userDataSumms.size() == userAllSingerDatas.size()){

                                SharedPrefrernceController.setLoginType(SetSingerActivity.this, "l");
                                ApplicationController.getInstance().setLoginState("l");
                                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                                finish();
                    }
                } else{
                    Toast.makeText(SetSingerActivity.this, "정보 불러오는 데에 실패하였습니다", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<UserAllSingerResponse> call, Throwable t) {

            }
        });
    }
}
