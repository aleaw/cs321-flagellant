package com.example.aleaweeks.flagellant;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.NumberPicker;

public class SettingsActivity extends AppCompatActivity {

    private EditText mDonationEditText;
    private EditText mFocusTimeEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        mDonationEditText = (EditText)findViewById(R.id.et_donation);
        String donationText = mDonationEditText.getText().toString();

        mFocusTimeEditText = (EditText)findViewById(R.id.et_focus_time);
        String focusTime = mFocusTimeEditText.getText().toString();

    }
}
