package com.example.myapplication.entity;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Context context;

    private static final String PREF_NAME = "user_session";
    private static final String KEY_IS_LOGGED_IN = "isLoggedIn";
    private static final String KEY_USER_ID = "userId";
    private static final String KEY_USER_LOGIN = "userLogin";

    public SessionManager(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    // Zapisanie informacji o zalogowanym użytkowniku
    public void saveUserSession(String userId, String userLogin) {
        editor.putBoolean(KEY_IS_LOGGED_IN, true);
        editor.putString(KEY_USER_ID, userId);
        editor.putString(KEY_USER_LOGIN, userLogin);
        editor.apply();
    }

    // Sprawdzenie, czy użytkownik jest zalogowany
    public boolean isLoggedIn() {
        return sharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false);
    }

    // Pobranie ID zalogowanego użytkownika
    public String getUserId() {
        return sharedPreferences.getString(KEY_USER_ID, null);
    }

    // Pobranie loginu zalogowanego użytkownika
    public String getUserLogin() {
        return sharedPreferences.getString(KEY_USER_LOGIN, null);
    }

    // Wylogowanie użytkownika (usunięcie sesji)
    public void logout() {
        editor.clear();
        editor.apply();
    }
}
