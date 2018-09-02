package com.hr.oresk.onebudget.database.TABLES;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

import static com.hr.oresk.onebudget.AppProvider.CONTENT_AUTHORITY;
import static com.hr.oresk.onebudget.AppProvider.CONTENT_AUTHORITY_URI;

public class BalanceContract {
     public static final String TABLE_NAME = "Balance";

    public static class Columns {
        public static final String _ID = BaseColumns._ID;
        public static final String BALANCE_ACCOUNT = "Account";
        public static final String BALANCE_CATEGORY = "Category";
        public static final String BALANCE_AMOUNT = "Amount";
        public static final String BALANCE_DESCRIPTION = "Description";
        public static final String BALANCE_DATE = "Date";

        private Columns() {
            // private constructor to prevent instantiation
        }
    }

    public static final Uri CONTENT_URI = Uri.withAppendedPath(CONTENT_AUTHORITY_URI, TABLE_NAME);

    public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd." + CONTENT_AUTHORITY + "." + TABLE_NAME;
    public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd." + CONTENT_AUTHORITY + "." + TABLE_NAME;


    public static Uri buildBalanceUri(long balanceId) {
        return ContentUris.withAppendedId(CONTENT_URI, balanceId);
    }

//    public static Uri buildBalanceUri(String keyword) {
//        return Uri.withAppendedPath(CONTENT_URI,keyword);
//    }

    public static long getBalanceId(Uri uri) {
        return ContentUris.parseId(uri);
    }

    public static long getBalanceAccount(Uri uri) {
        return ContentUris.parseId(uri);
    }

    public static long getBalanceCategory(Uri uri) {
        return ContentUris.parseId(uri);
    }
}
