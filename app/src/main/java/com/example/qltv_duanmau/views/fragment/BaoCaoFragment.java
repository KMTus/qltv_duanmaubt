package com.example.qltv_duanmau.views.fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import com.example.qltv_duanmau.R;
import com.example.qltv_duanmau.dao.PhieuMuonDao;
import com.example.qltv_duanmau.dao.ThongKeDao;
import com.example.qltv_duanmau.databinding.FragmentBaoCaoBinding;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


public class BaoCaoFragment extends Fragment {

  private FragmentBaoCaoBinding binding;

  private ThongKeDao dao;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentBaoCaoBinding.inflate(inflater, container, false);

      dao = new ThongKeDao(requireContext());

      userClick();


        return binding.getRoot();
    }

    private void userClick() {
        binding.imgTungay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        // hàm để lấy ngày tháng
                        calendar.set(year, month, dayOfMonth);

                        binding.edTungay.setText(simpleDateFormat.format(calendar.getTime()));
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });

        binding.imgDenngay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        // hàm để lấy ngày tháng
                        calendar.set(year, month, dayOfMonth);

                        binding.edDenngay.setText(simpleDateFormat.format(calendar.getTime()));
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });

        binding.btnCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int doanhthu;
                doanhthu = dao.doanhThuTheoNgay(binding.edTungay.getText().toString(), binding.edDenngay.getText().toString());
                Locale locale = new Locale("nv", "VN");
                NumberFormat nf = NumberFormat.getCurrencyInstance(locale);
                String tienfomat = nf.format(doanhthu);
                binding.tvHienthikq.setText( tienfomat);
            }
        });
    }
}