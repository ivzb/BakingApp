package com.udacity.baking.ui;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class MasterListAdapter extends BaseAdapter {

    private Context mContext;
    private List<String> mTitles;

    MasterListAdapter(Context context, List<String> titles) {
        mContext = context;
        mTitles = titles;
    }

    @Override
    public int getCount() {
        return mTitles.size();
    }

    @Override
    public Object getItem(int i) {
        return mTitles.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        TextView titleView;

        if (convertView == null) {
            titleView = new TextView(mContext);
            titleView.setPadding(8, 8, 8, 8);
        } else {
            titleView = (TextView) convertView;
        }

        titleView.setText(mTitles.get(position));

        return titleView;
    }
}