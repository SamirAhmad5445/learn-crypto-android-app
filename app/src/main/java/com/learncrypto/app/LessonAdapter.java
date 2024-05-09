package com.learncrypto.app;

import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.List;

public class LessonAdapter extends RecyclerView.Adapter<LessonAdapter.LessonViewHolder> {
    private DatabaseHelper dbHelper;
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

        dbHelper = new DatabaseHelper(itemView.getContext());

        return new LessonViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull LessonViewHolder holder, int position) {
        Lesson lesson = lessonList.get(position);
        if (lesson != null) {
            int lessonId = lesson.getId();
            String lessonName = lesson.getLessonName();
            String filePath = lesson.getFilePath();
            int level = lesson.getLevel();

            String lessonCardTitle = lessonId + ". " + lessonName;
            boolean isLessonFinished = dbHelper.isLessonFinished(lessonId);
            int lessonMark = dbHelper.getLessonMark(lessonId);

            holder.lesson_card_title.setText(lessonCardTitle);
            holder.lesson_card_level.setText(holder.getLevelString(level));

            holder.lesson_card_is_complete.setVisibility(View.VISIBLE);
            holder.lesson_mark.setVisibility(View.VISIBLE);
            holder.paintLessonMark(lessonMark > 1);

            if(!isLessonFinished) {
                holder.lesson_card_is_complete.setVisibility(View.GONE);
            }

            if(lessonMark == -1) {
                holder.lesson_mark.setVisibility(View.GONE);
            } else {
                String[] messages = {"failed", "nice try", "good job", "full mark"};
                TextView textView = (TextView) holder.lesson_mark.getChildAt(0);
                textView.setText(String.format("%s (%d/3)", messages[lessonMark], lessonMark));
            }

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
        public CardView lesson_mark;
        public String filePath;
        public LessonViewHolder(@NonNull View itemView) {
            super(itemView);
            lesson_card_title = itemView.findViewById(R.id.lesson_card_title);
            lesson_card_level = itemView.findViewById(R.id.lesson_card_level);
            lesson_card_is_complete = itemView.findViewById(R.id.lesson_card_is_complete);
            lesson_mark = itemView.findViewById(R.id.lesson_mark);
        }

        public void setFilePath(String filePath) {
            this.filePath = filePath;
        }

        public String getLevelString(int level) {
            return "Level " + level;
        }

        public void paintLessonMark(boolean isSuccess) {
            int color = isSuccess
                    ? ContextCompat.getColor(itemView.getContext(), R.color.primary_950)
                    : ContextCompat.getColor(itemView.getContext(), R.color.danger_900);

            int background = isSuccess
                    ? ContextCompat.getColor(itemView.getContext(), R.color.primary_200)
                    : ContextCompat.getColor(itemView.getContext(), R.color.danger_200);

            TextView text = (TextView) lesson_mark.getChildAt(0);

            text.setTextColor(color);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                lesson_mark.setBackgroundTintList(ColorStateList.valueOf(background));
            }}
    }


    public interface OnItemClickListener {
        void onItemClick(Lesson lesson);
    }
}
