package com.dts.gym_manager.domain

import com.dts.gym_manager.model.Login
import com.dts.gym_manager.model.Token
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    @POST("/login")
    fun login(@Body loginData: Login): Call<Token>
}
