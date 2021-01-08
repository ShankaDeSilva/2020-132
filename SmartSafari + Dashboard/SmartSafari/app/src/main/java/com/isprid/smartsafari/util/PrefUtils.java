package com.isprid.smartsafari.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class PrefUtils {

    private Context context;

    /**
     * Set Context to SharedPreferences.
     *
     * @param context - Must supply context
     */
    public PrefUtils(Context context) {
        this.context = context;
    }

    /**
     * Save data to SharedPreferences.
     *
     * @param key   - name for SharedPreferences
     * @param value - Value for SharedPreferences
     */
    public void savePrefsValue(String key, String value) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        final SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, value);
        editor.commit();
    }


    /**
     * Get data to SharedPreferences.
     *
     * @param key - name for SharedPreferences
     * @return value of given key
     */
    public String getPrefsValue(String key) {
        try {
            SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
            return sharedPrefs.getString(key, null);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * Check data exist in SharedPreferences.
     *
     * @param key - name for SharedPreferences
     * @return boolean value true or false
     */
    public boolean checkFromPrefs(String key) {
        try {
            SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
            return sharedPrefs.contains(key);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Remove selected key from SharedPreferences.
     *
     * @param key - name for SharedPreferences
     * @return boolean value true or false
     */
    @SuppressLint("NewApi")
    public boolean removeKeyFromPrefs(Context context, String key) {
        try {
            SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
            SharedPreferences.Editor editor = sharedPrefs.edit();
            editor.remove(key);
            editor.apply();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * Clear all data in SharedPreferences.
     */
    public void clearAllPrefsData() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        prefs.edit().clear().commit();
    }
}