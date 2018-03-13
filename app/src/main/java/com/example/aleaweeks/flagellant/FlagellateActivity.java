package com.example.aleaweeks.flagellant;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.os.Build;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;
import java.util.Arrays;

import android.app.Activity;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.wallet.AutoResolveHelper;
import com.google.android.gms.wallet.CardRequirements;
import com.google.android.gms.wallet.IsReadyToPayRequest;
import com.google.android.gms.wallet.PaymentData;
import com.google.android.gms.wallet.PaymentDataRequest;
import com.google.android.gms.wallet.PaymentMethodTokenizationParameters;
import com.google.android.gms.wallet.PaymentsClient;
import com.google.android.gms.wallet.TransactionInfo;
import com.google.android.gms.wallet.Wallet;
import com.google.android.gms.wallet.WalletConstants;

public class FlagellateActivity extends AppCompatActivity {

    private String[] mAppList;
    private TextView mAppTV;
    private View.OnClickListener mOnClickListener;
    public int flagellationCounter;
    public int donationCounter;
    public String donationAmount;
    private PaymentsClient mPaymentsClient;

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
        donationAmount = sharedPreferences.getString(
                getString(R.string.pref_donation_key),
                getString(R.string.pref_donation_default)
        );

        long flagellationTimeInMinutes = Long.parseLong(flagellationTime);
        flagellationCounter = (int) flagellationTimeInMinutes;
        long flagellationTimeInMilliseconds =TimeUnit.MINUTES.toMillis(flagellationTimeInMinutes);


        TextView timer = (TextView)findViewById(R.id.tv_timer);
        Button checkApps = (Button)findViewById(R.id.btn_add_apps);
        Button startFlagellationButton = (Button)findViewById(R.id.btn_start);

        mPaymentsClient =
                Wallet.getPaymentsClient(
                        this,
                        new Wallet.WalletOptions.Builder()
                                .setEnvironment(WalletConstants.ENVIRONMENT_TEST)
                                .build());

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
                        PaymentDataRequest request = createPaymentDataRequest();
                        if (request != null) {
                            AutoResolveHelper.resolveTask(
                                    mPaymentsClient.loadPaymentData(request),
                                    FlagellateActivity.this,
                                    // LOAD_PAYMENT_DATA_REQUEST_CODE is a constant value
                                    // you define.
                                    5/*LOAD_PAYMENT_DATA_REQUEST_CODE*/);
                        }
                        startFlagellationButton.setVisibility(View.VISIBLE);
                    }
                }.start();
                startFlagellationButton.setVisibility(View.INVISIBLE);
                checkApps.setVisibility(View.INVISIBLE);
            }
        });


    }

    private void isReadyToPay() {
        IsReadyToPayRequest request =
                IsReadyToPayRequest.newBuilder()
                        .addAllowedPaymentMethod(WalletConstants.PAYMENT_METHOD_CARD)
                        .addAllowedPaymentMethod(WalletConstants.PAYMENT_METHOD_TOKENIZED_CARD)
                        .build();
        Task<Boolean> task = mPaymentsClient.isReadyToPay(request);
        task.addOnCompleteListener(
                new OnCompleteListener<Boolean>() {
                    public void onComplete(Task<Boolean> task) {
                        try {
                            boolean result = task.getResult(ApiException.class);
                            if (result == true) {
                                // Show Google as payment option.
                            } else {
                                // Hide Google as payment option.
                            }
                        } catch (ApiException exception) {
                        }
                    }
                });
    }

    private PaymentDataRequest createPaymentDataRequest() {
        PaymentDataRequest.Builder request =
                PaymentDataRequest.newBuilder()
                        .setTransactionInfo(
                                TransactionInfo.newBuilder()
                                        .setTotalPriceStatus(WalletConstants.TOTAL_PRICE_STATUS_FINAL)
                                        .setTotalPrice("10.00")
                                        .setCurrencyCode("USD")
                                        .build())
                        .addAllowedPaymentMethod(WalletConstants.PAYMENT_METHOD_CARD)
                        .addAllowedPaymentMethod(WalletConstants.PAYMENT_METHOD_TOKENIZED_CARD)
                        .setCardRequirements(
                                CardRequirements.newBuilder()
                                        .addAllowedCardNetworks(
                                                Arrays.asList(
                                                        WalletConstants.CARD_NETWORK_AMEX,
                                                        WalletConstants.CARD_NETWORK_DISCOVER,
                                                        WalletConstants.CARD_NETWORK_VISA,
                                                        WalletConstants.CARD_NETWORK_MASTERCARD))
                                        .build());

        PaymentMethodTokenizationParameters params =
                PaymentMethodTokenizationParameters.newBuilder()
                        .setPaymentMethodTokenizationType(
                                WalletConstants.PAYMENT_METHOD_TOKENIZATION_TYPE_PAYMENT_GATEWAY)
                        .addParameter("gateway", "example")
                        .addParameter("gatewayMerchantId", "exampleGatewayMerchantId")
                        .build();

        request.setPaymentMethodTokenizationParameters(params);
        return request.build();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 5:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        PaymentData paymentData = PaymentData.getFromIntent(data);
                        String token = paymentData.getPaymentMethodToken().getToken();
                        break;
                    case Activity.RESULT_CANCELED:
                        break;
                    case AutoResolveHelper.RESULT_ERROR:
                        Status status = AutoResolveHelper.getStatusFromIntent(data);
                        // Log the status for debugging.
                        // Generally, there is no need to show an error to
                        // the user as the Google Pay API will do that.
                        break;
                    default:
                        // Do nothing.
                }
                break;
            default:
                // Do nothing.
        }
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
