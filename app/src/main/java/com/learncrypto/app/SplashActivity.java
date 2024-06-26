package com.learncrypto.app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

public class SplashActivity extends AppCompatActivity {
    private DatabaseHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Window window = getWindow();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.neutral_950));
        }

        db = new DatabaseHelper(this);
        db.init();

        boolean isLoggedIn = db.isUserHasAccount();

        new Handler().postDelayed(() -> {
            Intent intent = new Intent(SplashActivity.this, isLoggedIn ? MainActivity.class : WelcomeActivity.class);

            if(isLoggedIn) {
                intent.putExtra("fragment", "home");
            }

            startActivity(intent);
            finish();
        }, 2000);
    }
}