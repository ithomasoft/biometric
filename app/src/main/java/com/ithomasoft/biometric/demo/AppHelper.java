package com.ithomasoft.biometric.demo;

import android.content.Context;
import android.content.SharedPreferences;

public class AppHelper {

    public static boolean needBiometric(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("app", Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean("need_biometric", false);
    }

    public static void applyNeedBiometric(Context context, boolean need) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("app", Context.MODE_PRIVATE);
        sharedPreferences.edit().putBoolean("need_biometric", need).apply();
    }
}
