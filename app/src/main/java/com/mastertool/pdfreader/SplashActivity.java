package com.mastertool.pdfreader;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.viewbinding.ViewBinding;

import com.mastertool.pdfreader.base.BaseActivityBinding;
import com.mastertool.pdfreader.databinding.ActivitySplashBinding;

public class SplashActivity extends BaseActivityBinding<ActivitySplashBinding> {

    private final Handler handler = new Handler();

    @Override
    protected ViewBinding binding() {
        return ActivitySplashBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initViews(Bundle bundle) {
        handler.postDelayed(() -> startActivity(new Intent(SplashActivity.this, MainActivity.class)), 2000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }
}
