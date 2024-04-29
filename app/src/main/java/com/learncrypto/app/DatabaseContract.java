package com.learncrypto.app;

public class DatabaseContract {
    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private DatabaseContract() {}

    // User Table:
    public static class UserTable {
        public static final String TABLE_NAME = "user_table";
        public static final String COLUMN_NAME_ID = "_user_id";
        public static final String COLUMN_NAME_FIRST_NAME = "first_name";
        public static final String COLUMN_NAME_LAST_NAME = "last_name";
        public static final String COLUMN_NAME_SCORE = "score";
        // Foreign key for the 1:1 relationship any user has 1 current level
        public static final String COLUMN_NAME_USER_LEVEL = "user_level";
        public static final String SQL_CREATE_USER =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        COLUMN_NAME_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        COLUMN_NAME_FIRST_NAME + " TEXT NOT NULL," +
                        COLUMN_NAME_LAST_NAME + " TEXT NOT NULL," +
                        COLUMN_NAME_SCORE + " INTEGER NOT NULL," +
                        COLUMN_NAME_USER_LEVEL + " INTEGER," +
                        "FOREIGN KEY (" + COLUMN_NAME_USER_LEVEL + ") REFERENCES " +
                        LevelTable.TABLE_NAME + " (" + LevelTable.COLUMN_NAME_ID + "));";
        public static final String SQL_DELETE_USER =
                "DROP TABLE IF EXISTS " + TABLE_NAME + ";";
    }

    // Level Table:
    public static class LevelTable {
        public static final String TABLE_NAME = "level_table";
        public static final String SEED_FILE = "levelTableSeed.txt";
        public static final String COLUMN_NAME_ID = "_level_id";
        public static final String COLUMN_NAME_LEVEL_NAME = "level_name";
        public static final String SQL_CREATE_LEVEL =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        COLUMN_NAME_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        COLUMN_NAME_LEVEL_NAME + " TEXT NOT NULL);";
        public static final String SQL_DELETE_LEVEL =
                "DROP TABLE IF EXISTS " + TABLE_NAME + ";";
    }

    // Lesson Table:
    public static class LessonTable {
        public static final String TABLE_NAME = "lesson_table";
        public static final String SEED_FILE = "lessonTableSeed.txt";
        public static final String COLUMN_NAME_ID = "_lesson_id";
        public static final String COLUMN_NAME_LESSON_NAME = "lesson_name";
        public static final String COLUMN_NAME_FILE_PATH = "file_path";
        public static final String COLUMN_NAME_IS_FINISHED = "is_finished";
        public static final String COLUMN_NAME_FOREIGN_LEVEL_ID = "foreign_level_id";
        public static final String SQL_CREATE_LESSON =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        COLUMN_NAME_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        COLUMN_NAME_LESSON_NAME + " TEXT NOT NULL," +
                        COLUMN_NAME_FILE_PATH + " TEXT NOT NULL," +
                        COLUMN_NAME_IS_FINISHED + " BOOLEAN DEFAULT FALSE," +
                        COLUMN_NAME_FOREIGN_LEVEL_ID + " INTEGER," +
                        "FOREIGN KEY (" + COLUMN_NAME_FOREIGN_LEVEL_ID + ") REFERENCES " +
                        LevelTable.TABLE_NAME + " (" + LevelTable.COLUMN_NAME_ID + "));";
        public static final String SQL_DELETE_LESSON =
                "DROP TABLE IF EXISTS " + TABLE_NAME + ";";
    }

    // Question Table:
    public static class QuestionTable {
        public static final String TABLE_NAME = "question_table";
        public static final String SEED_FILE = "questionTableSeed.txt";
        public static final String COLUMN_NAME_ID = "_question_id";
        public static final String COLUMN_NAME_QUESTION_TEXT = "question_text";
        public static final String COLUMN_NAME_CHOICE_A = "choice_a";
        public static final String COLUMN_NAME_CHOICE_B = "choice_b";
        public static final String COLUMN_NAME_CHOICE_C = "choice_c";
        public static final String COLUMN_NAME_CORRECT_CHOICE = "correct_choice";
        public static final String COLUMN_NAME_USER_CHOICE = "user_choice";
        public static final String COLUMN_NAME_IS_CORRECT = "is_correct";
        public static final String COLUMN_NAME_FOREIGN_LESSON_ID = "foreign_lesson_id";
        public static final String SQL_CREATE_QUESTION =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        COLUMN_NAME_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        COLUMN_NAME_QUESTION_TEXT + " TEXT NOT NULL," +
                        COLUMN_NAME_CHOICE_A + " TEXT NOT NULL," +
                        COLUMN_NAME_CHOICE_B + " TEXT NOT NULL," +
                        COLUMN_NAME_CHOICE_C + " TEXT NOT NULL," +
                        COLUMN_NAME_CORRECT_CHOICE + " TEXT NOT NULL," +
                        COLUMN_NAME_USER_CHOICE + " TEXT," +
                        COLUMN_NAME_IS_CORRECT + " BOOLEAN DEFAULT FALSE," +
                        COLUMN_NAME_FOREIGN_LESSON_ID + " INTEGER," +
                        "FOREIGN KEY (" + COLUMN_NAME_FOREIGN_LESSON_ID + ") REFERENCES " +
                        LessonTable.TABLE_NAME + " (" + LessonTable.COLUMN_NAME_ID + "));";
        public static final String SQL_DELETE_QUESTION =
                "DROP TABLE IF EXISTS " + TABLE_NAME + ";";
    }
}
