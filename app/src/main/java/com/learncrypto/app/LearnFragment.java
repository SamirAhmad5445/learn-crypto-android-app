package com.learncrypto.app;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class LearnFragment extends Fragment {
    private DatabaseHelper dbHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_learn, container, false);

        dbHelper = new DatabaseHelper(getActivity());
        dbHelper.init();

        LessonAdapter lessonAdapter = new LessonAdapter(loadLessonsDataFromDB(), (lesson) -> {
            Intent intent = new Intent(getActivity(), LessonActivity.class);
            intent.putExtra("LESSON_ID", lesson.getId());
            intent.putExtra("LESSON_FILE_PATH", lesson.getFilePath());
            intent.putExtra("LESSON_IS_COMPLETE", lesson.getIsComplete());

            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivityForResult(intent, 1);
        });

        RecyclerView lessonRecyclerView = view.findViewById(R.id.learn_recyclerview);
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
                int lessonId = cursor.getInt(0);
                String lessonName = cursor.getString(1);
                String filePath = cursor.getString(2);
                int isComplete = cursor.getInt(3);
                int levelId = cursor.getInt(4);

                lessonsList.add(new Lesson(lessonId, lessonName, filePath, isComplete, levelId));
            }
        }
        return lessonsList;
    }
}