package com.hyejeanmoon.splashcompose.utils

import android.content.Context
import android.content.SharedPreferences

class SharedPreferencesUtils {

    private val sharedPreferences: SharedPreferences

    constructor(context: Context){
        sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCE_DATA_NAME,Context.MODE_PRIVATE)
    }

    fun putString(name: String, data: String) {
        val editor = sharedPreferences.edit()
        editor.putString(name,data)
        editor.apply()
    }

    fun getString(name: String):String {
        return sharedPreferences.getString(name,"") ?:""
    }

    fun putInt(name: String, position:Int) {
        val editor = sharedPreferences.edit()
        editor.putInt(name,position)
        editor.apply()
    }

    fun getInt(name: String):Int {
        return sharedPreferences.getInt(name,0)
    }

    companion object {
        private const val SHARED_PREFERENCE_DATA_NAME = "SPLASH_COMPOSE"

        const val KEY_ORDER_BY = "SPLASH_COMPOSE_ORDER_BY"
        const val KEY_DISPLAY_RESOLUTION = "SPLASH_COMPOSE_DISPLAY_RESOLUTION"
        const val KEY_DOWNLOAD_RESOLUTION = "SPLASH_COMPOSE_DOWNLOAD_RESOLUTION"
    }
}