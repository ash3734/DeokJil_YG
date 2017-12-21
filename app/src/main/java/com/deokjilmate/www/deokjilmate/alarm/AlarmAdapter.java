package com.deokjilmate.www.deokjilmate.alarm;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.deokjilmate.www.deokjilmate.R;

import java.util.ArrayList;
import java.util.HashMap;


public class AlarmAdapter extends BaseExpandableListAdapter{
    private ArrayList<String> groupList = null;
    private HashMap<String,ArrayList<ChildListContent>> childList = null;

    //private ArrayList<ArrayList<ChildStateObject>> childList = null;

//    private ArrayList<ArrayList<String>> childList = null;
    private LayoutInflater inflater = null;
    private ViewHolder viewHolder = null;
    MainView view;

    public AlarmAdapter(Context c, ArrayList<String> groupList,
                        HashMap<String,ArrayList<ChildListContent>> childList, MainView view) {
        super();
        this.view = view;
        this.inflater = LayoutInflater.from(c);
        this.groupList = groupList;
        this.childList = childList;
    }


    class ViewHolder {
        public TextView singerTextView;
        public Switch aSwitch;
        public Switch todaySwitch;
    }
    @Override
    public String getGroup(int groupPosition) {
        return groupList.get(groupPosition);
    }
    @Override
    public int getGroupCount() {
        return groupList.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        View v = convertView;
        if(v==null){
            viewHolder = new ViewHolder();
            v = inflater.inflate(R.layout.alarm_parent,parent,false);
            viewHolder.singerTextView = (TextView)v.findViewById(R.id.alram_textview_singer);
            v.setTag(viewHolder);
        }
        else{
            viewHolder = (ViewHolder)v.getTag();
        }

        ImageView view = (ImageView)v.findViewById(R.id.alarm_arrow);
        TextView textView = (TextView)v.findViewById(R.id.alram_textview_singer);

        if(isExpanded){
            view.setImageResource(R.drawable.gather_spread);
        }else{
            view.setImageResource(R.drawable.gather_fold);
        }

        viewHolder.singerTextView.setText(getGroup(groupPosition));

        return v;
    }

    @Override
    public ChildListContent getChild(int groupPosition, int childPosition) {
        return this.childList.get(this.groupList.get(groupPosition)).get(childPosition);
    }
    @Override
    public int getChildrenCount(int groupPosition) {
        //
         Log.d("AlarmAdap1", groupList.toString());
        Log.d("AlarmAdap1", childList.toString());

        return this.childList.get(this.groupList.get(groupPosition)).size();
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        View v = convertView;
        Boolean state = null;

        Log.d("방송이름확인",getChild(groupPosition,childPosition).getMp_name());


        if(v==null){
            viewHolder = new ViewHolder();
            v = inflater.inflate(R.layout.alarm_child,null);
            viewHolder.singerTextView = (TextView)v.findViewById(R.id.alram_textview_program);

            viewHolder.aSwitch = (Switch)v.findViewById(R.id.alram_switch_);

            viewHolder.todaySwitch = (Switch)v.findViewById(R.id.alarm_today);

            v.setTag(viewHolder);
        }
        else{
            viewHolder = (ViewHolder)v.getTag();
        }

        viewHolder.singerTextView.setTag(getGroup(groupPosition));
        viewHolder.singerTextView.setText(getChild(groupPosition,childPosition).getMp_name());
//        viewHolder.aSwitch.setChecked(true);

//        if(viewHolder.singerTextView.getText().toString().contains("출연"))
//            viewHolder.aSwitch.setVisibility(View.GONE);
//        else
//            viewHolder.aSwitch.setChecked(getChild(groupPosition,childPosition).isState());
         if(getChild(groupPosition, childPosition).getMp_name().contains("출연"))
             viewHolder.aSwitch.setVisibility(View.GONE);
         else {
             viewHolder.aSwitch.setVisibility(View.VISIBLE);
             viewHolder.aSwitch.setChecked(getChild(groupPosition, childPosition).isState());//
         }


            viewHolder.aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b==true){
                    //얘는 아니었던 애가 true로 눌렸을 때
                    Log.d("AlarmAdap", "true");
                    ViewGroup viewGroup = (ViewGroup)compoundButton.getParent();
                    TextView temp = (TextView)viewGroup.getChildAt(0);
                    Log.d("AlarmAdap", temp.getText().toString());
                    //태그는 방송 이름
                    view.updateStateCheck(temp.getTag().toString(),temp.getText().toString(),b, viewHolder.todaySwitch.isChecked());
                }
                else{
                    //얘는 맞았던 애가 false로 눌렸을 때
                    Log.d("AlarmAdap", "false");
                    ViewGroup viewGroup = (ViewGroup)compoundButton.getParent();
                    TextView temp = (TextView)viewGroup.getChildAt(0);
                    Log.d("AlarmAdap", temp.getText().toString());
                    view.updateStateCheck(temp.getTag().toString(),temp.getText().toString(),b, viewHolder.todaySwitch.isChecked());
                }
            }
        });

        viewHolder.todaySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                //이 부분이 상단에 알림 켜는 곳
                if(b==true) view.updateStateCheck(viewHolder.aSwitch.getTag().toString(),viewHolder.aSwitch.getText().toString(),viewHolder.aSwitch.isChecked(), b);
                else view.updateStateCheck(viewHolder.aSwitch.getTag().toString(),viewHolder.aSwitch.getText().toString(),viewHolder.aSwitch.isChecked(), b);
            }
        });


        return v;


    }
    @Override
    public boolean hasStableIds() {
        return true;
    }
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {

        return true;
    }
}
