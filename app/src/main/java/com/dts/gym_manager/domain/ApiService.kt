package com.dts.gym_manager.domain

import com.dts.gym_manager.model.*
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.*

interface ApiService {

    @POST("/login")
    fun login(@Body loginData: Login): Call<RegistrationInfo>

    @GET("/memberships")
    fun getCurrentMembership(): Call<Membership?>

    @GET("/users")
    fun getUsersInfo(): Call<User>

    @GET("/users/my_info")
    fun getUserById(): Call<User>

    @POST("/register")
    fun register(@Body data: RegistrationData): Call<RegistrationInfo>

    @GET("/wallets")
    fun getWallets(): Call<List<Wallets>>

    @PUT("/wallets")
    fun topUp(@Body value: TopUp): Call<Wallets>

    @GET("/goods")
    fun getGoodsList(): Call<List<Goods>>

    @GET("/goods/purchase")
    fun purchaseGoods(@Query("goods_id") goodsId: Array<Long>): Call<Transaction>
}
