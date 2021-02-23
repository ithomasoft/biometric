package com.ithomasoft.biometric;

import androidx.biometric.BiometricPrompt;

public abstract class BiometricIdentifyCallback {
    public void onOther() {
    }

    public void onNoBiometric() {
    }

    public void onSucceeded(BiometricPrompt.AuthenticationResult result) {
    }

    public void onError(int code, String reason) {
    }

    public void onCancel() {

    }
}
