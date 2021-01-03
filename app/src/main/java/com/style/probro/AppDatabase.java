package com.style.probro;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.style.probro.room.MyCartDao;
import com.style.probro.models.MyCartItem;

@Database(entities = {MyCartItem.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract MyCartDao myCartDao();
    public static AppDatabase db;
    public static void init(Context appContext) {
        db = Room.databaseBuilder(appContext,
                AppDatabase.class, "probro-db").build();
    }

    public static AppDatabase getDb() {
        return db;
    }

}
