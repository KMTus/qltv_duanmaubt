package com.example.qltv_duanmau.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.example.qltv_duanmau.R;
import com.example.qltv_duanmau.databinding.ActivityWelcomeBinding;
import com.example.qltv_duanmau.views.fragment.LoginFragment;
import com.example.qltv_duanmau.views.fragment.ManHinhChao;

public class WelcomeActivity extends AppCompatActivity {
    ActivityWelcomeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWelcomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, new ManHinhChao()).commit();


    }
}