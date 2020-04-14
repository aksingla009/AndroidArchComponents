package com.aman.demo.data.preferences

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager

private const val KEY_QUOTES_SAVED_AT = "quotes_saved_at"

class PreferenceProvider(
    context: Context
) {
    //Even if we pass fragment context or activity context we will get the whole application context
    //Which will prevent the memory leaks
    private val appContext = context.applicationContext

    private val preference: SharedPreferences
        get() = PreferenceManager.getDefaultSharedPreferences(appContext)

    fun saveLastTimeStampToPreference(savedAt: String) {
        preference.edit().putString(KEY_QUOTES_SAVED_AT, savedAt).apply()
        //apply is to save the values

    }

    fun getLastTimeStampFromPreference(): String? {
        return preference.getString(KEY_QUOTES_SAVED_AT, null)
    }
}