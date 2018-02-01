package com.dksazxv.dskf.com.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.dksazxv.dskf.com.db.tables.AddressTable;
import com.dksazxv.dskf.com.db.tables.OrderTable;
import com.dksazxv.dskf.com.db.tables.PersonTable;


public class DataBaseHelper extends SQLiteOpenHelper {

    private static final String TAG = DataBaseHelper.class.toString();

    private static final String     DATABASE_NAME       = "repairs_clients.db";
    private static final int        DATABASE_VERSION    = 1;

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, "onCreate database " + DATABASE_NAME);
        AddressTable.onCreate(db);
        PersonTable.onCreate(db);
        OrderTable.onCreate(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(TAG, "onUpgrade database to newVersion =" + newVersion + " from oldVersion =" + oldVersion);
        AddressTable    .onUpgrade(db, oldVersion, newVersion);
        PersonTable     .onUpgrade(db, oldVersion, newVersion);
        OrderTable      .onUpgrade(db, oldVersion, newVersion);
    }
}
