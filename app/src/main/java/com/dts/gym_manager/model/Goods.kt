package com.dts.gym_manager.model

data class Goods(
    val id: Long,
    val title: String,
    val currencyType: Wallets.ValueType,
    val price: Float,
    val description: String
)
