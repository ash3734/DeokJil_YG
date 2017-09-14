package com.deokjilmate.www.deokjilmate.MyPage.AddSinger;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.RequestManager;
import com.deokjilmate.www.deokjilmate.CustomDialog;
import com.deokjilmate.www.deokjilmate.Login.SetSingerNameData;
import com.deokjilmate.www.deokjilmate.MyPage.MyPageAllSingerNumbers;
import com.deokjilmate.www.deokjilmate.R;
import com.deokjilmate.www.deokjilmate.UserDataSumm;
import com.deokjilmate.www.deokjilmate.application.ApplicationController;
import com.deokjilmate.www.deokjilmate.network.NetworkService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

import static android.view.View.GONE;
import static com.bumptech.glide.gifdecoder.GifHeaderParser.TAG;

/**
 * Created by dldud on 2017-02-17.
 */

public class AddsingerAdapter extends RecyclerView.Adapter<AddSingerViewHolder>{


    RequestManager requestManager;
    ArrayList<AddSingerItemData> addSingerItemDatas;//이건 추천목록
    ArrayList<AddSingerItemData> allSingerList;//이건 전체 가수 목록
    ArrayList<AddSingerItemData> searchSingerList;//실제 보여줄 것.
    HashMap<String, String> singerPNData = new HashMap<String, String>();
    NetworkService networkService;
    SetSingerNameData setSingerNameData;
    private MyPageAllSingerNumbers myPageAllSingerNumberses;
    private Context addSingerActivity;
    private int totalSingerCount;
    private int selectSingerNum;
    private String firebaseToken;
    public boolean search = false;
    private ArrayList<UserDataSumm> userDataSumms;
    private Context context;
    private CustomDialog customDialog;
    private int clickedPosition = -1;
    private int mainSinger = -1;




    public AddsingerAdapter(Context addSingerActivity, RequestManager requestManager, ArrayList<AddSingerItemData> allSingerList,
                            HashMap<String, String> singerPNData, NetworkService networkService, String firebaseToken,
                            ArrayList<AddSingerItemData> addSingerItemDatas, Context context) {
        this.requestManager = requestManager;
        this.addSingerItemDatas = addSingerItemDatas;//이건 추천목록
        this.searchSingerList = allSingerList;
        this.allSingerList = new ArrayList<AddSingerItemData>();
        this.allSingerList.addAll(allSingerList);
        Log.v("전체", String.valueOf(this.allSingerList.size()));

        this.addSingerActivity = addSingerActivity;
        //this.singerPNData = new HashMap<String, String>();
        this.singerPNData.putAll(singerPNData);
        this.networkService = networkService;
        this.myPageAllSingerNumberses = ApplicationController.getInstance().getMyPageAllSingerNumberses();
        this.totalSingerCount = ApplicationController.getInstance().getTotalSingerCount();
        this.firebaseToken = firebaseToken;
        this.userDataSumms = new ArrayList<UserDataSumm>();
        this.userDataSumms = ApplicationController.getInstance().getUserDataSumms();
        this.mainSinger = userDataSumms.get(0).getSinger_id();
        this.context = context;
        setHasStableIds(true);

    }

