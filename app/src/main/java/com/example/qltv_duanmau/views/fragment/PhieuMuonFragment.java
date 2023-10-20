package com.example.qltv_duanmau.views.fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.text.Editable;
import android.text.TextWatcher;
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

import com.example.qltv_duanmau.R;
import com.example.qltv_duanmau.adapter.PhieuMuonAdapter;
import com.example.qltv_duanmau.dao.AccountDao;
import com.example.qltv_duanmau.dao.PhieuMuonDao;
import com.example.qltv_duanmau.dao.SachDao;
import com.example.qltv_duanmau.dao.ThanhVienDao;
import com.example.qltv_duanmau.databinding.FragmentPhieuMuonBinding;
import com.example.qltv_duanmau.models.PhieuMuonModels;
import com.example.qltv_duanmau.models.SachModel;
import com.example.qltv_duanmau.models.ThanhVienModels;
import com.example.qltv_duanmau.models.ThuThuModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class PhieuMuonFragment extends Fragment {

    private FragmentPhieuMuonBinding binding;

    private PhieuMuonModels phieuMuonModels;

    private List<PhieuMuonModels> list;
    List<PhieuMuonModels> tempList ;

    private PhieuMuonAdapter adapter;

    private PhieuMuonDao dao;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPhieuMuonBinding.inflate(inflater, container, false);


        //config
        dao = new PhieuMuonDao(getContext());
        phieuMuonModels = new PhieuMuonModels();
        list = dao.getDataPhieuMuon();

        // tim kiem phieu muon

        // tạo ra list phuc vu tìm kiếm
        tempList = dao.getDataPhieuMuon();

        binding.edtTK.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                list.clear();
                for (PhieuMuonModels phieuMuonModels1:tempList){
                    if (String.valueOf(phieuMuonModels1.getId_phieu()).contains(charSequence.toString())){
                        list.add(phieuMuonModels1);
                    }

                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        //inti recyclerview
        initRecyclerView();

        //userClick
        userClick();


        return binding.getRoot();
    }

    private void initRecyclerView() {
        adapter = new PhieuMuonAdapter(requireContext(), (List<PhieuMuonModels>) list, dao);
        binding.rcvItem.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.rcvItem.setAdapter(adapter);
    }

    private void userClick() {
        binding.buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogAdd();
            }
        });
    }

    private SachDao sachDao;

    private ArrayList<SachModel> sachModelArrayList;

    ArrayList<ThanhVienModels> thanhVienModelsArrayList;


    private String idSach, idThanhVien, idThuThu;

    ThanhVienDao thanhVienDao;
    int Trasach;


    private void showDialogAdd() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        LayoutInflater layoutInflater = getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.dialog_add_phieu_muon, null);
        builder.setView(view);

        Spinner spiner_sach = view.findViewById(R.id.spiner_sach);
        Spinner spinner_thanh_vien = view.findViewById(R.id.spinner_thanh_vien);
        Spinner spinner_thu_thu = view.findViewById(R.id.spinner_thu_thu);

        EditText edt_gia_tien = view.findViewById(R.id.edt_gia_tien);
        TextView edt_ngay = view.findViewById(R.id.edt_ngay);

        CheckBox chk_trasach = (CheckBox) view.findViewById(R.id.chk_sachtra);



        //////////////////////////////// spinner sach //////////////////////////////////

        sachDao = new SachDao(requireContext());
        sachModelArrayList = sachDao.getListSach();
        String[] dataSach = new String[sachModelArrayList.size()];

        for (int i = 0; i < sachModelArrayList.size(); i++) {
            dataSach[i] = sachModelArrayList.get(i).getTenSach();
        }

        ArrayAdapter<String> adapterSach = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, dataSach);
        adapterSach.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spiner_sach.setAdapter(adapterSach);
        Log.d("TAG", "SachModelArrayList: " + sachModelArrayList.toString());

        spiner_sach.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Lấy ID sách tương ứng với tên sách được chọn
                String tenSachDuocChon = (String) parent.getItemAtPosition(position);
                idSach = String.valueOf(sachModelArrayList.get(position).getId());
                Log.d("TAG", "onItemSelected: " + tenSachDuocChon);
                Log.d("TAG", "Retrieved ID: " + idSach);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Xử lý khi không có gì được chọn
                Toast.makeText(requireContext(), "Mời chọn", Toast.LENGTH_SHORT).show();
            }
        });

        //////////////////////////////// spinner sach //////////////////////////////////



        //////////////////////////////// spinner thanh vien //////////////////////////////////

        thanhVienDao = new ThanhVienDao(requireContext());
        thanhVienModelsArrayList = (ArrayList<ThanhVienModels>) thanhVienDao.getAllDataThanhVien();
        String[] dataThanhVien = new String[thanhVienModelsArrayList.size()];

        for (int i = 0; i < thanhVienModelsArrayList.size(); i++) {
            dataThanhVien[i] = thanhVienModelsArrayList.get(i).getTenThanhVien(); // Assuming ThanhVienModels has a suitable method to get the member name
        }

        ArrayAdapter<String> adapterThanhVien = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, dataThanhVien);
        adapterThanhVien.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_thanh_vien.setAdapter(adapterThanhVien);

        spinner_thanh_vien.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                idThanhVien = String.valueOf(thanhVienModelsArrayList.get(i).getIdThanhVien()); // Store the selected book ID or title
                Log.d("TAG", "onItemSelected: " + idThanhVien);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // Handle when nothing is selected
                Toast.makeText(requireContext(), "Mời chọn", Toast.LENGTH_SHORT).show();
            }
        });

        //////////////////////////////// spinner thanh vien //////////////////////////////////


        //////////////////////////////// spinner thủ thư //////////////////////////////////
        AccountDao accountDao = new AccountDao(requireContext());
        ArrayList<ThuThuModel> thuThuModelArrayList = accountDao.getAllAccounts();
        String[] dataThuThu = new String[thuThuModelArrayList.size()];

        for (int i = 0; i < thuThuModelArrayList.size(); i++) {
            dataThuThu[i] = thuThuModelArrayList.get(i).getName(); // Assuming ThanhVienModels has a suitable method to get the member name
        }

        ArrayAdapter<String> adapterThuThu = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, dataThuThu);
        adapterThuThu.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_thu_thu.setAdapter(adapterThuThu);

        spinner_thu_thu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                idThuThu = String.valueOf(thuThuModelArrayList.get(i).getId()); // Store the selected book ID or title
                Log.d("TAG", "onItemSelected: " + idThuThu);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //////////////////////////////// spinner thủ thư //////////////////////////////////


        edt_ngay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(System.currentTimeMillis());
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        calendar.set(year, month, dayOfMonth);
                        edt_ngay.setText(sdf.format(calendar.getTime()));
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });

