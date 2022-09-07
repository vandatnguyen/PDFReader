package com.mastertool.pdfreader.base;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;

public abstract class BaseDialog {

    private final Context context;
    private final AlertDialog.Builder mDialogBuilder;

    protected View view;

    private AlertDialog mDialog;

    public BaseDialog(Context context) {
        this.context = context;
        this.mDialogBuilder = new AlertDialog.Builder(context);
        this.mDialogBuilder.setCancelable(isCancelable());
        this.mDialogBuilder.setOnCancelListener(dialogInterface -> this.onDialogCancel());
        this.view = this.getView();
    }

    protected abstract boolean isCancelable();

    public BaseDialog show() {
        try {
            ((Activity) this.getContext()).runOnUiThread(() -> {
                this.mDialogBuilder.setView(this.view);
                if (this.mDialog == null) {
                    this.mDialog = this.mDialogBuilder.create();
                    this.mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    this.mDialog.setCanceledOnTouchOutside(isCancelable());
                    this.mDialog.setCancelable(isCancelable());
                }
                if (this.mDialog.getWindow() != null) {
                    this.mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                }
                if (!((Activity) this.context).isFinishing()) {
                    this.mDialog.show();
                    onShowing();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }

    public void hide() {
        try {
            ((Activity) this.context).runOnUiThread(() -> {
                if (this.mDialog != null) {
                    this.mDialog.dismiss();
                }
                this.onDialogCancel();
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void dimiss() {
        if (this.mDialog != null) {
            this.mDialog.dismiss();
        }
    }

    protected abstract View getView();

    public AlertDialog getmDialog() {
        return mDialog;
    }

    public boolean isShowing() {
        return this.mDialog != null && this.mDialog.isShowing();
    }

    public abstract void onDialogCancel();

    public Context getContext() {
        return this.context;
    }

    public abstract void onShowing();
}
