

package com.example.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "StudentDB";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "students";
    private static final String COL_ID = "id";
    private static final String COL_NAME = "name";
    private static final String COL_REGNO = "regno";
    private static final String COL_DEPT = "department";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String query = "CREATE TABLE " + TABLE_NAME + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_NAME + " TEXT, " +
                COL_REGNO + " TEXT UNIQUE, " +
                COL_DEPT + " TEXT)";

        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    // ADD STUDENT
    public boolean addStudent(String name, String regno, String department) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COL_NAME, name);
        values.put(COL_REGNO, regno);
        values.put(COL_DEPT, department);

        long result = db.insert(TABLE_NAME, null, values);

        return result != -1;
    }

    // VIEW STUDENTS
    public Cursor getStudents() {

        SQLiteDatabase db = this.getReadableDatabase();

        return db.rawQuery(
                "SELECT * FROM " + TABLE_NAME,
                null
        );
    }

    // UPDATE STUDENT
    public boolean updateStudent(
            String name,
            String regno,
            String department) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COL_NAME, name);
        values.put(COL_DEPT, department);

        int result = db.update(
                TABLE_NAME,
                values,
                COL_REGNO + "=?",
                new String[]{regno}
        );

        return result > 0;
    }

    // DELETE STUDENT
    public boolean deleteStudent(String regno) {

        SQLiteDatabase db = this.getWritableDatabase();

        int result = db.delete(
                TABLE_NAME,
                COL_REGNO + "=?",
                new String[]{regno}
        );

        return result > 0;
    }
}
