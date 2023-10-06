package com.example.qltv_duanmau.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.qltv_duanmau.R;
import com.example.qltv_duanmau.dao.AccountDao;
import com.example.qltv_duanmau.dao.PhieuMuonDao;
import com.example.qltv_duanmau.dao.SachDao;
import com.example.qltv_duanmau.dao.ThanhVienDao;
import com.example.qltv_duanmau.databinding.ItemPhieuMuonBinding;
import com.example.qltv_duanmau.models.PhieuMuonModels;
import com.example.qltv_duanmau.models.SachModel;
import com.example.qltv_duanmau.models.ThanhVienModels;
import com.example.qltv_duanmau.models.ThuThuModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class PhieuMuonAdapter extends RecyclerView.Adapter<PhieuMuonAdapter.ViewHolder> {
    private Context context;

    private List<PhieuMuonModels> phieuMuonModels;

    private PhieuMuonDao dao;

    private ThanhVienDao thanhVienDao;

    private SachDao sachDao;

    private AccountDao accountDao;

    public PhieuMuonAdapter(Context context, List<PhieuMuonModels> phieuMuonModels, PhieuMuonDao dao) {
        this.context = context;
        this.phieuMuonModels = phieuMuonModels;
        this.dao = dao;
    }

    @NonNull
    @Override
    public PhieuMuonAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemPhieuMuonBinding binding = ItemPhieuMuonBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull PhieuMuonAdapter.ViewHolder holder, int position) {
        PhieuMuonModels models = phieuMuonModels.get(position);

        thanhVienDao = new ThanhVienDao(context);

        sachDao = new SachDao(context);

        SachModel sachModel = sachDao.getId(String.valueOf(models.getId_sach()));

        ThanhVienModels thanhVienModels = thanhVienDao.getIDThanhVien(String.valueOf(models.getId_thanh_vien()));

        accountDao = new AccountDao(context);

        ThuThuModel thuThuModel = accountDao.getObjectID(String.valueOf(models.getId_thu_thu()));

        //set data to UI
        holder.binding.tvMapm.setText("Mã phiếu mượn : " + models.getId_phieu());
        holder.binding.tvNgMuon.setText("Người mượn : " + thanhVienModels.getTenThanhVien());
        holder.binding.tvTenSach.setText("Tên sách : " + sachModel.getTenSach());
        holder.binding.tvNgay.setText("Ngày mượn : " + models.getNgay());
        holder.binding.tvGia.setText("Giá tiền : " + models.getGia_tien());
        holder.binding.tvTt.setText("Thủ thư lên phiếu : " + thuThuModel.getName());


        if (models.getTrangthai() == 0){
            holder.binding.tvTrangThai.setText("Chưa trả sách");
            holder.binding.tvTrangThai.setTextColor(Color.RED);
        }
        else {
            holder.binding.tvTrangThai.setText("Đã trả sách");
            holder.binding.tvTrangThai.setTextColor(Color.GREEN);
        }

        holder.binding.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Xác nhận xóa")
                        .setMessage("Bạn có chắc chắn xóa phếu mượn này")
                        .setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dao.deletePM(models);
                                phieuMuonModels.clear();
                                phieuMuonModels.addAll(dao.getDataPhieuMuon());
                                notifyDataSetChanged();
                                Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
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
        });

        holder.binding.imgUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogAdd(models, phieuMuonModels, holder.binding.tvTrangThai);
            }
        });
    }

    private ArrayList<SachModel> sachModelArrayList;

    ArrayList<ThanhVienModels> thanhVienModelsArrayList;

    String[] data, dataThanhVien;

    private String idSach, idThanhVien, idThuThu;

    int Trasach;

    int mst = 0, mstv;
