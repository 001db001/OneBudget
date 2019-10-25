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

import com.hr.oresk.onebudget.database.TABLES.AccountsContract;

import java.security.InvalidParameterException;


/**
 * A simple {@link Fragment} subclass.
 */
public class AccountFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{
    public static final int LOADER_ID = 1;
    private static final String TAG = "AccountFragment";

    private Account_CRV_Adapter mAdapterAccount;
    DividerItemDecoration dividerItemDecoration;

    public AccountFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader(LOADER_ID, null, this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_account, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.account_RecView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        mAdapterAccount = new Account_CRV_Adapter(
                null,
                (Account_CRV_Adapter.OnAccountClickListener) getActivity()
        );
        recyclerView.setAdapter(mAdapterAccount);

        dividerItemDecoration  = new DividerItemDecoration(recyclerView.getContext(),
                layoutManager.getOrientation());
        dividerItemDecoration.setDrawable(new ColorDrawable(Color.MAGENTA));
        recyclerView.addItemDecoration(dividerItemDecoration);
        return view;
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        String[] projection = {
                AccountsContract.Columns._ID,
                AccountsContract.Columns.ACCOUNTS_NAME,
                AccountsContract.Columns.ACCOUNTS_DESCRIPTION,
                AccountsContract.Columns.ACCOUNTS_AMOUNT
        };

        String sortOrder = AccountsContract.Columns._ID + " COLLATE NOCASE";
        switch (id) {
            case LOADER_ID:
                return new CursorLoader(
                        getActivity(),
                        AccountsContract.CONTENT_URI,
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
        mAdapterAccount.swapCursor(data);
        int count = mAdapterAccount.getItemCount();
        Log.d(TAG, "onLoadFinished: count is " + count);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        mAdapterAccount.swapCursor(null);
    }
}
