package com.ithomasoft.biometric;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

final class BiometricLifecycle implements Application.ActivityLifecycleCallbacks {
    static final BiometricLifecycle INSTANCE = new BiometricLifecycle();

    void init(Application app) {
        app.registerActivityLifecycleCallbacks(this);
    }

    void unInit(Application app) {
        app.unregisterActivityLifecycleCallbacks(this);
    }

    @Override
    public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {
        verify(activity);
    }

    @Override
    public void onActivityStarted(@NonNull Activity activity) {

    }

    @Override
    public void onActivityResumed(@NonNull Activity activity) {
        verify(activity);

    }

    @Override
    public void onActivityPaused(@NonNull Activity activity) {

    }

    @Override
    public void onActivityStopped(@NonNull Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {
        verify(activity);
    }

    @Override
    public void onActivityDestroyed(@NonNull Activity activity) {

    }


    private void verify(Activity activity) {
        BiometricHelper.verifyBiometric(activity, new BiometricStateCallback() {
            @Override
            public void onSuccess() {
                super.onSuccess();
                BiometricManager.setHardwareDetected(true);
                BiometricManager.setBiometricInfo(true);
            }

            @Override
            public void onNotSupport() {
                super.onNotSupport();
                BiometricManager.setHardwareDetected(false);
            }

            @Override
            public void onNoInfo() {
                super.onNoInfo();
                BiometricManager.setHardwareDetected(true);
                BiometricManager.setBiometricInfo(false);
            }

            @Override
            public void onUnknown() {
                super.onUnknown();
                BiometricManager.setHardwareDetected(true);
                BiometricManager.setBiometricInfo(false);
            }
        });
    }
}
