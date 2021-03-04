package com.example.elementsfoodapp.ui.favorites;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.elementsfoodapp.db.Food;
import com.example.elementsfoodapp.db.FoodRepository;

import java.util.List;

public class FavoritesViewModel extends ViewModel {

    private FoodRepository mRepository;

    public FavoritesViewModel(Application application) {
        super();
        mRepository = new FoodRepository(application);
    }

    public void update(Food food) { mRepository.update(food); }

    public void deleteFavoriteFood(Food food) { mRepository.deleteFavoriteFood(food); }

    public LiveData<List<Food>> getFavoriteFoods() { return mRepository.getFavoriteFoods(); }
}