package com.ithomasoft.biometric.demo;

import android.content.Context;
import android.content.SharedPreferences;

public class UserHelper {

    public static boolean isLogin(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("user", Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean("login_state", false);
    }

    public static void setLoginState(Context context, boolean isLogin) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("user", Context.MODE_PRIVATE);
        sharedPreferences.edit().putBoolean("login_state", isLogin).apply();
    }

    public static String getUserName(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("user", Context.MODE_PRIVATE);
        return sharedPreferences.getString("username", "666");
    }

    public static void setUserName(Context context, String username) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("user", Context.MODE_PRIVATE);
        sharedPreferences.edit().putString("username", username).apply();
    }

    public static String getPassword(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("user", Context.MODE_PRIVATE);
        return sharedPreferences.getString("password", "123456");
    }

    public static void setPassword(Context context, String password) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("user", Context.MODE_PRIVATE);
        sharedPreferences.edit().putString("password", password).apply();
    }


}
