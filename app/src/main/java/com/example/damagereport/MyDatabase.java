package com.example.damagereport;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class MyDatabase extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "Damages.db";
    private static final int DATABASE_VERSION = 2;

    private static final String TABLE_NAME = "Damages";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_DAMAGE_DATE = "damage_date";
    private static final String COLUMN_DAMAGE_TIME = "damage_time";
    private static final String COLUMN_DAMAGE_LOCATION = "damage_location";
    private static final String COLUMN_DAMAGE_TYPE = "damage_type";
    private static final String COLUMN_DAMAGE_DESCRIPTION = "damage_description";
    private static final String COLUMN_DAMAGE_SEVERITY = "damage_severity";
    private static final String COLUMN_DAMAGE_STATUS = "damage_status";
    private static final String COLUMN_DAMAGE_PROBABLE_CAUSE = "damage_probable_cause";
    private static final String COLUMN_ID_USER = "id_user";

    private static final String TABLE_NAME_USER = "User";
    private static final String COLUMN_USER_ID = "id_user";
    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_USER_NAME = "name";
    private static final String COLUMN_USER_SURNAME = "surname";
    private static final String COLUMN_USER_PASSWORD = "user_password";
    private static final String COLUMN_USER_EMAIL = "user_email";
    private static final String COLUMN_USER_PHONE = "user_phone";

    public MyDatabase(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME +
                " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_DAMAGE_DATE + " TEXT, " +
                COLUMN_DAMAGE_TIME + " TEXT, " +
                COLUMN_DAMAGE_LOCATION + " TEXT, " +
                COLUMN_DAMAGE_TYPE + " TEXT, " +
                COLUMN_DAMAGE_DESCRIPTION + " TEXT, " +
                COLUMN_DAMAGE_SEVERITY + " TEXT, " +
                COLUMN_DAMAGE_STATUS + " TEXT, " +
                COLUMN_DAMAGE_PROBABLE_CAUSE + " TEXT, " +
                COLUMN_ID_USER + " INTEGER);";
        db.execSQL(query);

        String userQuery = "CREATE TABLE " + TABLE_NAME_USER +
                " (" + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_USERNAME + " TEXT, " +
                COLUMN_USER_NAME + " TEXT, " +
                COLUMN_USER_SURNAME + " TEXT, " +
                COLUMN_USER_PASSWORD + " TEXT, " +
                COLUMN_USER_EMAIL + " TEXT, " +
                COLUMN_USER_PHONE + " TEXT);";
        db.execSQL(userQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_USER);
        onCreate(db);
    }

    public void insertReport(Report report) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_DAMAGE_DATE, report.getDamageDate());
        cv.put(COLUMN_DAMAGE_TIME, report.getDamageTime());
        cv.put(COLUMN_DAMAGE_LOCATION, report.getDamageLocation());
        cv.put(COLUMN_DAMAGE_TYPE, report.getDamageType());
        cv.put(COLUMN_DAMAGE_DESCRIPTION, report.getDamageDescription());
        cv.put(COLUMN_DAMAGE_SEVERITY, report.getDamageSeverity());
        cv.put(COLUMN_DAMAGE_STATUS, report.getDamageStatus());
        cv.put(COLUMN_DAMAGE_PROBABLE_CAUSE, report.getDamageProbableCause());
        cv.put(COLUMN_ID_USER, report.getIdUser());

        long result = db.insert(TABLE_NAME, null, cv);
        if (result == -1) {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show();
        }
        db.close();
    }

    public List<Report> getAllReports() {
        List<Report> reportList = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        try {
            cursor = db.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                do {
                    Report report = new Report(
                            cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)),
                            cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DAMAGE_DATE)),
                            cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DAMAGE_TIME)),
                            cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DAMAGE_LOCATION)),
                            cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DAMAGE_TYPE)),
                            cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DAMAGE_DESCRIPTION)),
                            cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DAMAGE_SEVERITY)),
                            cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DAMAGE_STATUS)),
                            cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DAMAGE_PROBABLE_CAUSE)),
                            cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID_USER))
                    );
                    reportList.add(report);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }

        return reportList;
    }

    public void updateReport(Report report) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_DAMAGE_DATE, report.getDamageDate());
        cv.put(COLUMN_DAMAGE_TIME, report.getDamageTime());
        cv.put(COLUMN_DAMAGE_LOCATION, report.getDamageLocation());
        cv.put(COLUMN_DAMAGE_TYPE, report.getDamageType());
        cv.put(COLUMN_DAMAGE_DESCRIPTION, report.getDamageDescription());
        cv.put(COLUMN_DAMAGE_SEVERITY, report.getDamageSeverity());
        cv.put(COLUMN_DAMAGE_STATUS, report.getDamageStatus());
        cv.put(COLUMN_DAMAGE_PROBABLE_CAUSE, report.getDamageProbableCause());
        cv.put(COLUMN_ID_USER, report.getIdUser());

        long result = db.update(TABLE_NAME, cv, COLUMN_ID + "=?", new String[]{String.valueOf(report.getId())});
        if (result == -1) {
            Toast.makeText(context, "Update Failed", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Update Successful", Toast.LENGTH_SHORT).show();
        }
        db.close();
    }

    public void deleteReport(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME, COLUMN_ID + "=?", new String[]{String.valueOf(id)});
        if (result == -1) {
            Toast.makeText(context, "Delete Failed", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Delete Successful", Toast.LENGTH_SHORT).show();
        }
        db.close();
    }

    public long registerUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_USERNAME, user.getUsername());
        cv.put(COLUMN_USER_NAME, user.getName());
        cv.put(COLUMN_USER_SURNAME, user.getSurname());
        cv.put(COLUMN_USER_PASSWORD, user.getPassword());
        cv.put(COLUMN_USER_EMAIL, user.getEmail());
        cv.put(COLUMN_USER_PHONE, user.getPhone());

        long result = db.insert(TABLE_NAME_USER, null, cv);
        if (result == -1) {
            Toast.makeText(context, "User Registration Failed", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "User Registered Successfully", Toast.LENGTH_SHORT).show();
        }
        db.close();
        return result;
    }

    public boolean checkLogin(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_USER_ID};
        String selection = COLUMN_USERNAME + "=? AND " + COLUMN_USER_PASSWORD + "=?";
        String[] selectionArgs = {username, password};

        Cursor cursor = db.query(TABLE_NAME_USER, columns, selection, selectionArgs, null, null, null);
        int count = cursor.getCount();
        cursor.close();
        db.close();

        return count > 0;
    }

    public User getUser(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        User user = null;

        String[] columns = {COLUMN_USER_ID, COLUMN_USERNAME, COLUMN_USER_NAME, COLUMN_USER_SURNAME,
                COLUMN_USER_EMAIL, COLUMN_USER_PASSWORD, COLUMN_USER_PHONE};
        String selection = COLUMN_USERNAME + "=?";
        String[] selectionArgs = {username};

        Cursor cursor = db.query(TABLE_NAME_USER, columns, selection, selectionArgs, null, null, null);

        if (cursor.moveToFirst()) {
            user = new User(
                    cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_USER_ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USERNAME)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USER_NAME)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USER_SURNAME)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USER_EMAIL)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USER_PASSWORD)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USER_PHONE))
            );
        }

        cursor.close();
        db.close();
        return user;
    }

    public boolean isUsernameTaken(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        try {
            String query = "SELECT * FROM " + TABLE_NAME_USER + " WHERE " + COLUMN_USERNAME + " = ?";
            cursor = db.rawQuery(query, new String[]{username});
            return cursor.getCount() > 0;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }
    }

    public boolean updateUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_USER_NAME, user.getName());
        cv.put(COLUMN_USER_SURNAME, user.getSurname());
        cv.put(COLUMN_USER_EMAIL, user.getEmail());
        cv.put(COLUMN_USER_PASSWORD, user.getPassword());
        cv.put(COLUMN_USER_PHONE, user.getPhone());

        int result = db.update(TABLE_NAME_USER, cv, COLUMN_USERNAME + "=?", new String[]{user.getUsername()});
        db.close();

        if (result > 0) {
            Toast.makeText(context, "User Updated Successfully", Toast.LENGTH_SHORT).show();
            return true;
        } else {
            Toast.makeText(context, "User Update Failed", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public boolean deleteUser(String username) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(TABLE_NAME_USER, COLUMN_USERNAME + "=?", new String[]{username});
        db.close();

        if (result > 0) {
            Toast.makeText(context, "User Deleted Successfully", Toast.LENGTH_SHORT).show();
            return true;
        } else {
            Toast.makeText(context, "User Deletion Failed", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

}
