package com.dts.gym_manager.domain.retrofit

import com.dts.gym_manager.domain.ApiService
import com.dts.gym_manager.domain.OnApiResultCallback
import com.dts.gym_manager.domain.retrofit.exception.*
import com.dts.gym_manager.domain.utils.asException
import com.dts.gym_manager.model.*
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

class RestApiService(private val apiService: ApiService, private val gson: Gson) {

    fun login(email: String, password: String, onResult: OnApiResultCallback<Token>) {
        val login = Login(email, password)

        apiService.login(login).enqueue(
            object : Callback<Token> {
                override fun onResponse(call: Call<Token>, response: Response<Token>) {
                    if (response.isSuccessful) {
                        val resultToken = response.body()
                        if (resultToken != null) onResult.onSuccess(resultToken)
                        else onResult.onFail(TokenNullException())
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

    fun getCurrentMembership(onResult: OnApiResultCallback<Membership>) {
        apiService.getCurrentMembership().enqueue(
            object : Callback<Membership> {

                override fun onResponse(call: Call<Membership>, response: Response<Membership>) {
                    if (response.isSuccessful) {
                        val responseMembership = response.body()
                        if (responseMembership != null) onResult.onSuccess(responseMembership)
                        else onResult.onFail(MembershipNullException())
                    } else {
                        response.errorBody()?.string()?.let { msg ->
                            try {
                                val error = gson.fromJson(msg, ApiError::class.java)
                                onResult.onFail(error.msg.asException())
                            } catch (e: Exception) {
                                onResult.onFail(e)
                            }
                        } ?: onResult.onFail(MembershipDataNullException())
                    }
                }

                override fun onFailure(call: Call<Membership>, t: Throwable) {
                    Timber.e(t)
                    onResult.onFail(t.message.asException())
                }
            }
        )
    }

    fun getUsersInfo(onResult: OnApiResultCallback<User>) {
        apiService.getUsersInfo().enqueue(
            object : Callback<User> {
                override fun onResponse(call: Call<User>, response: Response<User>) {
                    if (response.isSuccessful) {
                        val responseUserData = response.body()
                        if (responseUserData != null) onResult.onSuccess(responseUserData)
                        else onResult.onFail(UserDataIsNullException())
                    } else {
                        response.errorBody()?.string()?.let { msg ->
                            try {
                                val error = gson.fromJson(msg, ApiError::class.java)
                                onResult.onFail(error.msg.asException())
                            } catch (e: Exception) {
                                onResult.onFail(e)
                            }
                        } ?: onResult.onFail(UserDataIsNullException())
                    }
                }

                override fun onFailure(call: Call<User>, t: Throwable) {
                    Timber.e(t)
                    onResult.onFail(t.message.asException())
                }
            }
        )
    }

    fun getUserById(onResult: OnApiResultCallback<User>) {
        apiService.getUserById().enqueue(
            object : Callback<User> {
                override fun onResponse(call: Call<User>, response: Response<User>) {
                    if (response.isSuccessful) {
                        val responseUser = response.body()
                        if (responseUser != null) onResult.onSuccess(responseUser)
                        else onResult.onFail(UserDataIsNullException())
                    } else {
                        response.errorBody()?.string()?.let { msg ->
                            try {
                                val error = gson.fromJson(msg, ApiError::class.java)
                                onResult.onFail(error.msg.asException())
                            } catch (e: Exception) {
                                onResult.onFail(e)
                            }
                        } ?: onResult.onFail(UserDataIsNullException())
                    }
                }

                override fun onFailure(call: Call<User>, t: Throwable) {
                    Timber.e(t)
                    onResult.onFail(t.message.asException())
                }
            }
        )
    }
}
