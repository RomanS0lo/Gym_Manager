package com.dts.gym_manager.domain.retrofit

import com.dts.gym_manager.domain.ApiService
import com.dts.gym_manager.domain.OnApiResultCallback
import com.dts.gym_manager.domain.utils.asException
import com.dts.gym_manager.model.ApiError
import com.dts.gym_manager.model.Login
import com.dts.gym_manager.model.Token
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

class RestApiService {

    private val gson = Gson()

    fun login(email: String, password: String, onResult: OnApiResultCallback<Token>) {
        val retrofit = ServiceBuilder.buildService(ApiService::class.java)
        val login = Login(email, password)

        retrofit.login(login).enqueue(
            object : Callback<Token> {
                override fun onResponse(call: Call<Token>, response: Response<Token>) {
                    if (response.isSuccessful) {
                        val resultToken = response.body()
                        if (resultToken != null) onResult.onSuccess(resultToken)
                        else onResult.onFail("Token is null".asException())
                    } else {
                        response.errorBody()?.string()?.let { msg ->
                            try {
                                val error = gson.fromJson(msg, ApiError::class.java)
                                onResult.onFail(error.msg.asException())
                            } catch (e: Exception) {
                                onResult.onFail(e)
                            }
                        } ?: onResult.onFail("Login is fail".asException())
                    }
                }

                override fun onFailure(call: Call<Token>, t: Throwable) {
                    Timber.e(t)
                    onResult.onFail(t.message.asException())
                }
            }
        )
    }
}
