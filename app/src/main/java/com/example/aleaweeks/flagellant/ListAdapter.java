package com.example.aleaweeks.flagellant;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.util.SparseBooleanArray;

import java.util.stream.IntStream;

/**
 * Created by aleaweeks on 3/9/18.
 */

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.AppListViewHolder>{
    private static int[] arrayOfTimeWastingApps;
    private String[] mAppList;
    private OnAppCheckedChangeListener mCheckChangedListener;
    private  int mItems;
    private static final String TAG = ListAdapter.class.getSimpleName();
    private static int itemsInArrayOfTimeWastingApps = 0;

    // sparse boolean array for checking the state of the items
    private SparseBooleanArray itemStateArray = new SparseBooleanArray();

    public ListAdapter(int size) {
        mAppList = new String[size];
        arrayOfTimeWastingApps = new int[size];
       // mCheckChangedListener = checkedChangeListener;
    }

    @Override
    public AppListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.app_selection_item, parent, false);
        AppListViewHolder viewHolder = new AppListViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(AppListViewHolder holder, int position) {
        String app = mAppList[position];
        holder.bind(app, position);
      //  holder.setIsRecyclable(false);
    }

    public void addApp(String app, int items) {
        mItems = items;
        mAppList[mItems] = app;
        mItems+=1;
        notifyDataSetChanged();
    }

    public int getItemCount() {
        return mAppList.length;
    }

    public static int[] getTimeWastingAppArray() {
        return arrayOfTimeWastingApps;
    }

    public static int getNumOfElements() {
        return itemsInArrayOfTimeWastingApps;
    }

    public interface OnAppCheckedChangeListener {
        void onAppCheckedChanged(String app, boolean isChecked);
    }

    class AppListViewHolder extends RecyclerView.ViewHolder {

       // CheckedTextView mCheckedTextView;
        private TextView mAppTextView;
        private CheckBox mCheckBox;

        public AppListViewHolder(final View itemView) {
            super(itemView);
            mAppTextView = (TextView)itemView.findViewById(R.id.tv_app_name);
            mCheckBox = (CheckBox)itemView.findViewById(R.id.app_checkbox);

            mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    int adapterPosition = getAdapterPosition();

                    if (!itemStateArray.get(adapterPosition, false)) {
                        mCheckBox.setChecked(true);

                        arrayOfTimeWastingApps[itemsInArrayOfTimeWastingApps] = adapterPosition;
                        itemsInArrayOfTimeWastingApps+=1;

                        itemStateArray.put(adapterPosition, true);
                    }
                    else  {
                        mCheckBox.setChecked(false);
                        itemStateArray.put(adapterPosition, false);
                        // if item if unchecked, delete from array
                        if(contains(arrayOfTimeWastingApps, adapterPosition, itemsInArrayOfTimeWastingApps)) {
                            //delete and write shift function
                            for(int i = 0; i < itemsInArrayOfTimeWastingApps; i++) {
                                int temp = arrayOfTimeWastingApps[i+1];
                                arrayOfTimeWastingApps[i] = temp;
                            }
                            itemsInArrayOfTimeWastingApps-=1;
                        }
                    }

                    Log.d(TAG, "Array at: " + adapterPosition + " is " + isChecked);
                }
            });


//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
////                    int adapterPosition = getAdapterPosition();
////                    if (!itemStateArray.get(adapterPosition, false)) {
////                        mCheckBox.setChecked(true);
////                        itemStateArray.put(adapterPosition, true);
////                    }
////                    else  {
////                        mCheckBox.setChecked(false);
////                        itemStateArray.put(adapterPosition, false);
////                    }
//                }
//            });
        }

        void bind (String app, int position) {
            mAppTextView.setText(app);
            if (!itemStateArray.get(position, false)) {
                mCheckBox.setChecked(false);
            } else {
                mCheckBox.setChecked(true);
            }

        }

        public boolean contains(int[] a, int num, int size) {
            boolean contains = false;
            for(int i = 0; i<size; i++) {
                if(a[i] == num) {
                    contains = true;
                    break;
                }
            }
            return contains;
        }
        // code taken from: https://android.jlelse.eu/android-handling-checkbox-state-in-recycler-views-71b03f237022
    }
}
