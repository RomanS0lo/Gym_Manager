package com.dts.gym_manager.model

import android.graphics.Color

data class Price(
    val level: Membership.Level,
    val price: Float,
    val duration: Int,
    var color: Int,
    var textColor:Int
)
