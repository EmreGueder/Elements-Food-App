package com.example.elementsfoodapp;

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
}
