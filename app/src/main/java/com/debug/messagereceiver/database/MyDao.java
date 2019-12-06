package com.debug.messagereceiver.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.debug.messagereceiver.Transaction;

import java.util.List;

@Dao
public interface MyDao {

    @Insert
    public void addUser(Transaction transaction);

    @Query("SELECT * FROM `Transaction`")
    List<Transaction> getAllTransactions();

    @Query("SELECT * FROM `TRANSACTION` WHERE category = :cate")
    List<Transaction> getSelectedTransactions(String cate);

    @Query("UPDATE `Transaction` SET category=:category WHERE transactionId = :id")
    void updateCategory(String category, String id);

    @Query("SELECT transactionId FROM `TRANSACTION` WHERE category = :cate")
    LiveData<List<String>> getids(String cate);

    @Query("SELECT amount FROM `TRANSACTION` WHERE category = :cate")
    LiveData<List<String>> getamount(String cate);

    @Query("SELECT transactionDate FROM `TRANSACTION` WHERE category = :cate")
    LiveData<List<String>> getdate(String cate);

    @Query("SELECT beneficiary FROM `TRANSACTION` WHERE category = :cate")
    LiveData<List<String>> getBenificiary(String cate);
}
