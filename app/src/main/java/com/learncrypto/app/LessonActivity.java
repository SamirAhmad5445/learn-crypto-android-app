package com.learncrypto.app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.text.Spanned;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import io.noties.markwon.Markwon;

public class LessonActivity extends AppCompatActivity {
    private int lessonId;
    private String filePath;
    private DatabaseHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson);

        // to darken the status bar
        Window window = getWindow();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.neutral_950));
        }

        lessonId = getIntent().getIntExtra("LESSON_ID", -1);
        filePath = getIntent().getStringExtra("LESSON_FILE_PATH");
        int isComplete = getIntent().getIntExtra("LESSON_IS_COMPLETE", 0);

        if(lessonId == -1) {
            Toast.makeText(this, "invalid lesson id", Toast.LENGTH_SHORT).show();
            return;
        }

        Button lesson_back_btn = findViewById(R.id.lesson_back_btn);
        String lessonString = "lesson " + lessonId;
        lesson_back_btn.setText(lessonString);
        lesson_back_btn.setOnClickListener(v -> finish());

        Markwon markwon = Markwon.create(this);

        final Spanned spanned = markwon.toMarkdown(getMarkdownContent());

        TextView lessonContent = findViewById(R.id.lesson_content);
        lessonContent.setText(spanned);

        dbHelper = new DatabaseHelper(this);

        QuestionAdapter questionAdapter = new QuestionAdapter(loadQuestionFromDb(),lessonId, isComplete);
        RecyclerView questionRecyclerView = findViewById(R.id.question_recyclerview);
        questionRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        questionRecyclerView.setAdapter(questionAdapter);
    }

    private String getMarkdownContent() {
        String markdown;
        InputStream file;

        try {
            file = getAssets().open(filePath);
            int size = file.available();
            byte[] buffer = new byte[size];
            file.read(buffer);
            file.close();
            markdown = new String(buffer, "UTF-8");
        } catch (IOException err) {
            err.printStackTrace();
            markdown = "";
        }

        return markdown;
    }

    private List<Question> loadQuestionFromDb() {
        Cursor cursor = dbHelper.getQuestionByLessonId(lessonId);

        if(cursor.getCount() == 0) {
            return null;
        }

        List<Question> questionList = new ArrayList<>();

        while (cursor.moveToNext()) {
            int questionId = cursor.getInt(0);
            String questionText = cursor.getString(1);
            String choiceA = cursor.getString(2);
            String choiceB = cursor.getString(3);
            String choiceC = cursor.getString(4);
            String correctChoice = cursor.getString(5);
            int isCorrect = cursor.getInt(7);

            questionList.add(new Question(
                    questionId,
                    questionText,
                    choiceA,
                    choiceB,
                    choiceC,
                    correctChoice,
                    isCorrect
            ));
        }

        return questionList;
    }
}