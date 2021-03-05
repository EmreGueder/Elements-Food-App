package com.example.elementsfoodapp.ui.favorites;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.elementsfoodapp.db.Food;
import com.example.elementsfoodapp.db.FoodRepository;

import java.util.List;

public class FavoritesViewModel extends AndroidViewModel {

    private FoodRepository mRepository;

    public FavoritesViewModel(Application application) {
        super(application);
        mRepository = new FoodRepository(application);
    }

    public void update(Food food) { mRepository.update(food); }

    public void insertFavoriteFood(int foodId) { mRepository.insertFavoriteFood(foodId); }

    public void deleteFavoriteFood(int foodId) { mRepository.deleteFavoriteFood(foodId); }

    public LiveData<List<Food>> getFavoriteFoods() { return mRepository.getFavoriteFoods(); }
}