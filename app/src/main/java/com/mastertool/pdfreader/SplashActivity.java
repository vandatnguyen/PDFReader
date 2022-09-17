package com.mastertool.pdfreader;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;

import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingClientStateListener;
import com.android.billingclient.api.BillingResult;
import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.QueryPurchasesParams;
import com.mastertool.pdfreader.base.BaseActivityBinding;
import com.mastertool.pdfreader.databinding.ActivitySplashBinding;
import com.mastertool.pdfreader.vip.utilies.Prefs;

public class SplashActivity extends BaseActivityBinding<ActivitySplashBinding> {
    BillingClient billingClient;
    Prefs prefs;

    @Override
    protected ViewBinding binding() {
        return ActivitySplashBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initViews(Bundle bundle) {
        getSupportActionBar().hide();
        prefs = new Prefs(this);

        checkSubscription();

        // remove title
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        new Handler().postDelayed(() -> {
            startActivity(new Intent(SplashActivity.this, MainActivity.class));
            finish();
            }, 3000);
    }

    void checkSubscription(){

        billingClient = BillingClient.newBuilder(this).enablePendingPurchases().setListener((billingResult, list) -> {}).build();
        final BillingClient finalBillingClient = billingClient;
        billingClient.startConnection(new BillingClientStateListener() {
            @Override
            public void onBillingServiceDisconnected() {

            }

            @Override
            public void onBillingSetupFinished(@NonNull BillingResult billingResult) {

                if(billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK){
                    finalBillingClient.queryPurchasesAsync(
                            QueryPurchasesParams.newBuilder().setProductType(BillingClient.ProductType.SUBS).build(), (billingResult1, list) -> {
                                if (billingResult1.getResponseCode() == BillingClient.BillingResponseCode.OK){
                                    if(list.size()>0){
                                        prefs.setPremium(1);
                                        int i = 0;
                                        for (Purchase purchase: list){
                                            Log.d("testOffer",purchase.getOriginalJson());
                                            Log.d("testOffer", " index" + i);
                                            i++;
                                        }
                                    } else {
                                        prefs.setPremium(0);
                                    }
                                }
                            });

                }

            }
        });
    }
}
