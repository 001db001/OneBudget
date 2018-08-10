package com.hr.oresk.onebudget.database.TABLES;

import android.provider.BaseColumns;

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
}
