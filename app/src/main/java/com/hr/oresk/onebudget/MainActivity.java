package com.hr.oresk.onebudget;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.hr.oresk.onebudget.database.TABLES.BalanceContract;
import com.hr.oresk.onebudget.database.TABLES.InvoiceContract;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private static int rerere;

    public static int getRerere() {
        return rerere;
    }

    public static void setRerere(int rerere) {
        MainActivity.rerere = rerere;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);





//        String[] projection = {InvoiceContract.Columns.INVOICE_AMOUNT, InvoiceContract.Columns.INVOICE_DESCRIPTION};//TODO: Setting witch column will be pulled from table (To see all of them use null).
//
//
//        ContentResolver contentResolver = getContentResolver();
//        String selection = null;
//
//        ContentValues values = new ContentValues();
//        setRerere(0);
//        switch (getRerere()) {
//            case 0:
//                 selection= BalanceContract.Columns._ID + " = ? ";
//                break;
//            case 1:
//                selection = BalanceContract.Columns.BALANCE_ACCOUNT + " = ? ";
//                break;
//            case 2:
//                selection =BalanceContract.Columns.BALANCE_CATEGORY + " = ? ";
//        }
//
////        String selection = BalanceContract.Columns.BALANCE_ACCOUNT + " =?";
////        String selection = BalanceContract.Columns.BALANCE_CATEGORY + " = ? ";
//
//        String[] args = {"3"};
//        Log.d(TAG, "onCreate: ////>>>>"+ getRerere());
////        Cursor cursor = contentResolver.query(BalanceContract.buildBalanceUri(2),
//        Cursor cursor = contentResolver.query(BalanceContract.CONTENT_URI,
//                null,
//                null,
//                null,
//                BalanceContract.Columns._ID);
//        Log.d(TAG, "onCreate: >>>>>>>>>>>>>>>>>>>>>>><<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
//
//        if (cursor != null) {
//
//            Log.d(TAG, "onCreate: number of rows: " + cursor.getCount());
//            while (cursor.moveToNext()) {
//
//                for (int i = 0; i < cursor.getColumnCount(); i++) {
//                    Log.d(TAG, "onCreate: " + cursor.getColumnName(i) + ": " + cursor.getString(i));
//                }
//                Log.d(TAG, "onCreate: =================================");
//            }
//
//            //TODO: Getting last balance insert
////            if(cursor.moveToLast()){
////            for (int i = 0;i< cursor.getColumnCount();i++) {
////                Log.d(TAG, "onCreate: "+cursor.getColumnName(i)+": "+cursor.getString(i));
////            }
////        }
//
//            cursor.close();
//        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
