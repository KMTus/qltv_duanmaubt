package com.example.qltv_duanmau.views.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.qltv_duanmau.R;
import com.example.qltv_duanmau.adapter.TopBookAdapter;
import com.example.qltv_duanmau.dao.ThongKeDao;
import com.example.qltv_duanmau.databinding.FragmentThongKeTopBinding;
import com.example.qltv_duanmau.models.TopBookModel;

import java.util.List;


public class ThongKeTopFragment extends Fragment {

    private FragmentThongKeTopBinding binding;

    private TopBookAdapter topBookAdapter;

    private List<TopBookModel> topBookModelList;

    private ThongKeDao staticDao;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentThongKeTopBinding.inflate(inflater, container, false);

        staticDao = new ThongKeDao(requireContext());

        topBookModelList = staticDao.getTopBookModels();

        initRecyclerview();

        return binding.getRoot();

    }

    private void initRecyclerview() {
        topBookAdapter = new TopBookAdapter(requireContext(), topBookModelList);
        binding.rcvItems.setAdapter(topBookAdapter);
    }
}