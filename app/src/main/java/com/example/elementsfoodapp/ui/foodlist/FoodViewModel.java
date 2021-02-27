package com.example.elementsfoodapp.ui.foodlist;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.elementsfoodapp.db.Food;
import com.example.elementsfoodapp.db.FoodRepository;

import java.util.List;

public class FoodViewModel extends AndroidViewModel {

    private FoodRepository mRepository;

    private LiveData<List<Food>> mAllFoods;

    public FoodViewModel (Application application) {
        super(application);
        mRepository = new FoodRepository(application);
        mAllFoods = mRepository.getAllFoods();
    }

    public LiveData<List<Food>> getAllFoods() { return mAllFoods; }

    public void insert(Food food) { mRepository.insert(food); }

    public void update(Food food) { mRepository.update(food); }

    public void deleteFood(Food food) { mRepository.deleteFood(food); }

    public void deleteAll() { mRepository.deleteAll(); }

    public LiveData<List<Food>> getSearchResults(String foodName) {
        return mRepository.getSearchResults(foodName);
    }

    public LiveData<List<Food>> getTypeFilterResults(String property) {
        return mRepository.getTypeFilterResults(property);
    }

    public LiveData<List<Food>> getElementFilterResults(String property) {
        return mRepository.getElementFilterResults(property);
    }

    public LiveData<List<Food>> getFlavorFilterResults(String property) {
        return mRepository.getFlavorFilterResults(property);
    }

    public LiveData<List<Food>> getThermalEffectFilterResults(String property) {
        return mRepository.getThermalEffectFilterResults(property);
    }

    public LiveData<List<Food>> getTargetOrganFilterResults(String property) {
        return mRepository.getTargetOrganFilterResults(property);
    }
}