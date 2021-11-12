package com.dts.gym_manager.model

data class RegistrationData(
    val firstName: String?,
    val lastName: String?,
    val sex: User.Sex?,
    val age: Int?,
    val type: User.Type?,
    val login: String?,
    val password: String?
)
