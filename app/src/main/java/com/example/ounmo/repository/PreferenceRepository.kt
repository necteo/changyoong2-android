package com.example.ounmo.repository

import android.content.Context
import android.content.SharedPreferences

class PreferenceRepository(context: Context) {
    private val preferences: SharedPreferences =
        context.getSharedPreferences("preferences_name", Context.MODE_PRIVATE)

    fun getString(key: String, default: String): String {
        return preferences.getString(key, default).toString()
    }

    fun setString(key: String, value: String) {
        preferences.edit().putString(key, value).apply()
    }
}