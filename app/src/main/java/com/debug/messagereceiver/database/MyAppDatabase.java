package com.debug.messagereceiver.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.debug.messagereceiver.Transaction;

@Database(entities = {Transaction.class} , version = 1)
public abstract class MyAppDatabase extends RoomDatabase {

    private static MyAppDatabase instance;
    private static final String DB_NAME = "Transaction_DB";
    public static synchronized MyAppDatabase getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),MyAppDatabase.class,DB_NAME).
            fallbackToDestructiveMigration().build();
        }
        return instance;
    }

    public abstract MyDao myDao();

}
