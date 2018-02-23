package com.example.aleaweeks.flagellant;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.EditText;
import android.view.Menu;


public class SettingsActivity extends AppCompatActivity {

    private EditText mDonationEditText;
    private EditText mFocusTimeEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

//        // get user inputted data
//        mDonationEditText = (EditText)findViewById(R.id.et_donation);
//        String donationText = mDonationEditText.getText().toString();
//
//        mFocusTimeEditText = (EditText)findViewById(R.id.et_focus_time);
//        String focusTime = mFocusTimeEditText.getText().toString();

        // display menu icon and menu item

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_item_flagellate, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        final Intent startActivityIntent = new Intent(this, FlagellateActivity.class);
        final Intent summaryActivityIntent = new Intent(this, SummaryActivity.class);

        switch (item.getItemId()) {
            case R.id.action_start:
                startActivity(startActivityIntent);
                return true;
            case R.id.action_summary:
                startActivity(summaryActivityIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
