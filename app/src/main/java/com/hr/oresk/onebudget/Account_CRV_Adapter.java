package com.hr.oresk.onebudget;

import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.hr.oresk.onebudget.database.TABLES.AccountsContract;

class Account_CRV_Adapter extends RecyclerView.Adapter<Account_CRV_Adapter.AccountViewHolder>{
    private static final String TAG = "Account_CRV_Adapter";
    private Cursor mCursor;
    private OnAccountClickListener mListener;

    interface OnAccountClickListener {
        void onEditClickAccount(Account account);

        void onDeleteClickAccount(Account account);
    }

    public Account_CRV_Adapter(Cursor cursor, OnAccountClickListener listener) {
        mCursor = cursor;
        mListener = listener;
    }

    @NonNull
    @Override
    public AccountViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.account_recview_item, parent, false);
        return new AccountViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AccountViewHolder holder, int position) {
        if ((mCursor == null) || (mCursor.getCount() == 0)) {
            holder.mName.setText(R.string.ari_Name_Text);
            holder.mDescription.setText(R.string.ari_Description_Text);
            holder.mAmount.setText(R.string.ari_Amount_Text);
            holder.edit_btn.setVisibility(View.GONE);
            holder.delete_btn.setVisibility(View.GONE);
        } else {
            if (!mCursor.moveToPosition(position)) {
                throw new IllegalStateException("Couldn't move cursor to position " + position);
            }

            final Account account = new Account(
                    mCursor.getLong(mCursor.getColumnIndex(AccountsContract.Columns._ID)),
                    mCursor.getString(mCursor.getColumnIndex(AccountsContract.Columns.ACCOUNTS_NAME)),
                    mCursor.getString(mCursor.getColumnIndex(AccountsContract.Columns.ACCOUNTS_AMOUNT)),
                    mCursor.getString(mCursor.getColumnIndex(AccountsContract.Columns.ACCOUNTS_DESCRIPTION))
            );

            holder.mName.setText(account.getName());
            holder.mDescription.setText(account.getDescription());
            holder.mAmount.setText(account.getAmount());
            holder.edit_btn.setVisibility(View.VISIBLE);
            holder.delete_btn.setVisibility(View.VISIBLE);

            View.OnClickListener buttonListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (v.getId()) {
                        case R.id.ari_Edit:
                            mListener.onEditClickAccount(account);
                            break;
                        case R.id.ari_Delete:
                            mListener.onDeleteClickAccount(account);
                            break;
                        default:
                            Log.d(TAG, "onClick: found unexpected button id");
                    }
                }
            };

            holder.edit_btn.setOnClickListener(buttonListener);
            holder.delete_btn.setOnClickListener(buttonListener);
        }

    }

    @Override
    public int getItemCount() {
        if ((mCursor == null) || (mCursor.getCount() == 0)) {
            return 1;
        } else return mCursor.getCount();
    }

    Cursor swapCursor(Cursor newCursor) {
        if (newCursor == mCursor) {
            return null;
        }

        final Cursor oldCuror = mCursor;
        mCursor = newCursor;

        if (newCursor != null) {
            notifyDataSetChanged();
        } else {
            notifyItemRangeRemoved(0, getItemCount());
        }
        return oldCuror;
    }

    static class AccountViewHolder extends RecyclerView.ViewHolder {
        TextView mName;
        TextView mDescription;
        TextView mAmount;
        ImageButton edit_btn;
        ImageButton delete_btn;

        public AccountViewHolder(View itemView) {
            super(itemView);

            this.mName = itemView.findViewById(R.id.ari_name);
            this.mDescription = itemView.findViewById(R.id.ari_Description);
            this.mAmount = itemView.findViewById(R.id.ari_Amount);
            this.edit_btn = itemView.findViewById(R.id.ari_Edit);
            this.delete_btn = itemView.findViewById(R.id.ari_Delete);
        }
    }
}
