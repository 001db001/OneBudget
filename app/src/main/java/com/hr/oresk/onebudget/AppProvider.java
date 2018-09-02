package com.hr.oresk.onebudget;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.hr.oresk.onebudget.database.TABLES.AccountsContract;
import com.hr.oresk.onebudget.database.TABLES.BalanceContract;
import com.hr.oresk.onebudget.database.TABLES.CategoriesContract;
import com.hr.oresk.onebudget.database.TABLES.InvoiceContract;

import java.net.URI;

/**
 * Provider for OneBudget app. This is the only that knows about {@link AppDatabase}
 */

public class AppProvider extends ContentProvider {
    private static final String TAG = "AppProvider";



    private AppDatabase mOpenHelper;


    public static final UriMatcher sUriMatcher = buildUriMatcher();

    public static final String CONTENT_AUTHORITY = "com.hr.oresk.onebudget.provider";
    public static final Uri CONTENT_AUTHORITY_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final int INVOICE = 100;
    public static final int INVOICE_ID = 101;

    public static final int CATEGORIES = 200;
    public static final int CATEGORIES_ID = 201;

    public static final int BALANCE = 300;
    public static final int BALANCE_ID = 301;
    public static final int BALANCE_ACCOUNT = 302;
    public static final int BALANCE_CATEGORY = 303;

    public static final int ACCOUNTS = 400;
    public static final int ACCOUNTS_ID = 401;


    private static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        Log.d(TAG, "buildUriMatcher: ==================================================================");

        //eg. content://com.hr.oresk.onebudget.provider/Invoice
        matcher.addURI(CONTENT_AUTHORITY, InvoiceContract.TABLE_NAME, INVOICE);
        //eg. content://com.hr.oresk.onebudget.provider/Invoice/8
        matcher.addURI(CONTENT_AUTHORITY, InvoiceContract.TABLE_NAME + "/#", INVOICE_ID);

        matcher.addURI(CONTENT_AUTHORITY, CategoriesContract.TABLE_NAME, CATEGORIES);
        matcher.addURI(CONTENT_AUTHORITY, CategoriesContract.TABLE_NAME + "/#", CATEGORIES_ID);

        matcher.addURI(CONTENT_AUTHORITY, BalanceContract.TABLE_NAME, BALANCE);
        matcher.addURI(CONTENT_AUTHORITY, BalanceContract.TABLE_NAME + "/#", BALANCE_ID);


//        switch (MainActivity.getRerere()) {
//
//            case 0:
//                matcher.addURI(CONTENT_AUTHORITY, BalanceContract.TABLE_NAME + "/#", BALANCE_ACCOUNT);
//                Log.d(TAG, "buildUriMatcher: "+MainActivity.getRerere()+" // "+ s);
//            case 1:
//                matcher.addURI(CONTENT_AUTHORITY, BalanceContract.TABLE_NAME + "/#", BALANCE_CATEGORY);
//                Log.d(TAG, "buildUriMatcher: "+MainActivity.getRerere()+" // "+ s);
//            case 2:
//                matcher.addURI(CONTENT_AUTHORITY, BalanceContract.TABLE_NAME + "/#", BALANCE_ID);
//                Log.d(TAG, "buildUriMatcher: "+MainActivity.getRerere()+" // "+ s);
//        }


//        if (s == 1) {
//            matcher.addURI(CONTENT_AUTHORITY, BalanceContract.TABLE_NAME + "/#", BALANCE_ACCOUNT);
//        } else if (s == 2) {
//            matcher.addURI(CONTENT_AUTHORITY, BalanceContract.TABLE_NAME + "/#", BALANCE_CATEGORY);
//        } else {
//            matcher.addURI(CONTENT_AUTHORITY, BalanceContract.TABLE_NAME + "/#", BALANCE_ID);
//        }

        matcher.addURI(CONTENT_AUTHORITY, AccountsContract.TABLE_NAME, ACCOUNTS);
        matcher.addURI(CONTENT_AUTHORITY, AccountsContract.TABLE_NAME + "/#", ACCOUNTS_ID);


