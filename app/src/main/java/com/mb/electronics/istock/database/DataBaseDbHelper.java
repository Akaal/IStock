package com.mb.electronics.istock.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.mb.electronics.istock.database.DataContract.ProductEntry;

public class DataBaseDbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "inventory.db";
    private final String SQL_CREATE_PRODUCT_TABLE = "CREATE TABLE " + DataContract.ProductEntry.TABLE_NAME + " (" +
            DataContract.ProductEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            DataContract.ProductEntry.COLUMN_PRODUCT_ID + " TEXT , " +
            ProductEntry.COLUMN_STORE_ID + " TEXT , " +
            ProductEntry.COLUMN_PRODUCT_NAME + " TEXT UNIQUE NOT NULL, " +
            ProductEntry.COLUMN_QUANTITY + " REAL , " +
            ProductEntry.COLUMN_MRP + " TEXT , " +
            ProductEntry.COLUMN_OUR_SELLING_PRICE + " TEXT , " +
            ProductEntry.COLUMN_SKU_ID + " TEXT NOT NULL," +
            ProductEntry.COLUMN_ADDED_ON + " TEXT NOT NULL " +
            " );";


    public DataBaseDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_PRODUCT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + ProductEntry.TABLE_NAME);
    }

    // CHECK(" + ProductEntry.COLUMN_QUANTITY + " > 0)
}
