package com.dksazxv.dskf.com.db.tables;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by Александр on 11.09.2016.
 */
public class PersonTable {

    private static final String TAG = PersonTable.class.toString();

    public static final String TABLE_PERSON                 = "Person";
    public static final String COLUMN_ID                    = "IDPerson";
    public static final String COLUMN_FIRST_NAME            = "FirstName";
    public static final String COLUMN_LAST_NAME             = "LastName";
    public static final String COLUMN_MIDDLE_NAME           = "MiddleName";
    public static final String COLUMN_PHONE_NUMBER          = "PhoneNumber";
    public static final String COLUMN_ID_ADDRESS            = "IDAddress";

    private static final String CREATE_TABLE = "CREATE TABLE "
            + TABLE_PERSON
            + "("
            + COLUMN_ID             + " integer primary key autoincrement,"
            + COLUMN_FIRST_NAME     + " text not null,"
            + COLUMN_LAST_NAME      + " text not null,"
            + COLUMN_MIDDLE_NAME    + " text not null,"
            + COLUMN_PHONE_NUMBER   + " text not null"
            + COLUMN_ID_ADDRESS     + " integer not null,"
            + " FOREIGN KEY("
            + COLUMN_ID_ADDRESS     +")"
            + " REFERENCES " + AddressTable.TABLE_ADDRESS
            + "(" + AddressTable.COLUMN_ID + ")"
            + ");";

    public static void onCreate(SQLiteDatabase database) {
        Log.d(TAG, "onCreate table " + TABLE_PERSON);
        database.execSQL(CREATE_TABLE);
    }

    public static void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        Log.d(TAG, "onUpgrade table to newVersion =" + newVersion + " from oldVersion =" + oldVersion);

        database.execSQL("DROP TABLE IF EXISTS " + TABLE_PERSON);
        onCreate(database);
    }
}
