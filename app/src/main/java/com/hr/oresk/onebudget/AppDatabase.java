package com.hr.oresk.onebudget;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.hr.oresk.onebudget.database.TABLES.AccountsContract;
import com.hr.oresk.onebudget.database.TABLES.BalanceContract;
import com.hr.oresk.onebudget.database.TABLES.CategoriesContract;
import com.hr.oresk.onebudget.database.TABLES.InvoiceContract;


/**
 * Basic database class for the application.
 * <p>
 * The only class that should use this is {@link AppProvider}.
 */
class AppDatabase extends SQLiteOpenHelper {
    public static final String TAG = "AppDatabase";

    public static final String DATABASE_NAME = "OneBudget.db";
    public static final int DATABASE_VERSION = 1;

    //Implement AppDatabase as a singleton
    private static AppDatabase instance = null;

    private AppDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.d(TAG, "AppDatabase: constructor");
    }



/**
     * Get instance of the app's singleton database helper object
     *
     * @param context the content provider context.
     * @return a SQLite database helper object
     */
    static AppDatabase getInstance(Context context) {
        if (instance == null) {
            Log.d(TAG, "getInstance: crating new instance");
            instance = new AppDatabase(context);
        }
        Log.d(TAG, "getInstance: test");
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, "onCreate: starts");
        sSQL_Invoice(db);
        sSQL_Categories(db);
        sSQL_Balance(db);
        sSQL_Account(db);
        Log.d(TAG, "onCreate: ends");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(TAG, "onUpgrade: starts");
        switch (oldVersion) {
            case 1:
                //upgrade logic from version 1
                break;
            default:
                throw new IllegalStateException("onUpgrade() with unknown newVersion: " + newVersion);
        }
        Log.d(TAG, "onUpgrade: ends");
    }

    private void sSQL_Invoice(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + InvoiceContract.TABLE_NAME + " (" +
                InvoiceContract.Columns._ID + " INTEGER PRIMARY KEY NOT NULL, " +
                InvoiceContract.Columns.INVOICE_ACCOUNT + " INTEGER, " +
                InvoiceContract.Columns.INVOICE_CATEGORY + " INTEGER, " +
                InvoiceContract.Columns.INVOICE_AMOUNT + " TEXT, " +
                InvoiceContract.Columns.INVOICE_DATE + " INTEGER, " +
                InvoiceContract.Columns.INVOICE_TYPE + " TEXT, " +
                InvoiceContract.Columns.INVOICE_DESCRIPTION + " TEXT);";
        db.execSQL(sql);
        Log.d(TAG,">>>>INVOICE TABLE<<<< "+ sql);

    }

    private void sSQL_Categories( SQLiteDatabase db) {

        String sql ="CREATE TABLE " + CategoriesContract.TABLE_NAME + " (" +
                CategoriesContract.Columns._ID + " INTEGER PRIMARY KEY NOT NULL, " +
                CategoriesContract.Columns.CATEGORIES_NAME + " TEXT, " +
                CategoriesContract.Columns.CATEGORIES_INVOICE_TYPE + " TEXT, " +
                CategoriesContract.Columns.CATEGORIES_DESCRIPTION + " TEXT);";
        db.execSQL(sql);
        Log.d(TAG, ">>>>CATEGORIES TABLE<<<< "+ sql);

    }

    private void sSQL_Balance(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + BalanceContract.TABLE_NAME + " (" +
                BalanceContract.Columns._ID + " INTEGER PRIMARY KEY NOT NULL, " +
                BalanceContract.Columns.BALANCE_ACCOUNT + " INTEGER, " +
                BalanceContract.Columns.BALANCE_CATEGORY + " INTEGER, " +
                BalanceContract.Columns.BALANCE_AMOUNT + " TEXT, " +
                BalanceContract.Columns.BALANCE_DESCRIPTION + " TEXT, " +
                BalanceContract.Columns.BALANCE_DATE + " INTEGER);";
        db.execSQL(sql);
        Log.d(TAG, ">>>>BALANCE TABLE<<<< "+sql );
    }

    private void sSQL_Account(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + AccountsContract.TABLE_NAME + " (" +
                AccountsContract.Columns._ID + " INTEGER PRIMARY KEY NOT NULL, " +
                AccountsContract.Columns.ACCOUNTS_NAME + " TEXT, " +
                AccountsContract.Columns.ACCOUNTS_AMOUNT + " TEXT, " +
                AccountsContract.Columns.ACCOUNTS_DESCRIPTION + " TEXT);";
        db.execSQL(sql);
        Log.d(TAG, ">>>>ACCOUNTS TABLE<<<< " + sql);
    }

}
