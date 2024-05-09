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
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {
    private DatabaseHelper dbHelper;
    private EditText first_name_input;
    private EditText last_name_input;
    private EditText email_input;
    private EditText password_input;
    private Button login_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Window window = getWindow();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.neutral_950));
        }

        dbHelper = new DatabaseHelper(this);

        first_name_input = findViewById(R.id.first_name_input);
        last_name_input = findViewById(R.id.last_name_input);
        email_input = findViewById(R.id.email_input);
        password_input = findViewById(R.id.password_input);

        login_btn = findViewById(R.id.login_btn);

        login_btn.setOnClickListener(v -> {
            boolean isValid = validateInput(
                    first_name_input.getText().toString(),
                    last_name_input.getText().toString(),
                    email_input.getText().toString(),
                    password_input.getText().toString()
            );

            if(isValid) {
                login_btn.setText("Creating your account...");

                boolean isAccountCreated = dbHelper.createUserAccount(
                        first_name_input.getText().toString(),
                        last_name_input.getText().toString(),
                        email_input.getText().toString(),
                        password_input.getText().toString()
                );

                if(isAccountCreated) {
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    finish();
                } else {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        login_btn.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.danger_500)));
                    }

                    login_btn.setText("Restarting in 5s...");

                    Toast.makeText(this, "Oops, something went off, the app will restart in 5s", Toast.LENGTH_SHORT).show();

                    new Handler().postDelayed(() -> {
                        startActivity(new Intent(LoginActivity.this, WelcomeActivity.class));
                        finish();
                    }, 5000);
                }
            }
        });
    }

    public boolean validateInput(String firstName, String lastName, String email, String password) {
        if (firstName.isEmpty()) {
            Toast.makeText(this, "First name can not be empty", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(isFirstLetterSpecial(firstName.charAt(0))) {
            Toast.makeText(this, "Invalid first name, can't start with special character", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (lastName.isEmpty()) {
            Toast.makeText(this, "Last name can not be empty", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(isFirstLetterSpecial(lastName.charAt(0))) {
            Toast.makeText(this, "Invalid last name, can't start with special character", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (email.isEmpty()) {
            Toast.makeText(this, "Email can not be empty", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Email is invalid", Toast.LENGTH_SHORT).show();
            return false;
        }

        return validatePassword(password);
    }

    public boolean validatePassword(String password) {
        if (password.isEmpty()) {
            Toast.makeText(this, "Password can not be empty.", Toast.LENGTH_LONG).show();
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

    private boolean isFirstLetterSpecial(char ch){
        Character c = new Character(ch);
        String l = c.toString();

        return l.matches(".*[@\\-_#$!%*?&].*");
    }
}