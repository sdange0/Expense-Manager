package com.debug.messagereceiver;

import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Period;
import java.util.Comparator;
import java.util.Date;

import static android.bluetooth.BluetoothHidDeviceAppQosSettings.MAX;

@Entity
public class Transaction {

    @PrimaryKey
    @NonNull
    private String transactionId;

    private String amount;

    private String transactionDate;

    private String beneficiary;

    private String transaction_source;

    private String category;

    public String getBeneficiary() {
        return beneficiary;
    }

    public void setBeneficiary(String beneficiary) {
        this.beneficiary = beneficiary;
    }

    public String getTransaction_source() {
        return transaction_source;
    }

    public void setTransaction_source(String transaction_source) {
        this.transaction_source = transaction_source;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }



}
