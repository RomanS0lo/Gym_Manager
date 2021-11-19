package com.dts.gym_manager.top_up

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dts.gym_manager.domain.OnApiResultCallback
import com.dts.gym_manager.domain.retrofit.RestApiService
import com.dts.gym_manager.model.Wallets
import com.dts.gym_manager.model.WalletsViewState
import timber.log.Timber

class TopUpViewModel(private val apiService: RestApiService) : ViewModel() {

    private var valueType: Wallets.ValueType? = null
    private var value: Float? = null

    private val topUpLiveData = MutableLiveData<Wallets>()

    private val walletLiveData = MutableLiveData<WalletsViewState>()

    fun onWalletResult(): LiveData<WalletsViewState> = walletLiveData

    fun onTopUpResult(): LiveData<Wallets> = topUpLiveData

    fun saveTopUpValue(valueType: Wallets.ValueType, value: Float) {
        this.valueType = valueType
        this.value = value
    }

    fun topUp() {
        val type = valueType ?: return
        val number = value ?: return
        apiService.topUp(
            type,
            number,
            object : OnApiResultCallback<Wallets> {
                override fun onSuccess(response: Wallets) {
                    topUpLiveData.postValue(response)
                }

                override fun onFail(exception: Exception) {
                    Timber.e(exception)
                }
            })
    }

    fun loadWallets() {
        apiService.getWallets(object : OnApiResultCallback<List<Wallets>> {
            override fun onSuccess(response: List<Wallets>) {
                val hours = response.firstOrNull { it.type == Wallets.ValueType.HOURS }
                val money = response.firstOrNull { it.type == Wallets.ValueType.MONEY }

                walletLiveData.postValue(
                    WalletsViewState(
                        money?.amount ?: 0f,
                        hours?.amount ?: 0f
                    )
                )
            }

            override fun onFail(exception: Exception) {
                Timber.e(exception)
            }
        })
    }
}
