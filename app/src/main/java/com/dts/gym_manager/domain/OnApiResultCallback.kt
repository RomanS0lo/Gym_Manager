package com.dts.gym_manager.domain

interface OnApiResultCallback<T> {

    fun onSuccess(response: T)

    fun onFail(exception: Exception, code: Int)
}
