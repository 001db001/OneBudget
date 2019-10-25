package com.hr.oresk.onebudget;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.hr.oresk.onebudget.database.TABLES.AccountsContract;
import com.hr.oresk.onebudget.database.TABLES.BalanceContract;
import com.hr.oresk.onebudget.database.TABLES.CategoriesContract;
import com.hr.oresk.onebudget.database.TABLES.InvoiceContract;


public class MainActivity
        extends AppCompatActivity
        implements
        Invoice_CRV_Adapter.OnInvoiceClickListener,
        Account_CRV_Adapter.OnAccountClickListener,
        Category_CRV_Adapter.OnCategoryClickListener {

    private static final String TAG = "MainActivity";

    private HomeFragment mHomeFragment;
    private InvoiceFragment mInvoiceFragment;
    private CategoryFragment mCategoryFragment;
    private AccountFragment mAccountFragment;

    FloatingActionButton fab_plus,fab_invoice, fab_account, fab_category;
    Animation f_open, f_close, rotate_clock, rotate_anitClock;
    boolean isOpen = false;
    private TextView balanceView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);*/

        final Intent intent = new Intent(this, AddEdit_Invoice.class);

        FrameLayout frameLayout = findViewById(R.id.main_Frame);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav);

        /*mHomeFragment = new HomeFragment();*/
        mInvoiceFragment = new InvoiceFragment();
        mCategoryFragment = new CategoryFragment();
        mAccountFragment = new AccountFragment();
        setFragment(mInvoiceFragment);

        balanceView = findViewById(R.id.text_balance);
        settingBalanceView();

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    /*case R.id.bottomNav_Home:
                        setFragment(mHomeFragment);
                        return true;*/
                    case R.id.bottomNav_Invoice:
                        setFragment(mInvoiceFragment);
                        return true;
                    case R.id.bottomNav_Category:
                        setFragment(mCategoryFragment);
                        return true;
                    case R.id.bottomNav_Account:
                        setFragment(mAccountFragment);
                        return true;
                    default:
                        return false;
                }
            }
        });

        /*TODO://///////////////// I have to initialize Balance table before first use of app??????
        ContentResolver contentResolver = getContentResolver();
        ContentValues values = new ContentValues();

        Cursor cursor = contentResolver.query(BalanceContract.CONTENT_URI,
                null,
                null,
                null,
                BalanceContract.Columns._ID);
        values.put(BalanceContract.Columns.BALANCE_AMOUNT, "0");
        contentResolver.insert(BalanceContract.CONTENT_URI, values);*/

//        String[] description = {"Description of Category One", "Description of Category Two", "Description of Category Three"};
//        String[] invoiceType = {"Income", "Expense", "Income"};
//        String[] amount = {"0", "0", "0"};
//
//            values.put(CategoriesContract.Columns.CATEGORIES_NAME, );
//
//            values.put(CategoriesContract.Columns.CATEGORIES_DESCRIPTION, );
//
//            values.put(CategoriesContract.Columns.CATEGORIES_INVOICE_TYPE, invoiceType[i]);
//
//            values.put(CategoriesContract.Columns.CATEGORIES_AMOUNT, amount[i]);
//
//        contentResolver.insert(CategoriesContract.CONTENT_URI, values);

