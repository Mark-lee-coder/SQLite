package com.example.sqliteexample;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.view.View;

public class ContactsDB {
    public static final String KEY_ROWID = "_id";
    public static final String KEY_NAME = "person_name";
    public static final String KEY_PHONE = "phone_number";

    private final String DATABASE_NAME = "ContactsDB";
    private final String DATABASE_TABLE = "ContactsTable";
    private final int DATABASE_VERSION = 1;

    private DBHelper ourHelper;
    private final Context ourContext;
    private SQLiteDatabase ourDatabase;

    public ContactsDB (View.OnClickListener context) { ourContext = (Context) context; }

    private class DBHelper extends SQLiteOpenHelper {
        public DBHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            String sqlCode = "CREATE TABLE " + DATABASE_TABLE + " (" +
                    KEY_ROWID + "INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    KEY_NAME + "TEXT NOT NULL, " +
                    KEY_PHONE + "TEXT NOT NULL);";

            db.execSQL(sqlCode);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
            onCreate(db);
        }
    }

    public void open() throws SQLException {
        ourHelper = new DBHelper(ourContext);
        ourDatabase = ourHelper.getWritableDatabase();

    }

    public void close() {
        ourHelper.close();
    }

    public void createEntry(String name, String phone) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_NAME, name);
        contentValues.put(KEY_PHONE, phone);

        ourDatabase.insert(DATABASE_TABLE, null, contentValues);
    }

    public String getData() {
        String [] columns = new String [] { KEY_ROWID, KEY_NAME, KEY_PHONE }; //string array declaration

        Cursor cursor = ourDatabase.query(DATABASE_TABLE, columns, null, null, null, null, null);

        StringBuilder result = new StringBuilder();

        int indexRowID = cursor.getColumnIndex(KEY_ROWID);
        int indexName = cursor.getColumnIndex(KEY_NAME);
        int indexPhone = cursor.getColumnIndex(KEY_PHONE);

        for (cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext()) {
            result.append(cursor.getString(indexRowID)).append(": ")
                    .append(cursor.getString(indexName)).append(" ")
                    .append(cursor.getString(indexPhone)).append("/n");
        }

        cursor.close();

        return result.toString();
    }

    public void deleteEntry(String rowId) {
        ourDatabase.delete(DATABASE_TABLE, KEY_ROWID + "=?", new String[]{rowId});
    }

    public void updateEntry(String rowId, String name, String phone) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_NAME, name);
        contentValues.put(KEY_PHONE, phone);

        ourDatabase.update(DATABASE_TABLE, contentValues, KEY_ROWID + "?", new String[]{});
    }
}