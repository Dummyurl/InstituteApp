package com.mksmcqapplicationtest;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.Map;


public class ExpandableListAdapter extends BaseExpandableListAdapter {

    Activity context;
    //    Map<String, List<String>> NavCollections;
    List<String> Navitems;
    List<String> groupListCode;
    TextView font_icon;
    Typeface font;
    View view;
    List<String> fa_group;



    Integer[] fa_group_exam = new Integer[]{R.string.fa_attendance, R.string.fa_attendance, R.string.fa_attendance, R.string.fa_data, R.string.fa_data, R.string.fa_data};
    Integer[] fa_group_attendace = new Integer[]{R.string.fa_attendance, R.string.fa_attendance, R.string.fa_attendance};
    Integer[] fa_group_fees = new Integer[]{R.string.fa_fees, R.string.fa_fees};
    Integer[] fa_group_activedeactive = new Integer[]{R.string.fa_attendance, R.string.fa_data, R.string.fa_data,
            R.string.fa_profile, R.string.fa_music};
    Integer[] fa_group_notification = new Integer[]{R.string.fa_notification, R.string.fa_notification, R.string.fa_notification};
    Integer[] fa_group_guest = new Integer[]{R.string.fa_data, R.string.fa_data, R.string.fa_profile};//, R.string.fa_music
    Integer[] fa_group_utility = new Integer[]{R.string.fa_data, R.string.fa_profile, R.string.fa_profile};
    Integer[] fa_group_communication = new Integer[]{R.string.fa_email,
            R.string.fa_changepassword, R.string.fa_changepassword,
            R.string.fa_ratethisapp, R.string.fa_share};
    Integer[] fa_group_other = new Integer[]{R.string.fa_userMannual, R.string.fa_userMannual, R.string.fa_videoManual, R.string.fa_developBy};


    public ExpandableListAdapter(Activity context, List<String> Navitems,List<String> grouplistCode, List<String> fa_group) {
        this.context = context;
        this.Navitems = Navitems;
        this.groupListCode = grouplistCode;
        this.fa_group = fa_group;
    }


    public Object getChildIcon(int groupPosition, int childPosition) {
        if (groupPosition == 11) {
            return fa_group_other[childPosition];
        }
        return null;
    }

    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }


    public View getChildView(final int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        final String child_itme = (String) getChild(groupPosition, childPosition);
        Integer fa = (Integer) getChildIcon(groupPosition, childPosition);
        LayoutInflater inflater = context.getLayoutInflater();

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.child_item, null);
        }
        font = Typeface.createFromAsset(context.getAssets(), "fontawesome-webfont.ttf");
        TextView font_icon_child = (TextView) convertView.findViewById(R.id.font_icon_child);
        font_icon_child.setTypeface(font);
        TextView item = (TextView) convertView.findViewById(R.id.exam_portal);
        item.setText(child_itme);
        font_icon_child.setText(fa);

        return convertView;
    }


    public Object getGroup(int groupPosition) {
        return Navitems.get(groupPosition);
    }

    @Override
    public Object getChild(int i, int i1) {
        return null;
    }

    public Object getGroupIcon(int groupPosition) {
        return fa_group.get(groupPosition);
    }

    public int getGroupCount() {
        return Navitems.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return 0;
    }

    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {

        String itme = (String) getGroup(groupPosition);
        String fa = (String) getGroupIcon(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.group_item, null);
        }

        font = Typeface.createFromAsset(context.getAssets(), "fontawesome-webfont.ttf");
        font_icon = (TextView) convertView.findViewById(R.id.font_icon);
        font_icon.setTypeface(font);

        TextView group_icon = (TextView) convertView.findViewById(R.id.group_icon);
        group_icon.setTypeface(font);
        getChildrenCount(groupPosition);
        if (getChildrenCount(groupPosition) == 0) {
            group_icon.setVisibility(View.INVISIBLE);
        } else {
            group_icon.setVisibility(View.VISIBLE);
            group_icon.setText(isExpanded ? context.getResources().getString(R.string.fa_minus) : context.getResources().getString(R.string.fa_plus));
        }
        TextView item = (TextView) convertView.findViewById(R.id.exam_group_portal);
        item.setText(itme);
        font_icon.setText(new String(Character.toChars(Integer.parseInt(fa, 16))));


        return convertView;
    }

    public boolean hasStableIds() {
        return true;
    }

    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}

