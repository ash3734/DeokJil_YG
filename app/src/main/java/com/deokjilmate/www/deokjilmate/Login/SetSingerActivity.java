package com.deokjilmate.www.deokjilmate.Login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.deokjilmate.www.deokjilmate.AllSinger.AllSingerDetails;
import com.deokjilmate.www.deokjilmate.AllSinger.AllSingerRanking;
import com.deokjilmate.www.deokjilmate.R;
import com.deokjilmate.www.deokjilmate.SingerList;
import com.deokjilmate.www.deokjilmate.application.ApplicationController;
import com.deokjilmate.www.deokjilmate.home.HomeActivity;
import com.deokjilmate.www.deokjilmate.network.NetworkService;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_set_singer);
        ButterKnife.bind(this);
        Glide.with(this).load(R.drawable.topbar_back).into(backButton);
        //getValues();
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

        Call<AllSingerRanking> setSingerRankingCall = networkService.setSingerRanking();
        setSingerRankingCall.enqueue(new Callback<AllSingerRanking>() {
            @Override
            public void onResponse(Call<AllSingerRanking> call, Response<AllSingerRanking> response) {
                if(response.isSuccessful())
                {
                    allSingerDetails = response.body().result;
                    for(int i = 0; i<allSingerDetails.size(); i++)
                    {
                        setSingerItemDatas.add(new SetSingerItemData(allSingerDetails.get(i).getSinger_img(),
                                allSingerDetails.get(i).getSinger_name(), R.drawable.sign_addgasu));
                        allSingerList.add(new SetSingerItemData(allSingerDetails.get(i).getSinger_img(),
                                allSingerDetails.get(i).getSinger_name(), R.drawable.sign_addgasu));
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
                    setSingerAdapter.search = false;
                }
                setSingerAdapter.filter(text);
            }
            public void afterTextChanged(Editable s) {
            }
        });
    }

    @OnClick(R.id.Setsinger_next)
    public void clickNext(){








        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.SetSinger_backImage)
    public void ClickBack()
    {
        Intent intent = new Intent(getApplicationContext(), SetProfileActivity.class);
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
                //true
                break;
            case 2:
                //Sns
                uid = getIntent().getExtras().getString("uid");
                member_name = getIntent().getExtras().getString("member_name");
                notSns = getIntent().getExtras().getBoolean("notSns");
                //false
                break;
            default:
                break;
        }
    }
}
