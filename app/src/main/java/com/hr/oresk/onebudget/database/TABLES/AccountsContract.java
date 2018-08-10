package com.hr.oresk.onebudget.database.TABLES;

import android.provider.BaseColumns;

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

}
