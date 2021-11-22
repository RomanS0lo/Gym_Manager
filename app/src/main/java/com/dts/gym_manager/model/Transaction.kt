package com.dts.gym_manager.model

import java.util.*

data class Transaction(
    val id: Long,
    val userId: Long,
    val createAt: Date,
    val amountData: Map<Wallets.ValueType, Float>,
    val goods: List<Goods>,
    var status: Status = Status.CREATED
) {
    enum class Status {
        CREATED, IN_PROGRESS, SUCCESS, FAIL
    }
}
