package imageHint;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;

import com.example.daisy.dailyapple.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by Daisy on 10/5/15.
 */
public class CatAdapter extends ArrayAdapter<CatEntry> implements ImageBackupHolder {
    Context context;
    int layoutResourceId;
    List<CatEntry> data = null;
    public ImageLoader imageLoader;
    boolean initialized = false;
    private List<View> rowMap;
    public static final int NUM_ROW = 4;
    public static Queue<CatEntry> backUpQueue;

    public CatAdapter(Context context, int layoutResourceId) {
        super(context, layoutResourceId);
        this.context = context;
        this.layoutResourceId = layoutResourceId;
        imageLoader = new ImageLoader(context, this);
    }

    public void setData(List<CatEntry> catEntries) {
        if (catEntries == null) {
            return;
        }
        addAll(catEntries);
        this.data = catEntries;
        refreshBackUpQueue();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (data == null) {
            return ((Activity) context).getLayoutInflater().inflate(layoutResourceId, parent, false);
        }
        if (!initialized) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context
                    .LAYOUT_INFLATER_SERVICE);
            rowMap = new ArrayList<>(NUM_ROW);
            for (int i = 0; i < NUM_ROW; i++) {
                View row = inflater.inflate(layoutResourceId, parent, false);
                CheckBox checkBox = (CheckBox) row.findViewById(R.id.checkbox);
                ImageView imageView = (ImageView) row.findViewById(R.id.imgIcon);
                CatEntry entry = data.get(i);
                if (entry.getIcon() != null) {
                    Log.d("Daisy", "getView called with url " + entry.getIcon());
                    imageLoader.DisplayImage(entry.getIcon(), imageView,
                            i);
                }
                checkBox.setChecked(false);
                rowMap.add(row);
            }
            initialized = true;
        }
        return rowMap.get(position);
//        View row = convertView;
//        CatHolder holder = null;
//        if (row == null) {
//            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context
//                    .LAYOUT_INFLATER_SERVICE);
//            row = inflater.inflate(layoutResourceId, parent, false);
//            holder = new CatHolder();
//            holder.imageView = (ImageView) row.findViewById(R.id.imgIcon);
//            holder.checkBox = (CheckBox) row.findViewById(R.id.checkbox);
//            row.setTag(holder);
//        } else {
//            holder = (CatHolder) row.getTag();
//        }
//        CatEntry entry = data.get(position);
//        if (entry.getIcon() != null) {
//            Log.d("Daisy", "getView called with url " + entry.getIcon());
//            imageLoader.DisplayImage(entry.getIcon(), holder.imageView);
//        }
//        holder.checkBox.setChecked(false);
//        return row;
    }

    @Override
    public int getCount() {
        if (data == null) {
            return 0;
        }
//        Log.v("Daisy","getCount size is "+data.size());
        return NUM_ROW;
    }

    static class CatHolder {
        private ImageView imageView;
        private CheckBox checkBox;
    }


    @Override
    public Queue<CatEntry> getBackupList() {
        if (backUpQueue == null) {
            refreshBackUpQueue();
        }
        return backUpQueue;
    }

    private void refreshBackUpQueue() {
        if (backUpQueue == null) {
            backUpQueue = new ConcurrentLinkedQueue<>();
        } else {
            backUpQueue.clear();
        }
        // 8 - 15
        for (int i = data.size() - NUM_ROW; i < data.size(); i++) {
            backUpQueue.add(data.get(i));
        }
        Log.d("Daisy", "backupqueue is now: " + backUpQueue.toString());
    }

    @Override
    public List<CatEntry> getCatEntryList() {
        Log.d("Daisy", "data is now: " + data.toString());
        return data;
    }
}

interface ImageBackupHolder {
    public Queue<CatEntry> getBackupList();

    public List<CatEntry> getCatEntryList();
}
