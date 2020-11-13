package com.example.androidsqlitepracticepart2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import androidx.annotation.Nullable;

public class DBManager extends SQLiteOpenHelper {
    private static final String DATABASE = "location_db";
    private static final String TABLE_NAME = "location";
    private static final String ID = "id";
    private static final String NAME = "name";
    private Context context;

    public DBManager(@Nullable Context context) {
        super(context, DATABASE, null, 1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sqlQuery = "CREATE TABLE " + TABLE_NAME + " (" +
                ID + " TEXT primary key, " +
                NAME + " TEXT)";
        db.execSQL(sqlQuery);
        Toast.makeText(context, "Create successfylly", Toast.LENGTH_SHORT).show();


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
        Toast.makeText(context, "Drop successfylly", Toast.LENGTH_SHORT).show();
    }

    public void addNewLocation(Location location) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ID, location.getId().toString());
        values.put(NAME, location.getName());
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public List<Location> getLocations() {
        List<Location> locations = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "SELECT * FROM " + TABLE_NAME;
        Cursor cursor = db.rawQuery(sql, null);
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Location location = new Location();
                location.setId(UUID.fromString(cursor.getString(0)));
                location.setName(cursor.getString(1));
                locations.add(location);
                cursor.moveToNext();
            }
        } finally {
            db.close();
        }
        return locations;

    }

    public boolean updateLocation(Location location) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NAME, location.getName());
        return db.update(TABLE_NAME, values, ID + "=?", new String[]{location.getId().toString()}) > 0;
    }

    ;

    public boolean deleteLocation(Location location) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, ID + "=?", new String[]{location.getId().toString()}) > 0;
    }

    ;


}
