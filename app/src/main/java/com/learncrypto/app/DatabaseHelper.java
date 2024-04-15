package com.learncrypto.app;

import android.content.ContentValues;
import android.content.Context;
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

    private void seedDb(){
        SQLiteDatabase db = this.getWritableDatabase();
        seedLevelTable(db);
//        seedLessonTable(db);
//        seedQuestionTable(db);

//        ContentValues values = new ContentValues();
//        values.put(DatabaseContract.LevelTable.COLUMN_NAME_LEVEL_NAME, "Samir Ahmad");
//        long result = db.insert(DatabaseContract.LevelTable.TABLE_NAME, null, values);
//        if(result == -1) {
//            Toast.makeText(context, "Failed to seed the database", Toast.LENGTH_SHORT).show();
//        } else {
//            Toast.makeText(context, "Successfully seeded the database", Toast.LENGTH_SHORT).show();
//        }
    }

    public Cursor getData() {
        seedDb();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + DatabaseContract.LevelTable.TABLE_NAME;

        Cursor cursor = null;
        if(db!= null) {
            cursor = db.rawQuery(query, null);
        }

        return cursor;
    }

    private void seedLevelTable(SQLiteDatabase db) {

        InputStream seedFile = null;
        BufferedReader seedFileReader = null;

        try {
            seedFile = getContext().getAssets().open("levelTableSeed.txt");
            seedFileReader = new BufferedReader(new InputStreamReader(seedFile));

            db.beginTransaction();
            Cursor cursor = null;
            cursor = db.rawQuery(
                    "SELECT * FROM " + DatabaseContract.LevelTable.TABLE_NAME, null
            );

            if(cursor.getCount() == 10) {
                return;
            }

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
                String[] values = line.split("\\|");
                if (values.length == 3) {
                    String lessonName = values[0];
                    String filePath = values[1];
                    int levelId = Integer.parseInt(values[2]);

                    String query = "INSERT INTO " + DatabaseContract.LessonTable.TABLE_NAME +
                            " (" + DatabaseContract.LessonTable.COLUMN_NAME_LESSON_NAME +
                            ", " + DatabaseContract.LessonTable.COLUMN_NAME_FILE_PATH +
                            ", " + DatabaseContract.LessonTable.COLUMN_NAME_FOREIGN_LEVEL_ID +
                            ") VALUES (?, ?, ?);";

                    db.execSQL(query, new Object[]{lessonName, filePath, levelId});
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
            seedFile = getContext().getAssets().open("questionTableSeed");
            seedFileReader = new BufferedReader(new InputStreamReader(seedFile));

            db.beginTransaction();

            String line = seedFileReader.readLine();
            while(line != null) {
                String[] values = line.split("\\|");
                if(values.length == 6) {
                    String questionText = values[0];
                    String choiceA = values[1];
                    String choiceB = values[2];
                    String choiceC = values[3];
                    String correctChoice = values[4];
                    int lessonId = Integer.parseInt(values[5]);

                    String query = "INSERT INTO " + DatabaseContract.QuestionTable.TABLE_NAME +
                            " (" + DatabaseContract.QuestionTable.COLUMN_NAME_QUESTION_TEXT +
                            ", " + DatabaseContract.QuestionTable.COLUMN_NAME_CHOICE_A +
                            ", " + DatabaseContract.QuestionTable.COLUMN_NAME_CHOICE_B +
                            ", " + DatabaseContract.QuestionTable.COLUMN_NAME_CHOICE_C +
                            ", " + DatabaseContract.QuestionTable.COLUMN_NAME_CORRECT_CHOICE +
                            ", " + DatabaseContract.QuestionTable.COLUMN_NAME_FOREIGN_LESSON_ID +
                            ") VALUES (?, ?, ?, ?, ?, ?);";

                    db.execSQL(query, new Object[] {questionText, choiceA, choiceB, choiceC, correctChoice, lessonId});
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
