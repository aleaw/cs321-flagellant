package com.example.aleaweeks.flagellant;

import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.util.SparseBooleanArray;

/**
 * Created by aleaweeks on 3/9/18.
 */

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.AppListViewHolder>{
    private String[] mAppList;
    private OnAppCheckedChangeListener mCheckChangedListener;
    private int mItems;

    // sparse boolean array for checking the state of the items
    private SparseBooleanArray itemStateArray = new SparseBooleanArray();

    public ListAdapter(int size, OnAppCheckedChangeListener checkedChangeListener) {
        mAppList = new String[size];
        mCheckChangedListener = checkedChangeListener;
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
        holder.setIsRecyclable(false);
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

    public interface OnAppCheckedChangeListener {
        void onAppCheckedChanged(String app, boolean isChecked);
    }

    class AppListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

       // CheckedTextView mCheckedTextView;
        private TextView mAppTextView;
        private CheckBox mCheckBox;

        public AppListViewHolder(final View itemView) {
            super(itemView);
            mAppTextView = (TextView)itemView.findViewById(R.id.tv_app_name);
            mCheckBox = (CheckBox)itemView.findViewById(R.id.app_checkbox);
            itemView.setOnClickListener(this);
//            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                @Override
//                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                    String app = mAppList[getAdapterPosition()];
//                    mCheckChangedListener.onAppCheckedChanged(app, isChecked);
//                }
//            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                }
            });
        }

        void bind (String app, int position) {
            mAppTextView.setText(app);
            if (!itemStateArray.get(position, false)) {
                mCheckBox.setChecked(false);}

        }

        // code taken from: https://android.jlelse.eu/android-handling-checkbox-state-in-recycler-views-71b03f237022
        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            if (!itemStateArray.get(adapterPosition, false)) {
                mCheckBox.setChecked(true);
                itemStateArray.put(adapterPosition, true);
            }
            else  {
                mCheckBox.setChecked(false);
                itemStateArray.put(adapterPosition, false);
            }
        }
    }
}
