package com.dts.gym_manager.login

import androidx.lifecycle.ViewModel
import com.dts.gym_manager.domain.OnApiResultCallback
import com.dts.gym_manager.domain.retrofit.RestApiService
import com.dts.gym_manager.model.Token
import timber.log.Timber

class LoginViewModel : ViewModel() {
    private val apiService = RestApiService()

    fun login(email: String, password: String) {
        apiService.login(email, password, onResult = object : OnApiResultCallback<Token> {
            override fun onSuccess(response: Token) {
                Timber.d("Login Success $response")
            }

            override fun onFail(exception: Exception) {
                Timber.e(exception)
            }
        })
    }
}
