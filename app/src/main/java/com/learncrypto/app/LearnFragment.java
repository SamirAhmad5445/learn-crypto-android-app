package com.learncrypto.app;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class LearnFragment extends Fragment {
    private RecyclerView lessonRecyclerView;
    private LessonAdapter lessonAdapter;
    private String hello;
    private DatabaseHelper dbHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_learn, container, false);

        dbHelper = new DatabaseHelper(getActivity());
        dbHelper.init();

        lessonAdapter = new LessonAdapter(loadLessonsDataFromDB());

        lessonRecyclerView = view.findViewById(R.id.learn_recyclerview);
        lessonRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        lessonRecyclerView.setAdapter(lessonAdapter);

        return view;
    }

    private List<Lesson> loadLessonsDataFromDB() {
        List<Lesson> lessonsList = new ArrayList<>();
        Cursor cursor = dbHelper.getLessonsData();

        if(cursor.getCount() == 0) {
            Toast.makeText(getContext(), "No data", Toast.LENGTH_SHORT).show();
        } else {
            while(cursor.moveToNext()) {
                String lessonName = cursor.getString(1);
                String filePath = cursor.getString(2);
                int levelId = cursor.getInt(4);

                lessonsList.add(new Lesson(lessonName, filePath, levelId));
            }
        }
        return lessonsList;
    }
}