package com.dts.gym_manager.model

data class User(
    val id: Long = 0,
    val firstName: String?,
    val lastName: String?,
    val sex: Sex?,
    val age: Int?,
    val type: Type?
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
