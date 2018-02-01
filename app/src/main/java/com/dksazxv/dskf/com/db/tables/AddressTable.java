package com.dksazxv.dskf.com.db.tables;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by Александр on 11.09.2016.
 */
public class AddressTable {

    private static final String TAG = PersonTable.class.toString();

    public static final String TABLE_ADDRESS                    = "AddressPerson";
    public static final String COLUMN_ID                        = "IDAddressPerson";
    public static final String COLUMN_CITY                      = "City";
    public static final String COLUMN_STREET                    = "Street";
    public static final String COLUMN_HOUSE_NUMBER              = "HouseNumber";
    public static final String COLUMN_APARTMENT_NUMBER          = "ApartmentNumber";

    private static final String CREATE_TABLE = "CREATE TABLE "
            + TABLE_ADDRESS
            + "("
            + COLUMN_ID                 + " integer primary key autoincrement,"
            + COLUMN_CITY               + " text not null,"
            + COLUMN_STREET             + " text not null,"
            + COLUMN_HOUSE_NUMBER       + " integer not null,"
            + COLUMN_APARTMENT_NUMBER   + " integer not null"
            + ");";

    public static void onCreate(SQLiteDatabase database) {
        Log.d(TAG, "onCreate table " + TABLE_ADDRESS);
        database.execSQL(CREATE_TABLE);
    }

    public static void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        Log.d(TAG, "onUpgrade table to newVersion =" + newVersion + " from oldVersion =" + oldVersion);

        database.execSQL("DROP TABLE IF EXISTS " + TABLE_ADDRESS);
        onCreate(database);
    }
}
