package com.example.qltv_duanmau.views.fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.qltv_duanmau.R;
import com.example.qltv_duanmau.adapter.ThanhVienAdapter;
import com.example.qltv_duanmau.dao.ThanhVienDao;
import com.example.qltv_duanmau.databinding.FragmentQuanLyThanhVieBinding;
import com.example.qltv_duanmau.models.ThanhVienModels;

import java.util.Collection;
import java.util.List;


public class QuanLyThanhVieFragment extends Fragment implements ThanhVienAdapter.ITV{

    private FragmentQuanLyThanhVieBinding binding;

    public static final String TAG = "QuanLyThanhVieFragment";

    private ThanhVienDao thanhVienDao;

    private List<ThanhVienModels> thanhVienModelsList;

    private ThanhVienAdapter adapter;

    private ThanhVienAdapter.ITV listener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentQuanLyThanhVieBinding.inflate(inflater, container, false);

        // khởi tạo lopws dao
        thanhVienDao = new ThanhVienDao(requireContext());

        // khởi tạo interface
        listener = this;

        // gán giá trị cho list lấy từ db qua lớp dao
        thanhVienModelsList = thanhVienDao.getAllDataThanhVien();

        //hàm set giá trị cho recyclerview
        initRecyclerView();


        //hàm sự kiện khi ngời dùng click vào views
        userClickListener();


        return binding.getRoot();
    }

    private void initRecyclerView() {
        // kởi tạo adapter
        adapter = new ThanhVienAdapter(requireContext(), thanhVienModelsList, listener);
        binding.rcvItem.setAdapter(adapter);
    }

    private void userClickListener() {
        //nút hiển thị dialog thêm
        binding.buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //hàm hiểu thị dialog thêm
                showDialogThemThanhVien();
            }
        });
    }

    private void showDialogThemThanhVien() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        LayoutInflater layoutInflater = getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.dialog_add_thanh_vien, null);
        builder.setView(view);

        // ánh xạ views
        EditText edt_ten = (EditText) view.findViewById(R.id.edt_ten);
        EditText edt_nam_sinh = (EditText) view.findViewById(R.id.edt_nam_sinh);

        TextView tv_title = (TextView) view.findViewById(R.id.text_title);

        builder.setPositiveButton("Tạo", new DialogInterface.OnClickListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String ten = edt_ten.getText().toString();
                String nam_sinh = edt_nam_sinh.getText().toString();

                if (ten.isEmpty() || nam_sinh.isEmpty()) {
                    Toast.makeText(requireContext(), "Không để trống !", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    // thêm vào db
                    ThanhVienModels models = new ThanhVienModels();
                    models.setTenThanhVien(ten);
                    models.setNamSinh(nam_sinh);

                    // check tangjg thái khi thêm vào db
                    long result = thanhVienDao.insert(models);

                    if (result > 0) {
                        Toast.makeText(requireContext(), "Thêm thành công!", Toast.LENGTH_SHORT).show();
                        // clear data khi add thành công
                        thanhVienModelsList.clear();
                        // clear xong thì gán toàn bộ giá trị được lây từ db vào list
                        thanhVienModelsList.addAll(thanhVienDao.getAllDataThanhVien());
                        adapter.notifyDataSetChanged();
                        dialogInterface.dismiss();
                    } else {
                        Toast.makeText(requireContext(), "Thêm thất bại!", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

        builder.setNegativeButton("HỦY", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

builder.create().show();

    }

    @Override
    public void click(ThanhVienModels thanhVienModels) {

    }

    @Override
    public void update(ThanhVienModels thanhVienModels) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        LayoutInflater layoutInflater = getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.dialog_add_thanh_vien, null);
        builder.setView(view);

        // ánh xạ views
        EditText edt_ten = (EditText) view.findViewById(R.id.edt_ten);
        EditText edt_nam_sinh = (EditText) view.findViewById(R.id.edt_nam_sinh);

        TextView tv_title = (TextView) view.findViewById(R.id.text_title);
        tv_title.setText("Sửa thông tin");

        edt_ten.setText(thanhVienModels.getTenThanhVien());
        edt_nam_sinh.setText(thanhVienModels.getNamSinh());

        builder.setPositiveButton("Tạo", new DialogInterface.OnClickListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String ten = edt_ten.getText().toString();
                String nam_sinh = edt_nam_sinh.getText().toString();

                if (ten.isEmpty() || nam_sinh.isEmpty()) {
                    Toast.makeText(requireContext(), "Không để trống !", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    // thêm vào db
                    thanhVienModels.setTenThanhVien(ten);
                    thanhVienModels.setNamSinh(nam_sinh);

                    // check tangjg thái khi thêm vào db
                    long result = thanhVienDao.updateThanhVien(thanhVienModels);

                    if (result > 0) {
                        Toast.makeText(requireContext(), "Sửa thành công!", Toast.LENGTH_SHORT).show();
                        // clear data khi add thành công
                        thanhVienModelsList.clear();
                        // clear xong thì gán toàn bộ giá trị được lây từ db vào list
                        thanhVienModelsList.addAll(thanhVienDao.getAllDataThanhVien());
                        adapter.notifyDataSetChanged();
                        dialogInterface.dismiss();
                    } else {
                        Toast.makeText(requireContext(), "Sửa thất bại!", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

        builder.setNegativeButton("HỦY", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        builder.create().show();
    }

    @Override
    public void delete(ThanhVienModels thanhVienModels) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Xác nhận xóa !")
                .setMessage("Bạn có chắc chắn muốn xóa thành viên có tên " + thanhVienModels.getTenThanhVien() + "và id : " + thanhVienModels.getIdThanhVien() )
                .setNegativeButton("XÓA", new DialogInterface.OnClickListener() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        int result = thanhVienDao.deleteThanhVien(thanhVienModels);
                        if (result > 0) {
                            Toast.makeText(requireContext(), "Xóa thành công!", Toast.LENGTH_SHORT).show();
                            thanhVienModelsList.clear();
                            // clear xong thì gán toàn bộ giá trị được lây từ db vào list
                            thanhVienModelsList.addAll(thanhVienDao.getAllDataThanhVien());
                            adapter.notifyDataSetChanged();
                            dialogInterface.dismiss();
                        } else {
                            Toast.makeText(requireContext(), "Xóa thất bại!", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setPositiveButton("HỦY", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .create().show();
    }
}