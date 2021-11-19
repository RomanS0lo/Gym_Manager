package com.dts.gym_manager.top_up

import com.dts.gym_manager.model.Wallets

interface Communicator {

    fun saveTopUpValue(valueType: Wallets.ValueType, value: Int)
}

interface TopUpCallback {

    fun onCompleteTopUp()
}
