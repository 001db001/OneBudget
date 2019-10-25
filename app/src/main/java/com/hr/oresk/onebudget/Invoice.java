package com.hr.oresk.onebudget;

import java.io.Serializable;

class Invoice implements Serializable {
    public static final long serialVersionUID = 20180903L;

    private long m_Id;
    private final String mAmount;
    private final String mDate;
    private final String mInvoice_Type;
    private final String mDescription;
    private final String mAccountId;
    private final String mCategoryId;

    Invoice(long id, String amount, String date, String invoice_type, String description, String accountId, String categoryId) {
        m_Id = id;
        mAmount = amount;
        mDate = date;
        mInvoice_Type = invoice_type;
        mDescription = description;
        mAccountId = accountId;
        mCategoryId = categoryId;
    }

    public long getId() {
        return m_Id;
    }

    public String getAmount() {
        return mAmount;
    }

    public String getDate() {
        return mDate;
    }

    public String getInvoice_Type() {
        return mInvoice_Type;
    }

    public String getDescription() {
        return mDescription;
    }

    public String getAccountId() {
        return mAccountId;
    }

    public String getCategoryId() {
        return mCategoryId;
    }

    public void setM_Id(long m_Id) {
        this.m_Id = m_Id;
    }

    @Override
    public String toString() {
        return "Invoice{" +
                "m_Id=" + m_Id +
                ", mAmount='" + mAmount + '\'' +
                ", mDate='" + mDate + '\'' +
                ", mInvoice_Type='" + mInvoice_Type + '\'' +
                ", mDescription='" + mDescription + '\'' +
                '}';
    }
}
