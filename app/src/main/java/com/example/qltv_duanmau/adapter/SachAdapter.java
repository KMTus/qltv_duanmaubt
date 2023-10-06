package com.example.qltv_duanmau.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.qltv_duanmau.databinding.DiaglogaddSachBinding;
import com.example.qltv_duanmau.databinding.ItemSachBinding;
import com.example.qltv_duanmau.dao.SachDao;
import com.example.qltv_duanmau.models.SachModel;

import java.util.ArrayList;

public class SachAdapter extends RecyclerView.Adapter<SachAdapter.ViewHolder> {
    Context context;
    ArrayList<SachModel> arrayList;
    SachDao dao;

    public SachAdapter(Context context, ArrayList<SachModel> arrayList, SachDao dao) {
        this.context = context;
        this.arrayList = arrayList;
        this.dao = dao;
    }

    @NonNull
    @Override
    public SachAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemSachBinding binding = ItemSachBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull SachAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        SachModel obj = arrayList.get(position);
        holder.binding.tvTenSach.setText("Tên sách: "+obj.getTenSach());
        holder.binding.tvGia.setText("Giá: " + obj.getGiaThue());
        holder.binding.tvLoaiSach.setText("Tên thể loại: " + obj.getTenTL());

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
        ItemSachBinding binding;
        public ViewHolder(@NonNull ItemSachBinding binding) {
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
                dao.removeSach(id);
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

    String TLSach;
    int idTL;
    int mst = 0;
    private void openDialogUpdate(SachModel objUpdate) {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        DiaglogaddSachBinding binding1 = DiaglogaddSachBinding.inflate(inflater);
        View view = binding1.getRoot();
        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.show();
        //hien thi du lieu cu
        binding1.tvThem.setText("Sửa sách");
        binding1.edGia.setText("" + objUpdate.getGiaThue());
        binding1.edTenSach.setText(objUpdate.getTenSach());



        String[] data =dao.getAllTenTheLoai().toArray(new String[0]);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, data);
        Log.e("zzzzzz", "openDialogUpdate: "+data.length + data[0] +"   "+ objUpdate.getTenTL()  );

        for (int i = 0; i < data.length; i++) {
            if (objUpdate.getTenTL().equals(data[i]) ) {
                Log.e("TAG", "openDialogUpdate: "+ objUpdate.getTenTL() + "va " + data[i]);
                mst = i;
                break;
            }
        }

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding1.spinTLSach.setAdapter(adapter);
        binding1.spinTLSach.setSelection(mst);
        binding1.spinTLSach.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TLSach = data[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(context, "Hãy chọn thể loại sách", Toast.LENGTH_SHORT).show();
            }
        });

        Log.e("TAG", "idTL: " + idTL );


        binding1.btnHoanTat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenSach = binding1.edTenSach.getText().toString().trim();
                String gia = binding1.edGia.getText().toString().trim();


                if (TLSach.isEmpty() || tenSach.isEmpty() || gia.isEmpty()) {
                    Toast.makeText(context, "Vui lòng nhập đữ liệu", Toast.LENGTH_SHORT).show();

                    return;
                }else {
                    idTL = dao.getLoaiSachIdByTenTheLoai(TLSach);

                    objUpdate.setId(objUpdate.getId());
                    objUpdate.setTenSach(tenSach);
                    objUpdate.setGiaThue(Integer.parseInt(gia));
                    objUpdate.setTenTL(TLSach);
                    objUpdate.setIdTL(idTL);

                    dao.updateSach(objUpdate);
                    notifyDataSetChanged();

                    Toast.makeText(context, "Sửa thành công", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            }
        });
    }


}