//    private void showDialogAdd(PhieuMuonModels object, List<PhieuMuonModels> phieuMuonModels) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(context);
//        LayoutInflater layoutInflater = LayoutInflater.from(context);
//        View view = layoutInflater.inflate(R.layout.dialog_add_phieu_muon, null);
//        builder.setView(view);
//
//        Spinner spiner_sach = view.findViewById(R.id.spiner_sach);
//        Spinner spinner_thanh_vien = view.findViewById(R.id.spinner_thanh_vien);
//        Spinner spinner_thu_thu = view.findViewById(R.id.spinner_thu_thu);
//
//        EditText edt_gia_tien = view.findViewById(R.id.edt_gia_tien);
//        TextView edt_ngay = view.findViewById(R.id.edt_ngay);
//
//        CheckBox chk_trasach = view.findViewById(R.id.chk_sachtra);
//
//        edt_ngay.setText(object.getNgay());
//        edt_gia_tien.setText(String.valueOf(object.getGia_tien()));
//
//        if (object.getTrangthai() == 1){
//            chk_trasach.setChecked(true);
//        }
//        else {
//            chk_trasach.setChecked(false);
//
//        }
//
//        // Populate the data array for spinner_sach
//        sachDao = new SachDao(context);
//        sachModelArrayList = sachDao.getListSach();
//        String[] dataSach = new String[sachModelArrayList.size()];
//
//        for (int i = 0; i < sachModelArrayList.size(); i++) {
//            dataSach[i] = String.valueOf(sachModelArrayList.get(i).getId());
//            if (object.getId_sach() == sachModelArrayList.get(i).getId()) {
//                spiner_sach.setSelection(i);
//            }
//        }
//
//        ArrayAdapter<String> adapterSach = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, dataSach);
//        adapterSach.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spiner_sach.setAdapter(adapterSach);
//
//        // Populate the data array for spinner_thanh_vien
//        thanhVienDao = new ThanhVienDao(context);
//        thanhVienModelsArrayList = (ArrayList<ThanhVienModels>) thanhVienDao.getAllDataThanhVien();
//        String[] dataThanhVien = new String[thanhVienModelsArrayList.size()];
//
//        for (int i = 0; i < thanhVienModelsArrayList.size(); i++) {
//            dataThanhVien[i] = String.valueOf(thanhVienModelsArrayList.get(i).getIdThanhVien());
//            if (object.getId_thanh_vien() == thanhVienModelsArrayList.get(i).getIdThanhVien()) {
//                spinner_thanh_vien.setSelection(i);
//            }
//        }
//
//        ArrayAdapter<String> adapterThanhVien = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, dataThanhVien);
//        adapterThanhVien.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinner_thanh_vien.setAdapter(adapterThanhVien);
//
//        // Rest of your code...
//
//        // Set the button click listeners
//        builder.setPositiveButton("Sửa", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        if (chk_trasach.isChecked()) {
//                            Trasach = 1;
//                        } else {
//                            Trasach = 0;
//                        }
//                        if (edt_ngay.getText().toString().isEmpty() || edt_gia_tien.getText().toString().isEmpty()) {
//                            Toast.makeText(context, "Nhập đầy đủ thông tn", Toast.LENGTH_SHORT).show();
//                        }
//                        else {
//                            object.setId_sach(mst);
//                            object.setId_thanh_vien(mstv);
//                            object.setId_thu_thu(1);
//                            object.setNgay(edt_ngay.getText().toString());
//                            object.setTrangthai(Trasach);
//                            object.setGia_tien(Integer.parseInt(edt_gia_tien.getText().toString()));
//
//                            long result = dao.updatePM(object);
//                            if (result > 0) {
//                                phieuMuonModels.clear();
//                                phieuMuonModels.addAll(dao.getDataPhieuMuon());
//                                notifyDataSetChanged();
//                                Toast.makeText(context, "Thành công", Toast.LENGTH_SHORT).show();
//                            } else {
//                                Toast.makeText(context, "Lỗi", Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                    }
//                })
//                .setNegativeButton("HỦY", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        dialogInterface.dismiss();
//                    }
//                });
//
//        builder.create().show();
//    }

    private void showDialogAdd(PhieuMuonModels object, List<PhieuMuonModels> phieuMuonModels, TextView textView) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Cập nhật tình trạng phiếu mượn")
                .setMessage("Bạn chắc chắn muốn cập nhật trạng thái mượn sách của phiếu  " + object.getId_phieu() )
                .setPositiveButton("Cập nhật", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        int trangThaiNow = object.getTrangthai();
                        if (trangThaiNow == 0){
                            object.setTrangthai(1);
                            long result = dao.updatePM(object);
                            if (result > 0) {
                                Toast.makeText(context, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                                textView.setTextColor(Color.GREEN);
                                phieuMuonModels.clear();
                                phieuMuonModels.addAll(dao.getDataPhieuMuon());
                                notifyDataSetChanged();
                                dialogInterface.dismiss();
                            }
                        } else if (trangThaiNow == 1) {
                            object.setTrangthai(0);
                            long result = dao.updatePM(object);
                            if (result > 0) {
                                Toast.makeText(context, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                                textView.setTextColor(Color.RED);

                                phieuMuonModels.clear();
                                phieuMuonModels.addAll(dao.getDataPhieuMuon());
                                notifyDataSetChanged();
                                dialogInterface.dismiss();
                            }
                        }


                    }
                })
                .setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .create().show();
    }



    @Override
    public int getItemCount() {
        return phieuMuonModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemPhieuMuonBinding binding;
        public ViewHolder(@NonNull ItemPhieuMuonBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
