package com.dts.gym_manager.domain.retrofit

import com.dts.gym_manager.R
import com.dts.gym_manager.data.PrefsRepository
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
import java.net.HttpURLConnection

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
                override fun onResponse(
                    call: Call<RegistrationInfo>,
                    response: Response<RegistrationInfo>
                ) {
                    if (response.isSuccessful) {
                        val result = response.body()
                        prefs.token = result?.token
                        prefs.user = result?.user
                        if (result != null) onResult.onSuccess(result)
                        else onResult.onFail(TokenNullException(), response.code())
                    } else {
                        response.errorBody()?.string()?.let { msg ->
                            try {
                                val error = gson.fromJson(msg, ApiError::class.java)
                                onResult.onFail(error.msg.asException(), response.code())
                            } catch (e: Exception) {
                                onResult.onFail(e, response.code())
                            }
                        } ?: onResult.onFail("Login is fail".asException(), response.code())
                    }
                }

                override fun onFailure(call: Call<RegistrationInfo>, t: Throwable) {
                    Timber.e(t)
                    onResult.onFail(t.message.asException(), HttpURLConnection.HTTP_BAD_REQUEST)
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
                        else onResult.onFail(MembershipNullException(), response.code())
                    } else {
                        response.errorBody()?.string()?.let { msg ->
                            try {
                                val error = gson.fromJson(msg, ApiError::class.java)
                                onResult.onFail(error.msg.asException(), response.code())
                            } catch (e: Exception) {
                                onResult.onFail(e, response.code())
                            }
                        } ?: onResult.onFail(MembershipDataNullException(), response.code())
                    }
                }

                override fun onFailure(call: Call<Membership?>, t: Throwable) {
                    Timber.e(t)
                    onResult.onFail(t.message.asException(), HttpURLConnection.HTTP_BAD_REQUEST)
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
                        else onResult.onFail(UserDataIsNullException(), response.code())
                    } else {
                        response.errorBody()?.string()?.let { msg ->
                            try {
                                val error = gson.fromJson(msg, ApiError::class.java)
                                onResult.onFail(error.msg.asException(), response.code())
                            } catch (e: Exception) {
                                onResult.onFail(e, response.code())
                            }
                        } ?: onResult.onFail(UserDataIsNullException(), response.code())
                    }
                }

                override fun onFailure(call: Call<User>, t: Throwable) {
                    Timber.e(t)
                    onResult.onFail(t.message.asException(), HttpURLConnection.HTTP_BAD_REQUEST)
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
                        else onResult.onFail(UserDataIsNullException(), response.code())
                    } else {
                        response.errorBody()?.string()?.let { msg ->
                            try {
                                val error = gson.fromJson(msg, ApiError::class.java)
                                onResult.onFail(error.msg.asException(), response.code())
                            } catch (e: Exception) {
                                onResult.onFail(e, response.code())
                            }
                        } ?: onResult.onFail(UserDataIsNullException(), response.code())
                    }
                }

                override fun onFailure(call: Call<User>, t: Throwable) {
                    Timber.e(t)
                    onResult.onFail(t.message.asException(), HttpURLConnection.HTTP_BAD_REQUEST)
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
                            onResult.onFail(response.message().asException(), response.code())
                        }
                    } else {
                        onResult.onFail(response.message().asException(), response.code())
                    }
                }

                override fun onFailure(call: Call<RegistrationInfo>, t: Throwable) {
                    Timber.e(t)
                    onResult.onFail(t.message.asException(), HttpURLConnection.HTTP_BAD_REQUEST)
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
                            onResult.onFail(response.message().asException(), response.code())
                        }
                    } else {
                        onResult.onFail(response.message().asException(), response.code())
                    }
                }

                override fun onFailure(call: Call<List<Wallets>>, t: Throwable) {
                    Timber.e(t)
                    onResult.onFail(t.message.asException(), HttpURLConnection.HTTP_BAD_REQUEST)
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
                        else onResult.onFail(TokenNullException(), response.code())
                    } else {
                        response.errorBody()?.string()?.let { msg ->
                            try {
                                val error = gson.fromJson(msg, ApiError::class.java)
                                onResult.onFail(error.msg.asException(), response.code())
                            } catch (e: Exception) {
                                onResult.onFail(e, response.code())
                            }
                        } ?: onResult.onFail(
                            "Top Up is fail".asException(),
                            HttpURLConnection.HTTP_BAD_REQUEST
                        )
                    }
                }

                override fun onFailure(call: Call<Wallets>, t: Throwable) {
                    Timber.e(t)
                    onResult.onFail(t.message.asException(), HttpURLConnection.HTTP_BAD_REQUEST)
                }

            }
        )
    }
    fun getGoodsList(onResult: OnApiResultCallback<List<Goods>>) {
        apiService.getGoodsList().enqueue(
            object : Callback<List<Goods>> {
                override fun onResponse(call: Call<List<Goods>>, response: Response<List<Goods>>) {
                    if (response.isSuccessful) {
                        val goodsList = response.body()
                        if (goodsList != null) onResult.onSuccess(goodsList)
                        else onResult.onFail(GoodsNullException(), response.code())
                    } else {
                        onResult.onFail(response.message().asException(), response.code())
                    }
                }

                override fun onFailure(call: Call<List<Goods>>, t: Throwable) {
                    Timber.e(t)
                    onResult.onFail(t.message.asException(), HttpURLConnection.HTTP_BAD_REQUEST)
                }

            }
        )
    }

    fun purchaseGoods(goods: Array<Long>, onResult: OnApiResultCallback<Transaction>) {
        apiService.purchaseGoods(goods).enqueue(
            object : Callback<Transaction> {

                override fun onResponse(call: Call<Transaction>, response: Response<Transaction>) {
                    if (response.isSuccessful) {
                        val goodsList = response.body()
                        if (goodsList != null) onResult.onSuccess(goodsList)
                        else onResult.onFail(GoodsNullException(), response.code())
                    } else {
                        onResult.onFail(response.message().asException(), response.code())
                    }
                }

                override fun onFailure(call: Call<Transaction>, t: Throwable) {
                    Timber.e(t)
                    onResult.onFail(t.message.asException(), HttpURLConnection.HTTP_BAD_REQUEST)
                }

            }
        )
    }

    fun getMembershipPriceList(onResult: OnApiResultCallback<List<Price>>) {
        apiService.getMembershipPriceList().enqueue(
            object : Callback<List<Price>> {

                override fun onResponse(call: Call<List<Price>>, response: Response<List<Price>>) {
                    if (response.isSuccessful) {
                        if (response.body() != null) onResult.onSuccess(response.body()!!)
                        else {
                            onResult.onFail(PriceBodyIsNullException(), response.code())
                        }
                    } else {
                        response.errorBody()?.string()?.let { msg ->
                            try {
                                val error = gson.fromJson(msg, ApiError::class.java)
                                onResult.onFail(error.msg.asException(), response.code())
                            } catch (e: Exception) {
                                onResult.onFail(e, response.code())
                            }
                        } ?: onResult.onFail(UserDataIsNullException(), response.code())
                    }
                }

                override fun onFailure(call: Call<List<Price>>, t: Throwable) {
                    Timber.e(t)
                    onResult.onFail(t.message.asException(), HttpURLConnection.HTTP_BAD_REQUEST)
                }
            }
        )
    }

    fun createMembership(info: CreateMembershipInfo, onResult: OnApiResultCallback<Membership>) {
        val call = apiService.createMemberships(info)
        call.enqueue(object : Callback<Membership> {

            override fun onResponse(call: Call<Membership>, response: Response<Membership>) {
                if (response.isSuccessful) {
                    val createMembershipInfo = response.body()
                    if (createMembershipInfo != null) {
                        response.body()?.let { membership ->
                            onResult.onSuccess(membership)
                        } ?: onResult.onFail(Exception(response.message()), response.code())
                    } else {
                        onResult.onFail(CreateMembershipBodyIsEmptyExeption(), response.code())
                    }
                } else {
                    response.errorBody()?.string()?.let { msg ->
                        try {
                            val error = gson.fromJson(msg, ApiError::class.java)
                            onResult.onFail(error.msg.asException(), response.code())
                        } catch (e: Exception) {
                            onResult.onFail(e, response.code())
                        }
                    } ?: onResult.onFail(UserDataIsNullException(), response.code())
                }
            }

            override fun onFailure(call: Call<Membership>, t: Throwable) {
                Timber.e(t)
            }
        })
    }

    fun upgradeMembership(
        userId: Long,
        level: Membership.Level,
        onResult: OnApiResultCallback<Membership>
    ) {
        val call = apiService.upgradeMembership(UpgradeMembershipInfo(userId, level))
        call.enqueue(object : Callback<Membership> {

            override fun onResponse(call: Call<Membership>, response: Response<Membership>) {
                if (response.isSuccessful) {
                    val upgradeMembershipInfo = response.body()
                    if (upgradeMembershipInfo != null) {
                        response.body()?.let { membership ->
                            onResult.onSuccess(membership)
                        } ?: onResult.onFail(Exception(response.message()), response.code())
                    } else {
                        onResult.onFail(UpgradeMembershipBodyIsEmptyException(), response.code())
                    }
                } else {
                    response.errorBody()?.string()?.let { msg ->
                        try {
                            val error = gson.fromJson(msg, ApiError::class.java)
                            onResult.onFail(error.msg.asException(), response.code())
                        } catch (e: Exception) {
                            onResult.onFail(e, response.code())
                        }
                    } ?: onResult.onFail(UserDataIsNullException(), response.code())
                }
            }

            override fun onFailure(call: Call<Membership>, t: Throwable) {
                Timber.e(t)
            }
        })
    }
}
