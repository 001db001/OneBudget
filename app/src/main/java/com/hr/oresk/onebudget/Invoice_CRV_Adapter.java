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

import com.hr.oresk.onebudget.database.TABLES.InvoiceContract;

class Invoice_CRV_Adapter extends RecyclerView.Adapter<Invoice_CRV_Adapter.InvoiceViewHolder> {
    private static final String TAG = "C.R.V.A. Invoice";
    private Cursor mCursor;
    private OnInvoiceClickListener mListener;

    interface OnInvoiceClickListener {
        void onEditClickInvoice(Invoice invoice);

        void onDeleteClickInvoice(Invoice invoice);
    }

    public Invoice_CRV_Adapter(Cursor cursor, OnInvoiceClickListener listener) {
        Log.d(TAG, "Invoice_CRV_Adapter: Constructor called ");
        mCursor = cursor;
        mListener = listener;
    }

    @NonNull
    @Override
    public InvoiceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Log.d(TAG, "onCreateViewHolder: new view requested");
        View view =
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.invoice_list_item, parent, false);
        return new InvoiceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Invoice_CRV_Adapter.InvoiceViewHolder holder, int position) {
        // Log.d(TAG, "onBindViewHolder: starts");


        if ((mCursor == null) || (mCursor.getCount() == 0)) {
            //Log.d(TAG, "onBindViewHolder: providing instruction");
            holder.descriptionView.setText(R.string.ili_description_here);
            holder.accountView.setText(R.string.ili_account_here);
            holder.categoryView.setText(R.string.ili_category_here);
            holder.amountView.setText(R.string.ili_amount_here);
            holder.typeView.setText(R.string.ili_type_here);
            holder.edit_btn.setVisibility(View.GONE);
            holder.delete_button.setVisibility(View.GONE);
        } else {
            if (!mCursor.moveToPosition(position)) {
                throw new IllegalStateException("Couldn't move cursor to position " + position);
            }

            final Invoice invoice = new Invoice(
                    mCursor.getLong(mCursor.getColumnIndex(InvoiceContract.Columns._ID)),
                    mCursor.getString(mCursor.getColumnIndex(InvoiceContract.Columns.INVOICE_AMOUNT)),
                    null,
                    mCursor.getString(mCursor.getColumnIndex(InvoiceContract.Columns.INVOICE_TYPE)),
                    mCursor.getString(mCursor.getColumnIndex(InvoiceContract.Columns.INVOICE_DESCRIPTION)),
                    mCursor.getString(mCursor.getColumnIndex(InvoiceContract.Columns.INVOICE_ACCOUNT)),
                    mCursor.getString(mCursor.getColumnIndex(InvoiceContract.Columns.INVOICE_CATEGORY))
            );
            holder.descriptionView.setText(invoice.getDescription());
            holder.accountView.setText(invoice.getAccountId());
            holder.categoryView.setText(invoice.getCategoryId());
            holder.amountView.setText(invoice.getAmount());
            holder.typeView.setText(invoice.getInvoice_Type());

            holder.edit_btn.setVisibility(View.VISIBLE);
            holder.delete_button.setVisibility(View.VISIBLE);

            View.OnClickListener buttonListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (v.getId()) {
                        case R.id.ili_btn_Edit:
                            assert mListener != null;
                            mListener.onEditClickInvoice(invoice);
                            break;
                        case R.id.ili_btn_Delete:
                            assert mListener != null;
                            mListener.onDeleteClickInvoice(invoice);
                            break;
                        default:
                            Log.d(TAG, "onClick: found unexpected button id");
                    }

                    //Log.d(TAG, "onClick: button with id " + v.getId() + " clicked");
                }
            };


            holder.edit_btn.setOnClickListener(buttonListener);
            holder.delete_button.setOnClickListener(buttonListener);
        }

    }

    @Override
    public int getItemCount() {
        //  Log.d(TAG, "getItemCount: starts");
        if ((mCursor == null) || (mCursor.getCount() == 0)) {
            return 1;
        } else return mCursor.getCount();
    }

    /**
     * swap new Cursor, returning old Cursor.
     * The returned cursor is <em>not</em> closed.
     *
     * @param newCursor
     * @return Returns the previously set cursor, or null if there wasn't one.
     * If the given new Cursor is same instance as the previously set Cursor, null is also returned.
     */
    Cursor swapCursor(Cursor newCursor) {
        if (newCursor == mCursor) {
            return null;
        }

        final Cursor oldCursor = mCursor;
        mCursor = newCursor;

        if (newCursor != null) {
            notifyDataSetChanged();
        } else {
            notifyItemRangeRemoved(0, getItemCount());
        }
        return oldCursor;
    }

    static class InvoiceViewHolder extends RecyclerView.ViewHolder {

        private static final String TAG = "InvoiceViewHolder";

        TextView descriptionView = null;
        TextView accountView = null;
        TextView categoryView = null;
        TextView amountView = null;
        TextView typeView = null;
        ImageButton edit_btn = null;
        ImageButton delete_button = null;


        public InvoiceViewHolder(View itemView) {
            super(itemView);

            this.descriptionView = itemView.findViewById(R.id.ili_description);
            this.accountView = itemView.findViewById(R.id.ili_account);
            this.categoryView = itemView.findViewById(R.id.ili_category);
            this.amountView = itemView.findViewById(R.id.ili_amount);
            this.typeView = itemView.findViewById(R.id.ili_invoiceType);
            this.edit_btn = itemView.findViewById(R.id.ili_btn_Edit);
            this.delete_button = itemView.findViewById(R.id.ili_btn_Delete);
        }
    }
}
