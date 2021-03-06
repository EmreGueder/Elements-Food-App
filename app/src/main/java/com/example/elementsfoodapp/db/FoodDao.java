package com.example.elementsfoodapp.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
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

    @Transaction
    @Query("SELECT * FROM food_table ORDER BY food ASC")
    LiveData<List<Food>> getAllFoods();

    @Query("SELECT * FROM food_table LIMIT 1")
    Food[] getAnyFood();

    @Transaction
    @Query("SELECT * FROM food_table WHERE food LIKE :foodName ORDER BY food ASC")
    LiveData<List<Food>> getSearchResults(String foodName);

    @Transaction
    @Query("SELECT * FROM food_table WHERE effect LIKE :effectDescription ORDER BY food ASC")
    LiveData<List<Food>> getEffectSearchResults(String effectDescription);

    @Transaction
    @Query("SELECT * FROM food_table WHERE type LIKE :property ORDER BY food ASC")
    LiveData<List<Food>> getTypeFilterResults(String property);

    @Transaction
    @Query("SELECT * FROM food_table WHERE element LIKE :property ORDER BY food ASC")
    LiveData<List<Food>> getElementFilterResults(String property);

    @Transaction
    @Query("SELECT * FROM food_table WHERE flavor LIKE :property ORDER BY food ASC")
    LiveData<List<Food>> getFlavorFilterResults(String property);

    @Transaction
    @Query("SELECT * FROM food_table WHERE thermal_effect LIKE :property ORDER BY food ASC")
    LiveData<List<Food>> getThermalEffectFilterResults(String property);

    @Transaction
    @Query("SELECT * FROM food_table WHERE target_organ LIKE :property ORDER BY food ASC")
    LiveData<List<Food>> getTargetOrganFilterResults(String property);

    @Transaction
    @Query("UPDATE food_table SET favorite_food = 1 WHERE id == :foodId")
    void insertFavoriteFood(int foodId);

    @Transaction
    @Query("UPDATE food_table SET favorite_food = 0 WHERE id == :foodId")
    void deleteFavoriteFood(int foodId);

    @Transaction
    @Query("SELECT * FROM food_table WHERE favorite_food == 1")
    LiveData<List<Food>> getFavoriteFoods();

    @Transaction
    @Query("UPDATE food_table SET timestamp = CURRENT_TIMESTAMP WHERE id == :foodId")
    void insertLastViewedFood(int foodId);

    @Transaction
    @Query("SELECT * FROM food_table WHERE timestamp != 0 ORDER BY timestamp DESC LIMIT 10")
    LiveData<List<Food>> getLastViewedFoods();

    @Transaction
    @Query("UPDATE food_table SET timestamp = 0")
    void deleteLastViewedFoods();


}
