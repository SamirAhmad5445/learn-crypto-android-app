package com.learncrypto.app;

public class Lesson {
    private final  int id;
    private final String lessonName;
    private final String filePath;
    private final int isComplete; // boolean flag
    private final int level;

    public Lesson(int id, String lessonName, String filePath, int isComplete, int level) {
        this.id = id;
        this.lessonName = lessonName;
        this.filePath = filePath;
        this.isComplete = isComplete;
        this.level = level;
    }

    public int getId() { return id; }

    public String getLessonName() {
        return lessonName;
    }

    public int getIsComplete() { return isComplete; }

    public String getFilePath() {
        return filePath;
    }

    public int getLevel() {
        return level;
    }
}
