package com.example.androidviews.ui.login;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class LoginViewModel extends ViewModel {
    private final MutableLiveData<Boolean> _loggedIn = new MutableLiveData<>(false);
    public LiveData<Boolean> loggedIn = _loggedIn;

    public void login(String user, String pass) {
        // Simulación de login
        _loggedIn.setValue(!user.isEmpty() && !pass.isEmpty());
    }
}
