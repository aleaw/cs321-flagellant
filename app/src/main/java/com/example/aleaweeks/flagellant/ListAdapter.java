package com.example.aleaweeks.flagellant;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by aleaweeks on 3/9/18.
 */

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.AppListViewHolder>{
    private String[] mAppList;
    private int mItems;

    public ListAdapter(int size) {
        mAppList = new String[size];
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
        holder.bind(app);
    }

    public void addApp(String app, int items) {
        mItems = items;
        mAppList[items] = app;
        mItems++;
        notifyDataSetChanged();
    }

    public int getItemCount() {
        return mAppList.length;
    }

    class AppListViewHolder extends RecyclerView.ViewHolder {

        private TextView mAppTextView;

        public AppListViewHolder(View itemView) {
            super(itemView);
            mAppTextView = (TextView)itemView.findViewById(R.id.tv_app_name);
        }

        void bind (String app) {
            mAppTextView.setText(app);
        }


    }
}
