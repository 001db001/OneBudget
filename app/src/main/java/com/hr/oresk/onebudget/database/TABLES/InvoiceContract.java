package com.hr.oresk.onebudget.database.TABLES;

import android.provider.BaseColumns;

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



}
