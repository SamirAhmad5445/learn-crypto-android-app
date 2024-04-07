package com.learncrypto.app;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.learncrypto.app.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Start app with home fragment
        updateFragment(new HomeFragment());

        // add select listener for th bottom nav
        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            int id = item.getItemId();

            if(id == R.id.navItemHome) {
                updateFragment(new HomeFragment());
            } else if(id == R.id.navItemLearn) {
                updateFragment(new LearnFragment());
            } else if(id == R.id.navItemEncrypt) {
                updateFragment(new EncryptFragment());
            } else if(id == R.id.navItemMore) {
                updateFragment(new MoreFragment());
            }

            return true;
        });
    }


    public void updateFragment(Fragment f) {
        FragmentManager m = getSupportFragmentManager();
        FragmentTransaction t = m.beginTransaction();
        t.replace(R.id.frameLayout, f);
        t.commit();
    }
}