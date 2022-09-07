package com.mastertool.pdfreader;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.viewbinding.ViewBinding;

import com.mastertool.pdfreader.base.BaseActivityBinding;
import com.mastertool.pdfreader.databinding.ActivityMainBinding;
import com.mastertool.pdfreader.dialog.InputLinkDialog;

public class MainActivity extends BaseActivityBinding<ActivityMainBinding> {

    private static final String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    @Override
    protected ViewBinding binding() {
        return ActivityMainBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initViews(Bundle bundle) {
        binding.btnUpgrade.setOnClickListener(view -> {
           updatePremium();
        });

        binding.layoutPdfOnline.setOnClickListener(view -> {
            inputLink();
        });

        binding.layoutPdfLocal.setOnClickListener(view -> {
            requestPermission();
        });
    }

    private boolean isNotStoragePmsGranted() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return this.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED;
        }
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1990) {
            requestPermission();
        }
    }

    private void requestPermission(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (this.isNotStoragePmsGranted()) {
                ActivityCompat.requestPermissions(this, PERMISSIONS_STORAGE, 1990);
                return;
            }
        }
        startScanPdf();
    }

    private void updatePremium() {
        // TODO: 07/09/2022
        Toast.makeText(this, "Features under development", Toast.LENGTH_SHORT).show();
    }

    private void inputLink() {
        new InputLinkDialog(this, link -> {
            Intent intent = new Intent(MainActivity.this, PdfActivity.class);
            intent.putExtra("PDF_URL", link);
            startActivity(intent);
        }).show();

    }

    private void startScanPdf() {
        startActivity(new Intent(MainActivity.this, PdfFileActivity.class));
    }
}
