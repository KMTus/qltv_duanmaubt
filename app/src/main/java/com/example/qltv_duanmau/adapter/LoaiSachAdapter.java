package com.example.qltv_duanmau.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.qltv_duanmau.databinding.DiaglogAddLoaisachBinding;
import com.example.qltv_duanmau.databinding.ItemLoaisachBinding;
import com.example.qltv_duanmau.dao.LoaiSachDao;
import com.example.qltv_duanmau.models.LoaiSachModel;

import java.util.ArrayList;

public class LoaiSachAdapter extends RecyclerView.Adapter<LoaiSachAdapter.ViewHolder> {
    Context context;
    ArrayList<LoaiSachModel> arrayListl;
    LoaiSachDao dao;

    public LoaiSachAdapter(Context context, ArrayList<LoaiSachModel> arrayListl, LoaiSachDao dao) {
        this.context = context;
        this.arrayListl = arrayListl;
        this.dao = dao;
    }

    @NonNull
    @Override
    public LoaiSachAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemLoaisachBinding binding = ItemLoaisachBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull LoaiSachAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        LoaiSachModel obj = arrayListl.get(position);
        holder.binding.tvTenTL.setText("ID loại sách : " + obj.getId() + " - " + obj.getTenTL());
        holder.binding.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialogDelete(obj.getId(), position);
            }
        });

        holder.binding.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialogUpdate(obj);
            }
        });

    }

    @Override
    public int getItemCount() {
        return arrayListl.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemLoaisachBinding binding;
        public ViewHolder(@NonNull ItemLoaisachBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }


    private void openDialogDelete(int id, int index) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Bạn có chắc muốn xóa không ?");
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dao.removeLoaiSach(id);
                Toast.makeText(context, "Xoá thành công", Toast.LENGTH_SHORT).show();
                arrayListl.remove(index);
                notifyDataSetChanged();
                dialogInterface.dismiss();
            }
        });
        builder.setNegativeButton("không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();

            }
        });
        builder.show();
    }

    private void openDialogUpdate(LoaiSachModel objUpdate) {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        DiaglogAddLoaisachBinding binding1 = DiaglogAddLoaisachBinding.inflate(inflater);
        View view = binding1.getRoot();
        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.show();
        binding1.tvThem.setText("Sửa thể loại sách");
        binding1.edTenTL.setText(objUpdate.getTenTL());

        binding1.btnHoanTat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenTL =  binding1.edTenTL.getText().toString().trim();
                if (tenTL.isEmpty()) {
                    binding1.edTenTL.setError("Vui lòng nhập đữ liệu");
                    return;
                }else {
                    objUpdate.setId(objUpdate.getId());
                    objUpdate.setTenTL(tenTL);
                    dao.updateLoaiSach(objUpdate);
                    notifyDataSetChanged();

                    Toast.makeText(context, "Thêm mới thành công", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            }
        });
    }
}
