package com.example.elementsfoodapp.ui.globalSearch;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class GlobalSearchViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public GlobalSearchViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is globalSearch fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}