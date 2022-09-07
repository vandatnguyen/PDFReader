package com.mastertool.pdfreader.base;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;

public abstract class BaseActivityBinding<T extends ViewBinding> extends BaseActivity {
    protected T binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int windowFeature = onRequestWindowFeature();
        if (windowFeature > 0) {
            requestWindowFeature(windowFeature);
        }
        try {
            binding = (T) this.binding();
        } catch (Exception e) {
            e.printStackTrace();
        }
        setContentView(binding.getRoot());
        this.initViews(savedInstanceState);
    }

    protected abstract ViewBinding binding();

    protected int onRequestWindowFeature() {
        return -1;
    }

    protected abstract void initViews(Bundle bundle);

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}
