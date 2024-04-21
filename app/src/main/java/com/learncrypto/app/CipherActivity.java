package com.learncrypto.app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import java.util.Objects;

public class CipherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cipher);

        Window window = getWindow();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.neutral_950));
        }

        String cipherName = getIntent().getStringExtra("CIPHER_NAME");


        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Fragment fragment = null;
        if(Objects.equals(cipherName, Ciphers.Shift.CIPHER_NAME)) {
            fragment= new ShiftCipherFragment();
        } else if(Objects.equals(cipherName, Ciphers.Affine.CIPHER_NAME)) {
            fragment= new AffineCipherFragment();
        }

        if (fragment != null) {
            transaction.replace(R.id.cipher_container, fragment);
            transaction.commit();
        }

        Button cipher_back_btn = findViewById(R.id.cipher_back_btn);
        cipher_back_btn.setText(cipherName);
        cipher_back_btn.setOnClickListener(v -> {
            finish();
        });
    }
}