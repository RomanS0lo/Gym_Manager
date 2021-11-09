package com.dts.gym_manager.model

import java.util.*

data class Membership(
    val id: Long,
    val userId: Long,
    var level: Level,
    var dayStart: Date,
    var dayEnd: Date,
    var isActivated: Boolean = true
) {
    enum class Level {
        BASIC, GOLD, PREMIUM
    }
}
