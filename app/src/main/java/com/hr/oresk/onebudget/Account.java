package com.hr.oresk.onebudget;

import java.io.Serializable;

public class Account implements Serializable {
    public static final long serialVersionUID = 20180906L;

    private long mId;
    private final String mName;
    private final String mAmount;
    private final String mDescription;

    public Account(long id, String name, String amount, String description) {
        mId = id;
        mName = name;
        mAmount = amount;
        mDescription = description;
    }

    public long getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public String getAmount() {
        return mAmount;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setId(long id) {
        mId = id;
    }

    @Override
    public String toString() {
        return "Account{" +
                "mId=" + mId +
                ", mName='" + mName + '\'' +
                ", mAmount='" + mAmount + '\'' +
                ", mDescription='" + mDescription + '\'' +
                '}';
    }

}
