package com.dts.gym_manager.model

import com.dts.gym_manager.domain.OnApiResultCallback

data class CreateMembershipInfo(
    val level: Membership.Level,
    val duration: Int,
    val userId: Long?
)