// Add similar listeners for spinner_thanh_vien and spinner_thu_thu if needed

        builder.setPositiveButton("Thêm", new DialogInterface.OnClickListener() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String ngayMuon = edt_ngay.getText().toString();
                        String giaTien = edt_gia_tien.getText().toString();

                        if (chk_trasach.isChecked()) {
                            Trasach = 1;
                        } else {
                            Trasach = 0;
                        }

                        if (ngayMuon.isEmpty() || giaTien.isEmpty()) {
                            Toast.makeText(requireContext(), "Nhập đầy đủ thông tn", Toast.LENGTH_SHORT).show();
                        } else {
                            PhieuMuonModels models = new PhieuMuonModels();
                            models.setId_sach(Integer.parseInt(idSach));
                            models.setId_thanh_vien(Integer.parseInt(idThanhVien));
                            models.setId_thu_thu(Integer.parseInt(idThuThu));
                            models.setNgay(ngayMuon);
                            models.setTrangthai(Trasach);
                            models.setGia_tien(Integer.parseInt(giaTien));

                            long result = dao.insertPM(models);
                            if (result > 0) {
                                list.clear();
                                list.addAll(dao.getDataPhieuMuon());
                                adapter.notifyDataSetChanged();
                                Toast.makeText(requireContext(), "Thành công", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(requireContext(), "Lỗi", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                })
                .setNegativeButton("HỦY", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

        builder.create().show();

    }
}