package com.dts.gym_manager.packet

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dts.gym_manager.domain.OnApiResultCallback
import com.dts.gym_manager.domain.retrofit.RestApiService
import com.dts.gym_manager.model.Goods
import com.dts.gym_manager.model.Transaction
import timber.log.Timber

class PacketViewModel(private val apiService: RestApiService) : ViewModel() {

    private val cartList = mutableListOf<Goods>()

    private val onGoodsLiveData = MutableLiveData<List<Goods>>()

    private val isPurchaseSuccessLiveData = MutableLiveData<Boolean>()

    fun onPurchaseSuccessResult(): LiveData<Boolean> = isPurchaseSuccessLiveData

    fun onGoodsResult(): LiveData<List<Goods>> = onGoodsLiveData

    fun getGoodsList() {
        apiService.getGoodsList(object : OnApiResultCallback<List<Goods>> {
            override fun onSuccess(response: List<Goods>) {
                onGoodsLiveData.postValue(response)
            }

            override fun onFail(exception: Exception, code: Int) {
                Timber.e(exception)
            }

        })
    }

    fun purchaseGoods() {
        val goodsId = cartList.map { it.id }
        apiService.purchaseGoods(goodsId.toTypedArray(), object : OnApiResultCallback<Transaction> {
            override fun onSuccess(response: Transaction) {
                isPurchaseSuccessLiveData.postValue(true)
                Timber.e("Goods Successfully Bought")
                cartList.clear()
            }

            override fun onFail(exception: Exception, code: Int) {
                isPurchaseSuccessLiveData.postValue(false)
                Timber.e(exception)
            }

        })
    }

    fun addGoods(goods: Goods) {
        cartList.add(goods)
    }

    fun getCartGoods(): List<Goods> = cartList
}
