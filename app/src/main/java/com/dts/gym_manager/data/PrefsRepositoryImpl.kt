package com.dts.gym_manager.data

import android.content.Context
import androidx.core.content.edit
import com.dts.gym_manager.model.RegistrationInfo
import com.dts.gym_manager.model.Token
import com.dts.gym_manager.model.User
import com.google.gson.Gson

class PrefsRepositoryImpl(context: Context, private val gson: Gson) : PrefsRepository {

    companion object {
        private const val NAME = "app_prefs"

        private const val KEY_USER_TOKEN = "key:user_token"
        private const val KEY_LOGGED_USER = "key:logged_user"
        private const val KEY_USER = "key:user"
    }

    private val prefs = context.getSharedPreferences(NAME, Context.MODE_PRIVATE)

    override var token: Token?
        get() = prefs.getString(KEY_USER_TOKEN, null)
            ?.let { token -> gson.fromJson(token, Token::class.java) }
        set(value) = prefs.edit { putString(KEY_USER_TOKEN, gson.toJson(value)) }

    override var isUserLoggedIn: Boolean
        get() = prefs.getBoolean(KEY_LOGGED_USER, false)
        set(value) = prefs.edit { putBoolean(KEY_LOGGED_USER, value) }

    override var user: User?
        get() = prefs.getString(KEY_USER, null)
            ?.let { user -> gson.fromJson(user, User::class.java) }
        set(value) = prefs.edit { putString(KEY_USER, gson.toJson(value)) }
}
