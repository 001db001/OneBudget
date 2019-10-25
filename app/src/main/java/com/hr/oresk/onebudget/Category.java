package com.hr.oresk.onebudget;

import java.io.Serializable;

public class Category implements Serializable {
    public static final long serialVersionUID = 20180907L;

    private long mId;
    private final String mName;
    private final String mInvoiceType;
    private final String mDescription;
    private final String mAmount;

    public Category(long id, String name, String invoiceType, String description, String amount) {
        mId = id;
        mName = name;
        mInvoiceType = invoiceType;
        mDescription = description;
        mAmount = amount;
    }

    public long getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public String getInvoiceType() {
        return mInvoiceType;
    }

    public String getDescription() {
        return mDescription;
    }

    public String getAmount() {
        return mAmount;
    }

    public void setId(long id) {
        mId = id;
    }

    @Override
    public String toString() {
        return "Category{" +
                "mId=" + mId +
                ", mName='" + mName + '\'' +
                ", mInvoiceType='" + mInvoiceType + '\'' +
                ", mDescription='" + mDescription + '\'' +
                ", mAmount='" + mAmount + '\'' +
                '}';
    }
}
