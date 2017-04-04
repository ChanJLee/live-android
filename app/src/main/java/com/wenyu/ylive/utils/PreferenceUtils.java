package com.wenyu.ylive.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by chan on 17/4/2.
 */

public class PreferenceUtils {
    private static SharedPreferences sSharedPreferences;

    private static SharedPreferences getSharedPreferences(Context context) {
        if (sSharedPreferences == null) {
            synchronized (PreferenceUtils.class) {
                if (sSharedPreferences == null) {
                    sSharedPreferences = context.getSharedPreferences("ylive_config", Context.MODE_PRIVATE);
                }
            }
        }

        return sSharedPreferences;
    }

    public static String getString(Context appContext, String key) {
        return getSharedPreferences(appContext).getString(key, null);
    }
}
