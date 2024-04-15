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
    private List<Lesson> lessonList;
    private String hello;
    private DatabaseHelper dbHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_learn, container, false);

        lessonList = new ArrayList<Lesson>();
        lessonAdapter = new LessonAdapter(lessonList);

        lessonRecyclerView = view.findViewById(R.id.learn_recyclerview);
        lessonRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        lessonRecyclerView.setAdapter(lessonAdapter);

        dbHelper = new DatabaseHelper(getActivity());
        leadLessonsDataFromDB();
        TextView txt = (TextView) view.findViewById(R.id.hello);
        txt.setText(hello);
        return view;
    }

    private void leadLessonsDataFromDB() {
        Cursor cursor = dbHelper.getData();

        if(cursor.getCount() == 0) {
            Toast.makeText(getContext(), "No data", Toast.LENGTH_SHORT).show();
        } else {
            while(cursor.moveToNext()) {
                hello = cursor.getString(1);
            }
        }

    }
}