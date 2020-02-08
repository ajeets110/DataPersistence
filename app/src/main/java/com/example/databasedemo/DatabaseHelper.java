package com.example.databasedemo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.method.NumberKeyListener;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    // using constantns for clumn names

    private static final String DATABASE_NAME = "EmployeeDatabase";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "employees";
    private static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_DEPT = "department";
    public static final String COLUMN_JOIN_DATE = "joiningDate";
    public static final String COLUMN_SALARY = "salary";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TABLE_NAME + "(" +
                COLUMN_ID + " INTEGER NOT NULL CONSTRAINT employee_pk PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " varchar(200) NOT NULL, " +
                COLUMN_DEPT + " varchar(200) NOT NULL, " +
                COLUMN_JOIN_DATE + " varchar(200) NOT NULL, " +
                COLUMN_SALARY + " double NOT NULL);";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        // DROPPING THE TABLE AND RECREATING IT

        String sql = "DROP TABLE IF EXISTS " + TABLE_NAME + ";";
        db.execSQL(sql);
        onCreate(db);

    }

    boolean addEmployee(String name, String dept, String joiningDate, double salary) {

        // In order to write database, we need a writable database
        // This method returns a sqlite database instance
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        // we need to define a contentValue instance
        ContentValues cv = new ContentValues();

        // the first method of the put method is the column name and the second is the value
        cv.put(COLUMN_NAME, name);
        cv.put(COLUMN_DEPT, dept);
        cv.put(COLUMN_JOIN_DATE, joiningDate);
        cv.put(COLUMN_SALARY, salary);

        // insert method returns row number if the insert is successful and -1 if UNSUCCESSFUL
        return sqLiteDatabase.insert(TABLE_NAME, null, cv) != -1;

//        return true;

    }

    Cursor getAllEMployee() {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        return sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_NAME, null);
    }

    boolean updateEmployee(int id, String name, String dept, double salary) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        // we need to define a contentValue instance
        ContentValues cv = new ContentValues();

        // the first method of the put method is the column name and the second is the value
        cv.put(COLUMN_NAME, name);
        cv.put(COLUMN_DEPT, dept);
        cv.put(COLUMN_SALARY, salary);

        // this method updates number of rows affected
        return sqLiteDatabase.update(TABLE_NAME, cv, COLUMN_ID + "=?", new String[]{String.valueOf(id)}) > 0;

    }

    boolean deleteRow(int id){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        // we need to define a contentValue instance
        ContentValues cv = new ContentValues();

        // the delete method
        return sqLiteDatabase.delete(TABLE_NAME, COLUMN_ID + "=?", new String[]{String.valueOf(id)}) > 0;


    }

}
