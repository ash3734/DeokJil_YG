package com.deokjilmate.www.deokjilmate.Setting;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.deokjilmate.www.deokjilmate.R;

import java.util.ArrayList;

/**
 * Created by 김민경 on 2017-02-26.
 */

public class TermsAdapter extends BaseExpandableListAdapter {

    private ArrayList<String> groupList = null;
    private ArrayList<ArrayList<String>> childList = null;
    private LayoutInflater inflater = null;
    private TermsAdapter.ViewHolder viewHolder = null;

    public TermsAdapter (Context c, ArrayList<String> groupList,
                         ArrayList<ArrayList<String>> childList){
        super();
        this.inflater = LayoutInflater.from(c);
        this.groupList = groupList;
        this.childList = childList;
    }


    class ViewHolder {

        public TextView terms_order;
        public ImageView terms_order_icon;
        public TextView terms_content;
    }

    // 그룹 포지션을 반환한다.
    @Override
    public String getGroup(int groupPosition) {
        return groupList.get(groupPosition);
    }

    // 그룹 사이즈를 반환한다.
    @Override
    public int getGroupCount() {
        return groupList.size();
    }

    // 그룹 ID를 반환한다.
    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    // 그룹뷰 각각의 ROW
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {

        View v = convertView;

        if(v == null){
            viewHolder = new TermsAdapter.ViewHolder();
            v = inflater.inflate(R.layout.notice_parent, parent, false);
            viewHolder.terms_order = (TextView) v.findViewById(R.id.terms_order);
            viewHolder.terms_order_icon = (ImageView) v.findViewById(R.id.terms_order_icon);
            v.setTag(viewHolder);
        }else{
            viewHolder = (TermsAdapter.ViewHolder)v.getTag();
        }


        ImageView view = (ImageView)v.findViewById(R.id.terms_order_icon);

        // 그룹을 펼칠때와 닫을때 아이콘을 변경해 준다.
        if(isExpanded){
            view.setImageResource(R.drawable.meta);
        }else{
            view.setImageResource(R.drawable.aoa);
        }

        viewHolder.terms_order.setText(getGroup(groupPosition));

        return v;
    }

    // 차일드뷰를 반환한다.
    @Override
    public String getChild(int groupPosition, int childPosition) {
        return childList.get(groupPosition).get(childPosition);
    }

    // 차일드뷰 사이즈를 반환한다.
    @Override
    public int getChildrenCount(int groupPosition) {
        return childList.get(groupPosition).size();
    }

    // 차일드뷰 ID를 반환한다.
    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    // 차일드뷰 각각의 ROW
    @Override
    public View getChildView(int groupPosition, int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        View v = convertView;

        if(v == null){
            viewHolder = new TermsAdapter.ViewHolder();
            v = inflater.inflate(R.layout.terms_child, null);
            viewHolder.terms_content = (TextView) v.findViewById(R.id.terms_content);
            v.setTag(viewHolder);
        }else{
            viewHolder = (TermsAdapter.ViewHolder)v.getTag();
        }

        viewHolder.terms_content.setText(getChild(groupPosition, childPosition));

        return v;
    }

    @Override
    public boolean hasStableIds() { return true; }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) { return true; }



}



