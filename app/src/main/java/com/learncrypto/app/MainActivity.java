package com.learncrypto.app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.learncrypto.app.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    String currentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // to darken the status bar
        Window window = getWindow();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.neutral_950));
        }

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Start app with home fragment
        String intentFragment = getIntent().getStringExtra("fragment");
        if(intentFragment != null) {
            updateFragmentByName(intentFragment);
            currentFragment = intentFragment;
        } else {
            updateFragmentByName("home");
            currentFragment = "home";
        }


        // add select listener for th bottom nav
        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            int id = item.getItemId();

            if(id == R.id.navItemHome) {
                updateFragment(new HomeFragment());
                currentFragment = "home";
            } else if(id == R.id.navItemLearn) {
                updateFragment(new LearnFragment());
                currentFragment = "learn";
            } else if(id == R.id.navItemEncrypt) {
                updateFragment(new EncryptFragment());
                currentFragment = "encrypt";
            } else if(id == R.id.navItemMore) {
                updateFragment(new MoreFragment());
                currentFragment = "more";
            }
            return true;
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateFragmentByName(currentFragment);
    }


    public void updateFragment(Fragment f) {
        FragmentManager m = getSupportFragmentManager();
        FragmentTransaction t = m.beginTransaction();
        t.replace(R.id.frame_container, f);
        t.commit();
    }

    public void updateFragmentByName(String fragmentName) {
        switch (fragmentName.toLowerCase()) {
            default:
            case "home":
                updateFragment(new HomeFragment());
                binding.bottomNavigationView.setSelectedItemId(R.id.navItemHome);
                break;
            case "learn":
                updateFragment(new LearnFragment());
                binding.bottomNavigationView.setSelectedItemId(R.id.navItemLearn);
                break;
            case "encrypt":
                updateFragment(new EncryptFragment());
                binding.bottomNavigationView.setSelectedItemId(R.id.navItemEncrypt);
                break;
            case "more":
                updateFragment(new MoreFragment());
                binding.bottomNavigationView.setSelectedItemId(R.id.navItemMore);
                break;
        }
    }
}