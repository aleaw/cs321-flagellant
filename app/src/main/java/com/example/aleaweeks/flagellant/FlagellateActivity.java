package com.example.aleaweeks.flagellant;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Chronometer;

import android.Manifest;
import android.app.ActivityManager;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
//import android.support.design.widget.FloatingActionButton;
//import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.util.Log;
import org.w3c.dom.Text;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class FlagellateActivity extends AppCompatActivity {

    private String[] mAppList;
    private TextView mAppTV;
    private View.OnClickListener mOnClickListener;
//    private RecyclerView mAppListRecyclerView;
//    private ListAdapter mListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        Chronometer clock = (Chronometer)findViewById(R.id.chr_countdown_clock);

        Button startFlagellationButton = (Button)findViewById(R.id.btn_start);

        // Recycler View stuff
//        mAppListRecyclerView = (RecyclerView)findViewById(R.id.rv_app_list);
//
//        mAppListRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//        mAppListRecyclerView.setHasFixedSize(true);

        final Intent appListActivityIntent = new Intent(this, AppList.class);




        startFlagellationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    startActivity(appListActivityIntent);
//                mAppTV = (TextView)findViewById(R.id.tv_app_name);
//              //  CheckBox checkBox = findViewById(R.id.app_checkbox);
//                mAppList = getAppList();
//                printAppList(mAppList);
//                mListAdapter = new ListAdapter(mAppList.length);
//                mAppListRecyclerView.setAdapter(mListAdapter);
//
//                for(int i = 0; i < mAppList.length; i++) {
////                    mAppTV.setText("");
////                    mListAdapter.addApp(mAppList[i]);
////                  //  CheckBox checkBox = findViewById(R.id.app_checkbox);
////                   // checkBox.setText("");
////                    for (String app : mAppList) {
////                        mAppTV.append(app + "\n\n");
////                        CheckBox checkBox = findViewById(R.id.app_checkbox);
////                        checkBox.setText("");
////                    }
//                }

            }
        });

        //checkForSin();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_item_flagellate, menu);
             return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        final Intent settingsActivityIntent = new Intent(this, SettingsActivity.class);
        final Intent summaryActivityIntent = new Intent(this, SummaryActivity.class);
        switch (item.getItemId()) {
            case R.id.action_settings:
                startActivity(settingsActivityIntent);
                return true;
            case R.id.action_summary:
                startActivity(summaryActivityIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


//    // Getting user's app logic
//
//    //final int permissionUsageStats = ContextCompat.checkSelfPermission(this, Manifest.permission.PACKAGE_USAGE_STATS);
//    protected void checkForSin(final int[] appIndex){
//        final Handler handler = new Handler();  //Handler that runs the function every 10 seconds.
//        final int delay = 10000;//milliseconds
//        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//
//            UsageStatsManager mUsageStatsManager = (UsageStatsManager) getSystemService(Context.USAGE_STATS_SERVICE);
//            long time = System.currentTimeMillis();
//            // Get usage stats for the last 10 seconds
//            List<UsageStats> stats = mUsageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, time - 1000 * 10, time);
//            // Sort the stats by the last time used
//            if (stats == null || stats.size() == 0) {
//                startActivity(new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS));
//            }
//        }
//
//        handler.postDelayed(new Runnable(){
//            String topPackageName = "";
//            public void run(){
//                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//
//                    UsageStatsManager mUsageStatsManager = (UsageStatsManager) getSystemService (Context.USAGE_STATS_SERVICE);
//                    long time = System.currentTimeMillis();
//                    // Get usage stats for the last 10 seconds
//                    List<UsageStats> stats = mUsageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, time - 1000*10, time);
//                    // Sort the stats by the last time used
//                    if(stats != null){
//
//                        SortedMap<Long,UsageStats> mySortedMap = new TreeMap<Long,UsageStats>();
//                        //add all of the stats to a map while sorting them by the time in which the app was opened
//                        for (UsageStats usageStats : stats) {
//                            mySortedMap.put(usageStats.getLastTimeUsed(),usageStats);
//                        }
//                        if(!mySortedMap.isEmpty()) {
//                            //Get the name of the package corresponding to  the last app opened.
//                            topPackageName =  mySortedMap.get(mySortedMap.lastKey()).getPackageName();
//                        }
//                    }
//
//                    final Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
//                    mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
//                    //get a list of all packages corresponding to applications
//                    final List<ResolveInfo> pkgAppsList = getPackageManager().queryIntentActivities( mainIntent, 0);
//                    //Put all packages corresponding to the apps selected by the user into an array
//                    ResolveInfo selectedPkgs[] = new ResolveInfo[appIndex.length];
//                    for(int i = 0; i < appIndex.length; i++){
//                        selectedPkgs[i] = pkgAppsList.get(appIndex[i]);
//                    }
//                    //Check the currently opened app against the apps in the selected app list
//                    for(int i = 0; i < selectedPkgs.length; i++){
//                        if(selectedPkgs[i].activityInfo.packageName == topPackageName){
//                            //Add money to total owed money
//                        }
//                    }
//                }
//                handler.postDelayed(this, delay);
//            }
//        }, delay);
//    }
//
//    protected String[] getAppList(){
//        String pkgAppsObj = "";
//        Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
//        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
//        List<ResolveInfo> pkgAppsList = getPackageManager().queryIntentActivities( mainIntent, 0);
//        String AppList[] = new String[pkgAppsList.size()];
//        for(int i = 0; i < pkgAppsList.size(); i++) {
//            pkgAppsObj = pkgAppsList.get(i).loadLabel(getPackageManager()).toString();
//            AppList[i] = pkgAppsObj;
//        }
//        return AppList;
//    }
//
//    protected static void printAppList(String[] AppList){
//        String appString = "";
//        for (int i = 0; i < AppList.length; i++){
//            appString += (AppList[i] + "\n");
//        }
//        Log.d("printAppList", appString);
//    }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }


}
