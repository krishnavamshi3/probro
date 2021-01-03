package com.style.probro.app_utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/*
 * A Singleton for managing your SharedPreferences.
 *
 * You should make sure to change the SETTINGS_NAME to what you want
 * and choose the operating made that suits your needs, the default is
 * MODE_PRIVATE.
 *
 * IMPORTANT: The class is not thread safe. It should work fine in most
 * circumstances since the write and read operations are fast. However
 * if you call edit for bulk updates and do not commit your changes
 * there is a possibility of data loss if a background thread has modified
 * preferences at the same time.
 *
 * Usage:
 *
 * int sampleInt = ${NAME}.getInstance(context).getInt(Key.SAMPLE_INT);
 * ${NAME}.getInstance(context).set(Key.SAMPLE_INT, sampleInt);
 *
 * If ${NAME}.getInstance(Context) has been called once, you can
 * simple use ${NAME}.getInstance() to save some precious line space.
 */
public class AppSharedPreference {
    // TODO: CHANGE THIS TO SOMETHING MEANINGFUL
    private static final String SETTINGS_NAME = "default_settings";
    private static final String ARTICLE_ID_SEPARATOR = ",";
    private static AppSharedPreference sSharedPrefs;
    private final SharedPreferences mPref;
    private SharedPreferences.Editor mEditor;
    private boolean mBulkUpdate = false;

    public static List<String> getArticleIDFromBag() {
        String articleIDs = sSharedPrefs.getString(Key.CART_ARTICLE_ID_S, "");
        List<String> articleIDArray = new ArrayList<>();
        for(String id : articleIDs.split(ARTICLE_ID_SEPARATOR)) {
            if(id != null && !id.isEmpty()) {
                articleIDArray.add(id);
            }

        }
        return articleIDArray;
    }

    public static void addArticleIDToBag(String id) {
        String articleIDs = sSharedPrefs.getString(Key.CART_ARTICLE_ID_S, "");
        List<String> articleIDArray = Arrays.asList(articleIDs.split(ARTICLE_ID_SEPARATOR));
        if(articleIDArray.contains(id)) {
            return;
        }
        
        articleIDs = articleIDs + ARTICLE_ID_SEPARATOR + id;
        sSharedPrefs.put(Key.CART_ARTICLE_ID_S, articleIDs);
    }

    public static void removeArticleIDFromCart(String id) {
        String articleIDs = sSharedPrefs.getString(Key.CART_ARTICLE_ID_S, "");
        List<String> articleIDArray = new LinkedList<String>( Arrays.asList(articleIDs.split(ARTICLE_ID_SEPARATOR)));
        boolean isRemoved = articleIDArray.remove(id);
        if(isRemoved) {
            StringBuilder articleIDBuilder = new StringBuilder();
            for(String articleID : articleIDArray) {
                articleIDBuilder.append(articleID);
                articleIDBuilder.append(ARTICLE_ID_SEPARATOR);
            }
            sSharedPrefs.put(Key.CART_ARTICLE_ID_S, articleIDBuilder.toString());
        }
    }

    /**
     * Enum representing your setting names or key for your setting.
     */
    public enum Key {
        /* Recommended naming convention:
         * ints, floats, doubles, longs:
         * SAMPLE_NUM or SAMPLE_COUNT or SAMPLE_INT, SAMPLE_LONG etc.
         *
         * boolean: IS_SAMPLE, HAS_SAMPLE, CONTAINS_SAMPLE
         *
         * String: SAMPLE_KEY, SAMPLE_STR or just SAMPLE
         */
        FIRST_TIME_USE_APP_KEY,
        CART_ARTICLE_ID_S,
        SAMPLE_STR,
        SAMPLE_INT
    }

    private AppSharedPreference(Context context) {
        mPref = context.getSharedPreferences(SETTINGS_NAME, Context.MODE_PRIVATE);
    }


    public static AppSharedPreference getInstance(Context context) {
        if (sSharedPrefs == null) {
            sSharedPrefs = new AppSharedPreference(context.getApplicationContext());
        }
        return sSharedPrefs;
    }

