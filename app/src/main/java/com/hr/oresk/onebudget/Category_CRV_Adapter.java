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

import com.hr.oresk.onebudget.database.TABLES.CategoriesContract;

class Category_CRV_Adapter extends RecyclerView.Adapter<Category_CRV_Adapter.CategoryViewHolder> {
    private static final String TAG = "Category_CRV_Adapter";
    private Cursor mCursor;
    private OnCategoryClickListener mListener;

    interface OnCategoryClickListener {
        void onEditClickCategory(Category category);

        void onDeleteClickCategory(Category category);
    }

    public Category_CRV_Adapter(Cursor cursor, OnCategoryClickListener listener) {
        mCursor = cursor;
        mListener = listener;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.category_recview_item, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        if ((mCursor == null) || (mCursor.getCount() == 0)) {
            holder.mName.setText(R.string.cri_Name_Text);
            holder.mDescription.setText(R.string.cri_Description_Text);
            holder.mAmount.setText(R.string.cri_Amount_Text);
            holder.edit_btn.setVisibility(View.GONE);
            holder.delete_btn.setVisibility(View.GONE);
        } else {
            if (!mCursor.moveToPosition(position)) {
                throw new IllegalStateException("Couldn't move to position " + position);
            }

            final Category category = new Category(
                    mCursor.getLong(mCursor.getColumnIndex(CategoriesContract.Columns._ID)),
                    mCursor.getString(mCursor.getColumnIndex(CategoriesContract.Columns.CATEGORIES_NAME)),
                    mCursor.getString(mCursor.getColumnIndex(CategoriesContract.Columns.CATEGORIES_INVOICE_TYPE)),
                    mCursor.getString(mCursor.getColumnIndex(CategoriesContract.Columns.CATEGORIES_DESCRIPTION)),
                    mCursor.getString(mCursor.getColumnIndex(CategoriesContract.Columns.CATEGORIES_AMOUNT))
            );
            holder.mName.setText(category.getName());
            holder.mDescription.setText(category.getDescription());
            holder.mAmount.setText(category.getAmount());

            holder.edit_btn.setVisibility(View.VISIBLE);
            holder.delete_btn.setVisibility(View.VISIBLE);

            View.OnClickListener buttonListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (v.getId()) {
                        case R.id.cri_Edit:
                            mListener.onEditClickCategory(category);
                            break;
                        case R.id.cri_Delete:
                            mListener.onDeleteClickCategory(category);
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
        } else {
            return mCursor.getCount();
        }
    }

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


    static class CategoryViewHolder extends RecyclerView.ViewHolder {
        TextView mName;
        TextView mDescription;
        TextView mAmount;
        ImageButton edit_btn;
        ImageButton delete_btn;

        public CategoryViewHolder(View itemView) {
            super(itemView);
            this.mName = itemView.findViewById(R.id.cri_Name);
            this.mDescription = itemView.findViewById(R.id.cri_Description);
            this.mAmount = itemView.findViewById(R.id.cri_Amount);
            this.edit_btn = itemView.findViewById(R.id.cri_Edit);
            this.delete_btn = itemView.findViewById(R.id.cri_Delete);
        }
    }
}
