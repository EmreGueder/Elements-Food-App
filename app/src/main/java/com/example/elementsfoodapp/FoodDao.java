package com.example.elementsfoodapp;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface FoodDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Food food);

    @Delete
    void deleteFood(Food food);

    @Update
    void update(Food... food);

    @Query("DELETE FROM food_table")
    void deleteAll();

    @Query("SELECT * from food_table ORDER BY food ASC")
    LiveData<List<Food>> getAllFoods();

    @Query("SELECT * from food_table LIMIT 1")
    Food[] getAnyFood();
}
