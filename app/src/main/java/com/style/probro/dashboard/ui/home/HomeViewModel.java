package com.style.probro.dashboard.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.style.probro.AppDatabase;
import com.style.probro.room.MyCartDao;
import com.style.probro.models.MyCartItem;

import java.util.List;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<String> mText;
    private MyCartDao myCartDao;

    public HomeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
        myCartDao = AppDatabase.getDb().myCartDao();
    }

    public LiveData<String> getText() {
        return mText;
    }

    public LiveData<List<MyCartItem>> getMyCartItemList() {
        return myCartDao.getAll();
    }

}