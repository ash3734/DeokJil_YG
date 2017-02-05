package com.deokjilmate.www.deokjilmate.alarm;

import android.content.Context;
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

/**
 * Created by 김민경 on 2017-02-05.
 */

public class AlarmAdapter extends BaseExpandableListAdapter{
    private ArrayList<String> groupList = null;

    private ArrayList<ArrayList<ChildStateObject>> childList = null;
    private LayoutInflater inflater = null;
    private ViewHolder viewHolder = null;
    MainView view;

    public AlarmAdapter(Context c, ArrayList<String> groupList,
                                 ArrayList<ArrayList<ChildStateObject>> childList, MainView view) {
        super();
        this.view = view;
        this.inflater = LayoutInflater.from(c);
        this.groupList = groupList;
        this.childList = childList;
    }


    class ViewHolder {
        public TextView singerTextView;
        public Switch aSwitch;
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
/////
        ImageView view = (ImageView)v.findViewById(R.id.alarm_arrow);
        TextView textView = (TextView)v.findViewById(R.id.alram_textview_singer);

        if(isExpanded){
            view.setImageResource(R.drawable.alarm_arrow_down);
            textView.setTextColor(0xFFFF7D8F);
        }else{
            view.setImageResource(R.drawable.alarm_arrow);
            textView.setTextColor(0xFF000000);
        }
//////

        viewHolder.singerTextView.setText(getGroup(groupPosition));
 
        return v;
    }

    @Override
    public ChildStateObject getChild(int groupPosition, int childPosition) {
        return childList.get(groupPosition).get(childPosition);
    }
    @Override
    public int getChildrenCount(int groupPosition) {
        return childList.get(groupPosition).size();
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        View v = convertView;
        Boolean state = null;

        if(v==null){
            viewHolder = new ViewHolder();
            v = inflater.inflate(R.layout.alarm_child,null);
            viewHolder.singerTextView = (TextView)v.findViewById(R.id.alram_textview_program);

            viewHolder.aSwitch = (Switch)v.findViewById(R.id.alram_switch_);

            v.setTag(viewHolder);
        }
        else{
            viewHolder = (ViewHolder)v.getTag();
        }

        viewHolder.singerTextView.setTag(getGroup(groupPosition));
        viewHolder.singerTextView.setText(getChild(groupPosition,childPosition).name);


        viewHolder.aSwitch.setChecked(getChild(groupPosition,childPosition).state);

        viewHolder.aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b==true){
                    ViewGroup viewGroup = (ViewGroup)compoundButton.getParent();
                    TextView temp = (TextView)viewGroup.getChildAt(0);
                    view.updateStateCheck(temp.getTag().toString(),temp.getText().toString(),b);
                }
                else{
                    ViewGroup viewGroup = (ViewGroup)compoundButton.getParent();
                    TextView temp = (TextView)viewGroup.getChildAt(0);
                    view.updateStateCheck(temp.getTag().toString(),temp.getText().toString(),b);
                }

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
