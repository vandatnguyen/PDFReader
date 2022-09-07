package com.mastertool.pdfreader.dialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.mastertool.pdfreader.base.BaseDialog;
import com.mastertool.pdfreader.databinding.DialogInputLinkBinding;

public class InputLinkDialog extends BaseDialog {
    private DialogInputLinkBinding binding;
    private OnInputLinkCallback callback;

    public InputLinkDialog(Context context, OnInputLinkCallback callback) {
        super(context);
        this.callback = callback;
    }

    @Override
    protected boolean isCancelable() {
        return true;
    }

    @Override
    protected View getView() {
        this.binding = DialogInputLinkBinding.inflate(LayoutInflater.from(getContext()));
        return binding.getRoot();    }

    @Override
    public void onDialogCancel() {

    }

    @Override
    public void onShowing() {
        this.binding.tvOpen.setOnClickListener(view -> {
            if (this.binding.edtLink.getText() == null){
                Toast.makeText(getContext(), "Please enter file link!", Toast.LENGTH_SHORT).show();
                return;
            }
            String link = this.binding.edtLink.getText().toString();
            if (link.isEmpty()){
                Toast.makeText(getContext(), "Please enter file link!", Toast.LENGTH_SHORT).show();
                return;
            }
            callback.doOpen(link);
        });
    }

    public interface OnInputLinkCallback {
        void doOpen(String link);
    }
}
