package com.example.elementsfoodapp.ui.categories;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.elementsfoodapp.Food;
import com.example.elementsfoodapp.FoodRepository;

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
}