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
    // database data members
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "LearnCrypto.db";
    private final Context context;
    // shared preferences maneging
    public static final String DATABASE_PREFERENCES = "database_preferences";
    public static final String PREFERENCES_IS_EXIST = "isExist";

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
        db.execSQL(DatabaseContract.UserTable.SQL_DROP_USER);
        db.execSQL(DatabaseContract.LevelTable.SQL_DROP_LEVEL);
        db.execSQL(DatabaseContract.LessonTable.SQL_DROP_LESSON);
        db.execSQL(DatabaseContract.QuestionTable.SQL_DROP_QUESTION);
        onCreate(db);
    }

    // this function start the seeding if the app is just been opened for the first time
    public void init() {
        // check if the db has been created already
        if(isExist()) {
            return;
        }
        // if not then seed the empty db
        seedDb();
        // set the SharedPreferences item PREFERENCES_IS_EXIST to true
        SharedPreferences.Editor editor = context.getSharedPreferences(DATABASE_PREFERENCES, Context.MODE_PRIVATE).edit();
        editor.putBoolean(PREFERENCES_IS_EXIST, true);
        editor.apply();
    }
    // check is the data exist
    public boolean isExist() {
        SharedPreferences preferences = context.getSharedPreferences(DATABASE_PREFERENCES, Context.MODE_PRIVATE);
        return preferences.getBoolean(PREFERENCES_IS_EXIST, false);
    }

    // Seeding functions: set of function that create the initial data from seed files for the table
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
            seedFile = getContext().getAssets().open(DatabaseContract.LevelTable.SEED_FILE);
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
            seedFile = getContext().getAssets().open(DatabaseContract.LessonTable.SEED_FILE);
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
                    values.put(DatabaseContract.LessonTable.COLUMN_NAME_IS_FINISHED, false);

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
            seedFile = getContext().getAssets().open(DatabaseContract.QuestionTable.SEED_FILE);
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

    // data getters: set of functions to get data from the different tables
    public boolean isUserHasAccount() {
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM " +  DatabaseContract.UserTable.TABLE_NAME + ";";

        Cursor cursor;
        if(db!= null) {
            cursor = db.rawQuery(query, null);
            return cursor.getCount() != 0;
        }

        return false;
    }

    public boolean createUserAccount(String firstName, String lastName, String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseContract.UserTable.COLUMN_NAME_FIRST_NAME, firstName);
        values.put(DatabaseContract.UserTable.COLUMN_NAME_LAST_NAME, lastName);
        values.put(DatabaseContract.UserTable.COLUMN_NAME_EMAIL, email);
        values.put(DatabaseContract.UserTable.COLUMN_NAME_PASSWORD, password);
        values.put(DatabaseContract.UserTable.COLUMN_NAME_SCORE, 0);
        values.put(DatabaseContract.UserTable.COLUMN_NAME_USER_LEVEL, 0);

        long result = db.insert(DatabaseContract.UserTable.TABLE_NAME, null, values);

        return result != -1;
    }
    public boolean updateUserAccount(String firstName, String lastName, String email) {
        SQLiteDatabase db = this.getReadableDatabase();

        ContentValues values = new ContentValues();
        values.put(DatabaseContract.UserTable.COLUMN_NAME_FIRST_NAME, firstName);
        values.put(DatabaseContract.UserTable.COLUMN_NAME_LAST_NAME, lastName);
        values.put(DatabaseContract.UserTable.COLUMN_NAME_EMAIL, email);

        long result = db.update(DatabaseContract.UserTable.TABLE_NAME, values, null, null);

        return result != -1;
    }

    public boolean updateUserPassword(String newPassword) {
        SQLiteDatabase db = this.getReadableDatabase();

        ContentValues values = new ContentValues();
        values.put(DatabaseContract.UserTable.COLUMN_NAME_PASSWORD, newPassword);

        long result = db.update(DatabaseContract.UserTable.TABLE_NAME, values, null, null);

        return result != -1;
    }

    public UserAccount getUserAccount() {
        SQLiteDatabase db = this.getReadableDatabase();
        if(!updateUserScore() && !updateUserLevel()) {
            return null;
        }

        String query = "SELECT * FROM " + DatabaseContract.UserTable.TABLE_NAME + ";";

        Cursor cursor = null;
        if(db != null) {
            cursor = db.rawQuery(query, null);
        }

        if(cursor != null) {
            cursor.moveToFirst();

            return new UserAccount(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getInt(5),
                    cursor.getInt(6)
            );
        }

        return null;
    }

    public int getUserScore() {
        SQLiteDatabase db = this.getReadableDatabase();
        if(!updateUserScore()) {
            return 0;
        }

        String query = "SELECT " + DatabaseContract.UserTable.COLUMN_NAME_SCORE +
                " FROM " + DatabaseContract.UserTable.TABLE_NAME + ";";

        Cursor cursor = null;
        if(db != null) {
            cursor = db.rawQuery(query, null);
        }

        if(cursor != null) {
            cursor.moveToFirst();

            return cursor.getInt(0);
        }

        return 0;
    }

    public boolean updateUserScore() {
        SQLiteDatabase db = this.getWritableDatabase();

        int score = getCorrectQuestionCount() * DatabaseContract.UserTable.POINTS_VALUE;

        ContentValues values = new ContentValues();
        values.put(DatabaseContract.UserTable.COLUMN_NAME_SCORE, score);

        long result = db.update(DatabaseContract.UserTable.TABLE_NAME, values, null, null);

        return result != -1;
    }

    public int getMaxScore() {
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT " + DatabaseContract.QuestionTable.COLUMN_NAME_ID +
                " FROM " +  DatabaseContract.QuestionTable.TABLE_NAME;

        Cursor cursor;
        if(db!= null) {
            cursor = db.rawQuery(query, null);
            if(cursor != null) {
                return cursor.getCount() * DatabaseContract.UserTable.POINTS_VALUE;
            }
        }

        return 0;
    }

    public int getUserLevel() {
        SQLiteDatabase db = this.getReadableDatabase();
        if(!updateUserLevel()) {
            return 0;
        }

        String query = "SELECT " + DatabaseContract.UserTable.COLUMN_NAME_USER_LEVEL +
                " FROM " + DatabaseContract.UserTable.TABLE_NAME + ";";

        Cursor cursor = null;
        if(db != null) {
            cursor = db.rawQuery(query, null);
        }

        if(cursor != null) {
            cursor.moveToFirst();

            return cursor.getInt(0);
        }

        return 0;
    }

    public boolean updateUserLevel() {
        SQLiteDatabase db = this.getWritableDatabase();
        int scorePerLevel = getMaxScore() / (DatabaseContract.UserTable.LEVEL_MAX + 1);
        int level = Math.min(
                (getUserScore() / scorePerLevel) + 1,
                DatabaseContract.UserTable.LEVEL_MAX
        ) ;

        ContentValues values = new ContentValues();
        values.put(DatabaseContract.UserTable.COLUMN_NAME_USER_LEVEL, level);

        long result = db.update(DatabaseContract.UserTable.TABLE_NAME, values, null, null);

        return result != -1;
    }

    public void deleteUserAccount() {
        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL(DatabaseContract.QuestionTable.SQL_DELETE_QUESTION);
        db.execSQL(DatabaseContract.LessonTable.SQL_DELETE_LESSON);
        db.execSQL(DatabaseContract.UserTable.SQL_DELETE_USER);
        db.execSQL(DatabaseContract.LevelTable.SQL_DELETE_LEVEL);

        SharedPreferences.Editor editor = context.getSharedPreferences(DATABASE_PREFERENCES, Context.MODE_PRIVATE).edit();
        editor.putBoolean(PREFERENCES_IS_EXIST, false);
        editor.apply();
    }

    public Cursor getLessonsData() {
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM " +  DatabaseContract.LessonTable.TABLE_NAME + ";";

        Cursor cursor = null;
        if(db!= null) {
            cursor = db.rawQuery(query, null);
        }

        return cursor;
    }

    public void updateLessonToFinished(int lessonId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseContract.LessonTable.COLUMN_NAME_IS_FINISHED, 1);

        db.update(
                DatabaseContract.LessonTable.TABLE_NAME,
                values,
                DatabaseContract.LessonTable.COLUMN_NAME_ID + "=?",
                new String[]{String.valueOf(lessonId)}
        );
    }

    public int getFinishedLessonCount() {
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT " + DatabaseContract.LessonTable.COLUMN_NAME_ID +
                " FROM " +  DatabaseContract.LessonTable.TABLE_NAME +
                " WHERE " + DatabaseContract.LessonTable.COLUMN_NAME_IS_FINISHED +
                " = 1;" ;

        Cursor cursor;
        if(db!= null) {
            cursor = db.rawQuery(query, null);
            if(cursor != null) {
                return cursor.getCount();
            }
        }

        return 0;
    }


    public Cursor getQuestionByLessonId(int lessonId) {
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM " + DatabaseContract.QuestionTable.TABLE_NAME +
                " WHERE " + DatabaseContract.QuestionTable.COLUMN_NAME_FOREIGN_LESSON_ID +
                " = " + lessonId + ";";

        Cursor cursor = null;
        if (db != null) {
             cursor = db.rawQuery(query, null);
        }

        return cursor;
    }

    public void updateUserChoice(int questionId, String choice) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseContract.QuestionTable.COLUMN_NAME_USER_CHOICE, choice);

        db.update(
                DatabaseContract.QuestionTable.TABLE_NAME,
                values,
                DatabaseContract.QuestionTable.COLUMN_NAME_ID + " = ?",
                new String[]{String.valueOf(questionId)}
        );
    }

    public void updateQuestionIsCorrect(int questionId, int isCorrect) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DatabaseContract.QuestionTable.COLUMN_NAME_IS_CORRECT, isCorrect);

        db.update(
                DatabaseContract.QuestionTable.TABLE_NAME,
                values,
                DatabaseContract.QuestionTable.COLUMN_NAME_ID + " = ?",
                new String[] {String.valueOf(questionId)}
        );
    }

    public int getCorrectQuestionCount() {
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT " + DatabaseContract.QuestionTable.COLUMN_NAME_ID +
                " FROM " +  DatabaseContract.QuestionTable.TABLE_NAME +
                " WHERE " + DatabaseContract.QuestionTable.COLUMN_NAME_IS_CORRECT +
                " = 1;" ;

        Cursor cursor;
        if(db!= null) {
            cursor = db.rawQuery(query, null);
            if(cursor != null) {
                return cursor.getCount();
            }
        }

        return 0;
    }

    public String getUserChoiceByQuestionId(int questionId) {
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT " + DatabaseContract.QuestionTable.COLUMN_NAME_USER_CHOICE +
                " FROM " + DatabaseContract.QuestionTable.TABLE_NAME +
                " WHERE " + DatabaseContract.QuestionTable.COLUMN_NAME_ID +
                " = " + questionId + ";";

        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(query, null);
        }

        if(cursor != null) {
            cursor.moveToNext();
        }

        String userChoice =  cursor.getString(0);
        if(userChoice == null) {
            return "";
        }

        return userChoice;
    }
}
