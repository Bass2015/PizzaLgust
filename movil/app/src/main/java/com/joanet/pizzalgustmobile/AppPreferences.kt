package com.joanet.pizzalgustmobile

import android.content.Context
import android.content.SharedPreferences

class AppPreferences(context: Context) {
    private val preferences: SharedPreferences = context.getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)

    var authToken: String?
        get() = preferences.getString("authToken", null)
        set(value) = preferences.edit().putString("authToken", value).apply()
}

