package com.example.qltv_duanmau.views.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.example.qltv_duanmau.R;
import com.example.qltv_duanmau.dao.AccountDao;
import com.example.qltv_duanmau.databinding.FragmentDoiMatKhauBinding;
import com.example.qltv_duanmau.models.ThuThuModel;


public class DoiMatKhauFragment extends Fragment {

    private FragmentDoiMatKhauBinding binding;

    private AccountDao dao;

    private ThuThuModel object;

    private String username;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentDoiMatKhauBinding.inflate(inflater, container, false);

        //
        dao = new AccountDao(requireContext());

        //get data intent
        getDataIntent();

        //userclick
        userClick();




        return binding.getRoot();
    }

    private void getDataIntent() {
        Intent intent = getActivity().getIntent();
        username = intent.getStringExtra("username");
        object = dao.getThuThuById(username);
    }

    private void userClick() {
        binding.btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.edpassold.setText("");
                binding.edPassnew.setText("");
                binding.edEnterpass.setText("");
            }
        });

        binding.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkn() > 0) {
                    object.setPassword(binding.edPassnew.getText().toString());
                    int kq = dao.changePassword(object);
                    if (kq > 0) {
                        Toast.makeText(getActivity(), "Thay đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
                        binding.edpassold.setText("");
                        binding.edPassnew.setText("");
                        binding.edEnterpass.setText("");

                        hideKeyboard();
                    }else {
                        Toast.makeText(getActivity(),"Thay đổi mật khẩu thất bại",Toast.LENGTH_SHORT).show();
                        hideKeyboard();
                    }
                }
            }
        });


    }

    // Hàm ẩn bàn phím
    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(binding.btnSave.getWindowToken(), 0);
        }
    }

    public int checkn() {
        int check = 1;
        if (binding.edpassold.getText().length() == 0 || binding.edPassnew.getText().length() == 0 || binding.edEnterpass.getText().length() == 0) {
            Toast.makeText(getActivity(), "Hãy điền đầy đủ thông tin !", Toast.LENGTH_SHORT).show();
            check = -1;
        } else {
            String passold = object.getPassword();
            String passnew = binding.edPassnew.getText().toString();
            String passent = binding.edEnterpass.getText().toString();
            if (!passold.equals(binding.edpassold.getText().toString())) {
                Toast.makeText(getActivity(), "Sai mật khẩu cũ", Toast.LENGTH_SHORT).show();
                return check = -1;
            }
            if (!passnew.equals(passent)) {
                Toast.makeText(getActivity(), "Nhập lại mật khẩu không giống", Toast.LENGTH_SHORT).show();
                check = -1;
            }
        }
        return check;
    }
}