package com.mastertool.pdfreader.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;

import org.jetbrains.annotations.NotNull;

public abstract class BaseFragmentBinding<T extends ViewBinding> extends BaseFragment {
    protected Context context;
    protected T binding;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Nullable
    @Override
    public Context getContext() {
        return context;
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        try {
            binding = (T) this.binding();
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.initViews(savedInstanceState);
        return this.binding.getRoot();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (isVisible()) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    protected abstract void initViews(Bundle bundle);

    protected abstract ViewBinding binding();

    public void onHideView() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}
