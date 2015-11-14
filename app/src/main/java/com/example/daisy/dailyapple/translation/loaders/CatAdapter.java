package com.example.daisy.dailyapple.translation.loaders;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.daisy.dailyapple.R;

import java.util.List;

/**
 * Created by Daisy on 10/5/15.
 */
public class CatAdapter extends ArrayAdapter<CatEntry> {
    Context context;
    int layoutResourceId;
    List<CatEntry> data = null;
    public ImageLoader imageLoader;

    public CatAdapter(Context context, int layoutResourceId) {
        super(context, layoutResourceId);
        this.context = context;
        this.layoutResourceId = layoutResourceId;
        imageLoader = new ImageLoader(context);
    }

    public void setData(List<CatEntry> catEntries) {
        if (catEntries == null) {
            return;
        }
        addAll(catEntries);
        this.data = catEntries;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (data == null) {
            return ((Activity) context).getLayoutInflater().inflate(layoutResourceId, parent, false);
        }
        View row = convertView;
        CatHolder holder = null;
        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context
                    .LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(layoutResourceId, parent, false);
            holder = new CatHolder();
            holder.imageView = (ImageView) row.findViewById(R.id.imgIcon);
            holder.checkBox = (CheckBox) row.findViewById(R.id.checkbox);
            row.setTag(holder);
        } else {
            holder = (CatHolder) row.getTag();
        }
        CatEntry entry = data.get(position);
        holder.checkBox.setChecked(false);
        imageLoader.DisplayImage(entry.getIcon(), holder.imageView);
        return row;
    }

    @Override
    public int getCount() {
        if (data == null) {
            return 0;
        }
//        Log.v("Daisy","getCount size is "+data.size());
        return data.size();
    }

    static class CatHolder {
        private ImageView imageView;
        private CheckBox checkBox;
    }
}
