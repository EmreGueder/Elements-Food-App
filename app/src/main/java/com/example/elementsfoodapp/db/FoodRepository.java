package com.example.elementsfoodapp.db;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class FoodRepository {

    private FoodDao mFoodDao;
    private LiveData<List<Food>> mAllFoods;

    public FoodRepository(Application application) {
        FoodRoomDatabase db = FoodRoomDatabase.getDatabase(application);
        mFoodDao = db.foodDao();
        mAllFoods = mFoodDao.getAllFoods();
    }

    public LiveData<List<Food>> getAllFoods() { return mAllFoods; }

    public void insert(Food food) {
        FoodRoomDatabase.databaseWriteExecutor.execute(() -> {
            mFoodDao.insert(food);
        });
    }

    public void deleteFood(Food food) {
        FoodRoomDatabase.databaseWriteExecutor.execute(() -> {
            mFoodDao.deleteFood(food);
        });
    }

    public void update(Food food) {
        FoodRoomDatabase.databaseWriteExecutor.execute(() -> {
            mFoodDao.update(food);
        });
    }

    public LiveData<List<Food>> getSearchResults(String foodName) {
            return mFoodDao.getSearchResults(foodName);
    }

    public LiveData<List<Food>> getEffectSearchResults(String effectDescription) {
        return mFoodDao.getEffectSearchResults(effectDescription);
    }

    public LiveData<List<Food>> getTypeFilterResults(String property) {
        return mFoodDao.getTypeFilterResults(property);
    }

    public LiveData<List<Food>> getElementFilterResults(String property) {
        return mFoodDao.getElementFilterResults(property);
    }

    public LiveData<List<Food>> getFlavorFilterResults(String property) {
        return mFoodDao.getFlavorFilterResults(property);
    }

    public LiveData<List<Food>> getThermalEffectFilterResults(String property) {
        return mFoodDao.getThermalEffectFilterResults(property);
    }

    public LiveData<List<Food>> getTargetOrganFilterResults(String property) {
        return mFoodDao.getTargetOrganFilterResults(property);
    }

    public LiveData<List<Food>> getFavoriteFoods() {
        return mFoodDao.getFavoriteFoods();
    }

    public LiveData<List<Food>> getLastViewedFoods() { return mFoodDao.getLastViewedFoods(); }

    public void insertFavoriteFood(int foodId) {
        FoodRoomDatabase.databaseWriteExecutor.execute(() -> {
            mFoodDao.insertFavoriteFood(foodId);
        });
    }

    public void deleteFavoriteFood(int foodId) {
        FoodRoomDatabase.databaseWriteExecutor.execute(() -> {
            mFoodDao.deleteFavoriteFood(foodId);
        });
    }

    public void insertLastViewedFood(int foodId) {
        FoodRoomDatabase.databaseWriteExecutor.execute(() -> {
            mFoodDao.insertLastViewedFood(foodId);
        });
    }

    public void deleteLastViewedFoods() {
        FoodRoomDatabase.databaseWriteExecutor.execute(() -> {
            mFoodDao.deleteLastViewedFoods();
        });
    }
}
