package com.ithomasoft.biometric;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.fragment.app.FragmentActivity;

final class BiometricHelper {
    static int failCount = 0;

    /**
     * 验证当前设备生物识别功能
     *
     * @param context
     * @param callback
     */
    static void verifyBiometric(Context context, BiometricStateCallback callback) {
        int result = BiometricManager.from(context)
                .canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG);

        if (callback != null) {
            switch (result) {
                case BiometricManager.BIOMETRIC_SUCCESS:
                    callback.onSuccess();
                    break;
                case BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE:
                case BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE:
                case BiometricManager.BIOMETRIC_ERROR_SECURITY_UPDATE_REQUIRED:
                case BiometricManager.BIOMETRIC_ERROR_UNSUPPORTED:
                    callback.onNotSupport();
                    break;
                case BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED:
                    callback.onNoInfo();
                    break;
                case BiometricManager.BIOMETRIC_STATUS_UNKNOWN:
                default:
                    callback.onUnknown();
            }
        }
    }


    /**
     * 添加指纹，在调用之前先进行设备硬件支持的判断
     *
     * @param context
     */
    static void addBiometric(Context context) {
        //没有录入指纹，打开相应的录入指纹页面
        String pcgName;
        String clsName;
        if (RomUtils.isHuawei() || RomUtils.isHonor()) {
            pcgName = "com.android.settings";
            clsName = "com.android.settings.fingerprint.FingerprintSettingsActivity";
        } else if (RomUtils.isVivo()) {
            pcgName = "com.android.settings";
            clsName = "com.android.settings.Settings$FingerpintAndFaceSettingsActivity";
        } else {
            pcgName = "com.android.settings";
            clsName = "com.android.settings.Settings";
        }

        if (!TextUtils.isEmpty(pcgName) && !TextUtils.isEmpty(clsName)) {
            Intent intent = new Intent();
            ComponentName componentName = new ComponentName(pcgName, clsName);
            intent.setAction(Intent.ACTION_VIEW);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setComponent(componentName);
            context.startActivity(intent);
        }
    }

    /**
     * 开始进行验证
     *
     * @param context
     * @param title
     * @param other
     * @param callback
     */
    static void startBiometric(FragmentActivity context, String title, String other, BiometricIdentifyCallback callback) {
        failCount = 0;
        BiometricPrompt.PromptInfo promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle(title)
                .setNegativeButtonText(other)
                .setAllowedAuthenticators(BiometricManager.Authenticators.BIOMETRIC_STRONG)
                .build();
        BiometricPrompt biometricPrompt = new BiometricPrompt(context, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                Log.e("BiometricHelper", errorCode + "--" + errString);
                if (callback != null) {
                    switch (errorCode) {
                        case BiometricPrompt.ERROR_NO_BIOMETRICS:
                        case BiometricPrompt.ERROR_NO_DEVICE_CREDENTIAL:
                            callback.onNoBiometric();
                            break;
                        case BiometricPrompt.ERROR_NEGATIVE_BUTTON:
                        case BiometricPrompt.ERROR_LOCKOUT:
                        case BiometricPrompt.ERROR_LOCKOUT_PERMANENT:
                        case BiometricPrompt.ERROR_NO_SPACE:
                        case BiometricPrompt.ERROR_SECURITY_UPDATE_REQUIRED:
                        case BiometricPrompt.ERROR_TIMEOUT:
                        case BiometricPrompt.ERROR_UNABLE_TO_PROCESS:
                        case BiometricPrompt.ERROR_VENDOR:
                        case BiometricPrompt.ERROR_HW_UNAVAILABLE:
                        case BiometricPrompt.ERROR_HW_NOT_PRESENT:
                            callback.onOther();
                            break;
                        case BiometricPrompt.ERROR_USER_CANCELED:
                        case BiometricPrompt.ERROR_CANCELED:
                            callback.onCancel();
                            break;
                        default:
                            callback.onError(errorCode, errString.toString());
                    }

                }
            }

            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);

                Log.e("BiometricHelper", "onAuthenticationSucceeded");
                if (callback != null) {
                    callback.onSucceeded(result);
                }
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                Log.e("BiometricHelper", "onAuthenticationFailed");
                failCount++;
                if (callback != null && failCount >= 3) {
                    callback.onOther();
                }
            }
        });
        biometricPrompt.authenticate(promptInfo);
    }


}
