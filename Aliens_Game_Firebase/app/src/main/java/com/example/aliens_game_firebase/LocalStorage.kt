package com.example.aliens_game_firebase

import android.content.Context
import androidx.core.content.edit

class LocalStorage {

    companion object {

        private const val SHARED_PREFERENCE_STORE = "aliens"

        fun writeString(key: String, value: String, context: Context) {
            val sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCE_STORE, Context.MODE_PRIVATE)
            sharedPreferences.edit {
                putString(key, value)
                apply()
            }
        }


        fun fetchString(key: String, context: Context, default: String? = null): String? {
            val sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCE_STORE, Context.MODE_PRIVATE)
            return sharedPreferences.getString(key, default)
        }


        fun writeInt(key: String, value : Int, context: Context) {
            val sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCE_STORE, Context.MODE_PRIVATE)
            sharedPreferences.edit {
                putInt(key, value)
                apply()
            }
        }


        fun fetchInt(key: String, context: Context, default: Int = 0): Int {
            val sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCE_STORE, Context.MODE_PRIVATE)
            return sharedPreferences.getInt(key, default)
        }

    }

}