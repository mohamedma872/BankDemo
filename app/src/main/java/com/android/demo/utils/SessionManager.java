package com.android.demo.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class SessionManager {
    // Shared Preferences
    SharedPreferences pref;

    // Editor for Shared preferences
    SharedPreferences.Editor editor;

    // Context
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String PREF_NAME = "Bank";

    public SessionManager(Context context) {
        this._context = context;
        try {
            pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
            editor = pref.edit();
        } catch (Exception ex) {
            Log.d("Session", "" + ex.getStackTrace());

        }

    }
    public void SaveToken(long UserID) {
        editor.putLong("UserID", UserID);
        editor.commit();
        editor.apply();
    }
    public long getToken() {
        return   pref.getLong("UserID", 177);

    }
}