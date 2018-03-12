package com.example.aleaweeks.flagellant;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.support.annotation.RequiresApi;
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
import java.util.concurrent.TimeUnit;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class FlagellateActivity extends AppCompatActivity {

    private String[] mAppList;
    private TextView mAppTV;
    private View.OnClickListener mOnClickListener;
    public int flagellationCounter;
    public int donationCounter;
    public int donationAmount;


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        String flagellationTime = sharedPreferences.getString(
                getString(R.string.pref_time_key),
                getString(R.string.pref_time_default)
        );

        // *** donation counter variable *** //
        donationAmount = sharedPreferences.getInt(
                getString(R.string.pref_donation_key),
                Integer.parseInt(getString(R.string.pref_donation_default))
        );

        long flagellationTimeInMinutes = Long.parseLong(flagellationTime);
        flagellationCounter = (int) flagellationTimeInMinutes;
        long flagellationTimeInMilliseconds =TimeUnit.MINUTES.toMillis(flagellationTimeInMinutes);
//        Chronometer clock = (Chronometer)findViewById(R.id.chr_countdown_clock);
//        clock.setBase(flagellationCounter);
//        clock.setCountDown(true);
//
//        clock.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
//            @Override
//            public void onChronometerTick(Chronometer chronometer) {
//
//            }
//        });


        TextView timer = (TextView)findViewById(R.id.tv_timer);
        Button checkApps = (Button)findViewById(R.id.btn_add_apps);
        Button startFlagellationButton = (Button)findViewById(R.id.btn_start);

        final Intent appListActivityIntent = new Intent(this, AppList.class);

     //   String finalFlagellationTime = flagellationTime;
        checkApps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(appListActivityIntent);
            }
        });

        startFlagellationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // clock.start();
                new CountDownTimer(flagellationTimeInMilliseconds, 60000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        long minutesRemaining = (millisUntilFinished / 60000)+1;
                        timer.setText("Minutes Remaining: " + minutesRemaining );
                        flagellationCounter-=60000;
                    }
                    @Override
                    public void onFinish() {
                        timer.setText("Flagellation session has ended");
                        startFlagellationButton.setVisibility(View.VISIBLE);
                    }
                }.start();
                startFlagellationButton.setVisibility(View.INVISIBLE);
            }
        });


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

}
