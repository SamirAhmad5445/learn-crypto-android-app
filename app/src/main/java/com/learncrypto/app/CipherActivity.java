package com.learncrypto.app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

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

        TextView cipher_name = findViewById(R.id.cipher_name);
        cipher_name.setText(cipherName);

        Button cipher_back_btn = findViewById(R.id.cipher_back_btn);
        cipher_back_btn.setOnClickListener(v -> {
            Intent intent = new Intent(CipherActivity.this, MainActivity.class);
            startActivity(intent);
        });
    }
}