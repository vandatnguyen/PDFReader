package com.mastertool.pdfreader.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mastertool.pdfreader.base.BaseViewHolderBinding;
import com.mastertool.pdfreader.databinding.ItemFileBinding;
import com.mastertool.pdfreader.model.PDFFile;

import java.util.ArrayList;
import java.util.List;

public class PDFListAdapter extends RecyclerView.Adapter<PDFListAdapter.ViewHolder> {
    private Context context;
    private ArrayList<PDFFile> listData = new ArrayList<>();
    private OnClickFileItem listener;

    public PDFListAdapter(Context context) {
        this.context = context;
    }

    public PDFListAdapter setData(ArrayList<PDFFile> data) {
        listData.clear();
        if (data != null) {
            listData.addAll(data);
        }
        notifyDataSetChanged();
        return this;
    }

    public PDFListAdapter setListener(OnClickFileItem callback) {
        this.listener = callback;
        return this;
    }

    @NonNull
    @Override
    public PDFListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemFileBinding.inflate(LayoutInflater.from(context), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PDFListAdapter.ViewHolder holder, int position) {
        if (this.listData != null) {
            PDFFile pdfFile = listData.get(position);
            holder.binding.tvTitle.setText(pdfFile.getName());
            holder.binding.tvInfo.setText(pdfFile.getDate());
            holder.binding.clItemFile.setOnClickListener(view -> {
                if (listener != null) {
                    listener.onClick(pdfFile.getPath());
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if (listData == null) {
            return 0;
        }
        return listData.size();
    }

    public class ViewHolder extends BaseViewHolderBinding<ItemFileBinding> {
        public ViewHolder(ItemFileBinding binding) {
            super(binding);
        }
    }

    public interface OnClickFileItem {
        void onClick(String path);
    }
}
