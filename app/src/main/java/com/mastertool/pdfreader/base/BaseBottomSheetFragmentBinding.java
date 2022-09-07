package com.mastertool.pdfreader.base;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewbinding.ViewBinding;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public abstract class BaseBottomSheetFragmentBinding<T extends ViewBinding> extends BottomSheetDialogFragment {

    protected T binding;
    private Context context;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        try {
            binding = (T) this.binding();
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.initViews(savedInstanceState);
        Dialog dialog = getDialog();
        if (dialog != null) {
            dialog.setCanceledOnTouchOutside(isCanceledOnTouchOutside());
            dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        }
        return binding.getRoot();
    }

//    public int getTheme() {
//        return R.style.BottomSheetDialogTheme;
//    }

    protected boolean isCanceledOnTouchOutside() {
        return true;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        try {
            if (dialog instanceof BottomSheetDialog) {
                BottomSheetDialog bottomSheetDialog = (BottomSheetDialog) dialog;
                BottomSheetBehavior<FrameLayout> behavior = bottomSheetDialog.getBehavior();
                behavior.setSkipCollapsed(true);
                behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dialog;
    }

    @Override
    public void show(@NonNull FragmentManager manager, @Nullable String tag) {
        try {
            if (manager.findFragmentByTag(tag) != null) {
                return;
            }
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.add(this, tag).commitAllowingStateLoss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void dismiss() {
        try {
            super.dismissAllowingStateLoss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected abstract void initViews(Bundle savedInstanceState);

    protected abstract ViewBinding binding();

    @Override
    public void onAttach(@NonNull Context context) {
        this.context = context;
        super.onAttach(context);
    }

    @Nullable
    @Override
    public Context getContext() {
        return context;
    }
}
