package com.mastertool.pdfreader;

import android.os.Bundle;

import androidx.viewbinding.ViewBinding;

import com.mastertool.pdfreader.base.BaseActivityBinding;
import com.mastertool.pdfreader.databinding.ActivityPdfBinding;

import java.io.File;

public class PdfActivity extends BaseActivityBinding<ActivityPdfBinding> {
    @Override
    protected ViewBinding binding() {
        return ActivityPdfBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initViews(Bundle bundle) {
        String path = getIntent().getStringExtra("PDF_PATH");
        binding.pdfView.fromFile(new File(path));
    }
}
