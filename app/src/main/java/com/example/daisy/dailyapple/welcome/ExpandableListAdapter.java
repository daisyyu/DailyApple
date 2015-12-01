package com.example.daisy.dailyapple.welcome;

/**
 * Created by Daisy on 11/29/15.
 */

import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.daisy.dailyapple.R;

public class ExpandableListAdapter extends BaseExpandableListAdapter {

    private Context _context;
    private List<NavigationDrawerDataPump.GroupItem> _listDataHeader; // header titles
    // child data in format of header title, child title
    private HashMap<NavigationDrawerDataPump.ParentTitle,
            List<NavigationDrawerDataPump.ChildItem>> _listDataChild;
    private static StateListDrawable stateListIndicatorDrawableUp;
    private static StateListDrawable stateListIndicatorDrawableDown;

    public ExpandableListAdapter(Context context,
                                 List<NavigationDrawerDataPump.GroupItem> listDataHeader,
                                 HashMap<NavigationDrawerDataPump.ParentTitle,
                                         List<NavigationDrawerDataPump.ChildItem>>
                                         listChildData) {
        this._context = context;
        this._listDataHeader = listDataHeader;
        this._listDataChild = listChildData;
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition).parentTitle)
                .get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        final NavigationDrawerDataPump.ChildItem childItem =
                (NavigationDrawerDataPump.ChildItem) getChild
                        (groupPosition, childPosition);
        final String childText = childItem.title;

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_item, null);
        }

        TextView txtListChild = (TextView) convertView
                .findViewById(R.id.lblListItem);

        txtListChild.setText(childText);
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition).parentTitle)
                .size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this._listDataHeader.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this._listDataHeader.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        NavigationDrawerDataPump.GroupItem headerItem =
                (NavigationDrawerDataPump.GroupItem) getGroup
                        (groupPosition);
        String headerTitle = headerItem.title;
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_group, null);
        }

        TextView lblListHeader = (TextView) convertView
                .findViewById(R.id.lblListHeader);
        ImageView indicatorView = (ImageView) convertView.findViewById(R.id
                .indicator);

        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);
        if (headerItem.isExpandable) {
            indicatorView.setImageDrawable(getThemeIndicatorDrawable(isExpanded));
        }
        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    private Drawable getThemeIndicatorDrawable(boolean isExpanded) {
        if (isExpanded) {
            if (stateListIndicatorDrawableUp == null) {
                TypedArray expandableListViewStyle = _context.getTheme()
                        .obtainStyledAttributes(new int[]{android.R.attr.expandableListViewStyle});
                TypedArray groupIndicator = _context.getTheme()
                        .obtainStyledAttributes
                                (expandableListViewStyle.getResourceId(0, 0), new int[]{android.R.attr.groupIndicator});
                StateListDrawable stateListIndicatorDrawable;
                stateListIndicatorDrawable = (StateListDrawable) groupIndicator
                        .getDrawable(0);
                stateListIndicatorDrawable.setState(new int[]{android.R.attr.state_expanded});
                stateListIndicatorDrawableUp = stateListIndicatorDrawable;
            }
            return stateListIndicatorDrawableUp.getCurrent();
        } else {

            if (stateListIndicatorDrawableDown == null) {
                TypedArray expandableListViewStyle = _context.getTheme()
                        .obtainStyledAttributes(new int[]{android.R.attr.expandableListViewStyle});
                TypedArray groupIndicator = _context.getTheme()
                        .obtainStyledAttributes
                                (expandableListViewStyle.getResourceId(0, 0), new int[]{android.R.attr.groupIndicator});
                StateListDrawable stateListIndicatorDrawable;
                stateListIndicatorDrawable = (StateListDrawable) groupIndicator
                        .getDrawable(0);
                stateListIndicatorDrawableDown = stateListIndicatorDrawable;
            }
            return stateListIndicatorDrawableDown.getCurrent();
        }
    }
}
