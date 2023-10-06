package com.example.qltv_duanmau.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.qltv_duanmau.R;
import com.example.qltv_duanmau.databinding.ActivityMainBinding;
import com.example.qltv_duanmau.views.fragment.BaoCaoFragment;
import com.example.qltv_duanmau.views.fragment.DoiMatKhauFragment;
import com.example.qltv_duanmau.views.fragment.ManHinhChinhFragment;
import com.example.qltv_duanmau.views.fragment.PhieuMuonFragment;
import com.example.qltv_duanmau.views.fragment.QuanLyLoaiSachFragment;
import com.example.qltv_duanmau.views.fragment.QuanLySachFragment;
import com.example.qltv_duanmau.views.fragment.QuanLyThanhVieFragment;
import com.example.qltv_duanmau.views.fragment.QuanLyThuThuFragment;
import com.example.qltv_duanmau.views.fragment.ThongKeTopFragment;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        replaceFragment(new PhieuMuonFragment());

        //getdata intent
        getDataIntent();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(MainActivity.this, binding.drawerlayout, toolbar, 0, 0);
        drawerToggle.syncState();
        binding.navi.setNavigationItemSelectedListener(this);
    }

    private void getDataIntent() {
        Intent intent = getIntent();
        String user = intent.getStringExtra("role");
        if (user.equals("admin")) {
            binding.navi.getMenu().findItem(R.id.nav_admin).setVisible(true);
        } else {
            binding.navi.getMenu().findItem(R.id.nav_admin).setVisible(false);
        }
    }

    public void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.layout_content, fragment);
        transaction.commit();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.pm_navi) {
            setTitle("Phiếu Mượn");
            replaceFragment(new PhieuMuonFragment());
            binding.drawerlayout.close();
        } else if (id == R.id.tls_navi) {
            setTitle("Thể loại sách");
            replaceFragment(new QuanLyLoaiSachFragment());
            binding.drawerlayout.close();
        } else if (id == R.id.qls_navi) {
            setTitle("Quản lý sách");
            replaceFragment(new QuanLySachFragment());
            binding.drawerlayout.close();
        } else if (id == R.id.tv_navi) {
            setTitle("Thành Viên");
            replaceFragment(new QuanLyThanhVieFragment());
            binding.drawerlayout.close();
        } else if (id == R.id.nav_top10) {
            setTitle("Top 10 sách");
            replaceFragment(new ThongKeTopFragment());
            binding.drawerlayout.close();
        } else if (id == R.id.nav_tong) {
            setTitle("Doanh thu");
            replaceFragment(new BaoCaoFragment());
            binding.drawerlayout.close();
        } else if (id == R.id.nav_admin) {
            setTitle("Thủ thư");
            replaceFragment(new QuanLyThuThuFragment());
            binding.drawerlayout.close();
        } else if (id == R.id.nav_doimk) {
            setTitle("Đổi mật khẩu");
            replaceFragment(new DoiMatKhauFragment());
            binding.drawerlayout.close();
        } else if (id == R.id.exit) {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("Bạn có chắc chắn muốn đăng xuất")
                    .setPositiveButton("Đăng xuất", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent intent = new Intent(MainActivity.this, WelcomeActivity.class);
                            startActivity(intent);
                            finish();
                            binding.drawerlayout.close();
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

        return true;
    }
}