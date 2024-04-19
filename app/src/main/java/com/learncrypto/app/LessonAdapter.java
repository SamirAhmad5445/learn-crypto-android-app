package com.learncrypto.app;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class LessonAdapter extends RecyclerView.Adapter<LessonAdapter.LessonViewHolder> {
    private final List<Lesson> lessonList;

    private final OnItemClickListener listener;

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
            int lessonId = lesson.getId();
            String lessonName = lesson.getLessonName();
            String filePath = lesson.getFilePath();
            int isComplete = lesson.getIsComplete();
            int level = lesson.getLevel();

            String lessonCardTitle = lessonId + ". " + lessonName;

            holder.lesson_card_title.setText(lessonCardTitle);
            holder.lesson_card_level.setText(holder.getLevelString(level));
            if(isComplete == 0) {
                holder.lesson_card_is_complete.setVisibility(View.INVISIBLE);
            }

            holder.lesson_card_is_success.setVisibility(View.INVISIBLE);
            holder.lesson_card_is_failed.setVisibility(View.INVISIBLE);

            holder.setFilePath(filePath);
            holder.itemView.setOnClickListener(v -> listener.onItemClick(lesson));
        }
    }

    @Override
    public int getItemCount() {
        return lessonList.size();
    }

    public static class LessonViewHolder extends RecyclerView.ViewHolder {
        public TextView lesson_card_title;
        public TextView lesson_card_level;
        public CardView lesson_card_is_complete;
        public CardView lesson_card_is_success;
        public CardView lesson_card_is_failed;
        public String filePath;
        public LessonViewHolder(@NonNull View itemView) {
            super(itemView);
            lesson_card_title = itemView.findViewById(R.id.lesson_card_title);
            lesson_card_level = itemView.findViewById(R.id.lesson_card_level);
            lesson_card_is_complete = itemView.findViewById(R.id.lesson_card_is_complete);
            lesson_card_is_success = itemView.findViewById(R.id.lesson_card_is_success);
            lesson_card_is_failed = itemView.findViewById(R.id.lesson_card_is_failed);
        }

        public void setFilePath(String filePath) {
            this.filePath = filePath;
        }


        public String getLevelString(int level) {
            return "Level " + level;
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Lesson lesson);
    }
}