    @Override
    public AddSingerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.mypage_add_singer_recycle, parent,false);

        //화면상에 보여줄 레이아웃을 지정해주어야 함.
        AddSingerViewHolder viewHolder = new AddSingerViewHolder(itemView);
        //itemView.setOnClickListener(clickEvent);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final AddSingerViewHolder holder, final int position) {


        if(search == false) {
            holder.singer_rank.setText(String.valueOf(addSingerItemDatas.get(position).getSinger_rank()));
            requestManager.load(addSingerItemDatas.get(position).singer_image).into(holder.singer_Image);
            holder.singer_Name.setText(addSingerItemDatas.get(position).singer_name);

            //이거 기본 세팅
            if (!checkHave(addSingerItemDatas.get(position).singer_id)) {
                holder.add_singer.setImageResource(addSingerItemDatas.get(position).add_singer);
            } else{
                if(addSingerItemDatas.get(position).singer_id == mainSinger) {
                    Log.v("포지션",  String.valueOf(position));
                    holder.add_singer.setVisibility(GONE);
                }
                else
                    holder.add_singer.setImageResource(addSingerItemDatas.get(position).cancel_add);
            }

            //이건 클릭했을 때
            holder.add_singer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    selectSingerNum = addSingerItemDatas.get(position).singer_id;
                    Log.v("넘버", String.valueOf(selectSingerNum));
                    Log.v("넘버", String.valueOf(totalSingerCount));
                    //;
                    if (checkHave(selectSingerNum)) {
                        //Log.v("내 가수들", myPageAllSingerNumberses.toString());
                        //TODO : 여기서 삭제 여부를 물어봐야 함.
                        clickedPosition = position;
                        deleteCheck();
                        Log.v("EditAdap", "이미 있음");
                    } else {
                        if (userDataSumms.size() < 4) {//전체 가수 length.
                            clickedPosition = position;
                            addCheck();
                        } else {
                            Log.v("EditAdap", "가수는 4명까지");
                            Toast.makeText(addSingerActivity.getApplicationContext(), "가수는 4명까지", Toast.LENGTH_LONG).show();
                        }
                    }
                }
            });
        }else{
            holder.singer_rank.setText(String.valueOf(searchSingerList.get(position).getSinger_rank()));
            requestManager.load(searchSingerList.get(position).singer_image).into(holder.singer_Image);
            holder.singer_Name.setText(searchSingerList.get(position).singer_name);
            if (!checkHave(searchSingerList.get(position).singer_id)) {
                holder.add_singer.setImageResource(searchSingerList.get(position).add_singer);
                //selectSingerNum = searchSingerList.get(position).singer_id;
            } else{
                if(searchSingerList.get(position).singer_id == mainSinger) {
                    Log.v("포지션", String.valueOf(position));
                    holder.add_singer.setVisibility(GONE);
                }
                else
                    holder.add_singer.setImageResource(searchSingerList.get(position).cancel_add);
            }

            holder.add_singer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    selectSingerNum = searchSingerList.get(position).singer_id;
                    Log.v("넘버", String.valueOf(selectSingerNum));
                    Log.v("넘버", String.valueOf(totalSingerCount));
                    //;
                    if (checkHave(selectSingerNum)) {
                        //Log.v("내 가수들", myPageAllSingerNumberses.toString());
                        clickedPosition = position;
                        deleteCheck();
                        Log.v("EditAdap", "이미 있음");
                    } else {
                        if (userDataSumms.size() < 4) {//전체 가수 length.
                            clickedPosition = position;
                            Log.v("포지션 add", String.valueOf(clickedPosition));
                            addCheck();

//                            userDataSumms.add(new UserDataSumm(searchSingerList.get(position).singer_id, searchSingerList.get(position).singer_name,
//                                    searchSingerList.get(position).singer_image));
//                            ApplicationController.getInstance().setUserDataSumms(userDataSumms);
//
//                            Call<SingerAddResponse> addSinger = networkService.addSinger(new SingerAddPost(totalSingerCount,
//                                    selectSingerNum, firebaseToken));
//                            holder.add_singer.setVisibility(GONE);
//                            notifyDataSetChanged();
//
//                            Toast.makeText(addSingerActivity.getApplicationContext(), "추가하였습니다", Toast.LENGTH_LONG).show();

                        } else {
                            Log.v("EditAdap", "가수는 4명까지");
                            Toast.makeText(addSingerActivity.getApplicationContext(), "가수는 4명까지", Toast.LENGTH_LONG).show();
                        }
                    }
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        if(!search)
        {
            return (allSingerList != null) ? allSingerList.size() : 0;

        }else {
            return (searchSingerList != null) ? searchSingerList.size() : 0;
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public boolean checkHave(int selectNum){
       // MyPageAllSingerNumbers myPageAllSingerNumbers = ApplicationController.getInstance().getMyPageAllSingerNumberses();
        ArrayList<UserDataSumm> userDataSumms = ApplicationController.getInstance().getUserDataSumms();
        for(int i = 0; i<userDataSumms.size(); i++){
            if(userDataSumms.get(i).getSinger_id() == selectNum)
                return true;
        }
            return false;
    }

    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());


        searchSingerList.clear();
        //입력한 데이터가 없을 경우에는 추천목록
        if (charText.length() == 0) {
            searchSingerList.addAll(allSingerList);
            search = false;
        }
        else
        {//검색하면 전체로부터 가져오기.
            Log.v(TAG, String.valueOf(allSingerList.size()));

            for (int i = 0; i < allSingerList.size() ; i++)
            {
                Log.v(TAG, "들어옴");
                Log.v(TAG, charText);

                String wp = allSingerList.get(i).getSinger_name();//실제 서버단에 저장된 가수 이름.AOA
                //실질적 비교를 위해서는 에이오에이라고 치면 이것이 AOA가 되어야 함.
                //해쉬 내에서 wp를 키로하는 애들의 value가 charText를 포함.
                Log.v(TAG, wp);
                Log.v(TAG, singerPNData.toString());
                Log.v(TAG, singerPNData.get(wp));
                // Log.v("가수", singerPNData.)
                if (wp.toLowerCase(Locale.getDefault()).contains(charText))
                {//실제 가수 이름이 charText를 갖고 있다면!.
                    searchSingerList.add(allSingerList.get(i));
                    Log.v(TAG, "일치");
                    search = true;
                }
                else if(singerPNData.get(wp).toLowerCase(Locale.getDefault()).contains(charText)) {
                    Log.v(TAG, "불일치");
                    searchSingerList.add(allSingerList.get(i));
                    search = true;
                }
                else {
                    Log.v(TAG, "레알 불일치");
                    //search = false;
                }
            }
        }
        //입력한 데이터가 있을 경우에는 일치하는 항목들만 찾아 출력해줍니다.
        notifyDataSetChanged();
    }

    public void addCheck(){
        String content = "가수목록에 추가하시겠습니까?";
        customDialog = new CustomDialog(context, content, addLeftListener, addRightListener, "취소", "추가");
        customDialog.show();
    }

    private View.OnClickListener addLeftListener = new View.OnClickListener() {
        public void onClick(View v) {
            //startActivity(new Intent(getApplicationContext(), HomeActivity.class));


            customDialog.dismiss();
            //setSingerActivity.SetComplete(ApplicationController.getInstance().getNumberSingerSet().get(temp_name));

        }
    };

    private View.OnClickListener addRightListener = new View.OnClickListener() {
        public void onClick(View v) {
            if(!search) {
                userDataSumms.add(new UserDataSumm(addSingerItemDatas.get(clickedPosition).singer_id, addSingerItemDatas.get(clickedPosition).singer_name,
                        addSingerItemDatas.get(clickedPosition).singer_image));
            }
            else{
                userDataSumms.add(new UserDataSumm(searchSingerList.get(clickedPosition).singer_id, searchSingerList.get(clickedPosition).singer_name,
                        searchSingerList.get(clickedPosition).singer_image));
            }
            ApplicationController.getInstance().setUserDataSumms(userDataSumms);

            Toast.makeText(addSingerActivity.getApplicationContext(), "추가하였습니다.", Toast.LENGTH_LONG).show();
            notifyDataSetChanged();
            customDialog.dismiss();

        }
    };

    public void deleteCheck(){
        String content = "가수목록에서 삭제하시겠습니까?";
        customDialog = new CustomDialog(context, content, deleteLeftListener, deleteRightListener, "취소", "삭제");
        customDialog.show();
    }

    private View.OnClickListener deleteLeftListener = new View.OnClickListener() {
        public void onClick(View v) {
            customDialog.dismiss();
        }
    };

    private View.OnClickListener deleteRightListener = new View.OnClickListener() {
        public void onClick(View v) {
            int singerId = -1;
            if(!search) {
                singerId = addSingerItemDatas.get(clickedPosition).singer_id;
            }
            else{
                singerId = searchSingerList.get(clickedPosition).singer_id;
            }

            Log.v("싱어 아이디", String.valueOf(singerId));

            int index = -1;
            for(int i = 0; i<userDataSumms.size(); i++){
                if(userDataSumms.get(i).getSinger_id() == singerId) {
                    index = i;
                    break;
                }
            }

            switch (index){
                case 0:
                    userDataSumms.get(0).setSinger_id(-1);
                    userDataSumms.get(0).setSinger_img("");
                    userDataSumms.get(0).setSinger_name("");
                    ApplicationController.getInstance().setMainExist(false);
                    break;
                default:
                    userDataSumms.remove(index);
                    break;
            }

            ApplicationController.getInstance().setUserDataSumms(userDataSumms);

            Toast.makeText(addSingerActivity.getApplicationContext(), "삭제하였습니다.", Toast.LENGTH_LONG).show();
            notifyDataSetChanged();
            customDialog.dismiss();
        }
    };
}
