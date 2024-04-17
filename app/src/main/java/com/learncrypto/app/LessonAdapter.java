package com.learncrypto.app;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class LessonAdapter extends RecyclerView.Adapter<LessonAdapter.LessonViewHolder> {
    private List<Lesson> lessonList;

    private OnItemClickListener listener;

    public LessonAdapter(List<Lesson> lessonList, OnItemClickListener listener) {
        this.lessonList = lessonList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public LessonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.lesson_card_item, parent, false);

        return new LessonViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull LessonViewHolder holder, int position) {
        Lesson lesson = lessonList.get(position);
        if(lesson != null) {
            String lessonName = lesson.getLessonName();
            String filePath = lesson.getFilePath();
            int level = lesson.getLevel();

            holder.lesson_card_title.setText(lessonName);
            holder.lesson_card_level.setText(holder.getLevelString(level));
            holder.setFilePath(filePath);
            holder.itemView.setOnClickListener(v -> {
                listener.onItemClick(filePath);
            });
        }
    }

    @Override
    public int getItemCount() {
        return lessonList.size();
    }

    public static class LessonViewHolder extends RecyclerView.ViewHolder {
        public TextView lesson_card_title;
        public TextView lesson_card_level;
        public String filePath;
        public LessonViewHolder(@NonNull View itemView) {
            super(itemView);
            lesson_card_title = itemView.findViewById(R.id.lesson_card_title);
            lesson_card_level = itemView.findViewById(R.id.lesson_card_level);
        }

        public void setFilePath(String filePath) {
            this.filePath = filePath;
        }

        public String getFilePath() {
            return filePath;
        }

        public String getLevelString(int level) {
            return "Level " + level;
        }
    }

    public interface OnItemClickListener {
        void onItemClick(String lessonFileName);
    }
}
