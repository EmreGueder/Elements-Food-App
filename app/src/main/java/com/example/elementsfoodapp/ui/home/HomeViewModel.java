package com.example.elementsfoodapp.ui.home;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.elementsfoodapp.db.Food;
import com.example.elementsfoodapp.db.FoodRepository;

import java.util.List;

/**ViewModel classes are used from the activity to communicate with the database.*/
public class HomeViewModel extends AndroidViewModel {

    private FoodRepository mRepository;

    public HomeViewModel(Application application) {
        super(application);
        mRepository = new FoodRepository(application);
    }

    public void update(Food food) { mRepository.update(food); }

    public LiveData<List<Food>> getLastViewedFoods() { return mRepository.getLastViewedFoods(); }

    public void insertLastViewedFood(int foodId) { mRepository.insertLastViewedFood(foodId); }

    public void deleteLastViewedFoods() { mRepository.deleteLastViewedFoods(); }

}