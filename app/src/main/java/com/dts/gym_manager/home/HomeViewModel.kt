package com.dts.gym_manager.home

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dts.gym_manager.R
import com.dts.gym_manager.domain.OnApiResultCallback
import com.dts.gym_manager.domain.retrofit.RestApiService
import com.dts.gym_manager.domain.retrofit.exception.MembershipDataNullException
import com.dts.gym_manager.domain.retrofit.exception.MembershipNullException
import com.dts.gym_manager.domain.retrofit.exception.UserDataIsNullException
import com.dts.gym_manager.model.Membership
import com.dts.gym_manager.model.User
import com.dts.gym_manager.model.Wallets
import com.dts.gym_manager.model.WalletsViewState
import timber.log.Timber

class HomeViewModel(
    private val apiService: RestApiService,
    private val app: Application
) : ViewModel() {

    private val userInfoLiveData = MutableLiveData<User>()

    private val onFailLiveData = MutableLiveData<String>()

    private val userMembershipLiveData = MutableLiveData<Membership?>()

    private val walletLiveData = MutableLiveData<WalletsViewState>()

    fun onWalletResult(): LiveData<WalletsViewState> = walletLiveData

    fun onUserInfoResult(): LiveData<User> = userInfoLiveData

    fun onFailResult(): LiveData<String> = onFailLiveData

    fun onMembershipResult(): LiveData<Membership?> = userMembershipLiveData

    fun setupHomeData() {
        apiService.getCurrentMembership(object : OnApiResultCallback<Membership?> {
            override fun onSuccess(response: Membership?) {
                if (response != null) {
                    userMembershipLiveData.postValue(response)
                } else {
                    Timber.e("Membership is null")
                }
            }

            override fun onFail(exception: Exception, code: Int) {
                Timber.e(exception)
                when (exception) {
                    is MembershipNullException -> onFailLiveData.postValue(app.getString(R.string.message_membership_null))
                    is MembershipDataNullException -> onFailLiveData.postValue(app.getString(R.string.message_membership_null))
                }
            }

        })
        apiService.getUserById(object : OnApiResultCallback<User> {
            override fun onSuccess(response: User) {
                userInfoLiveData.postValue(response)
            }

            override fun onFail(exception: Exception, code: Int) {
                Timber.e(exception)
                when (exception) {
                    is UserDataIsNullException -> onFailLiveData.postValue(app.getString(R.string.message_user_data_null))

                }
            }
        })

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

            override fun onFail(exception: Exception, code: Int) {
                Timber.e(exception)
            }

        })
    }
}
