package com.example.qltv_duanmau.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.qltv_duanmau.databinding.ItemThanhVienBinding;
import com.example.qltv_duanmau.models.ThanhVienModels;

import java.util.List;

public class ThanhVienAdapter extends RecyclerView.Adapter<ThanhVienAdapter.ViewHolder> {
    private Context context;

    private List<ThanhVienModels> thanhVienModelsList;

    private ITV listener;

    public interface ITV{
        void click(ThanhVienModels thanhVienModels);
        void update(ThanhVienModels thanhVienModels);
        void delete(ThanhVienModels thanhVienModels);
    }

    public ThanhVienAdapter(Context context, List<ThanhVienModels> thanhVienModelsList, ITV listener) {
        this.context = context;
        this.thanhVienModelsList = thanhVienModelsList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ThanhVienAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemThanhVienBinding binding = ItemThanhVienBinding.inflate(LayoutInflater.from(parent.getContext()),parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ThanhVienAdapter.ViewHolder holder, int position) {
        ThanhVienModels models = thanhVienModelsList.get(position);
        holder.binding.tvName.setText("Họ và tên : "+models.getTenThanhVien());
        holder.binding.tvNamSinh.setText("Năm sinh : "+models.getNamSinh());
        holder.binding.tvCanCuoc.setText("Căn cước : " +models.getCanCuoc());
        holder.binding.buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.delete(models);
            }
        });

        holder.binding.buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.update(models);
            }
        });
    }

    @Override
    public int getItemCount() {
        return thanhVienModelsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ItemThanhVienBinding binding;
        public ViewHolder(@NonNull ItemThanhVienBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
