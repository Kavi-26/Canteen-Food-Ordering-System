package com.collegecanteen.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;

    int PRIVATE_MODE = 0;
    private static final String PREF_NAME = "CollegeCanteenPref";
    private static final String IS_LOGIN = "IsLoggedIn";
    public static final String KEY_NAME = "name";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_ROLE = "role";
    public static final String KEY_MOBILE = "mobile";
    public static final String KEY_ROLL_NO = "rollNo";
    public static final String KEY_USER_ID = "user_id";

    public SessionManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void createLoginSession(String name, String email, String role, int userId, String mobile, String rollNo) {
        editor.putBoolean(IS_LOGIN, true);
        editor.putString(KEY_NAME, name);
        editor.putString(KEY_EMAIL, email);
        editor.putString(KEY_ROLE, role);
        editor.putInt(KEY_USER_ID, userId);
        editor.putString(KEY_MOBILE, mobile != null ? mobile : "");
        editor.putString(KEY_ROLL_NO, rollNo != null ? rollNo : "");
        editor.commit();
    }

    // Legacy overload for backward compat
    public void createLoginSession(String name, String email, String role) {
        createLoginSession(name, email, role, pref.getInt(KEY_USER_ID, 1), "", "");
    }

    public boolean isLoggedIn() {
        return pref.getBoolean(IS_LOGIN, false);
    }
    
    public String getUserName() {
        return pref.getString(KEY_NAME, "");
    }

    public String getUserEmail() {
        return pref.getString(KEY_EMAIL, "");
    }

    public String getUserRole() {
        return pref.getString(KEY_ROLE, "");
    }

    public String getUserMobile() {
        return pref.getString(KEY_MOBILE, "");
    }

    public String getUserRollNo() {
        return pref.getString(KEY_ROLL_NO, "");
    }
    
    public int getUserId() {
        return pref.getInt(KEY_USER_ID, 1); 
    }
    
    public void saveUserId(int id) {
        editor.putInt(KEY_USER_ID, id);
        editor.commit();
    }

    public void logout() {
        editor.clear();
        editor.commit();
    }
}
