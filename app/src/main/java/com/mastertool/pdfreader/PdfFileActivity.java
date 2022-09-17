package com.mastertool.pdfreader;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.webkit.MimeTypeMap;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.viewbinding.ViewBinding;

import com.mastertool.pdfreader.adapter.PDFListAdapter;
import com.mastertool.pdfreader.base.BaseActivityBinding;
import com.mastertool.pdfreader.databinding.ActivityPdfFileBinding;
import com.mastertool.pdfreader.model.PDFFile;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class PdfFileActivity extends BaseActivityBinding<ActivityPdfFileBinding> {
    private static final String SORT_ODER = "date_modified DESC";

    private static final String[] FILE_PROJECTION = new String[]{"_id",
            MediaStore.Files.FileColumns.DATA,
            MediaStore.Files.FileColumns.TITLE,
            MediaStore.Files.FileColumns.DATE_MODIFIED,
            MediaStore.Files.FileColumns.MIME_TYPE};

    private PDFListAdapter adapter;
    private Disposable disposable;

    @Override
    protected ViewBinding binding() {
        return ActivityPdfFileBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initViews(Bundle bundle) {
        getSupportActionBar().hide();
        binding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        adapter = new PDFListAdapter(this);
        adapter.setListener(new PDFListAdapter.OnClickFileItem() {
            @Override
            public void onClick(String path) {
                Intent intent = new Intent(PdfFileActivity.this, PdfActivity.class);
                intent.putExtra("PDF_PATH", path);
                startActivity(intent);
            }
        });

        this.binding.rvList.setAdapter(adapter);
        this.binding.rvList.setLayoutManager(new LinearLayoutManager(this));
        loadPDFFiles();
    }

    public void loadPDFFiles() {
        Single.create((SingleOnSubscribe<ArrayList<PDFFile>>) emitter -> {
            ArrayList<String> listAllPath = new ArrayList<>();
            ArrayList<PDFFile> listDocument = new ArrayList<>();
            MimeTypeMap singleton = MimeTypeMap.getSingleton();

            String[] formats = new String[]{"pdf"};
            String[] arg = new String[1];
            for (int i = 0; i < formats.length; i++) {
                arg[i] = singleton.getMimeTypeFromExtension(formats[i]);
            }

            StringBuilder clause = new StringBuilder();
            for (int i = 0; i < arg.length; i++) {
                clause.append(i == 0 ? MediaStore.Files.FileColumns.MIME_TYPE + "=?"
                        : " OR " + MediaStore.Files.FileColumns.MIME_TYPE + "=?");
            }

            Cursor cursor = getContentResolver().query(MediaStore.Files.getContentUri("external"), FILE_PROJECTION, clause.toString(), arg, SORT_ODER);

            if (cursor != null && cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    try {
                        String path = cursor.getString(1);
                        File item = new File(path);

                        if (listAllPath.contains(path) || !item.exists() || item.length() == 0) {
                            continue;
                        }

                        listAllPath.add(path);
                        listDocument.add(new PDFFile(path, item.getName(), formatDate(item.lastModified())));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            try {
                if (cursor != null) {
                    cursor.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            emitter.onSuccess(listDocument);
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<ArrayList<PDFFile>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onSuccess(@NonNull ArrayList<PDFFile> items) {
                        adapter.setData(items);
                        binding.tvEmpty.setVisibility(items.size() == 0 ? View.VISIBLE : View.GONE);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        e.printStackTrace();

                    }
                });
    }

    @NotNull
    public String formatDate(long millis) {
        @SuppressLint("SimpleDateFormat") DateFormat formater = new SimpleDateFormat("dd/MMM/yyyy - HH:mm");
        return formater.format(new Date(millis));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }
}
