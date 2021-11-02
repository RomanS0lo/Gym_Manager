package com.dts.gym_manager.model

import java.util.*

data class Token(
    val accountId: Long ,
    val token: String ,
    val createAt: Date ,
    val expiredAt: Date
)
