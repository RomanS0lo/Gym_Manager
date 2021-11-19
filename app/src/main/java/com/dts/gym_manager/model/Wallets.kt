package com.dts.gym_manager.model

import java.time.temporal.ValueRange

data class Wallets(
    val userid: Long,
    val type: ValueType,
    val amount : Float
){
    enum class ValueType{
        MONEY, HOURS
    }
}
