package com.example.elementsfoodapp.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.elementsfoodapp.db.Food;

import java.util.List;

@Dao
public interface FoodDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Food food);

    @Delete
    void deleteFood(Food food);

    @Update
    void update(Food... food);

    @Query("SELECT * FROM food_table ORDER BY food ASC")
    LiveData<List<Food>> getAllFoods();

    @Query("SELECT * FROM food_table LIMIT 1")
    Food[] getAnyFood();

    @Query("SELECT * FROM food_table WHERE food LIKE :foodName ORDER BY food ASC")
    LiveData<List<Food>> getSearchResults(String foodName);

    @Query("SELECT * FROM food_table WHERE type LIKE :property ORDER BY food ASC")
    LiveData<List<Food>> getTypeFilterResults(String property);

    @Query("SELECT * FROM food_table WHERE element LIKE :property ORDER BY food ASC")
    LiveData<List<Food>> getElementFilterResults(String property);

    @Query("SELECT * FROM food_table WHERE flavor LIKE :property ORDER BY food ASC")
    LiveData<List<Food>> getFlavorFilterResults(String property);

    @Query("SELECT * FROM food_table WHERE thermal_effect LIKE :property ORDER BY food ASC")
    LiveData<List<Food>> getThermalEffectFilterResults(String property);

    @Query("SELECT * FROM food_table WHERE target_organ LIKE :property ORDER BY food ASC")
    LiveData<List<Food>> getTargetOrganFilterResults(String property);
}