        return matcher;
    }

    @Override
    public boolean onCreate() {
        mOpenHelper = AppDatabase.getInstance(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        Log.d(TAG, "query: called with URI " + uri);
        final int match = sUriMatcher.match(uri);
        Log.d(TAG, "query: match is " + match);

        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();

        switch (match) {
            case INVOICE://TODO: TABLE: Invoice.
                queryBuilder.setTables(InvoiceContract.TABLE_NAME);
                break;
            case INVOICE_ID://TODO: TABLE: Invoice by _Id.
                queryBuilder.setTables(InvoiceContract.TABLE_NAME);
                long invoiceId = InvoiceContract.getInvoiceID(uri);
                queryBuilder.appendWhere(InvoiceContract.Columns._ID + " = " + invoiceId);
                break;
//            case CATEGORIES://TODO TABLE: Categories
//                queryBuilder.setTables(CategoriesContract.TABLE_NAME);
//                break;
//            case CATEGORIES_ID://TODO TABLE: Categories by _Id.
//                queryBuilder.setTables(CategoriesContract.TABLE_NAME);
//                long categoriesId = CategoriesContract.getCategoriesId(uri);
//                queryBuilder.appendWhere(CategoriesContract.Columns._ID + " = " + categoriesId);
//                break;
            case BALANCE://TODO TABLE: Balance
                Log.d(TAG, "query: balance");
                queryBuilder.setTables(BalanceContract.TABLE_NAME);
                break;
            case BALANCE_ID://TODO TABLE: Balance by _Id.
                Log.d(TAG, "query: balance by Id");
                queryBuilder.setTables(BalanceContract.TABLE_NAME);
                long balanceId = BalanceContract.getBalanceId(uri);
                queryBuilder.appendWhere(BalanceContract.Columns._ID + " = " + balanceId);
                break;
            case BALANCE_ACCOUNT://TODO TABLE: Balance by Account
                queryBuilder.setTables(BalanceContract.TABLE_NAME);
                long balanceAccount = BalanceContract.getBalanceAccount(uri);
                queryBuilder.appendWhere(BalanceContract.Columns.BALANCE_ACCOUNT + " = " + balanceAccount);
                break;
            case BALANCE_CATEGORY: //TODO TABLE: Balance by Categories
                queryBuilder.setTables(BalanceContract.TABLE_NAME);
                long balanceCategory = BalanceContract.getBalanceCategory(uri);
                queryBuilder.appendWhere(BalanceContract.Columns.BALANCE_CATEGORY + " = " + balanceCategory);
                break;
//            case ACCOUNTS: //TODO TABLE: Accounts.
//                queryBuilder.setTables(AccountsContract.TABLE_NAME);
//                break;
//            case ACCOUNTS_ID: //TODO TABLE: Accounts by _Id
//                queryBuilder.setTables(AccountsContract.TABLE_NAME);
//                long accountId = AccountsContract.getAccountId(uri);
//                queryBuilder.appendWhere(AccountsContract.Columns._ID +" = "+ accountId);

            default:
                throw new IllegalArgumentException("Unknown Uri: " + uri);
        }

        SQLiteDatabase db = mOpenHelper.getReadableDatabase();
        return queryBuilder.query(db, projection, selection, selectionArgs, null, null, sortOrder);
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        final int match = sUriMatcher.match(uri);

        switch (match) {
            case INVOICE:
                return InvoiceContract.CONTENT_TYPE;
            case INVOICE_ID:
                return InvoiceContract.CONTENT_ITEM_TYPE;
            case CATEGORIES:
                return CategoriesContract.CONTENT_TYPE;
            case CATEGORIES_ID:
                return CategoriesContract.CONTENT_ITEM_TYPE;
            case BALANCE:
                return BalanceContract.CONTENT_TYPE;
            case BALANCE_ID:
                return BalanceContract.CONTENT_ITEM_TYPE;
            //TODO: BALANCE_ACCOUNT & BALANCE_CATEGORIES are missing
            case ACCOUNTS:
                return AccountsContract.CONTENT_TYPE;
            case ACCOUNTS_ID:
                return AccountsContract.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalArgumentException("Unknown Uri: " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        Log.d(TAG, "Entering insert, called with uri: " + uri);
        final int match = sUriMatcher.match(uri);
        Log.d(TAG, "match is " + match);

        final SQLiteDatabase db;

        Uri returnUri = null; //TODO: ? ? ? ? ? ? ? ? ? ? ? ? ? ? ?
        long recordId;

        switch (match) {
            case INVOICE:
                db = mOpenHelper.getWritableDatabase();
                recordId = db.insert(InvoiceContract.TABLE_NAME, null, values);

                if (recordId >= 0) {
                    returnUri = InvoiceContract.buildInvoiceUri(recordId);
                } else {
                    throw new android.database.SQLException("Failed to insert into " + uri.toString());
                }
                break;
            case CATEGORIES:
                db = mOpenHelper.getWritableDatabase();
                recordId = db.insert(CategoriesContract.TABLE_NAME, null, values);
                if (recordId >= 0) {
                    returnUri = CategoriesContract.buildCategoryUri(recordId);
                } else {
                    throw new android.database.SQLException("Failed to insert into " + uri.toString());
                }
                break;
            case BALANCE:
                db = mOpenHelper.getWritableDatabase();
                recordId = db.insert(BalanceContract.TABLE_NAME, null, values);

                if (recordId >= 0) {
                    returnUri = BalanceContract.buildBalanceUri(recordId);
                } else {
                    throw new android.database.SQLException("Failed to insert into " + uri.toString());

                }
                break;
            case ACCOUNTS:
                db = mOpenHelper.getWritableDatabase();
                break;
            default:
                throw new IllegalArgumentException("Unknown uri: " + uri);
        }

        Log.d(TAG, "Exiting insert, returning " + returnUri);
        return returnUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        Log.d(TAG, "update called with uri: " + uri);
        final int match = sUriMatcher.match(uri);
        Log.d(TAG, "Match is " + match);

        final SQLiteDatabase db;
        int count;

        String selectionCriteria;

        switch (match) {
            case INVOICE:
                db = mOpenHelper.getWritableDatabase();
                count = db.delete(InvoiceContract.TABLE_NAME, selection, selectionArgs);
                break;
            case INVOICE_ID:
                db = mOpenHelper.getWritableDatabase();
                long invoiceId = InvoiceContract.getInvoiceID(uri);
                selectionCriteria = InvoiceContract.Columns._ID + " = " + invoiceId;
                if ((selection != null) && (selection.length() > 0)) {
                    selectionCriteria += " AND (" + selection + ")";
                }
                count = db.delete(InvoiceContract.TABLE_NAME, selectionCriteria, selectionArgs);
                break;
            case CATEGORIES:
                db = mOpenHelper.getWritableDatabase();
                count = db.delete(CategoriesContract.TABLE_NAME,  selection, selectionArgs);
                break;
            case CATEGORIES_ID:
                db = mOpenHelper.getWritableDatabase();
                long categoriesId = CategoriesContract.getCategoryID(uri);
                selectionCriteria = CategoriesContract.Columns._ID + " = " + categoriesId;
                if ((selection != null) && (selection.length() > 0)) {
                    selectionCriteria += " AND (" + selection + ")";
                }
                count = db.delete(CategoriesContract.TABLE_NAME,  selectionCriteria, selectionArgs);
                break;
            case BALANCE:
                db = mOpenHelper.getWritableDatabase();
                count = db.delete(BalanceContract.TABLE_NAME,  selection, selectionArgs);
                break;
            case BALANCE_ID:
                db = mOpenHelper.getWritableDatabase();
                long balanceId = BalanceContract.getBalanceId(uri);
                selectionCriteria = BalanceContract.Columns._ID + " = " + balanceId;
                if ((selection != null) && (selection.length() > 0)) {
                    selectionCriteria += " AND (" + selection + ")";
                }
                count = db.delete(BalanceContract.TABLE_NAME,  selectionCriteria, selectionArgs);
                break;
            case ACCOUNTS:
                db = mOpenHelper.getWritableDatabase();
                count = db.delete(AccountsContract.TABLE_NAME,  selection, selectionArgs);
                break;
            case ACCOUNTS_ID:
                db = mOpenHelper.getWritableDatabase();
                long accountId = AccountsContract.getAccountID(uri);
                selectionCriteria = AccountsContract.Columns._ID + " = " + accountId;
                if ((selection != null) && (selection.length() > 0)) {
                    selectionCriteria += " AND (" + selection + ")";
                }
                count = db.delete(AccountsContract.TABLE_NAME,  selectionCriteria, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown uri: " + uri);
        }
        Log.d(TAG, "Exiting delete, returning " + count);
        return count;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        Log.d(TAG, "update called with uri: " + uri);
        final int match = sUriMatcher.match(uri);
        Log.d(TAG, "Match is " + match);

        final SQLiteDatabase db;
        int count;

        String selectionCriteria;

        switch (match) {
            case INVOICE:
                db = mOpenHelper.getWritableDatabase();
                count = db.update(InvoiceContract.TABLE_NAME, values, selection, selectionArgs);
                break;
            case INVOICE_ID:
                db = mOpenHelper.getWritableDatabase();
                long invoiceId = InvoiceContract.getInvoiceID(uri);
                selectionCriteria = InvoiceContract.Columns._ID + " = " + invoiceId;
                if ((selection != null) && (selection.length() > 0)) {
                    selectionCriteria += " AND (" + selection + ")";
                }
                count = db.update(InvoiceContract.TABLE_NAME, values, selectionCriteria, selectionArgs);
                break;
            case CATEGORIES:
                db = mOpenHelper.getWritableDatabase();
                count = db.update(CategoriesContract.TABLE_NAME, values, selection, selectionArgs);
                break;
            case CATEGORIES_ID:
                db = mOpenHelper.getWritableDatabase();
                long categoriesId = CategoriesContract.getCategoryID(uri);
                selectionCriteria = CategoriesContract.Columns._ID + " = " + categoriesId;
                if ((selection != null) && (selection.length() > 0)) {
                    selectionCriteria += " AND (" + selection + ")";
                }
                count = db.update(CategoriesContract.TABLE_NAME, values, selectionCriteria, selectionArgs);
                break;
            case BALANCE:
                db = mOpenHelper.getWritableDatabase();
                count = db.update(BalanceContract.TABLE_NAME, values, selection, selectionArgs);
                break;
            case BALANCE_ID:
                db = mOpenHelper.getWritableDatabase();
                long balanceId = BalanceContract.getBalanceId(uri);
                selectionCriteria = BalanceContract.Columns._ID + " = " + balanceId;
                if ((selection != null) && (selection.length() > 0)) {
                    selectionCriteria += " AND (" + selection + ")";
                }
                count = db.update(BalanceContract.TABLE_NAME, values, selectionCriteria, selectionArgs);
                break;
            case ACCOUNTS:
                db = mOpenHelper.getWritableDatabase();
                count = db.update(AccountsContract.TABLE_NAME, values, selection, selectionArgs);
                break;
            case ACCOUNTS_ID:
                db = mOpenHelper.getWritableDatabase();
                long accountId = AccountsContract.getAccountID(uri);
                selectionCriteria = AccountsContract.Columns._ID + " = " + accountId;
                if ((selection != null) && (selection.length() > 0)) {
                    selectionCriteria += " AND (" + selection + ")";
                }
                count = db.update(AccountsContract.TABLE_NAME, values, selectionCriteria, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown uri: " + uri);
        }
        Log.d(TAG, "Exiting update, returning " + count);
        return count;
    }
}
