package uz.tech.base.utils

import android.content.Context
import android.content.SharedPreferences
import javax.inject.Inject


class Prefs @Inject constructor(context: Context) {

    val user_id = "user_id"
    val full_name = "full_name"
    private val prefsName: String = "TsulMobile"
    private var prefs: SharedPreferences = context.getSharedPreferences(prefsName, Context.MODE_PRIVATE)


    val language = "language"
    val token = "token"
    val login = "login"

    fun save(key: String, value: Int) {
        prefs.edit().putInt(key, value).apply()
    }

    fun save(key: String, value: String) {
        prefs.edit().putString(key, value).apply()
    }

    fun save(key: String, value: Float) {
        prefs.edit().putFloat(key, value).apply()
    }

    fun save(key: String, value: Boolean) {
        prefs.edit().putBoolean(key, value).apply()
    }

    fun save(key: String, value: Long) {
        prefs.edit().putLong(key, value).apply()
    }

    fun get(key: String, defValue: Int) = prefs.getInt(key, defValue)

    fun get(key: String, defValue: String) = prefs.getString(key, defValue) ?: ("" + defValue)

    fun get(key: String, defValue: Float) = prefs.getFloat(key, defValue)

    fun get(key: String, defValue: Boolean) = prefs.getBoolean(key, defValue)

    fun get(key: String, defValue: Long) = prefs.getLong(key, defValue)

    fun clear() {
        prefs.edit().clear().apply()
    }
}