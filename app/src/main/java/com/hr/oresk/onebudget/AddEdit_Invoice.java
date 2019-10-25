package com.hr.oresk.onebudget;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.hr.oresk.onebudget.database.TABLES.AccountsContract;
import com.hr.oresk.onebudget.database.TABLES.BalanceContract;
import com.hr.oresk.onebudget.database.TABLES.CategoriesContract;
import com.hr.oresk.onebudget.database.TABLES.InvoiceContract;

import java.text.DecimalFormat;

public class AddEdit_Invoice extends AppCompatActivity {

    private static final String TAG = "AddEdit_Invoice";

    public enum FragmentEditMode {EDIT, ADD}

    private FragmentEditMode mMode;

    private Invoice invoice;


    private EditText mDescription, mAmount;

    private Spinner mCategory, mAccount;

    private RadioGroup mRadioGroup;
    private RadioButton mRadioButton;

    private Button btn_Save;

    private TextView negNumView;
    String tks = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: starts");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addedit_invoice);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mAccount = findViewById(R.id.addEdit_Account);
        mCategory = findViewById(R.id.addEdite_Category);
        mRadioGroup = findViewById(R.id.radioGroup);
        mDescription = findViewById(R.id.addEdit_Description);
        mAmount = findViewById(R.id.addEdit_Amount);
        negNumView = findViewById(R.id.negNumTextView);

        btn_Save = findViewById(R.id.addEdite_save);

