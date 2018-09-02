package com.hr.oresk.onebudget.database.TABLES;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

import static com.hr.oresk.onebudget.AppProvider.CONTENT_AUTHORITY;
import static com.hr.oresk.onebudget.AppProvider.CONTENT_AUTHORITY_URI;

public class AccountsContract {

    public static final String TABLE_NAME = "Accounts";

    public static class Columns {
        public static final String _ID = BaseColumns._ID;
        public static final String ACCOUNTS_NAME = "Name";
        public static final String ACCOUNTS_AMOUNT = "Amount";
        public static final String ACCOUNTS_DESCRIPTION = "Description";

        private Columns() {
            // private constructor to prevent instantiation
        }
    }

    public static final Uri CONTENT_URI = Uri.withAppendedPath(CONTENT_AUTHORITY_URI, TABLE_NAME);

    public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd." + CONTENT_AUTHORITY + "." + TABLE_NAME;
    public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd." + CONTENT_AUTHORITY + "." + TABLE_NAME;

    public static Uri buildAccountUri(long taskId) {
        return ContentUris.withAppendedId(CONTENT_URI, taskId);
    }

    public static long getAccountID(Uri uri) {
        return ContentUris.parseId(uri);
    }

}
