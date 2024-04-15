package com.learncrypto.app;

public class Lesson {
    private final String lessonName;
    private final String filePath;
    private final int level;

    public Lesson(String lessonName, String filePath, int level) {
        this.lessonName = lessonName;
        this.filePath = filePath;
        this.level = level;
    }

    public String getLessonName() {
        return lessonName;
    }

    public String getFilePath() {
        return filePath;
    }

    public int getLevel() {
        return level;
    }
}
