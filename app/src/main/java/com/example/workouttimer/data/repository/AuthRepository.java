package com.example.workouttimer.data.repository;

import android.app.Activity;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class AuthRepository {

    private final FirebaseAuth auth;
    private String verificationId;
    private PhoneAuthProvider.ForceResendingToken resendToken;

    public AuthRepository() {
        auth = FirebaseAuth.getInstance();
    }

    // ✅ Send OTP
    public void sendOtp(String phoneNumber, Activity activity,
                        MutableLiveData<String> otpSent,
                        MutableLiveData<String> error) {

        // Ensure phone number is in E.164 format (+91xxxxxxxxxx)
        if (!phoneNumber.startsWith("+")) {
            error.postValue("Phone number must include country code, e.g. +91XXXXXXXXXX");
            return;
        }

        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(auth)
                        .setPhoneNumber(phoneNumber)
                        .setTimeout(60L, TimeUnit.SECONDS)
                        .setActivity(activity)
                        .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                            @Override
                            public void onVerificationCompleted(PhoneAuthCredential credential) {
                                // Auto-verification (instant login)
                                signInWithCredential(credential, error, otpSent);
                            }

                            @Override
                            public void onVerificationFailed(FirebaseException e) {
                                Log.e("AuthRepository", "Verification failed: " + e.getMessage(), e);
                                error.postValue("Verification failed: " + e.getMessage());
                            }

                            @Override
                            public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken token) {
                                super.onCodeSent(s, token);
                                verificationId = s;
                                resendToken = token;
                                otpSent.postValue("OTP Sent Successfully");
                            }
                        }).build();

        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    // ✅ Verify OTP entered by user
    public void verifyOtp(String otp, MutableLiveData<String> success, MutableLiveData<String> error) {
        if (verificationId == null) {
            error.postValue("Verification ID is null. Please request OTP again.");
            return;
        }

        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, otp);
        signInWithCredential(credential, error, success);
    }

    // ✅ Sign-in with Firebase using credential
    private void signInWithCredential(PhoneAuthCredential credential,
                                      MutableLiveData<String> error,
                                      MutableLiveData<String> success) {
        auth.signInWithCredential(credential)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        success.postValue("Login Successful");
                    } else {
                        if (task.getException() != null) {
                            error.postValue(task.getException().getMessage());
                        } else {
                            error.postValue("Unknown error occurred");
                        }
                    }
                });
    }

    // ✅ Optional: Resend OTP
    public void resendOtp(String phoneNumber, Activity activity,
                          MutableLiveData<String> otpSent,
                          MutableLiveData<String> error) {
        if (resendToken == null) {
            error.postValue("Resend token is null. Try sending OTP again.");
            return;
        }

        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(auth)
                        .setPhoneNumber(phoneNumber)
                        .setTimeout(60L, TimeUnit.SECONDS)
                        .setActivity(activity)
                        .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onVerificationCompleted(PhoneAuthCredential credential) {
                                signInWithCredential(credential, error, otpSent);
                            }

                            @Override
                            public void onVerificationFailed(FirebaseException e) {
                                error.postValue("Verification failed: " + e.getMessage());
                            }

                            @Override
                            public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken token) {
                                verificationId = s;
                                resendToken = token;
                                otpSent.postValue("OTP Resent Successfully");
                            }
                        })
                        .setForceResendingToken(resendToken) // 🔑 Key difference for resending OTP
                        .build();

        PhoneAuthProvider.verifyPhoneNumber(options);
    }
}
