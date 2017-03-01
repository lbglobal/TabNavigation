package com.wordplat.quickstart.utils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.wifi.WifiManager;
import android.os.Parcelable;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import org.json.JSONObject;

/**
 * Created by afon on 2017/2/4.
 */

/**
 * APP工具
 *
 * @author liutao
 */
public class AppUtils {
    private static final String SHORTCUT_INSTALL = "com.android.launcher.action.INSTALL_SHORTCUT";

    /**
     * 创建快捷方式
     *
     * @param act
     * 			上下文
     * @param iconResId
     *            快捷方式的图片
     * @param appNameResId
     *            快捷方式的名称
     * @param class1
     *            启动快捷方式要跳转到的Activity。
     */
    public static void createShortCut(Context act, int iconResId, int appNameResId, Class<?> class1) {
        SharedPreferences sp = act.getSharedPreferences("ShortCut", Context.MODE_PRIVATE);

        if(!sp.getBoolean("isShortCut", false)) {
            Intent shortcutintent = new Intent(SHORTCUT_INSTALL);
            // 不允许重复创建
            shortcutintent.putExtra("duplicate", false);
            // 需要显示的名称
            shortcutintent.putExtra(Intent.EXTRA_SHORTCUT_NAME, act.getString(appNameResId));
            // 快捷图片
            Parcelable icon = Intent.ShortcutIconResource.fromContext(act, iconResId);
            shortcutintent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, icon);
            // 点击快捷图片，运行的程序主入口
            Intent intent = new Intent(Intent.ACTION_MAIN, null, act, class1);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);
            shortcutintent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, intent);
            // 发送广播
            act.sendBroadcast(shortcutintent);

            sp.edit().putBoolean("isShortCut", true).commit();
        }
    }

    /**
     * 获取AndroidManifest.xml渠道号
     */
    public static String getChannel(Context context) {
        ApplicationInfo appInfo = null;
        String channelCode = null;
        try {
            appInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(),
                    PackageManager.GET_META_DATA);

            channelCode = appInfo.metaData.getString("UMENG_CHANNEL");

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } finally {
            appInfo = null;
        }
        return channelCode;
    }

    /**
     * 生成设备唯一标识码
     */
    public static String getDeviceId(Context context) {
        TelephonyManager telMana = null;
        WifiManager wifi = null;
        String deviceId = null;
        try {
            telMana = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

            deviceId = telMana.getDeviceId();

            if(TextUtils.isEmpty(deviceId)) {
                wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);

                deviceId = wifi.getConnectionInfo().getMacAddress();
            }

            if(TextUtils.isEmpty(deviceId)) {
                deviceId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            telMana = null;
            wifi = null;
        }

        return deviceId;
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dpTopx(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int pxTodp(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 友盟集成测试，获取设备信息
     */
    @Nullable
    public static String getDeviceInfo(Context context) {
        try{
            JSONObject json = new JSONObject();

            TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            String device_id = tm.getDeviceId();

            WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            String mac = wifi.getConnectionInfo().getMacAddress();
            json.put("mac", mac);

            if(TextUtils.isEmpty(device_id) ){
                device_id = mac;
            }

            if(TextUtils.isEmpty(device_id) ){
                device_id = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
            }

            json.put("device_id", device_id);

            return json.toString();

        }catch(Exception e){
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 获取App版本号
     */
    public static String getAppVersionName(Context context) {
        String packageName = null;
        String versionName = null;
        int versionCode = 0;
        try {
            packageName = context.getPackageName();

            versionName = context.getPackageManager().getPackageInfo(packageName, 0).versionName;

            versionCode = context.getPackageManager().getPackageInfo(packageName, 0).versionCode;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return versionName;
    }
}