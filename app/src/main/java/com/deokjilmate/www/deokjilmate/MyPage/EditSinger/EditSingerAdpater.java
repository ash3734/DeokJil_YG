package com.deokjilmate.www.deokjilmate.MyPage.EditSinger;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.RequestManager;
import com.deokjilmate.www.deokjilmate.R;
import com.deokjilmate.www.deokjilmate.UserDataSumm;
import com.deokjilmate.www.deokjilmate.application.ApplicationController;

import java.util.ArrayList;

/**
 * Created by dldud on 2017-02-19.
 */

public class EditSingerAdpater extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    //TODO : ps 디자인이 좀 덜 됨;
    private RequestManager requestManager_singer;
    private RequestManager requestManager_image;

    private ArrayList<EditSingerItemData> editSingerItemDatas;
    private static final int HEADER_VIEW = 1;
    private EditSingerHeadItemData editSingerHeadItemData;
    private int selectedSubPos = 0;
    private ArrayList<Integer> deleteList;
    private boolean mainExist = true;
    private ArrayList<UserDataSumm> userDataSumms;


    public EditSingerAdpater(RequestManager requestManager_image, RequestManager requestManager_singer, ArrayList<EditSingerItemData> editSingerItemDatas, EditSingerHeadItemData editSingerHeadItemData) {
        this.requestManager_singer = requestManager_singer;
        this.editSingerItemDatas = editSingerItemDatas;
        this.editSingerHeadItemData = editSingerHeadItemData;
        this.requestManager_image = requestManager_image;
        this.deleteList = new ArrayList<Integer>();
        this.userDataSumms = new ArrayList<UserDataSumm>();
        this.userDataSumms = ApplicationController.getInstance().getUserDataSumms();
        this.mainExist = ApplicationController.getInstance().isMainExist();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView;
        if(viewType == HEADER_VIEW)
        {
            itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.mypage_edit_singer_head,parent,false);
            EditSingerHeadViewHolder editSingerHeadViewHolder = new EditSingerHeadViewHolder(itemView);
            return editSingerHeadViewHolder;
        }
        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.mypage_edit_singer_recycle, parent, false);

        EditSingerViewHolder editSingerViewHolder = new EditSingerViewHolder(itemView);
        return editSingerViewHolder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        try {
            if (holder instanceof EditSingerViewHolder) {
                //서브를 찍음
                EditSingerViewHolder editSingerViewHolder = (EditSingerViewHolder) holder;

                requestManager_singer.load(editSingerItemDatas.get(position-1).singer_Image).into(editSingerViewHolder.singer_Image);
                editSingerViewHolder.singer_Name.setText(editSingerItemDatas.get(position-1).singer_Name);
                //editSingerViewHolder.set_Main.setImageResource(editSingerItemDatas.get(position-1).set_Main);
                // Log.v("셀렉포지션1", String.valueOf(position-1));
                //requestManager_image.load(editSingerItemDatas.get(position))
                requestManager_image.load(editSingerItemDatas.get(position-1).set_Main).into(editSingerViewHolder.set_Main);

                editSingerViewHolder.set_Main.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        selectedSubPos = (position-1);

                        changeWithMain();

                    }
                });

                //editSingerViewHolder.delete_Singer.setImageResource(editSingerItemDatas.get(position-1).delete_Singer);
                editSingerViewHolder.delete_Singer.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        selectedSubPos = (position-1);

                        editSingerItemDatas.remove(selectedSubPos);
                        userDataSumms.remove(selectedSubPos+1);
                        ApplicationController.getInstance().setUserDataSumms(userDataSumms);
                        notifyDataSetChanged();


                    }
                });

            } else if (holder instanceof EditSingerHeadViewHolder) {
                //헤더를 찍음(메인을 찍음)
                final EditSingerHeadViewHolder editSingerHeadViewHolder = (EditSingerHeadViewHolder) holder;

                requestManager_singer.load(editSingerHeadItemData.singer_Image).into(editSingerHeadViewHolder.singer_Image);
                editSingerHeadViewHolder.singer_Image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.v("EditAdap", "누르긴 누름3");
                    }
                });


                editSingerHeadViewHolder.singer_Name.setText(editSingerHeadItemData.singer_Name);
                Log.v("이름", editSingerHeadItemData.singer_Name);

                //editSingerHeadViewHolder.set_Sub.setImageResource(editSingerHeadItemData.set_Sub);
                requestManager_image.load(editSingerHeadItemData.set_Sub).into(editSingerHeadViewHolder.set_Sub);

                editSingerHeadViewHolder.set_Sub.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.v("EditAdap", "누르긴 누름1");
                        mainExist = false;

                        UserDataSumm tempUserData = new UserDataSumm(userDataSumms.get(0).getSinger_id(),
                                userDataSumms.get(0).getSinger_name(), userDataSumms.get(0).getSinger_img());

                        EditSingerItemData tempEditSingerItemData = new EditSingerItemData(userDataSumms.get(0).getSinger_img(),
                                userDataSumms.get(0).getSinger_name(), R.drawable.chgasu_main, R.drawable.chgasu_x);

                        editSingerItemDatas.add(tempEditSingerItemData);

                        userDataSumms.get(0).setSinger_id(-1);
                        userDataSumms.get(0).setSinger_img("");
                        userDataSumms.get(0).setSinger_name("");

                        editSingerHeadItemData.singer_Name = "";
                        editSingerHeadItemData.singer_Image = "";


                        userDataSumms.add(tempUserData);
                        ApplicationController.getInstance().setUserDataSumms(userDataSumms);


                        notifyDataSetChanged();


                    }
                });
                //editSingerHeadViewHolder.delete_Singer.setImageResource(R.drawable.addgasu_x);

                editSingerHeadViewHolder.delete_Singer.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //editSingerHeadItemData.
                        Log.v("EditAdap", "누르긴 누름2");
                        //editSingerHeadItemData = null;

                        mainExist = false;

                        userDataSumms.get(0).setSinger_id(-1);
                        userDataSumms.get(0).setSinger_img("");
                        userDataSumms.get(0).setSinger_name("");

                        ApplicationController.getInstance().setUserDataSumms(userDataSumms);

                        editSingerHeadItemData.singer_Name = "";
                        editSingerHeadItemData.singer_Image = "";
