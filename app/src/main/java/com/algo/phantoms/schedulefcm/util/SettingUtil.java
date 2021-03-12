package com.algo.phantoms.schedulefcm.util;

import android.content.Context;
import android.os.Build;
import android.provider.Settings;

import androidx.annotation.RequiresApi;

public class SettingUtil {

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static Boolean isTimeAutomatic(Context context) {
        return Settings.Global.getInt(
                context.getContentResolver(),
                Settings.Global.AUTO_TIME,
                0
        ) == 1;
    }

}
