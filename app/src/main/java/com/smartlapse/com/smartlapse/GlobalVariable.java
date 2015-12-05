package com.smartlapse.com.smartlapse;

/**
 * Created by Alex on 29/10/2015.
 */
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;


/**
 * GlobalVariable
 */
public class GlobalVariable extends Application {
    /**
     * Preferences of the application
     * <br> Contains different keys : language,username,password,rememberMe,AutomaticConnection
     */
    public static final String PREFS_NAME = "com.nomosphere.app.nomosphere.PREFERENCE_FILE_KEY";

    /**
     * Puts a string  in the preferences PREFS_NAME file, under the key Key
     * @param Key Entry in witch we want to store/add a string
     * @param value The string we want to store/add
     * @param context The context of the application
     */
    public void putPref(String Key, int value, Context context)
    {
        SharedPreferences sharedPref = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(Key, value);
        editor.commit();
    }

    /**
     * Gets the String stored in preferences PREFS_NAME under the key Key
     * @param Key Name of the key under which is stored the string we want to get
     * @param context The context of the application
     * @return The String stored in preferences PREFS_NAME under the key Key
     */
    public int getPref(String Key, Context context)
    {
        SharedPreferences sharedPref = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return sharedPref.getInt(Key, 0);
    }

    /**
     * Gets the Boolean stored in preferences PREFS_NAME under the key Key
     * @param Key Name of the key under which is stored the boolean we want to get
     * @param context The context of the application
     * @return The boolean stored in preferences PREFS_NAME under the key Key
     */
    public void removePref(String Key, Context context)
    {
        SharedPreferences sharedPref = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.remove(Key);
        editor.commit();
    }

}