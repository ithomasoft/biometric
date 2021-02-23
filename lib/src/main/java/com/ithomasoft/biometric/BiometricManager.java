package com.ithomasoft.biometric;

import android.annotation.SuppressLint;
import android.app.Application;
import android.util.Log;

import androidx.biometric.BiometricPrompt;
import androidx.fragment.app.FragmentActivity;

public final class BiometricManager {
    @SuppressLint("StaticFieldLeak")
    private static Application sApp;

    /**
     * 硬件支持
     */
    private static boolean hardwareDetected;
    /**
     * 是否录入信息
     */
    private static boolean biometricInfo;

    private BiometricManager() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static void init(final Application app) {
        if (app == null) {
            Log.e("BiometricManager", "app is null.");
            return;
        }
        if (sApp == null) {
            sApp = app;
            BiometricLifecycle.INSTANCE.init(sApp);
            return;
        }
        if (sApp.equals(app)) return;
        BiometricLifecycle.INSTANCE.unInit(sApp);
        sApp = app;
        BiometricLifecycle.INSTANCE.init(sApp);
    }

    /**
     * 注册需要监听的页面，都不传的话，都监听
     * @param className
     */
    public static void registerActivityName(String... className) {
        for (String name : className) {
            BiometricLifecycle.mActivityNameList.add(name);
        }
    }

    /**
     * 硬件是否支持
     *
     * @return true 支持，false 不支持
     */
    public static boolean isHardwareDetected() {
        return hardwareDetected;
    }

    public static void setHardwareDetected(boolean hardwareDetected) {
        BiometricManager.hardwareDetected = hardwareDetected;
    }

    /**
     * 有没有录入生物信息
     *
     * @return true 有生物信息，false 没有生物信息
     */
    public static boolean hasBiometricInfo() {
        return biometricInfo;
    }

    public static void setBiometricInfo(boolean biometricInfo) {
        BiometricManager.biometricInfo = biometricInfo;
    }

    /**
     * 是否能正常进行生物认证
     *
     * @return true 可以，false 不可以
     */
    public static boolean isBiometricEnable() {
        return hardwareDetected && biometricInfo;
    }

    /**
     * 添加生物信息
     */
    public static void addNewBiometricInfo() {
        if (hardwareDetected && !biometricInfo) {
            BiometricHelper.addBiometric(sApp);
        }
    }

    /**
     * 开始生物识别认证流程
     *
     * @param context
     * @param callback
     */
    public static void startBiometricIdentify(FragmentActivity context, BiometricIdentifyCallback callback) {
        if (hardwareDetected) {
            BiometricHelper.startBiometric(context, "指纹验证", "取消", callback);
        }
    }

    /**
     * 开始生物识别认证流程
     *
     * @param context
     * @param callback
     */
    public static void startBiometricIdentify(FragmentActivity context, String title, String other, BiometricIdentifyCallback callback) {
        if (hardwareDetected) {
            BiometricHelper.startBiometric(context, title, other, callback);
        }
    }
}
