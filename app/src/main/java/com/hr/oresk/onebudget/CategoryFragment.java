package com.hr.oresk.onebudget;


import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hr.oresk.onebudget.database.TABLES.CategoriesContract;

import java.security.InvalidParameterException;


/**
 * A simple {@link Fragment} subclass.
 */
public class CategoryFragment
        extends Fragment
        implements LoaderManager.LoaderCallbacks<Cursor>{

    private static final String TAG = "CategoryFragment";
    public static final int LOADER_ID = 2;

    private Category_CRV_Adapter mAdapterCategory;

    DividerItemDecoration dividerItemDecoration;

    public CategoryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader(LOADER_ID, null, this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_category, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.category_RecView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        mAdapterCategory = new Category_CRV_Adapter(
                null,
                (Category_CRV_Adapter.OnCategoryClickListener) getActivity()
        );
        recyclerView.setAdapter(mAdapterCategory);

        dividerItemDecoration  = new DividerItemDecoration(recyclerView.getContext(),
                layoutManager.getOrientation());
        dividerItemDecoration.setDrawable(new ColorDrawable(Color.CYAN));
        recyclerView.addItemDecoration(dividerItemDecoration);

        return view;
    }


    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        String[] projection = {
                CategoriesContract.Columns._ID,
                CategoriesContract.Columns.CATEGORIES_NAME,
                CategoriesContract.Columns.CATEGORIES_DESCRIPTION,
                CategoriesContract.Columns.CATEGORIES_AMOUNT,
                CategoriesContract.Columns.CATEGORIES_INVOICE_TYPE
        };

        String sortOrder = CategoriesContract.Columns._ID + " COLLATE NOCASE";
        switch (id) {
            case LOADER_ID:
                return new CursorLoader(
                        getActivity(),
                        CategoriesContract.CONTENT_URI,
                        projection,
                        null,
                        null,
                        sortOrder
                );
            default:
                throw new InvalidParameterException(TAG + ".onCreateLoader called with invalid loader id " + id);
        }

    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        mAdapterCategory.swapCursor(data);
        int count = mAdapterCategory.getItemCount();
        Log.d(TAG, "onLoadFinished: count is " + count);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        mAdapterCategory.swapCursor(null);
    }
}
