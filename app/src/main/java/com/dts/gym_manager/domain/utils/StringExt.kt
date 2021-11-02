package com.dts.gym_manager.domain.utils

fun String?.asException(): Exception {
    return Exception(this ?: "Msg is null")
}
