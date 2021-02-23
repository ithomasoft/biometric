package com.ithomasoft.biometric.demo;

import android.app.Application;

import com.ithomasoft.biometric.BiometricManager;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        BiometricManager.init(this);
//        BiometricManager.registerActivityName("MainActivity");
    }
}
