package com.dts.gym_manager.domain

import com.dts.gym_manager.model.Login
import com.dts.gym_manager.model.Membership
import com.dts.gym_manager.model.Token
import com.dts.gym_manager.model.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    @POST("/login")
    fun login(@Body loginData: Login): Call<Token>

    @GET("/memberships")
    fun getCurrentMembership(): Call<Membership>

    @GET("/users")
    fun getUsersInfo(): Call<User>

    @GET("/users/my_info")
    fun getUserById(): Call<User>
}
