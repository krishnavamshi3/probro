package com.style.probro.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.style.probro.models.MyCartItem;

import java.util.List;

@Dao
public interface MyCartDao {
    @Query("SELECT * FROM my_cart_item")
    LiveData<List<MyCartItem>> getAll();

    @Insert
    void insertAll(MyCartItem... myCartItems);

    @Insert
    void insertCartItem(MyCartItem myCartItem);

    @Update
    void updateCartItem(MyCartItem myCartItem);

    @Delete
    void delete(MyCartItem myCartItem);

}
