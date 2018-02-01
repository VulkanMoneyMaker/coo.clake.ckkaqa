package com.dksazxv.dskf.com.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.dksazxv.dskf.com.db.DataBaseHelper;
import com.dksazxv.dskf.com.db.tables.AddressTable;

public class AddresDAO {

    private DataBaseHelper mHelper;
    private SQLiteDatabase mDatabase;

    public AddresDAO(Context context){
        this.mHelper = new DataBaseHelper(context);
    }

    public void onOpen() throws SQLException {
        mDatabase = mHelper.getWritableDatabase();
    }

    public void onClose() {
        mHelper.close();
    }

    public void createAddress(String city, String street, int houseNumber, int apartmentNumber) {
        ContentValues contentValues = new ContentValues();

        contentValues.put(AddressTable.COLUMN_CITY, city);
        contentValues.put(AddressTable.COLUMN_STREET, street);
        contentValues.put(AddressTable.COLUMN_HOUSE_NUMBER, houseNumber);
        contentValues.put(AddressTable.COLUMN_APARTMENT_NUMBER, apartmentNumber);

        long addressID = mDatabase.insert(AddressTable.TABLE_ADDRESS, null, contentValues);

    }

}
