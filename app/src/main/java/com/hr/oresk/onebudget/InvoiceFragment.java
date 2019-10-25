package com.hr.oresk.onebudget;


import android.content.Intent;
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

import com.hr.oresk.onebudget.database.TABLES.InvoiceContract;

import java.security.InvalidParameterException;


/**
 * A simple {@link Fragment} subclass.
 */
public class InvoiceFragment
        extends Fragment
        implements LoaderManager.LoaderCallbacks<Cursor>{

    private static final String TAG = "InvoiceFragment";
    public static final int LOADER_ID = 0;

    private Invoice_CRV_Adapter mAdapterInvoice;

    DividerItemDecoration dividerItemDecoration ;


    public InvoiceFragment() {
        Log.d(TAG, "InvoiceFragment: constructor called");
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onActivityCreated: starts");
        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader(LOADER_ID, null, this);
    }

    @Override
    public View  onCreateView(LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: starts");
        View view = inflater.inflate(R.layout.fragment_invoice, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.RecView_Invoice);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        mAdapterInvoice = new Invoice_CRV_Adapter(
                null,
                (Invoice_CRV_Adapter.OnInvoiceClickListener) getActivity()
        );
        recyclerView.setAdapter(mAdapterInvoice);

        dividerItemDecoration  = new DividerItemDecoration(recyclerView.getContext(),
                layoutManager.getOrientation());
        dividerItemDecoration.setDrawable(new ColorDrawable(Color.RED));
        recyclerView.addItemDecoration(dividerItemDecoration);


        Log.d(TAG, "onCreateView: Returning");
        return view;

    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        Log.d(TAG, "onCreateLoader: starts with id " + id);
        String[] projection = {
                InvoiceContract.Columns._ID,
                InvoiceContract.Columns.INVOICE_DESCRIPTION,
                InvoiceContract.Columns.INVOICE_AMOUNT,
                InvoiceContract.Columns.INVOICE_ACCOUNT,
                InvoiceContract.Columns.INVOICE_CATEGORY,
                InvoiceContract.Columns.INVOICE_TYPE
        };

        String sortOrder = InvoiceContract.Columns._ID+" COLLATE NOCASE DESC";
        switch (id) {
            case LOADER_ID:
                return new CursorLoader(
                        getActivity(),
                        InvoiceContract.CONTENT_URI,
                        projection,
                        null,
                        null,
                        sortOrder);
            default:
                throw new InvalidParameterException(TAG + ".onCreateLoader called with invalid loader id " + id);
        }


    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        Log.d(TAG, "Entering onLoadFinished");
        mAdapterInvoice.swapCursor(data);
        int count = mAdapterInvoice.getItemCount();

        /*if (data != null) {
            while (data.moveToNext()) {
                for (int i=0 ; i<data.getColumnCount(); i++) {
                    Log.d(TAG, "onLoadFinished: " + data.getColumnName(i) + ": " + data.getString(i));
                }
                Log.d(TAG, "onLoadFinished: ===========================================");
            }
            count = data.getCount();
        }*/
        Log.d(TAG, "onLoadFinished: count is " + count);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        Log.d(TAG, "onLoaderReset: starts");
        mAdapterInvoice.swapCursor(null);
    }

}
