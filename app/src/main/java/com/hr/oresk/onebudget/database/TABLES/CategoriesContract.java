package com.hr.oresk.onebudget.database.TABLES;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

import static com.hr.oresk.onebudget.AppProvider.CONTENT_AUTHORITY;
import static com.hr.oresk.onebudget.AppProvider.CONTENT_AUTHORITY_URI;

public class CategoriesContract {

    public static final String TABLE_NAME = "Categories";

    public static class Columns {
        public static final String _ID = BaseColumns._ID;
        public static final String CATEGORIES_NAME = "Name";
        public static final String CATEGORIES_INVOICE_TYPE = "Invoice_Type";
        public static final String CATEGORIES_DESCRIPTION = "Description";
        public static final String CATEGORIES_AMOUNT = "Amount";

        private Columns() {
            // private constructor to prevent instantiation
        }
    }

    public static final Uri CONTENT_URI = Uri.withAppendedPath(CONTENT_AUTHORITY_URI, TABLE_NAME);

    public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd." + CONTENT_AUTHORITY + "." + TABLE_NAME;
    public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd." + CONTENT_AUTHORITY + "." + TABLE_NAME;

    public static Uri buildCategoryUri(long taskId) {
        return ContentUris.withAppendedId(CONTENT_URI, taskId);
    }

    public static long getCategoryID(Uri uri) {
        return ContentUris.parseId(uri);
    }
}
