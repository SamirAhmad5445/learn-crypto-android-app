package com.learncrypto.app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ChangePasswordActivity extends AppCompatActivity {
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        Window window = getWindow();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.neutral_950));
        }

        dbHelper = new DatabaseHelper(this);

        String oldPassword = getIntent().getStringExtra("password");

        EditText old_password_input = findViewById(R.id.old_password_input);
        EditText new_password_input = findViewById(R.id.new_password_input);
        EditText confirm_password_input = findViewById(R.id.confirm_password_input);

        Button change_password_btn =findViewById(R.id.change_password_btn);

        change_password_btn.setOnClickListener(v -> {
            if(old_password_input.getText().toString().isEmpty()) {
                Toast.makeText(this, "Please enter your old password", Toast.LENGTH_SHORT).show();
                return;
            }

            assert oldPassword != null;
            if (!oldPassword.equals(old_password_input.getText().toString())) {
                Toast.makeText(this, "Wrong password, please enter your old password correctly", Toast.LENGTH_SHORT).show();
                return;
            }

            String newPassword = new_password_input.getText().toString();
            String confirmPassword = confirm_password_input.getText().toString();

            boolean isValid = validatePassword(newPassword);
            if(isValid) {
                if(!newPassword.equals(confirmPassword)) {
                    Toast.makeText(this, "Password mismatch, please confirm your password.", Toast.LENGTH_SHORT).show();
                    return;
                }

                change_password_btn.setText("Changing your password...");

                boolean isAccountUpdated = dbHelper.updateUserPassword(newPassword);

                if(isAccountUpdated) {
                    finish();
                } else {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        change_password_btn.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.danger_500)));
                    }

                    change_password_btn.setText("Restarting in 5s...");

                    Toast.makeText(this, "Oops, something went off, the app will restart in 5s", Toast.LENGTH_SHORT).show();

                    new Handler().postDelayed(() -> {
                        Intent intent = new Intent(ChangePasswordActivity.this, MainActivity.class);
                        intent.putExtra("fragment", "more");
                        startActivity(intent);
                        finish();
                    }, 5000);
                }
            }

        });

    }

    public boolean validatePassword(String password) {
        if (password.isEmpty()) {
            Toast.makeText(this, "New password can not be empty.", Toast.LENGTH_LONG).show();
            return false;
        }

        boolean hasUppercase = !password.equals(password.toLowerCase());
        boolean hasLowercase = !password.equals(password.toUpperCase());
        boolean hasDigit = password.matches(".*\\d.*");
        boolean hasSpecial = password.matches(".*[@\\-_#$!%*?&].*");

        if (password.length() < 8) {
            Toast.makeText(this, "Password must be at least 8 characters long.", Toast.LENGTH_LONG).show();
            return false;
        }

        if (!hasLowercase) {
            Toast.makeText(this, "Password must contain at least one lowercase letter.", Toast.LENGTH_LONG).show();
            return false;
        }

        if (!hasUppercase) {
            Toast.makeText(this, "Password must contain at least one uppercase letter.", Toast.LENGTH_LONG).show();
            return false;
        }

        if (!hasDigit) {
            Toast.makeText(this, "Password must contain at least one number.", Toast.LENGTH_LONG).show();
            return false;
        }

        if (!hasSpecial) {
            Toast.makeText(this, "Password must contain at least one special character (@-_#$!%*?&).", Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }
}