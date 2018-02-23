package com.example.aleaweeks.flagellant;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

//     Intent settingsActivityIntent = new Intent(this, SettingsActivity.class);
//    final Intent summaryActivityIntent = new Intent(this, SettingsActivity.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Intent settingsActivityIntent = new Intent(this, SettingsActivity.class);

        Button loginWithPayPal = (Button)findViewById(R.id.btn_paypal);
        loginWithPayPal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(settingsActivityIntent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        final Intent settingsActivityIntent = new Intent(this, SettingsActivity.class);
        final Intent flagellateActivityIntent = new Intent(this, FlagellateActivity.class);
        final Intent summaryActivityIntent = new Intent(this, SummaryActivity.class);

        switch (item.getItemId()) {
            case R.id.action_start:
                startActivity(flagellateActivityIntent);
                return true;
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
