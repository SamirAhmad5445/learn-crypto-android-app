package com.learncrypto.app;

import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class HomeFragment extends Fragment {
    private View view;
    private DatabaseHelper dbHelper;
    private int newCardCount = 2;
    private Button get_started_btn;
    private TextView home_user_name;
    private TextView home_user_score;
    private TextView home_user_level;
    private TextView home_finished_lessons;
    private Button btn_try_spn;
    private Button btn_dismiss_spn;
    private Button btn_try_hill;
    private Button btn_dismiss_hill;
    private Button btn_view_lessons;
    private Button btn_view_ciphers;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);


        get_started_btn = view.findViewById(R.id.get_started_btn);

        home_user_name = view.findViewById(R.id.home_user_name);
        home_user_score = view.findViewById(R.id.home_user_score);
        home_user_level = view.findViewById(R.id.home_user_level);
        home_finished_lessons = view.findViewById(R.id.home_finished_lessons);

        btn_try_spn = view.findViewById(R.id.btn_try_spn);
        btn_dismiss_spn = view.findViewById(R.id.btn_dismiss_spn);

        btn_try_hill = view.findViewById(R.id.btn_try_hill);
        btn_dismiss_hill = view.findViewById(R.id.btn_dismiss_hill);

        btn_view_lessons = view.findViewById(R.id.btn_view_lessons);
        btn_view_ciphers = view.findViewById(R.id.btn_view_ciphers);

        dbHelper = new DatabaseHelper(getActivity());

        UserAccount user = dbHelper.getUserAccount();

        if(user == null) {
            Toast.makeText(view.getContext(), "Oops, something went wrong!", Toast.LENGTH_SHORT).show();
        } else {
            home_user_name.setText(user.getFullName().toLowerCase());
            home_user_score.setText(String.valueOf(user.getScore()));
            home_user_level.setText(getLevelString(dbHelper.getUserLevel()));
            home_finished_lessons.setText(String.valueOf(dbHelper.getFinishedLessonCount()));
        }

        get_started_btn.setOnClickListener(v -> {
                // get the main activity which ia the parent activity
                MainActivity mainActivity = (MainActivity)getActivity();

                // new learnFragment is needed to navigate to it
                Fragment learnFragment = new LearnFragment();

                // use the updateFragment to replace fragments + use setSelected to update the bottom nav item id
                if (mainActivity != null) {
                    mainActivity.updateFragment(learnFragment);
                    mainActivity.binding.bottomNavigationView.setSelectedItemId(R.id.navItemLearn);
                }
            }
        );

        btn_try_spn.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), CipherActivity.class);
            intent.putExtra("CIPHER_NAME", Ciphers.SPN.CIPHER_NAME);
            startActivity(intent);
        });

        btn_dismiss_spn.setOnClickListener(v -> {
            CardView spn_card = view.findViewById(R.id.new_cipher_spn);
            spn_card.setVisibility(View.GONE);
            newCardCount -= 1;
            if(newCardCount == 0) {
                TextView new_cipher_label = view.findViewById(R.id.new_cipher_label);
                new_cipher_label.setVisibility(View.GONE);
            }
        });

        btn_try_hill.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), CipherActivity.class);
            intent.putExtra("CIPHER_NAME", Ciphers.Hill.CIPHER_NAME);
            startActivity(intent);
        });

        btn_dismiss_hill.setOnClickListener(v -> {
            CardView hill_card = view.findViewById(R.id.new_cipher_hill);
            hill_card.setVisibility(View.GONE);
            newCardCount -= 1;
            if(newCardCount == 0) {
                TextView new_cipher_label = view.findViewById(R.id.new_cipher_label);
                new_cipher_label.setVisibility(View.GONE);
            }
        });

        btn_view_lessons.setOnClickListener(v -> {
            MainActivity mainActivity = (MainActivity)getActivity();
            Fragment learnFragment = new LearnFragment();

            if (mainActivity != null) {
                mainActivity.updateFragment(learnFragment);
                mainActivity.binding.bottomNavigationView.setSelectedItemId(R.id.navItemLearn);
            }
        });

        btn_view_ciphers.setOnClickListener(v -> {
            MainActivity mainActivity = (MainActivity)getActivity();
            Fragment encryptFragment = new EncryptFragment();

            if (mainActivity != null) {
                mainActivity.updateFragment(encryptFragment);
                mainActivity.binding.bottomNavigationView.setSelectedItemId(R.id.navItemEncrypt);
            }
        });


        return view;
    }

    private String getLevelString(int level) {
        return level < 10 ? ("0" + level) : String.valueOf(level);
    }
}