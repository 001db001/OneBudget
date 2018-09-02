package com.hr.oresk.onebudget.database.TABLES;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

import static com.hr.oresk.onebudget.AppProvider.CONTENT_AUTHORITY;
import static com.hr.oresk.onebudget.AppProvider.CONTENT_AUTHORITY_URI;

public class InvoiceContract {
   public static final String TABLE_NAME = "Invoice";

    public static class Columns {
        public static final String _ID = BaseColumns._ID;
        public static final String INVOICE_ACCOUNT = "Account";
        public static final String INVOICE_CATEGORY = "Category";
        public static final String INVOICE_AMOUNT = "Amount";
        public static final String INVOICE_DATE = "Date";
        public static final String INVOICE_TYPE = "Invoice_Type";
        public static final String INVOICE_DESCRIPTION = "Description";

        private Columns() {
            // private constructor to prevent instantiation
        }
    }

    /**
     * The URI to access the Invoice table.
     */
    public static final Uri CONTENT_URI = Uri.withAppendedPath(CONTENT_AUTHORITY_URI, TABLE_NAME);

   public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd." + CONTENT_AUTHORITY + "." + TABLE_NAME;
    public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd." + CONTENT_AUTHORITY + "." + TABLE_NAME;

    public static Uri buildInvoiceUri(long taskId) {
        return ContentUris.withAppendedId(CONTENT_URI, taskId);
    }

    public static long getInvoiceID(Uri uri) {
        return ContentUris.parseId(uri);
    }



}
