package com.dts.gym_manager.membership_list

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dts.gym_manager.R
import com.dts.gym_manager.data.PrefsRepository
import com.dts.gym_manager.di.viewModels
import com.dts.gym_manager.domain.OnApiResultCallback
import com.dts.gym_manager.domain.retrofit.RestApiService
import com.dts.gym_manager.domain.retrofit.exception.MembershipDataNullException
import com.dts.gym_manager.domain.retrofit.exception.MembershipNullException
import com.dts.gym_manager.domain.retrofit.exception.UserDataIsNullException
import com.dts.gym_manager.model.*
import timber.log.Timber
import java.net.HttpURLConnection

class MembershipListViewModel(
    private val apiService: RestApiService,
    private val prefs: PrefsRepository,
    private val app: Application
) : ViewModel() {

    private val onFailLiveData = MutableLiveData<String>()
    private val membershipPriceLiveData = MutableLiveData<List<Price>>()
    private val createMembershipLivedData = MutableLiveData<Membership>()
    private val upgradeMembershipLivedData = MutableLiveData<Membership>()
    private val userMembershipLiveData = MutableLiveData<Membership?>()

    var onShowUpgradeDialog: (() -> Unit)? = null
    val userId = prefs.token?.accountId

    fun onMembershipResult(): LiveData<Membership?> = userMembershipLiveData
    fun onMembershipPriceResult(): LiveData<List<Price>> = membershipPriceLiveData
    fun onCreateMembershipResult(): LiveData<Membership> = createMembershipLivedData
    fun onUpgradeMembershipResult(): LiveData<Membership> = createMembershipLivedData
    fun onFailResult(): LiveData<String> = onFailLiveData


    fun setupMembershipPriceList() {
        apiService.getMembershipPriceList(object : OnApiResultCallback<List<Price>> {

            override fun onFail(exception: Exception, code: Int) {
                Timber.e(exception)
                onFailLiveData.postValue((R.string.label_price_dont_exist).toString())
            }

            override fun onSuccess(response: List<Price>) {
                membershipPriceLiveData.postValue(response)
            }
        })
    }

    fun createMembership(level: Membership.Level, duration: Int) {
        apiService.createMembership(
            CreateMembershipInfo(
                level,
                duration,
                prefs.user?.id
            ),
            object : OnApiResultCallback<Membership> {

                override fun onSuccess(response: Membership) {
                    createMembershipLivedData.postValue(response)
                }

                override fun onFail(exception: Exception, code: Int) {
                    Timber.e(exception)
                    if (code == HttpURLConnection.HTTP_MOVED_TEMP) {
                        onShowUpgradeDialog?.invoke()
                    } else {
                        onFailLiveData.postValue(exception.message)
                    }
                }
            }
        )
    }

    fun upgradeMembership(userId: Long, level: Membership.Level) {
        apiService.upgradeMembership(userId, level, object : OnApiResultCallback<Membership> {

            override fun onSuccess(response: Membership) {
                upgradeMembershipLivedData.postValue(response)
            }

            override fun onFail(exception: Exception, code: Int) {
                Timber.e(exception)
            }
        })
    }

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
    }
}
