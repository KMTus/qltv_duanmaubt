package com.example.qltv_duanmau.views.fragment;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.qltv_duanmau.R;
import com.example.qltv_duanmau.dao.AccountDao;
import com.example.qltv_duanmau.databinding.FragmentLoginBinding;
import com.example.qltv_duanmau.views.MainActivity;


public class LoginFragment extends Fragment {
    FragmentLoginBinding binding;
    String user, pass;
    AccountDao dao;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(inflater, container, false);
        dao = new AccountDao(getContext());

        SharedPreferences preferences = requireContext().getSharedPreferences("USER_FILE", MODE_PRIVATE);
        binding.edUsername.setText(preferences.getString("USERNAME", ""));
        binding.edPass.setText(preferences.getString("PASSWORD", ""));
        binding.chkRemember.setChecked(preferences.getBoolean("REMEMBER", false));

        listener();


        return binding.getRoot();
    }
    private void listener() {
        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate();
            }
        });
    }

    private void validate() {
        user = binding.edUsername.getText().toString().trim();
        pass = binding.edPass.getText().toString().trim();

        if (user.isEmpty() || pass.isEmpty()){
            Toast.makeText(getContext(), "Vui lòng nhập đủ các trường", Toast.LENGTH_SHORT).show();
        } else if (dao.login(user, pass, 1) == 1) {
            Toast.makeText(getContext(), "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
            rememberUser(user, pass, binding.chkRemember.isChecked());
            startActivity(new Intent(getActivity(), MainActivity.class).putExtra("role", "admin").putExtra("username", user));
            Log.d("TAG", "validate: role admin" );
        }
        else if (dao.login(user, pass, 0) == 1) {
            Toast.makeText(getContext(), "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
            rememberUser(user, pass, binding.chkRemember.isChecked());
            startActivity(new Intent(getActivity(), MainActivity.class).putExtra("role", "nv").putExtra("username", user));
            Log.d("TAG", "validate: role nnhan vien" );
        }
        else {
            Toast.makeText(getContext(), "Sai ta khoản hoặc mật khâu", Toast.LENGTH_SHORT).show();
        }

    }

    public void rememberUser(String user, String pass, boolean status) {
        SharedPreferences pref = requireContext().getSharedPreferences("USER_FILE", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        if (!status) {
            // Xóa lưu trữ trước đó
            editor.clear();
        } else {
            // Lưu dữ liệu
            editor.putString("USERNAME", user);
            editor.putString("PASSWORD", pass);
            editor.putBoolean("REMEMBER", status);
        }
        // LƯu lại toàn bộ dữ liệu
        editor.commit();
    }

}