    public static AppSharedPreference getInstance() {
        if (sSharedPrefs != null) {
            return sSharedPrefs;
        }

        //Option 1:
        throw new IllegalArgumentException("Should use getInstance(Context) at least once before using this method.");
    }

    public void put(Key key, String val) {
        doEdit();
        mEditor.putString(key.name(), val);
        doCommit();
    }

    public void put(Key key, int val) {
        doEdit();
        mEditor.putInt(key.name(), val);
        doCommit();
    }

    public void put(Key key, boolean val) {
        doEdit();
        mEditor.putBoolean(key.name(), val);
        doCommit();
    }

    public void put(Key key, float val) {
        doEdit();
        mEditor.putFloat(key.name(), val);
        doCommit();
    }

    /**
     * Convenience method for storing doubles.
     * <p/>
     * There may be instances where the accuracy of a double is desired.
     * SharedPreferences does not handle doubles so they have to
     * cast to and from String.
     *
     * @param key The enum of the preference to store.
     * @param val The new value for the preference.
     */
    public void put(Key key, double val) {
        doEdit();
        mEditor.putString(key.name(), String.valueOf(val));
        doCommit();
    }

    public void put(Key key, long val) {
        doEdit();
        mEditor.putLong(key.name(), val);
        doCommit();
    }

    public String getString(Key key, String defaultValue) {
        return mPref.getString(key.name(), defaultValue);
    }

    public String getString(Key key) {
        return mPref.getString(key.name(), null);
    }

    public int getInt(Key key) {
        return mPref.getInt(key.name(), 0);
    }

    public int getInt(Key key, int defaultValue) {
        return mPref.getInt(key.name(), defaultValue);
    }

    public long getLong(Key key) {
        return mPref.getLong(key.name(), 0);
    }

    public long getLong(Key key, long defaultValue) {
        return mPref.getLong(key.name(), defaultValue);
    }

    public float getFloat(Key key) {
        return mPref.getFloat(key.name(), 0);
    }

    public float getFloat(Key key, float defaultValue) {
        return mPref.getFloat(key.name(), defaultValue);
    }

    /**
     * Convenience method for retrieving doubles.
     * <p/>
     * There may be instances where the accuracy of a double is desired.
     * SharedPreferences does not handle doubles so they have to
     * cast to and from String.
     *
     * @param key The enum of the preference to fetch.
     */
    public double getDouble(Key key) {
        return getDouble(key, 0);
    }

    /**
     * Convenience method for retrieving doubles.
     * <p/>
     * There may be instances where the accuracy of a double is desired.
     * SharedPreferences does not handle doubles so they have to
     * cast to and from String.
     *
     * @param key The enum of the preference to fetch.
     */
    public double getDouble(Key key, double defaultValue) {
        try {
            return Double.valueOf(mPref.getString(key.name(), String.valueOf(defaultValue)));
        } catch (NumberFormatException nfe) {
            return defaultValue;
        }
    }

    public boolean getBoolean(Key key, boolean defaultValue) {
        return mPref.getBoolean(key.name(), defaultValue);
    }

    public boolean getBoolean(Key key) {
        return mPref.getBoolean(key.name(), false);
    }

    /**
     * Remove keys from SharedPreferences.
     *
     * @param keys The enum of the key(s) to be removed.
     */
    public void remove(Key... keys) {
        doEdit();
        for (Key key : keys) {
            mEditor.remove(key.name());
        }
        doCommit();
    }

    /**
     * Remove all keys from SharedPreferences.
     */
    public void clear() {
        doEdit();
        mEditor.clear();
        doCommit();
    }

    @SuppressLint("CommitPrefEdits")
    public void edit() {
        mBulkUpdate = true;
        mEditor = mPref.edit();
    }

    public void commit() {
        mBulkUpdate = false;
        mEditor.commit();
        mEditor = null;
    }

    @SuppressLint("CommitPrefEdits")
    private void doEdit() {
        if (!mBulkUpdate && mEditor == null) {
            mEditor = mPref.edit();
        }
    }

    private void doCommit() {
        if (!mBulkUpdate && mEditor != null) {
            mEditor.commit();
            mEditor = null;
        }
    }
}
