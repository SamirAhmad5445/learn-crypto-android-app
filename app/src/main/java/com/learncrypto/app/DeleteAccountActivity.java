package com.learncrypto.app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class DeleteAccountActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_account);

        Window window = getWindow();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.neutral_950));
        }

        DatabaseHelper dbHelper = new DatabaseHelper(this);

        String password = getIntent().getStringExtra("password");
        EditText password_input = findViewById(R.id.delete_password);
        Button delete_btn = findViewById(R.id.delete_btn);

        delete_btn.setOnClickListener(v -> {
            if(password == null || !password.equals(password_input.getText().toString())) {
                Toast.makeText(this, "Wrong Password.", Toast.LENGTH_SHORT).show();
                return;
            }

            delete_btn.setText("Deleting Account...");
            dbHelper.deleteUserAccount();

            Intent intent = new Intent(DeleteAccountActivity.this, SplashActivity.class);
            startActivity(intent);
            finish();
        });
    }
}