package com.dksazxv.dskf.com.db.tables;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class OrderTable {

    private static final String TAG = PersonTable.class.toString();

    public static final String TABLE_ORDER                  = "Order";
    public static final String COLUMN_ID                    = "IDOrder";
    public static final String COLUMN_COMPLETED_WORK        = "CompletedWork";
    public static final String COLUMN_EQUIPMENT_ACCEPT      = "EquipmentAccept";
    public static final String COLUMN_DIFFICULT_LEVEL       = "DifficultLevel";
    public static final String COLUMN_DATE_APPLY            = "DateApply";
    public static final String COLUMN_TOTAL_COST            = "TotalCost";
    public static final String COLUMN_ID_PERSON             = "IDPerson";

    private static final String CREATE_TABLE = "CREATE TABLE "
            + TABLE_ORDER
            + "("
            + COLUMN_ID                         + " integer primary key autoincrement,"
            + COLUMN_COMPLETED_WORK             + " text,"
            + COLUMN_EQUIPMENT_ACCEPT           + " text,"
            + COLUMN_DIFFICULT_LEVEL            + " integer,"
            + COLUMN_DATE_APPLY                 + " text not null"
            + COLUMN_TOTAL_COST                 + " real,"
            + COLUMN_ID_PERSON                  + "integer not null,"
            + " FOREIGN KEY("
            + COLUMN_ID_PERSON     +")"
            + " REFERENCES " + PersonTable.TABLE_PERSON
            + "(" + PersonTable.COLUMN_ID + ")"
            + ");";

    public static void onCreate(SQLiteDatabase database) {
        Log.d(TAG, "onCreate table " + TABLE_ORDER);
        database.execSQL(CREATE_TABLE);
    }

    public static void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        Log.d(TAG, "onUpgrade table to newVersion =" + newVersion + " from oldVersion =" + oldVersion);

        database.execSQL("DROP TABLE IF EXISTS " + TABLE_ORDER);
        onCreate(database);
    }
}
