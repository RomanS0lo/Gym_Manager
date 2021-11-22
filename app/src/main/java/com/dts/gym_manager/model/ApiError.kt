package com.dts.gym_manager.model

import com.google.gson.annotations.SerializedName

data class ApiError(
    @SerializedName("msg", alternate = ["message"]) val msg: String
)
