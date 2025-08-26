package com.example.workouttimer.viewmodel;

import android.app.Activity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.workouttimer.data.repository.AuthRepository;

public class AuthViewModel extends ViewModel {

    private final AuthRepository repository;
    private final MutableLiveData<String> otpSent = new MutableLiveData<>();
    private final MutableLiveData<String> loginSuccess = new MutableLiveData<>();
    private final MutableLiveData<String> error = new MutableLiveData<>();

    public AuthViewModel() {
        repository = new AuthRepository();
    }

    public void sendOtp(String phoneNumber, Activity activity) {
        repository.sendOtp(phoneNumber, activity, otpSent, error);
    }

    public void verifyOtp(String otp) {
        repository.verifyOtp(otp, loginSuccess, error);
    }

    public LiveData<String> getOtpSent() {
        return otpSent;
    }

    public LiveData<String> getLoginSuccess() {
        return loginSuccess;
    }

    public LiveData<String> getError() {
        return error;
    }
}
