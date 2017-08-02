package com.deokjilmate.www.deokjilmate.MyPage.EditSinger;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.RequestManager;
import com.deokjilmate.www.deokjilmate.MyPage.MyPageAllSingerNumbers;
import com.deokjilmate.www.deokjilmate.R;
import com.deokjilmate.www.deokjilmate.application.ApplicationController;

import java.util.ArrayList;

/**
 * Created by dldud on 2017-02-19.
 */

public class EditSingerAdpater extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private RequestManager requestManager_singer;
    private RequestManager requestManager_image;

    private ArrayList<EditSingerItemData> editSingerItemDatas;
    private static final int HEADER_VIEW = 1;
    private EditSingerHeadItemData editSingerHeadItemData;
    private int selectedSubPos = 0;
    private ArrayList<Integer> deleteList;



    public EditSingerAdpater(RequestManager requestManager_image, RequestManager requestManager_singer, ArrayList<EditSingerItemData> editSingerItemDatas, EditSingerHeadItemData editSingerHeadItemData) {
        this.requestManager_singer = requestManager_singer;
        this.editSingerItemDatas = editSingerItemDatas;
        this.editSingerHeadItemData = editSingerHeadItemData;
        this.requestManager_image = requestManager_image;
        this.deleteList = new ArrayList<Integer>();
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
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
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


                editSingerViewHolder.delete_Singer.setImageResource(editSingerItemDatas.get(position-1).delete_Singer);
                editSingerViewHolder.delete_Singer.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //editSingerItemDatas.
                        //selectedSubPos = (position-1);

                        editSingerItemDatas.get(selectedSubPos).singer_Name = "";
                        editSingerItemDatas.get(selectedSubPos).delete_Singer = 0;
                        editSingerItemDatas.get(selectedSubPos).set_Main = 0;
                        editSingerItemDatas.get(selectedSubPos).singer_Image = "";


                        deleteList.add(position);

//                        ApplicationController.getInstance().setDeleteList(deleteList);
                        //sortList(position);
                        checkType(deleteList);

                        notifyDataSetChanged();


                    }
                });

            } else if (holder instanceof EditSingerHeadViewHolder) {
                //헤더를 찍음(메인을 찍음)
                EditSingerHeadViewHolder editSingerHeadViewHolder = (EditSingerHeadViewHolder) holder;

                requestManager_singer.load(editSingerHeadItemData.singer_Image).into(editSingerHeadViewHolder.singer_Image);
                editSingerHeadViewHolder.singer_Name.setText(editSingerHeadItemData.singer_Name);
                Log.v("이름", editSingerHeadItemData.singer_Name);

                //editSingerHeadViewHolder.set_Sub.setImageResource(editSingerHeadItemData.set_Sub);
                requestManager_image.load(editSingerHeadItemData.set_Sub).into(editSingerHeadViewHolder.set_Sub);

                editSingerHeadViewHolder.delete_Singer.setImageResource(editSingerHeadItemData.delete_Singer);

                editSingerHeadViewHolder.delete_Singer.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //editSingerHeadItemData.

                        editSingerHeadItemData.singer_Name = "";
                        editSingerHeadItemData.delete_Singer = 0;
                        editSingerHeadItemData.set_Sub = 0;
                        editSingerHeadItemData.singer_Image = "";

                        //TODO : 모양만 지우고 실제 delete는 나중에
                        //ApplicationController.getInstance().getMyPageAllSingerNumberses().setSingerb_id(0);
                        deleteList.add(0);
                        //ApplicationController.getInstance().setDeleteList(deleteList);
                        checkType(deleteList);
                        //ApplicationController.getInstance().getMyPageAllSingerNumberses().setSingerb_id((Integer)null);
                        notifyDataSetChanged();
                    }
                });

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
        int tempId = 0;
        MyPageAllSingerNumbers myPageAllSingerNumbers;

        tempImg = editSingerItemDatas.get(selectedSubPos).singer_Image;
        editSingerItemDatas.get(selectedSubPos).singer_Image = editSingerHeadItemData.singer_Image;
        editSingerHeadItemData.singer_Image = tempImg;

        tempName = editSingerItemDatas.get(selectedSubPos).singer_Name;
        editSingerItemDatas.get(selectedSubPos).singer_Name = editSingerHeadItemData.singer_Name;
        editSingerHeadItemData.singer_Name = tempName;

        myPageAllSingerNumbers = ApplicationController.getInstance().getMyPageAllSingerNumberses();
        tempId = myPageAllSingerNumbers.getSingerb_id();

        switch (selectedSubPos){
            case 0:
                ApplicationController.getInstance().getMyPageAllSingerNumberses().setSingerb_id(myPageAllSingerNumbers.getSinger0_id());
                ApplicationController.getInstance().getMyPageAllSingerNumberses().setSinger0_id(tempId);
                break;
            case 1:
                ApplicationController.getInstance().getMyPageAllSingerNumberses().setSingerb_id(myPageAllSingerNumbers.getSinger1_id());
                ApplicationController.getInstance().getMyPageAllSingerNumberses().setSinger1_id(tempId);
                break;
            case 2:
                ApplicationController.getInstance().getMyPageAllSingerNumberses().setSingerb_id(myPageAllSingerNumbers.getSinger2_id());
                ApplicationController.getInstance().getMyPageAllSingerNumberses().setSinger2_id(tempId);
                break;
            case 3:
                ApplicationController.getInstance().getMyPageAllSingerNumberses().setSingerb_id(myPageAllSingerNumbers.getSinger3_id());
                ApplicationController.getInstance().getMyPageAllSingerNumberses().setSinger3_id(tempId);
                break;
            default:
                break;
        }

        notifyDataSetChanged();
    }

    public void sortList(int position){
        switch (position){
            case 1:
                ApplicationController.getInstance().getMyPageAllSingerNumberses().setSinger0_id(
                        ApplicationController.getInstance().getMyPageAllSingerNumberses().getSinger1_id());
                ApplicationController.getInstance().getMyPageAllSingerNumberses().setSinger1_id(
                        ApplicationController.getInstance().getMyPageAllSingerNumberses().getSinger2_id());
               //ApplicationController.getInstance().getMyPageAllSingerNumberses().setSinger2_id((Integer)null);
                break;
            case 2:
                ApplicationController.getInstance().getMyPageAllSingerNumberses().setSinger1_id(
                        ApplicationController.getInstance().getMyPageAllSingerNumberses().getSinger2_id());
               // ApplicationController.getInstance().getMyPageAllSingerNumberses().setSinger2_id((Integer)null);
                break;
            case 3:
               // ApplicationController.getInstance().getMyPageAllSingerNumberses().setSinger2_id((Integer)null);
                break;
        }
    }

    public void checkType(ArrayList<Integer> deleteList){
        int size = deleteList.size();

        switch(size){
            case 0:

                break;
            case 1:
                if(deleteList.get(0)==0){
                    //메인을 지움

                }
                else if(deleteList.get(0) == 1){
                    ApplicationController.getInstance().getMyPageAllSingerNumberses().setSinger0_id(
                            ApplicationController.getInstance().getMyPageAllSingerNumberses().getSinger1_id());
                    ApplicationController.getInstance().getMyPageAllSingerNumberses().setSinger1_id(
                            ApplicationController.getInstance().getMyPageAllSingerNumberses().getSinger2_id());
                      deleteList.clear();
                    deleteList.add(3);
                    //하나짜리는 마지막만 지우는 것으로
                    //나머지는 새로 덮어쓴다고 생각.
                    ApplicationController.getInstance().setDeleteList(deleteList);

                }
                else if(deleteList.get(0)==2){
                    //서브 2지움
                }
                else{
                    //서브 3지움
                }
                break;
            case 2:
                if(deleteList.contains(0) && deleteList.contains(1)){

                }
                else if(deleteList.contains(0) && deleteList.contains(2)){

                }
                else if(deleteList.contains(0) && deleteList.contains(3)){

                }
                else if(deleteList.contains(1) && deleteList.contains(2)){

                }
                else if(deleteList.contains(1) && deleteList.contains(3)){

                }
                else if(deleteList.contains(2) && deleteList.contains(3)){

                }
                break;
            case 3:
                if(deleteList.contains(0) && deleteList.contains(1) && deleteList.contains(2)){

                }
                else if(deleteList.contains(0) && deleteList.contains(1) && deleteList.contains(3)){

                }
                else if(deleteList.contains(0) && deleteList.contains(2) && deleteList.contains(3)){

                }
                else if(deleteList.contains(1) && deleteList.contains(2) && deleteList.contains(3)){

                }
                break;
            case 4:
                break;
        }

    }
}
