package com.dts.gym_manager.domain.retrofit

import com.dts.gym_manager.data.PrefsRepository
import com.dts.gym_manager.domain.ApiService
import com.dts.gym_manager.domain.OnApiResultCallback
import com.dts.gym_manager.domain.retrofit.exception.MembershipDataNullException
import com.dts.gym_manager.domain.retrofit.exception.MembershipNullException
import com.dts.gym_manager.domain.retrofit.exception.TokenNullException
import com.dts.gym_manager.domain.retrofit.exception.UserDataIsNullException
import com.dts.gym_manager.domain.utils.asException
import com.dts.gym_manager.model.*
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

class RestApiService(
    private val apiService: ApiService,
    private val gson: Gson,
    private val prefs: PrefsRepository
) {

    fun login(
        email: String,
        password: String,
        onResult: OnApiResultCallback<RegistrationInfo>
    ) {
        val login = Login(email, password)

        apiService.login(login).enqueue(
            object : Callback<RegistrationInfo> {
                override fun onResponse(call: Call<RegistrationInfo>, response: Response<RegistrationInfo>) {
                    if (response.isSuccessful) {
                        val result = response.body()
                        prefs.token = result?.token
                        prefs.user = result?.user
                        if (result != null) onResult.onSuccess(result)
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

                override fun onFailure(call: Call<RegistrationInfo>, t: Throwable) {
                    Timber.e(t)
                    onResult.onFail(t.message.asException())
                }
            }
        )
    }

    fun getCurrentMembership(onResult: OnApiResultCallback<Membership?>) {
        apiService.getCurrentMembership().enqueue(
            object : Callback<Membership?> {

                override fun onResponse(call: Call<Membership?>, response: Response<Membership?>) {
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

                override fun onFailure(call: Call<Membership?>, t: Throwable) {
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

    fun register(
        firstName: String,
        lastName: String,
        age: Int,
        sex: User.Sex,
        login: String,
        password: String,
        onResult: OnApiResultCallback<RegistrationInfo>
    ) {
        val user = RegistrationData(
            firstName = firstName,
            lastName = lastName,
            age = age,
            sex = sex,
            login = login,
            password = password,
            type = User.Type.MEMBER
        )

        apiService.register(user).enqueue(
            object : Callback<RegistrationInfo> {

                override fun onResponse(
                    call: Call<RegistrationInfo>,
                    response: Response<RegistrationInfo>
                ) {
                    if (response.isSuccessful) {
                        val registrationInfo = response.body()
                        if (registrationInfo != null) {
                            prefs.user = registrationInfo.user
                            prefs.token = registrationInfo.token

                            onResult.onSuccess(registrationInfo)
                        } else {
                            onResult.onFail(response.message().asException())
                        }
                    } else {
                        onResult.onFail(response.message().asException())
                    }
                }

                override fun onFailure(call: Call<RegistrationInfo>, t: Throwable) {
                    Timber.e(t)
                    onResult.onFail(t.message.asException())
                }
            }
        )
    }

    fun getWallets(onResult: OnApiResultCallback<List<Wallets>>) {
        apiService.getWallets().enqueue(
            object : Callback<List<Wallets>> {
                override fun onResponse(
                    call: Call<List<Wallets>>,
                    response: Response<List<Wallets>>
                ) {
                    if (response.isSuccessful) {
                        val wallets = response.body()
                        if (wallets != null) {
                            onResult.onSuccess(wallets)
                        } else {
                            onResult.onFail(response.message().asException())
                        }
                    } else {
                        onResult.onFail(response.message().asException())
                    }
                }

                override fun onFailure(call: Call<List<Wallets>>, t: Throwable) {
                    Timber.e(t)
                    onResult.onFail(t.message.asException())
                }
            }
        )
    }

    fun topUp(valueType: Wallets.ValueType, value: Float, onResult: OnApiResultCallback<Wallets>) {
        val topUp = TopUp(valueType, value)
        apiService.topUp(topUp).enqueue(
            object : Callback<Wallets> {
                override fun onResponse(call: Call<Wallets>, response: Response<Wallets>) {
                    if (response.isSuccessful) {
                        val wallet = response.body()
                        if (wallet != null) onResult.onSuccess(wallet)
                        else onResult.onFail(TokenNullException())
                    } else {
                        response.errorBody()?.string()?.let { msg ->
                            try {
                                val error = gson.fromJson(msg, ApiError::class.java)
                                onResult.onFail(error.msg.asException())
                            } catch (e: Exception) {
                                onResult.onFail(e)
                            }
                        } ?: onResult.onFail("Top Up is fail".asException())
                    }
                }

                override fun onFailure(call: Call<Wallets>, t: Throwable) {
                    Timber.e(t)
                    onResult.onFail(t.message.asException())
                }

            }
        )
    }
}
