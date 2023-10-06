package com.example.qltv_duanmau.adapter;

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

import com.example.qltv_duanmau.dao.AccountDao;
import com.example.qltv_duanmau.databinding.DiaglogAddLoaisachBinding;
import com.example.qltv_duanmau.databinding.DialogThemThuThuBinding;
import com.example.qltv_duanmau.databinding.ItemThuThuBinding;
import com.example.qltv_duanmau.models.ThuThuModel;

import java.util.ArrayList;

public class ThuThuAdapter extends RecyclerView.Adapter<ThuThuAdapter.ViewHolder> {
    Context context;
    ArrayList<ThuThuModel> arrayList;
    AccountDao dao;

    public ThuThuAdapter(Context context, ArrayList<ThuThuModel> arrayList, AccountDao dao) {
        this.context = context;
        this.arrayList = arrayList;
        this.dao = dao;
    }


    @NonNull
    @Override
    public ThuThuAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemThuThuBinding binding = ItemThuThuBinding.inflate(LayoutInflater.from(parent.getContext()),parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ThuThuAdapter.ViewHolder holder, int position) {
        ThuThuModel obj = arrayList.get(position);
        //set du lieu
        holder.binding.tvName.setText("Họ và tên: " + obj.getName());
        holder.binding.tvUsername.setText("Tên tài khoản: " + obj.getUsername());
        holder.binding.tvPass.setText("Mật khẩu: *********" );

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
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemThuThuBinding binding;
        public ViewHolder(@NonNull ItemThuThuBinding binding) {
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
                dao.removeAccount(id);
                Toast.makeText(context, "Xoá thành công", Toast.LENGTH_SHORT).show();
                arrayList.remove(index);
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

//    private void openDialogUpdate(ThuThuModel objUpdate) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(context);
//        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
//        DialogThemThuThuBinding binding1 = DialogThemThuThuBinding.inflate(inflater);
//        View view = binding1.getRoot();
//        builder.setView(view);
//        Dialog dialog = builder.create();
//        dialog.show();
//        //set du lieu len ed
//        binding1.tv.setText("Sửa thông tin thủ thư");
//        binding1.edName.setText(objUpdate.getName());
//        binding1.edPass.setText(objUpdate.getPassword());
//        binding1.edUsername.setText(objUpdate.getUsername());
//
//        binding1.btnHoanTat.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String username =  binding1.edUsername.getText().toString().trim();
//                String name = binding1.edName.getText().toString().trim();
//                String pass = binding1.edPass.getText().toString().trim();
//
//                name =  binding1.edName.getText().toString().trim();
//                username = binding1.edUsername.getText().toString().trim();
//                pass = binding1.edPass.getText().toString().trim();
//                if (name.isEmpty() || pass. isEmpty() || username.isEmpty()){
//                    Toast.makeText(context, "Vui lòng nhập đủ các trường", Toast.LENGTH_SHORT).show();
//                }else {
//                    //luu du lieu
//                    objUpdate.setName(name);
//                    objUpdate.setPassword(pass);
//                    objUpdate.setUsername(username);
//
//                    dao.updateAccount(objUpdate);
//                    notifyDataSetChanged();
//
//                    Toast.makeText(context, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
//                    dialog.dismiss();
//
//
//                }
//            }
//        });
//    }

    private void openDialogUpdate(ThuThuModel objUpdate) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Xác nhật đặt lại mật khẩu")
                .setMessage("Chắc chắn muốn đặt lại mật khẩu của thủ thư  " + objUpdate.getName() + " và có id là : " + objUpdate.getId())
                .setMessage("Mặt khẩu đặt lại mặc định là 1")
                .setPositiveButton("Reset", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        objUpdate.setPassword("1");
                        dao.updateAccount(objUpdate);
                        Toast.makeText(context, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                        arrayList.clear();
                        arrayList.addAll(dao.getAllAccounts());
                        notifyDataSetChanged();
                        dialogInterface.dismiss();
                    }
                })
                .setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .create().show();
    }


}