        //------------------------------------------------------------------------------------------
        // TODO: custom Account spinner
        accountSpinner();
        //------------------------------------------------------------------------------------------
        //TODO: custom Categories spinner
        categoriesSpinner();
        //------------------------------------------------------------------------------------------
        getExtraInvoice();
        //------------------------------------------------------------------------------------------
        btn_Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save_edit_btn();
            }
        });

        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int radioId = mRadioGroup.getCheckedRadioButtonId();
                mRadioButton = findViewById(radioId);
                String type = mRadioButton.getText().toString();
                switch (type) {
                    case "Expense":
                        negNumView.setText("-");
                        break;
                    case "Income":
                        negNumView.setText("");
                        break;
                }
            }
        });

    }

    private void getExtraInvoice() {
        Bundle arguments = this.getIntent().getExtras();


        if (arguments != null) {
            Log.d(TAG, "onCreate: retrieving invoice details");

            invoice = (Invoice) arguments.getSerializable(Invoice.class.getSimpleName());
            if (invoice != null) {
                Log.d(TAG, "onCreate: invoice details found, editing...");
                mDescription.setText(invoice.getDescription());
                float num1 = Float.parseFloat(invoice.getAmount());
                if (num1 >= 0) {
                    mAmount.setText(invoice.getAmount());
                } else {
                    mAmount.setText(String.valueOf(num1 *-1));
                }


                String type = invoice.getInvoice_Type();
                mMode = FragmentEditMode.EDIT;
                switch (type) {
                    case "Expense":
                        Log.d(TAG, "getExtraInvoice: >>>>><<<<<<<<<<<<<");
                        mRadioButton = findViewById(R.id.addEdit_Expense);
                        mRadioButton.setChecked(true);
                        negNumView.setText("-");
                        break;
                    case "Income":
                        Log.d(TAG, "getExtraInvoice: <<<<<<<<<<>>>>>>>>>><");
                        mRadioButton = findViewById(R.id.addEdit_Income);
                        mRadioButton.setChecked(true);
                        negNumView.setText("");
                        break;
                }


            } else {
                mMode = FragmentEditMode.ADD;
            }
        } else {
            invoice = null;
            mMode = FragmentEditMode.ADD;
        }
    }

    private void save_edit_btn() {
        ContentResolver contentResolver = getContentResolver();
        ContentValues values = new ContentValues();
        int radioId = mRadioGroup.getCheckedRadioButtonId();
        mRadioButton = findViewById(radioId);


        String accountItem = null;
        Cursor cursorAccount = (Cursor) mAccount.getSelectedItem();
        if (cursorAccount != null) {
            accountItem = cursorAccount.getString(cursorAccount.getColumnIndex(AccountsContract.Columns.ACCOUNTS_NAME));
        }
        String categoryItem = null;
        Cursor cursorCategory = (Cursor) mCategory.getSelectedItem();
        if (cursorCategory != null) {
            categoryItem = cursorCategory.getString(cursorCategory.getColumnIndex(CategoriesContract.Columns.CATEGORIES_NAME));
        }

        String tks = negNumView.getText().toString() + mAmount.getText().toString();




        switch (mMode) {
            case EDIT:

                if (!mDescription.getText().toString().equals(invoice.getDescription())) {
                    values.put(InvoiceContract.Columns.INVOICE_DESCRIPTION, mDescription.getText().toString());
                }
                String sign = negNumView.getText().toString();
                String number = mAmount.getText().toString();
                if (!(sign+number).equals(invoice.getAmount())) {
                    values.put(InvoiceContract.Columns.INVOICE_AMOUNT, sign+number);
                    saveBalanceAmount(sign+number,false);

                }

                if (!mRadioButton.getText().toString().equals(invoice.getInvoice_Type())) {
                    values.put(InvoiceContract.Columns.INVOICE_TYPE, mRadioButton.getText().toString());
                }
                //----------------------------------------------------------------------------------
                //TODO:?????????
                values.put(InvoiceContract.Columns.INVOICE_ACCOUNT, accountItem);
                values.put(InvoiceContract.Columns.INVOICE_CATEGORY, categoryItem);
                //----------------------------------------------------------------------------------
                if (values.size() != 0) {
                    Log.d(TAG, "onClick: updating");
                    contentResolver.update(InvoiceContract.buildInvoiceUri(invoice.getId()), values, null, null);

                }
                Toast.makeText(AddEdit_Invoice.this, "Done Editing", Toast.LENGTH_SHORT).show();


                break;


            case ADD:
                if ((mDescription.length() > 0) && (mAmount.length() > 0)) {
                    Log.d(TAG, "onClick: adding new invoice");
                    values.put(InvoiceContract.Columns.INVOICE_DESCRIPTION, mDescription.getText().toString());
                    values.put(InvoiceContract.Columns.INVOICE_AMOUNT, tks);
                    values.put(InvoiceContract.Columns.INVOICE_ACCOUNT, accountItem);
                    values.put(InvoiceContract.Columns.INVOICE_CATEGORY, categoryItem);
                    values.put(InvoiceContract.Columns.INVOICE_TYPE, mRadioButton.getText().toString());
                    contentResolver.insert(InvoiceContract.CONTENT_URI, values);
                    Toast.makeText(AddEdit_Invoice.this, "Entry Saved", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(AddEdit_Invoice.this, "You have to input description and amount.", Toast.LENGTH_SHORT).show();
                }

                saveAccountAmount(accountItem, tks);
                saveCategoryAmount(categoryItem, tks);
                saveBalanceAmount(tks,true);
                break;

        }

        Log.d(TAG, "onClick: Done editing");
    }

    private void accountSpinner() {
        Cursor cursor;
        ContentResolver contentResolver = getContentResolver();
        String[] fromColumns = {AccountsContract.Columns.ACCOUNTS_NAME};
        int[] toViews = {android.R.id.text1};
        cursor = contentResolver.query(
                AccountsContract.CONTENT_URI,
                null,
                null,
                null,
                AccountsContract.Columns._ID);
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                cursor,
                fromColumns,
                toViews, 0);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mAccount.setAdapter(adapter);


        /*accountList.add(new Account(1, "PBZ tekuci", "1000", "Tekući račun u Pbz banci"));
        accountList.add(new Account(2, "PBZ debit", "25", "Žiro račun u Pbz banci"));
        accountList.add(new Account(3, "Gotovina", "20", "Gotovina dobro skrivena :)"));


        AccountSpinnerAdapter accountSpinnerAdapter =
                new AccountSpinnerAdapter
                        (AddEdit_Invoice.this, R.layout.account_spinner_layout, accountList
                        );
        mAccount.setAdapter(accountSpinnerAdapter);*/
    }

    private void categoriesSpinner() {
        Cursor cursor;
        ContentResolver contentResolver = getContentResolver();
        String[] fromColumns = {CategoriesContract.Columns.CATEGORIES_NAME};
        int[] toViews = {android.R.id.text1};
        cursor = contentResolver.query(
                CategoriesContract.CONTENT_URI,
                null,
                null,
                null,
                CategoriesContract.Columns._ID);
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                cursor,
                fromColumns,
                toViews, 0);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mCategory.setAdapter(adapter);



       /* categoryList.add(new Category(1, "Transport", "Expense", "Prijevoz"));
        categoryList.add(new Category(2, "Plaća", "Income", "Plaćaaaa"));
        categoryList.add(new Category(3, "Prehrana", "Expense", "hrana :)"));

        CategorySpinnerAdapter categorySpinnerAdapter =
                new CategorySpinnerAdapter
                        (AddEdit_Invoice.this, R.layout.category_spinner_layout, categoryList);
        mCategory.setAdapter(categorySpinnerAdapter);*/
    }

    private void saveAccountAmount(String arg_1, String arg_2) {
        ContentResolver contentResolver = getContentResolver();
        ContentValues values = new ContentValues();
        String[] projection = {
                AccountsContract.Columns.ACCOUNTS_NAME,
                AccountsContract.Columns.ACCOUNTS_AMOUNT,
                AccountsContract.Columns._ID
        };


        String[] args = {arg_1};

        Cursor cursor = contentResolver.query
                (
                        AccountsContract.CONTENT_URI,
                        projection,
                        AccountsContract.Columns.ACCOUNTS_NAME + " = ? ",
                        args,
                        AccountsContract.Columns._ID

                );
        Log.d(TAG, "onCreate: number of rows: " + cursor.getCount());

        while (cursor.moveToNext()) {
            String ss = cursor.getString(cursor.getColumnIndex(AccountsContract.Columns.ACCOUNTS_AMOUNT));
            String accountId = cursor.getString(cursor.getColumnIndex(AccountsContract.Columns._ID));
            float aa = Float.parseFloat(ss);
            float ab = Float.parseFloat(arg_2);
            float ac = aa + ab;
            values.put(AccountsContract.Columns.ACCOUNTS_AMOUNT, ac);
            for (int i = 0; i < cursor.getColumnCount(); i++) {
                Log.d(TAG, "onCreate: " + cursor.getColumnName(i) + ": " + cursor.getString(i));
            }
            Log.d(TAG, "onCreate: =================================");
            Log.d(TAG, "saveAccountAmount: /////////////////////////////////// " + ac + " \n ///////////////////////////// " + accountId);
            contentResolver.update(AccountsContract.buildAccountUri(Integer.parseInt(accountId)), values, null, null);
        }


    }

    private void saveCategoryAmount(String arg_1, String arg_2) {
        ContentResolver contentResolver = getContentResolver();
        ContentValues values = new ContentValues();
        String[] projection = {CategoriesContract.Columns.CATEGORIES_NAME, CategoriesContract.Columns.CATEGORIES_AMOUNT, CategoriesContract.Columns._ID};


        String[] args = {arg_1};

        Cursor cursor = contentResolver.query
                (
                        CategoriesContract.CONTENT_URI,
                        projection,
                        CategoriesContract.Columns.CATEGORIES_NAME + " = ? ",
                        args,
                        CategoriesContract.Columns._ID

                );
        Log.d(TAG, "onCreate: number of rows: " + cursor.getCount());

        while (cursor.moveToNext()) {
            String ss = cursor.getString(cursor.getColumnIndex(CategoriesContract.Columns.CATEGORIES_AMOUNT));
            String accountId = cursor.getString(cursor.getColumnIndex(CategoriesContract.Columns._ID));
            float aa = Float.parseFloat(ss) + Float.parseFloat(/*mAmount.getText().toString()*/arg_2);
            values.put(CategoriesContract.Columns.CATEGORIES_AMOUNT, aa);
            for (int i = 0; i < cursor.getColumnCount(); i++) {
                Log.d(TAG, "onCreate: " + cursor.getColumnName(i) + ": " + cursor.getString(i));
            }
            Log.d(TAG, "onCreate: =================================");
            Log.d(TAG, "saveAccountAmount: /////////////////////////////////// " + aa + " \n ///////////////////////////// " + accountId);
            contentResolver.update(CategoriesContract.buildCategoryUri(Integer.parseInt(accountId)), values, null, null);
        }


    }

    private void saveBalanceAmount(String arg1,boolean addOrEdit) {
        ContentResolver resolver = getContentResolver();
        ContentValues values = new ContentValues();
        String[] projection = {BalanceContract.Columns.BALANCE_AMOUNT};
        Cursor cursor = resolver.query(BalanceContract.CONTENT_URI, projection, null, null, BalanceContract.Columns._ID);
        assert cursor != null;
        cursor.moveToLast();
        String aa = cursor.getString(cursor.getColumnIndex(BalanceContract.Columns.BALANCE_AMOUNT));
        float answer;
        if (addOrEdit) {
             answer = Float.parseFloat(aa) + Float.parseFloat(arg1);
        } else {
            float dbAmount = Float.parseFloat(aa);
            float amount = Float.parseFloat(arg1);
            answer = dbAmount+(amount - dbAmount);
        }

        DecimalFormat df = new DecimalFormat("0.##");
        String finalAnswer = df.format(answer);
        values.put(BalanceContract.Columns.BALANCE_AMOUNT, finalAnswer);
        Log.d(TAG, "saveBalanceAmount: BALANCE ============================================== " + finalAnswer);
        resolver.insert(BalanceContract.CONTENT_URI, values);

    }


}

