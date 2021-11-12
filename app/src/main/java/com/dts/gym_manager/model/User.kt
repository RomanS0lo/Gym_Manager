package com.dts.gym_manager.model

data class User(
    val firstName: String,
    val lastName: String,
    val age: Int,
    val sex: Sex,
    val type: Type
) {

    companion object {
        const val USER_ID = "user_id"
    }

    enum class Sex {
        MALE, FEMALE
    }

    enum class Type {
        MEMBER, TRAINER, STAFF, ADMIN
    }
}
