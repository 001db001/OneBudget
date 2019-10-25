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

import com.hr.oresk.onebudget.database.TABLES.BalanceContract;
import com.hr.oresk.onebudget.database.TABLES.CategoriesContract;

public class AddEdit_Category extends AppCompatActivity {
    private static final String TAG = "AddEdit_Category";

    public enum FragmentEditModeCategory{EDIT, ADD}

    private FragmentEditModeCategory mMode;
    private EditText mName,mDescription, mAmount;
    private Button save_btn;
    private Category category;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addedit_category);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mName = findViewById(R.id.aec_Name);
        mDescription = findViewById(R.id.aec_Description);
        mAmount = findViewById(R.id.aec_Amount);
        save_btn = findViewById(R.id.aec_Save);

        getExtraCategory();

        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save_edit_btn();
            }
        });
    }

    private void getExtraCategory() {
        Bundle args = this.getIntent().getExtras();

        if (args != null) {

            category = (Category) args.getSerializable(Category.class.getSimpleName());

            if (args != null) {
                mName.setText(category.getName());
                mDescription.setText(category.getDescription());
                mAmount.setText(category.getAmount());

                mMode = FragmentEditModeCategory.EDIT;
            } else {
                mMode = FragmentEditModeCategory.ADD;
            }
        } else {
            category = null;
            mMode = FragmentEditModeCategory.ADD;
        }
    }

    private void save_edit_btn() {
        ContentResolver resolver = getContentResolver();
        ContentValues values = new ContentValues();
        String amount = mAmount.getText().toString();
        Log.d(TAG, "save_edit_btn:==================> <=====================");

        switch (mMode) {
            case EDIT:
                if (!mName.getText().equals(category.getName())) {
                    values.put(CategoriesContract.Columns.CATEGORIES_NAME, mName.getText().toString());
                }
                if (!mDescription.getText().equals(category.getDescription())) {
                    values.put(CategoriesContract.Columns.CATEGORIES_DESCRIPTION, mDescription.getText().toString());
                }
                if (!mAmount.getText().equals(category.getAmount())) {
                    values.put(CategoriesContract.Columns.CATEGORIES_AMOUNT, mAmount.getText().toString());
                }
                if (values.size() != 0) {
                    resolver.update(CategoriesContract.buildCategoryUri(category.getId()), values, null, null);
                    Toast.makeText(AddEdit_Category.this, "Entry updated ",Toast.LENGTH_SHORT).show();
                }

                break;
            case ADD:
                if ((mName.length() > 0) && (mAmount.length() > 0)) {
                    values.put(CategoriesContract.Columns.CATEGORIES_NAME, mName.getText().toString());
                    values.put(CategoriesContract.Columns.CATEGORIES_DESCRIPTION, mDescription.getText().toString());
                    values.put(CategoriesContract.Columns.CATEGORIES_AMOUNT, mAmount.getText().toString());
                    resolver.insert(CategoriesContract.CONTENT_URI, values);
                    Toast.makeText(AddEdit_Category.this, "Entry saved", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(AddEdit_Category.this, "NAME & AMOUNT fields are mandatory", Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }



}
