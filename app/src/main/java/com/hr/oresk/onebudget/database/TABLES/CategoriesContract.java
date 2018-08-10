package com.hr.oresk.onebudget.database.TABLES;

import android.provider.BaseColumns;

public class CategoriesContract {

    public static final String TABLE_NAME = "Categories";

    public static class Columns {
        public static final String _ID = BaseColumns._ID;
        public static final String CATEGORIES_NAME = "Name";
        public static final String CATEGORIES_INVOICE_TYPE = "Invoice_Type";
        public static final String CATEGORIES_DESCRIPTION = "Description";

        private Columns() {
            // private constructor to prevent instantiation
        }
    }
}
