package com.learncrypto.app;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MoreFragment extends Fragment {
    private DatabaseHelper dbHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_more, container, false);
        dbHelper = new DatabaseHelper(view.getContext());

        TextView user_name = view.findViewById(R.id.profile_user_name);
        TextView user_email = view.findViewById(R.id.profile_user_email);
        TextView user_score = view.findViewById(R.id.profile_user_score);
        TextView user_level = view.findViewById(R.id.profile_user_level);
        TextView user_lessons_count = view.findViewById(R.id.profile_user_lessons_count);
        TextView user_questions_count = view.findViewById(R.id.profile_user_questions_count);

        Button edit_btn = view.findViewById(R.id.profile_edit_btn);
        Button password_btn = view.findViewById(R.id.profile_password_btn);
        Button delete_btn = view.findViewById(R.id.profile_delete_btn);

        UserAccount user = dbHelper.getUserAccount();

        if(user == null) {
            Toast.makeText(view.getContext(), "Oops, something went wrong!", Toast.LENGTH_SHORT).show();
        } else {
            user_name.setText(user.getFullName().toLowerCase());
            user_email.setText(user.getEmail());
            user_score.setText(String.valueOf(dbHelper.getUserScore()));
            user_level.setText(getLevelString());
            user_lessons_count.setText(String.valueOf(dbHelper.getFinishedLessonCount()));
            user_questions_count.setText(String.valueOf(dbHelper.getCorrectQuestionCount()));
        }

        edit_btn.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), EditProfileActivity.class);
            if(user != null) {
                intent.putExtra("user_first_name", user.getFirstName());
                intent.putExtra("user_last_name", user.getLastName());
                intent.putExtra("user_email", user.getEmail());
            }
            startActivity(intent);
        });

        password_btn.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), ChangePasswordActivity.class);
            if(user != null) {
                intent.putExtra("password", user.getPassword());
            }
            startActivity(intent);
        });

        delete_btn.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), DeleteAccountActivity.class);
            if(user != null) {
                intent.putExtra("password", user.getPassword());
            }
            startActivity(intent);
            getActivity().finish();
        });

        return view;
    }

    private String getLevelString() {
        int level = dbHelper.getUserLevel();
        return level < 10 ? ("0" + level) : String.valueOf(level);
    }
}