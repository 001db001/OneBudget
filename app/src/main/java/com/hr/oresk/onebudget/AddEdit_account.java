package com.hr.oresk.onebudget;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.hr.oresk.onebudget.database.TABLES.AccountsContract;
import com.hr.oresk.onebudget.database.TABLES.BalanceContract;

import java.text.DecimalFormat;


public class AddEdit_account extends AppCompatActivity {
    private static final String TAG = "AddEdit_account";

    public enum FragmentEditModeAccount {EDIT, ADD}

    private FragmentEditModeAccount mMode;
    private Account account;
    private EditText mName, mDescription, mAmount;
    private Button save_btn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addedit_account);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mName = findViewById(R.id.aea_Name);
        mDescription = findViewById(R.id.aea_Description);
        mAmount = findViewById(R.id.aea_Amount);
        save_btn = findViewById(R.id.aea_Save);

        getExtraAccount();

        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save_edit_btn();
            }
        });
    }

    private void getExtraAccount() {
        Bundle args = this.getIntent().getExtras();

        if (args != null) {
            account = (Account) args.getSerializable(Account.class.getSimpleName());

            if (account != null) {
                Log.d(TAG, "getExtraAccount: account details found, editing...");

                mName.setText(account.getName());
                mDescription.setText(account.getDescription());
                mAmount.setText(account.getAmount());
                mMode = FragmentEditModeAccount.EDIT;
            } else {
                mMode = FragmentEditModeAccount.ADD;
            }
        } else {
            account = null;
            mMode = FragmentEditModeAccount.ADD;
        }
    }

    private void save_edit_btn() {
        ContentResolver resolver = getContentResolver();
        ContentValues values = new ContentValues();
        String amount = mAmount.getText().toString();

        switch (mMode) {
            case EDIT:
                xxEdit();
                if (!mName.getText().toString().equals(account.getName())) {
                    values.put(AccountsContract.Columns.ACCOUNTS_NAME, mName.getText().toString());
                }

                if (!mDescription.getText().toString().equals(account.getDescription())) {
                    values.put(AccountsContract.Columns.ACCOUNTS_DESCRIPTION, mDescription.getText().toString());
                }

                if (!mAmount.getText().toString().equals(account.getAmount())) {
                    values.put(AccountsContract.Columns.ACCOUNTS_AMOUNT, mAmount.getText().toString());
                }

                if (values.size() != 0/*TODO: (mName.length() > 0) && (mAmount.length() > 0)*/) {
                    resolver.update(AccountsContract.buildAccountUri(account.getId()), values, null, null);

                    Toast.makeText(AddEdit_account.this, "Entry updated "
                            + values.getAsString(AccountsContract.Columns.ACCOUNTS_NAME) + "; "
                            + values.getAsString(AccountsContract.Columns.ACCOUNTS_DESCRIPTION) + "; "
                            + values.getAsString(AccountsContract.Columns.ACCOUNTS_AMOUNT) + ".", Toast.LENGTH_SHORT).show();
                }

                break;


            case ADD:
                if ((mName.length() > 0) && (mAmount.length() > 0)) {
                    values.put(AccountsContract.Columns.ACCOUNTS_NAME, mName.getText().toString());
                    values.put(AccountsContract.Columns.ACCOUNTS_DESCRIPTION, mDescription.getText().toString());
                    values.put(AccountsContract.Columns.ACCOUNTS_AMOUNT, mAmount.getText().toString());
                    resolver.insert(AccountsContract.CONTENT_URI, values);

                    Toast.makeText(AddEdit_account.this, "Entry saved", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(AddEdit_account.this, "NAME & AMOUNT fields are mandatory", Toast.LENGTH_SHORT).show();
                }
                saveBalanceAmount(Float.parseFloat(mAmount.getText().toString()));
                break;
        }
    }

    private void saveBalanceAmount(float arg1) {
        ContentResolver resolver = getContentResolver();
        ContentValues values = new ContentValues();
        String[] projection = {BalanceContract.Columns.BALANCE_AMOUNT};

        Cursor cursor = resolver.query(BalanceContract.CONTENT_URI, projection, null, null, BalanceContract.Columns._ID);
        assert cursor != null;

        cursor.moveToLast();
        String aa = cursor.getString(cursor.getColumnIndex(BalanceContract.Columns.BALANCE_AMOUNT));
        float ab = Float.parseFloat(aa) + arg1;
        DecimalFormat df = new DecimalFormat("0.##");
        String finalAnswer = df.format(ab);
        values.put(BalanceContract.Columns.BALANCE_AMOUNT, finalAnswer);
        Log.d(TAG, "saveBalanceAmount: BALANCE ============================================== " + ab);
        resolver.insert(BalanceContract.CONTENT_URI, values);
    }

    private void xxEdit() {
        ContentResolver resolver = getContentResolver();

        String[] projection = {AccountsContract.Columns.ACCOUNTS_AMOUNT, AccountsContract.Columns.ACCOUNTS_NAME};
        String tt = mName.getText().toString();
        String[] xx = {tt};

        Cursor cursor = resolver.query(
                AccountsContract.CONTENT_URI, projection,
                AccountsContract.Columns.ACCOUNTS_NAME + " = ? ",
                xx,
                AccountsContract.Columns._ID);
        while (cursor.moveToNext()) {
            String dbAmount = cursor.getString(cursor.getColumnIndex(AccountsContract.Columns.ACCOUNTS_AMOUNT));
            String amount = mAmount.getText().toString();

            float dbAmountFloat = Float.parseFloat(dbAmount);
            float amountFloat = Float.parseFloat(amount);
            float leftover = amountFloat - dbAmountFloat;
            Log.d(TAG, "xxEdit: =======Unesen iznos "+amountFloat+"}------> Iznos iz baze podataka "+ dbAmountFloat+"}--------> Ostatak "+ leftover);

            saveBalanceAmount(leftover);



        }

    }


}
