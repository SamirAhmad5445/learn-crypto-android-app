package com.learncrypto.app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import org.commonmark.node.Heading;

import java.io.IOException;
import java.io.InputStream;

import io.noties.markwon.AbstractMarkwonPlugin;
import io.noties.markwon.Markwon;
import io.noties.markwon.MarkwonConfiguration;
import io.noties.markwon.MarkwonSpansFactory;
import io.noties.markwon.RenderProps;
import io.noties.markwon.SpanFactory;
import io.noties.markwon.core.MarkwonTheme;

public class LessonActivity extends AppCompatActivity {
    private String filePath;
    private Markwon markwon;
    TextView lessonContent;
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

        filePath = getIntent().getStringExtra("LESSON_FILE_PATH");

        markwon =  Markwon.create(this);

        final Spanned spanned = markwon.toMarkdown(getMarkdownContent());

        lessonContent = findViewById(R.id.lesson_content);
        lessonContent.setText(spanned);

    }

    private String getMarkdownContent() {
        String markdown;
        InputStream file = null;

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
}