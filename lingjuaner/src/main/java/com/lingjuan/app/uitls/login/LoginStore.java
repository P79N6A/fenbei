package com.lingjuan.app.uitls.login;

import android.content.Context;
import android.content.SharedPreferences;

public class LoginStore {
    private static final String LOGIN_INFO = "login_info"; // SharedPreference操作的文件

    public static void save(Context context, String key, String mobile) {
        SharedPreferences sharedPreferences =
            context.getSharedPreferences(LOGIN_INFO, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("mobile", mobile);
        editor.apply();
    }

    public static String get(Context context) {
        String mobile;
        SharedPreferences sharedPreferences =
            context.getSharedPreferences(LOGIN_INFO, Context.MODE_PRIVATE);
        mobile = sharedPreferences.getString("mobile", "null");
        return mobile;
    }

    public static void clean(Context context) {
        SharedPreferences sp = context.getSharedPreferences(LOGIN_INFO,
            Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.remove("mobile");
        editor.apply();
    }
}
