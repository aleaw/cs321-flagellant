package com.example.aleaweeks.flagellant;

import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.os.Build;
import android.os.Handler;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.CheckBox;
import android.graphics.drawable.Drawable;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;
import android.widget.Button;

public class AppList extends AppCompatActivity implements
        ListAdapter.OnAppCheckedChangeListener {

    private RecyclerView mAppListRecyclerView;
    private ListAdapter mListAdapter;
    private Drawable[] mIconArray;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_list);

        // Recycler View stuff
        mAppListRecyclerView = (RecyclerView)findViewById(R.id.rv_app_list);

        mAppListRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAppListRecyclerView.setHasFixedSize(true);

        CheckBox checkBox = findViewById(R.id.app_checkbox);
     //   ImageView icon = findViewById(R.id.app_icon);
        String[] mAppList = getAppList();
        mIconArray = getAppIcons();

        printAppList(mAppList);
        mListAdapter = new ListAdapter(mAppList.length, this);
        mAppListRecyclerView.setAdapter(mListAdapter);

        for(int i = 0; i < mAppList.length; i++) {
            mListAdapter.addApp(mAppList[i], i);
//            TextView mAppTV = (TextView)findViewById(R.id.tv_app_name);
//            mAppTV.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.ic_action_name,0,0);
        }
    }

    // Getting user's app logic

    //final int permissionUsageStats = ContextCompat.checkSelfPermission(this, Manifest.permission.PACKAGE_USAGE_STATS);
    protected void checkForSin(final int[] appIndex){
        final Handler handler = new Handler();  //Handler that runs the function every 10 seconds.
        final int delay = 10000;//milliseconds
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            UsageStatsManager mUsageStatsManager = (UsageStatsManager) getSystemService(Context.USAGE_STATS_SERVICE);
            long time = System.currentTimeMillis();
            // Get usage stats for the last 10 seconds
            List<UsageStats> stats = mUsageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, time - 1000 * 10, time);
            // Sort the stats by the last time used
            if (stats == null || stats.size() == 0) {
                startActivity(new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS));
            }
        }

        handler.postDelayed(new Runnable(){
            String topPackageName = "";
            public void run(){
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

                    UsageStatsManager mUsageStatsManager = (UsageStatsManager) getSystemService (Context.USAGE_STATS_SERVICE);
                    long time = System.currentTimeMillis();
                    // Get usage stats for the last 10 seconds
                    List<UsageStats> stats = mUsageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, time - 1000*10, time);
                    // Sort the stats by the last time used
                    if(stats != null){

                        SortedMap<Long,UsageStats> mySortedMap = new TreeMap<Long,UsageStats>();
                        //add all of the stats to a map while sorting them by the time in which the app was opened
                        for (UsageStats usageStats : stats) {
                            mySortedMap.put(usageStats.getLastTimeUsed(),usageStats);
                        }
                        if(!mySortedMap.isEmpty()) {
                            //Get the name of the package corresponding to  the last app opened.
                            topPackageName =  mySortedMap.get(mySortedMap.lastKey()).getPackageName();
                        }
                    }

                    final Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
                    mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
                    //get a list of all packages corresponding to applications
                    final List<ResolveInfo> pkgAppsList = getPackageManager().queryIntentActivities( mainIntent, 0);
                    //Put all packages corresponding to the apps selected by the user into an array
                    ResolveInfo selectedPkgs[] = new ResolveInfo[appIndex.length];
                    for(int i = 0; i < appIndex.length; i++){
                        selectedPkgs[i] = pkgAppsList.get(appIndex[i]);
                    }
                    //Check the currently opened app against the apps in the selected app list
                    for(int i = 0; i < selectedPkgs.length; i++){
                        if(selectedPkgs[i].activityInfo.packageName == topPackageName){
                            //Add money to total owed money
                        }
                    }
                }
                handler.postDelayed(this, delay);
            }
        }, delay);
    }

    protected String[] getAppList(){
        String pkgAppsObj = "";
        Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        List<ResolveInfo> pkgAppsList = getPackageManager().queryIntentActivities( mainIntent, 0);
        String AppList[] = new String[pkgAppsList.size()];
        for(int i = 0; i < pkgAppsList.size(); i++) {
            pkgAppsObj = pkgAppsList.get(i).loadLabel(getPackageManager()).toString();
            AppList[i] = pkgAppsObj;
        }
        return AppList;
    }

    protected static void printAppList(String[] AppList){
        String appString = "";
        for (int i = 0; i < AppList.length; i++){
            appString += (AppList[i] + "\n");
        }
        Log.d("printAppList", appString);
    }

    protected Drawable[] getAppIcons(){
        Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        List<ResolveInfo> pkgAppsList = getPackageManager().queryIntentActivities( mainIntent, 0);
        Drawable[] icons = new Drawable[pkgAppsList.size()];
        for(int i=0; i < pkgAppsList.size();i++) {
            ResolveInfo p = pkgAppsList.get(i);
            icons[i]= p.loadIcon(getPackageManager());
        }
        return icons;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_item_applist, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        final Intent flagellateActivityIntent = new Intent(this, FlagellateActivity.class);

        switch (item.getItemId()) {
            case R.id.action_start:
                startActivity(flagellateActivityIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onAppCheckedChanged(String app, boolean isChecked) {

    }
}
