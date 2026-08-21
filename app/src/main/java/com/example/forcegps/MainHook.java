package com.example.forcegps;

import android.location.LocationManager;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

public class MainHook implements IXposedHookLoadPackage {

    @Override
    public void handleLoadPackage(LoadPackageParam lpparam) throws Throwable {
        // Hook LocationManager 中的 isProviderEnabled 方法
        XposedHelpers.findAndHookMethod(
                LocationManager.class,
                "isProviderEnabled",
                String.class,
                new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        String provider = (String) param.args[0];
                        // 当查询的是 GPS Provider 时，强制返回 true
                        if (LocationManager.GPS_PROVIDER.equals(provider)) {
                            param.setResult(true);
                        }
                    }
                }
        );
    }
}