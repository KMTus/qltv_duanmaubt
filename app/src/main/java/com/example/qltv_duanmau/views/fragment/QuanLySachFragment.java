package com.example.qltv_duanmau.views.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.qltv_duanmau.databinding.DiaglogaddSachBinding;
import com.example.qltv_duanmau.databinding.FragmentQuanLySachBinding;
import com.example.qltv_duanmau.adapter.SachAdapter;
import com.example.qltv_duanmau.dao.SachDao;
import com.example.qltv_duanmau.models.PhieuMuonModels;
import com.example.qltv_duanmau.models.SachModel;

import java.util.ArrayList;


public class QuanLySachFragment extends Fragment {
    FragmentQuanLySachBinding binding;
    ArrayList<SachModel> arrayList;
    ArrayList<SachModel> templist;
    SachAdapter adapter;
    SachDao dao;
    String TLSach;
    int idTL;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentQuanLySachBinding.inflate(inflater, container, false);
        dao = new SachDao(getContext());

        binding.edtTKSach.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                arrayList.clear();
                for (SachModel sachModel:templist){
                    if (String.valueOf(sachModel.getTenSach()).contains(charSequence.toString())){
                        arrayList.add(sachModel);
                    }

                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        loadDataFromSQL();
        listener();

        return binding.getRoot();
    }

    private void  loadDataFromSQL() {
        arrayList = dao.getListSach();
        templist = dao.getListSach();
        Log.e("TAG", "loadDataFromSQL: "+ arrayList.size() );
        adapter = new SachAdapter(getContext(), arrayList, dao);
        binding.rcvSach.setAdapter(adapter);
    }

    private void listener() {
        binding.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                LayoutInflater inflater = ((Activity) getContext()).getLayoutInflater();
                DiaglogaddSachBinding binding1 = DiaglogaddSachBinding.inflate(inflater);
                View view = binding1.getRoot();
                builder.setView(view);
                Dialog dialog = builder.create();
                dialog.show();

                String[] data =dao.getAllTenTheLoai().toArray(new String[0]);
                ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, data);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                binding1.spinTLSach.setAdapter(adapter);
                binding1.spinTLSach.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        TLSach = data[position];
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        Toast.makeText(getContext(), "Hãy chọn thể loại sách", Toast.LENGTH_SHORT).show();
                    }
                });

                Log.e("TAG", "idTL: " + idTL );


                binding1.btnHoanTat.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String tenSach = binding1.edTenSach.getText().toString().trim();
                        String gia = binding1.edGia.getText().toString().trim();


                        if (TLSach.isEmpty() || tenSach.isEmpty() || gia.isEmpty()) {
                            Toast.makeText(getContext(), "Vui lòng nhập đữ liệu", Toast.LENGTH_SHORT).show();

                            return;
                        }else {
                            idTL = dao.getLoaiSachIdByTenTheLoai(TLSach);
                            SachModel objNew = new SachModel();

                            objNew.setTenSach(tenSach);
                            objNew.setGiaThue(Integer.parseInt(gia));
                            objNew.setTenTL(TLSach);
                            objNew.setIdTL(idTL);

                            dao.addSach(objNew);


                            loadDataFromSQL();
                            Toast.makeText(getContext(), "Thêm mới thành công", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }
                    }
                });
            }
        });
    }




}