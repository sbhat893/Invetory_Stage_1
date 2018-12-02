package com.examples.android.inventory.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ElectronicsDbHelper extends SQLiteOpenHelper {

    /**
     * Name of the database file
     */
    private static final String DATABASE_NAME = "inventory.db";
    /**
     * If you change the database schema, you must increment the database version.
     */
    private static final int DATABASE_VERSION = 1;

    /**
     * Constructs a new instance of {@link ElectronicsDbHelper}.
     *
     * @param context of the app
     */
    public ElectronicsDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * This is called when the database is created for the first time.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create a String that contains the SQL statement to create the electronics table
        String SQL_CREATE_ELECTRONICS_TABLE = "CREATE TABLE " + ElectronicsContract.ElectronicsEntry.TABLE_NAME + " ("
                + ElectronicsContract.ElectronicsEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + ElectronicsContract.ElectronicsEntry.COLUMN_PRODUCT_NAME + " TEXT NOT NULL, "
                + ElectronicsContract.ElectronicsEntry.COLUMN_PRODUCT_MODEL + " TEXT NOT NULL, "
                + ElectronicsContract.ElectronicsEntry.COLUMN_PRODUCT_BRAND + " TEXT NOT NULL, "
                + ElectronicsContract.ElectronicsEntry.COLUMN_SUPPLIER_NAME + " INTEGER NOT NULL, "
                + ElectronicsContract.ElectronicsEntry.COLUMN_SUPPLIER_PHONE + " TEXT, "
                + ElectronicsContract.ElectronicsEntry.COLUMN_QUANTITY + " INTEGER NOT NULL DEFAULT 0,"
                + ElectronicsContract.ElectronicsEntry.COLUMN_PRICE + " INTEGER NOT NULL DEFAULT 0)";
        // Execute the SQL statement
        db.execSQL(SQL_CREATE_ELECTRONICS_TABLE);
    }

    /**
     * This is called when the database needs to be upgraded.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // The database is still at version 1, so there's nothing to do be done here.
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}