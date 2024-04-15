package com.learncrypto.app;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class LessonAdapter extends RecyclerView.Adapter<LessonAdapter.LessonViewHolder> {
    private List<Lesson> lessonList;

    public LessonAdapter(List<Lesson> lessonList) {
        this.lessonList = lessonList;
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
        holder.lesson_card_title.setText(lesson.getLessonName());
        holder.lesson_card_level.setText(lesson.getLevel());
    }

    @Override
    public int getItemCount() {
        return lessonList.size();
    }

    public static class LessonViewHolder extends RecyclerView.ViewHolder {
        public TextView lesson_card_title;
        public TextView lesson_card_level;
        public LessonViewHolder(@NonNull View itemView) {
            super(itemView);
            lesson_card_title = itemView.findViewById(R.id.lesson_card_title);
            lesson_card_level = itemView.findViewById(R.id.lesson_card_level);

            // remember to set onClickListener
        }
    }
}