//                      editSingerHeadItemData

//                        //TODO : 모양만 지우고 실제 delete는 나중에
                        notifyDataSetChanged();
                    }
                });

                if(mainExist){
                    editSingerHeadViewHolder.main.setVisibility(View.VISIBLE);
                    editSingerHeadViewHolder.nonMain.setVisibility(View.GONE);
                }else{
                    editSingerHeadViewHolder.main.setVisibility(View.GONE);
                    editSingerHeadViewHolder.nonMain.setVisibility(View.VISIBLE);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return (editSingerItemDatas != null) ? editSingerItemDatas.size()+1 : 0;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            // This is where we'll add footer.
            return HEADER_VIEW;
        }
        return super.getItemViewType(position);
    }

    public void changeWithMain(){
        String tempImg = "";
        String tempName = "";
        int tempId = -1;
        UserDataSumm tempUserDataSumm;
        //MyPageAllSingerNumbers myPageAllSingerNumbers;

        if(mainExist){
            //메인이 있을 때
            //selectedSubPos는 리스트의 각 position에서 1뺀 값(서브일 경우)
            Log.v("EditAdap", String.valueOf(selectedSubPos+1));
            Log.v("EditAdap", userDataSumms.get(selectedSubPos+1).getSinger_name());
            Log.v("EditAdap", userDataSumms.get(0).getSinger_name());

            tempUserDataSumm = userDataSumms.get(0);
            userDataSumms.set(0, userDataSumms.get(selectedSubPos+1));
            userDataSumms.set(selectedSubPos+1, tempUserDataSumm);
            ApplicationController.getInstance().setUserDataSumms(userDataSumms);
            //이게 진짜 바꾼 거고 표면상으로 한 번 더 바꿔줘야 함

            Log.v("EditAdap", String.valueOf(selectedSubPos+1));
            Log.v("EditAdap", userDataSumms.get(selectedSubPos+1).getSinger_name());
            Log.v("EditAdap", userDataSumms.get(0).getSinger_name());

            editSingerItemDatas.get(selectedSubPos).singer_Image = userDataSumms.get(selectedSubPos+1).getSinger_img();
            editSingerHeadItemData.singer_Image = userDataSumms.get(0).getSinger_img();

            editSingerItemDatas.get(selectedSubPos).singer_Name = userDataSumms.get(selectedSubPos+1).getSinger_name();
            editSingerHeadItemData.singer_Name = userDataSumms.get(0).getSinger_name();

        }else{
            //메인이 없을 때 바꾼 그 서브 뒤에서부터는 하나씩 땡겨야 함.

            Log.v("EditAdap", String.valueOf(selectedSubPos+1));
            Log.v("EditAdap", userDataSumms.get(selectedSubPos+1).getSinger_name());
            Log.v("EditAdap", userDataSumms.get(0).getSinger_name());

            tempUserDataSumm = userDataSumms.get(0);//지웠을 때는 내용을 비울 것 공백이나 -1로
            userDataSumms.set(0, userDataSumms.get(selectedSubPos+1));
            userDataSumms.set(selectedSubPos+1, tempUserDataSumm);
            userDataSumms.remove(selectedSubPos+1);
            ApplicationController.getInstance().setUserDataSumms(userDataSumms);

            //editSingerItemDatas.get(selectedSubPos).singer_Name = userDataSumms.get(selectedSubPos+1).getSinger_name();
            editSingerHeadItemData.singer_Name = userDataSumms.get(0).getSinger_name();

            //editSingerItemDatas.get(selectedSubPos).singer_Image = userDataSumms.get(selectedSubPos+1).getSinger_img();
            editSingerHeadItemData.singer_Image = userDataSumms.get(0).getSinger_img();

            editSingerItemDatas.remove(selectedSubPos);

            mainExist = true;

        }

        notifyDataSetChanged();
    }
}
