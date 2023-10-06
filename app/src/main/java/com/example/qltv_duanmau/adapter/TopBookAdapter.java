package com.example.qltv_duanmau.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.qltv_duanmau.databinding.ItemBookTopBinding;
import com.example.qltv_duanmau.models.TopBookModel;

import java.util.List;

public class TopBookAdapter extends RecyclerView.Adapter<TopBookAdapter.ViewHoler> {

    private Context context;

    private List<TopBookModel> topBookModelList;

    public TopBookAdapter(Context context, List<TopBookModel> topBookModelList) {
        this.context = context;
        this.topBookModelList = topBookModelList;
    }

    @NonNull
    @Override
    public ViewHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemBookTopBinding binding = ItemBookTopBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHoler(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHoler holder, int position) {
        TopBookModel topBookModel = topBookModelList.get(position);
        holder.binding.tvTitle.setText(topBookModel.getTitle_book());
        holder.binding.tvQuantity.setText("Tổng số phiếu mượn đã lên : " + String.valueOf(topBookModel.getQuantity()));


// Replace commas with periods (assuming they represent decimal points)

    }

    @Override
    public int getItemCount() {
        return topBookModelList.size();
    }

    public class ViewHoler extends RecyclerView.ViewHolder {

        ItemBookTopBinding binding;
        public ViewHoler(@NonNull ItemBookTopBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
