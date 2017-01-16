package com.feamorx.cookebook.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import com.feamorx.cookebook.R;

/**
 * Created by x86 on 09.01.2017.
 */

public class Settings {

    public static final long DEFAULT_UPDATE_DATA_INTERVAL = 12 * 60 * 60 * 1000; //12h

    private Context context;
    private SharedPreferences preferences;

    public Settings(Context context) {
        this.context = context;
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public long getUpdateDataInterval() {
        long result = preferences.getLong("SHARED_UPDATE_INTERVAL", DEFAULT_UPDATE_DATA_INTERVAL);
        return result;
    }

    public void setDefaultUpdateDataInterval(long interval) {
        if (interval >= 0) {
            preferences.edit().putLong("SHARED_UPDATE_INTERVAL", interval).commit();
        }
    }

    public String getUpdateDataUrl(Integer dataVersion) {
        if (dataVersion == null) {
            dataVersion = 0;
        }
        String url = preferences.getString("SHARED_UPDATE_URL", null);
        if (TextUtils.isEmpty(url)) {
            url = context.getString(R.string.update_data_url);
        }
        String result;
        try {
            result = String.format(url, dataVersion);
        } catch (Exception ex) {
            result = url;
        }
        return result;
    }

    public void setUpdateDataUrl(String url) {
        preferences.edit().putString("SHARED_UPDATE_URL", url).commit();
    }
}