//        Cursor cursor = contentResolver.query(CategoriesContract.CONTENT_URI,
//                null,
//                null,
//                null,
//                CategoriesContract.Columns._ID);
//        if (cursor != null) {
//            Log.d(TAG, "onCreate: number of rows: " + cursor.getCount());
//            while (cursor.moveToNext()) {
//                for (int i=0 ; i < cursor.getColumnCount(); i++) {
//                    Log.d(TAG, "onCreate: ================================= " + cursor.getColumnName(i) + ": " + cursor.getString(i));
//
//                }
//            }
//        }

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

        fab_plus = (FloatingActionButton) findViewById(R.id.fab_plus);
        fab_invoice = (FloatingActionButton) findViewById(R.id.fab_invoice);
        fab_account = (FloatingActionButton) findViewById(R.id.fab_account);
        fab_category = (FloatingActionButton) findViewById(R.id.fab_category);

        f_open = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
        f_close = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_close);
        rotate_clock = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_clock);
        rotate_anitClock = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_aniclock);

        fab_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isOpen) {
                    fab_invoice.startAnimation(f_close);
                    fab_account.startAnimation(f_close);
                    fab_category.startAnimation(f_close);
                    fab_plus.startAnimation(rotate_anitClock);

                    fab_invoice.setClickable(false);
                    fab_account.setClickable(false);
                    fab_category.setClickable(false);

                    isOpen = false;
                } else {
                    fab_invoice.startAnimation(f_open);
                    fab_account.startAnimation(f_open);
                    fab_category.startAnimation(f_open);
                    fab_plus.startAnimation(rotate_clock);

                    fab_invoice.setClickable(true);
                    fab_account.setClickable(true);
                    fab_category.setClickable(true);

                    isOpen = true;
                }
            }
        });
        fab_invoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                invoiceEditRequest(null);
            }
        });
        fab_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                categoryEditRequest(null);
            }
        });
        fab_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                accountEditRequest(null);
            }
        });
    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_Frame, fragment);
        fragmentTransaction.commit();
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

    private void invoiceEditRequest(Invoice invoice) {
        Log.d(TAG, "invoiceEditRequest: starts");
        Intent detailIntent = new Intent(this, AddEdit_Invoice.class);
        if (invoice != null) {// Editing a invoice
            detailIntent.putExtra(Invoice.class.getSimpleName(), invoice);
            startActivity(detailIntent);
        } else {
            startActivity(detailIntent);
        }
    }

    private void accountEditRequest(Account account) {
        Log.d(TAG, "accountEditRequest: starts");
        Intent detailIntent = new Intent(this, AddEdit_account.class);
        if (account != null) {// Editing a account
            detailIntent.putExtra(Account.class.getSimpleName(), account);
            startActivity(detailIntent);
        } else {
            startActivity(detailIntent);
        }
    }

    private void categoryEditRequest(Category category) {
        Log.d(TAG, "categoryEditRequest: starts");
        Intent detailIntent = new Intent(this, AddEdit_Category.class);
        if (category != null) {// Editing a category
            detailIntent.putExtra(Category.class.getSimpleName(), category);
            startActivity(detailIntent);
        } else {
            startActivity(detailIntent);
        }
    }

    @Override
    public void onEditClickInvoice(Invoice invoice) {
        invoiceEditRequest(invoice);
    }

    @Override
    public void onDeleteClickInvoice(Invoice invoice) {
        getContentResolver().delete(InvoiceContract.buildInvoiceUri(invoice.getId()), null, null);
        setFragment(new InvoiceFragment());
    }

    @Override
    public void onEditClickAccount(Account account) {
        accountEditRequest(account);
    }

    @Override
    public void onDeleteClickAccount(Account account) {
        getContentResolver().delete(AccountsContract.buildAccountUri(account.getId()), null, null);
        setFragment(new AccountFragment());
    }

    @Override
    public void onEditClickCategory(Category category) {
        categoryEditRequest(category);
    }

    @Override
    public void onDeleteClickCategory(Category category) {
        getContentResolver().delete(CategoriesContract.buildCategoryUri(category.getId()), null, null);
        setFragment(new CategoryFragment());
    }

    private void settingBalanceView() {
        ContentResolver contentResolver = getContentResolver();
        String[] projection = {BalanceContract.Columns.BALANCE_AMOUNT};
        Cursor cursor = contentResolver.query(BalanceContract.CONTENT_URI,
                projection,
                null,
                null,
                BalanceContract.Columns._ID);
        assert cursor != null;
        cursor.moveToLast();
        String aa = cursor.getString(cursor.getColumnIndex(BalanceContract.Columns.BALANCE_AMOUNT));
        balanceView.setText("Balance = " + aa);

    }
}
