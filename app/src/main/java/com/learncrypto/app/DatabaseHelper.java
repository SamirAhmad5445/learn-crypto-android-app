package com.learncrypto.app;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "CryptographyCourse.db";
    public static final String DATABASE_PREFERENCES = "database_preferences";

    private final Context context;
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    public Context getContext() {
        return context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DatabaseContract.LevelTable.SQL_CREATE_LEVEL);
        db.execSQL(DatabaseContract.UserTable.SQL_CREATE_USER);
        db.execSQL(DatabaseContract.LessonTable.SQL_CREATE_LESSON);
        db.execSQL(DatabaseContract.QuestionTable.SQL_CREATE_QUESTION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DatabaseContract.UserTable.SQL_DELETE_USER);
        db.execSQL(DatabaseContract.LevelTable.SQL_DELETE_LEVEL);
        db.execSQL(DatabaseContract.LessonTable.SQL_DELETE_LESSON);
        db.execSQL(DatabaseContract.QuestionTable.SQL_DELETE_QUESTION);
        onCreate(db);
    }

    public boolean isExist() {
        SharedPreferences preferences = context.getSharedPreferences(DATABASE_PREFERENCES, Context.MODE_PRIVATE);
        return preferences.getBoolean("isExist", false);
    }

    public void init() {
        // check if the db has been created already
        if(isExist()) {
            return;
        }

        seedDb();

        SharedPreferences.Editor editor = context.getSharedPreferences(DATABASE_PREFERENCES, Context.MODE_PRIVATE).edit();
        editor.putBoolean("isExist", true);
        editor.apply();
    }

    public Cursor getData() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + DatabaseContract.LevelTable.TABLE_NAME;

        Cursor cursor = null;
        if(db!= null) {
            cursor = db.rawQuery(query, null);
        }

        return cursor;
    }

    private void seedDb(){
        SQLiteDatabase db = this.getWritableDatabase();

        seedLevelTable(db);
        seedLessonTable(db);
        seedQuestionTable(db);
    }

    private void seedLevelTable(SQLiteDatabase db) {
        InputStream seedFile = null;
        BufferedReader seedFileReader = null;

        try {
            seedFile = getContext().getAssets().open("levelTableSeed.txt");
            seedFileReader = new BufferedReader(new InputStreamReader(seedFile));

            db.beginTransaction();

            String line = seedFileReader.readLine();
            while (line != null) {
               ContentValues values = new ContentValues();
               values.put(DatabaseContract.LevelTable.COLUMN_NAME_LEVEL_NAME, line);

               db.insert(DatabaseContract.LevelTable.TABLE_NAME, null, values);

               line = seedFileReader.readLine();
            }

            db.setTransactionSuccessful();
        } catch (IOException err) {
            err.printStackTrace();
        } finally {
            db.endTransaction();
            if (seedFileReader != null) {
                try {
                    seedFileReader.close();
                } catch (IOException err) {
                    err.printStackTrace();
                }
            }
            if (seedFile != null) {
                try {
                    seedFile.close();
                } catch (IOException err) {
                    err.printStackTrace();
                }
            }
        }
    }

    private void seedLessonTable(SQLiteDatabase db) {
        InputStream seedFile = null;
        BufferedReader seedFileReader = null;

        try {
            seedFile = getContext().getAssets().open("lessonTableSeed.txt");
            seedFileReader = new BufferedReader(new InputStreamReader(seedFile));

            db.beginTransaction();

            String line = seedFileReader.readLine();
            while (line != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 3) {
                    ContentValues values = new ContentValues();
                    values.put(DatabaseContract.LessonTable.COLUMN_NAME_LESSON_NAME, parts[0]);
                    values.put(DatabaseContract.LessonTable.COLUMN_NAME_FILE_PATH, parts[1]);
                    values.put(DatabaseContract.LessonTable.COLUMN_NAME_FOREIGN_LEVEL_ID, parts[2]);
                    values.put(DatabaseContract.LessonTable.COLUMN_NAME_IS_COMPLETED, false);

                    db.insert(DatabaseContract.LessonTable.TABLE_NAME, null, values);
                } else {
                    Toast.makeText(context, "Failed to seed Lesson Table", Toast.LENGTH_SHORT).show();
                    break;
                }

                line = seedFileReader.readLine();
            }

            db.setTransactionSuccessful();
        } catch (IOException err) {
            err.printStackTrace();
        } finally {
            db.endTransaction();
            if (seedFileReader != null) {
                try {
                    seedFileReader.close();
                } catch (IOException err) {
                    err.printStackTrace();
                }
            }
            if (seedFile != null) {
                try {
                    seedFile.close();
                } catch (IOException err) {
                    err.printStackTrace();
                }
            }
        }
    }

    private void seedQuestionTable(SQLiteDatabase db) {
        InputStream seedFile = null;
        BufferedReader seedFileReader = null;

        try {
            seedFile = getContext().getAssets().open("questionTableSeed.txt");
            seedFileReader = new BufferedReader(new InputStreamReader(seedFile));

            db.beginTransaction();

            String line = seedFileReader.readLine();
            while(line != null) {
                String[] parts = line.split("\\|");
                if(parts.length == 6) {
                    ContentValues values = new ContentValues();
                    values.put(DatabaseContract.QuestionTable.COLUMN_NAME_QUESTION_TEXT, parts[0]);
                    values.put(DatabaseContract.QuestionTable.COLUMN_NAME_CHOICE_A, parts[1]);
                    values.put(DatabaseContract.QuestionTable.COLUMN_NAME_CHOICE_B, parts[2]);
                    values.put(DatabaseContract.QuestionTable.COLUMN_NAME_CHOICE_C, parts[3]);
                    values.put(DatabaseContract.QuestionTable.COLUMN_NAME_CORRECT_CHOICE, parts[4]);
                    values.put(DatabaseContract.QuestionTable.COLUMN_NAME_FOREIGN_LESSON_ID, parts[5]);
//                    values.put(DatabaseContract.QuestionTable.COLUMN_NAME_USER_CHOICE, "not answered");
                    values.put(DatabaseContract.QuestionTable.COLUMN_NAME_IS_CORRECT, false);

                    db.insert(DatabaseContract.QuestionTable.TABLE_NAME, null, values);
                }

                line = seedFileReader.readLine();
            }

            db.setTransactionSuccessful();
        } catch (IOException err) {
            err.printStackTrace();
        } finally {
            db.endTransaction();
            if (seedFileReader != null) {
                try {
                    seedFileReader.close();
                } catch (IOException err) {
                    err.printStackTrace();
                }
            }
            if (seedFile != null) {
                try {
                    seedFile.close();
                } catch (IOException err) {
                    err.printStackTrace();
                }
            }
        }
    }
}
