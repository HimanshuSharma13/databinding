package com.example.myapplication.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Account implements Parcelable {

    String accountNumber;
    String bankName;

    public Account(String accountNumber, String bankName) {
        setAccountNumber(accountNumber);
        setBankName(bankName);
    }

    protected Account(Parcel in) {
        accountNumber = in.readString();
        bankName = in.readString();
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public static final Creator<Account> CREATOR = new Creator<Account>() {
        @Override
        public Account createFromParcel(Parcel in) {
            return new Account(in);
        }

        @Override
        public Account[] newArray(int size) {
            return new Account[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(accountNumber);
        parcel.writeString(bankName);
    }
}
