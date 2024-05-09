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

public class EditProfileActivity extends AppCompatActivity {
    private DatabaseHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        Window window = getWindow();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.neutral_950));
        }

        dbHelper = new DatabaseHelper(this);

        String user_first_name = getIntent().getStringExtra("user_first_name");
        String user_last_name = getIntent().getStringExtra("user_last_name");
        String user_email = getIntent().getStringExtra("user_email");

        EditText first_name_input = findViewById(R.id.edit_first_name_input);
        EditText last_name_input = findViewById(R.id.edit_last_name_input);
        EditText email_input = findViewById(R.id.edit_email_input);

        Button update_btn = findViewById(R.id.update_btn);

        first_name_input.setHint(user_first_name);
        first_name_input.setText(user_first_name);
        last_name_input.setHint(user_last_name);
        last_name_input.setText(user_last_name);
        email_input.setHint(user_email);
        email_input.setText(user_email);

        update_btn.setOnClickListener(v -> {
            boolean isValid = validateInput(
                    first_name_input.getText().toString(),
                    last_name_input.getText().toString(),
                    email_input.getText().toString()
            );

            if(isValid) {
                update_btn.setText("Updating your account...");

                boolean isAccountUpdated = dbHelper.updateUserAccount(
                        first_name_input.getText().toString(),
                        last_name_input.getText().toString(),
                        email_input.getText().toString()
                );

                if(isAccountUpdated) {
                    finish();
                } else {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        update_btn.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.danger_500)));
                    }

                    update_btn.setText("Restarting in 5s...");

                    Toast.makeText(this, "Oops, something went off, the app will restart in 5s", Toast.LENGTH_SHORT).show();

                    new Handler().postDelayed(() -> {
                        Intent intent = new Intent(EditProfileActivity.this, MainActivity.class);
                        intent.putExtra("fragment", "more");
                        startActivity(intent);
                        finish();
                    }, 5000);
                }
            }
        });
    }

    public boolean validateInput(String firstName, String lastName, String email) {
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


        if(isFirstLetterSpecial(firstName.charAt(0))) {
            Toast.makeText(this, "Invalid first name, can't start with special character", Toast.LENGTH_SHORT).show();
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

        return true;
    }

    private boolean isFirstLetterSpecial(char ch){
        Character c = new Character(ch);
        String l = c.toString();

        return l.matches(".*[@\\-_#$!%*?&].*");
    }
}