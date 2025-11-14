package com.example.androidviews.ui.dashboard;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DashboardViewModel extends ViewModel {
    private final MutableLiveData<Integer> _count = new MutableLiveData<>(0);
    public LiveData<Integer> count = _count;

    public void increment() {
        Integer current = _count.getValue();
        if (current == null) current = 0;
        _count.setValue(current + 1);
    }
}
