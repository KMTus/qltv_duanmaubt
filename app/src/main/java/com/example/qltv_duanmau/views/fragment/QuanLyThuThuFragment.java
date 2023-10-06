package com.example.qltv_duanmau.views.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.qltv_duanmau.R;
import com.example.qltv_duanmau.adapter.ThuThuAdapter;
import com.example.qltv_duanmau.dao.AccountDao;
import com.example.qltv_duanmau.databinding.DiaglogAddLoaisachBinding;
import com.example.qltv_duanmau.databinding.DialogThemThuThuBinding;
import com.example.qltv_duanmau.databinding.FragmentQuanLyThuThuBinding;
import com.example.qltv_duanmau.models.ThuThuModel;

import java.util.ArrayList;


public class QuanLyThuThuFragment extends Fragment {
    FragmentQuanLyThuThuBinding binding;
    String name , username , password;
    AccountDao dao;
    ArrayList<ThuThuModel> arrayList;
    ThuThuAdapter adapter;




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        dao = new AccountDao(getContext());
        binding = FragmentQuanLyThuThuBinding.inflate(inflater, container, false);

        loadData();

        listener();

        return binding.getRoot();
    }

    private void loadData() {
        arrayList = dao.getAllAccounts();
        adapter = new ThuThuAdapter(getContext(), arrayList, dao);
        binding.rcvThuThu.setAdapter(adapter);

    }

    private void listener() {
        binding.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialogAdd();
            }
        });
    }

    private void openDialogAdd() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = ((Activity) getContext()).getLayoutInflater();
        DialogThemThuThuBinding binding1 = DialogThemThuThuBinding.inflate(inflater);
        View view = binding1.getRoot();
        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.show();

        binding1.btnHoanTat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //validate
                name =  binding1.edName.getText().toString().trim();
                username = binding1.edUsername.getText().toString().trim();
                password = binding1.edPass.getText().toString().trim();
                if (name.isEmpty() || password. isEmpty() || username.isEmpty()){
                    Toast.makeText(getContext(), "Vui lòng nhập đủ các trường", Toast.LENGTH_SHORT).show();
                }else if (dao.checkUsername(username)) {
                    binding1.edUsername.setError("Tài khoản không khả dụng");
                }else {
                    //luu du lieu

                    dao.register(username, password,name, 0);
                    loadData();
                    Toast.makeText(getContext(), "Thêm mới thành công", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();


                }
            }
        });
    }
}