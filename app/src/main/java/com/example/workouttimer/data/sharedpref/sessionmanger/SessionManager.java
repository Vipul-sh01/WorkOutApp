package com.example.workouttimer.data.sharedpref.sessionmanger;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {

    // SharedPreferences file name
    private static final String PREF_NAME = "UserSessionPref";

    // Keys
    private static final String IS_LOGGED_IN = "isLoggedIn";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_EMAIL = "email";

    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context context;

    // Constructor
    public SessionManager(Context context) {
        this.context = context;
        pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = pref.edit();
    }

    // Save login session
    public void createLoginSession(String username, String email) {
        editor.putBoolean(IS_LOGGED_IN, true);
        editor.putString(KEY_USERNAME, username);
        editor.putString(KEY_EMAIL, email);
        editor.apply(); // save changes
    }

    // Get user details
    public String getUsername() {
        return pref.getString(KEY_USERNAME, null);
    }

    public String getEmail() {
        return pref.getString(KEY_EMAIL, null);
    }

    // Check login
    public boolean isLoggedIn() {
        return pref.getBoolean(IS_LOGGED_IN, false);
    }

    // Clear session (Logout)
    public void logout() {
        editor.clear();
        editor.apply();
    }
}
