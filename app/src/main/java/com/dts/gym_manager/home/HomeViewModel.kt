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
import timber.log.Timber

class HomeViewModel(private val apiService: RestApiService, private val app: Application) :
    ViewModel() {

    private var userInfoLiveData = MutableLiveData<User>()

    private val onFailLiveData = MutableLiveData<String>()

    private var userMembershipLiveData = MutableLiveData<Membership>()

    fun onUserInfoResult(): LiveData<User> = userInfoLiveData

    fun onFailResult(): LiveData<String> = onFailLiveData

    fun onMembershipResult(): LiveData<Membership> = userMembershipLiveData

    fun setupHomeData() {
        apiService.getCurrentMembership(object : OnApiResultCallback<Membership> {
            override fun onSuccess(response: Membership) {
                userMembershipLiveData.postValue(response)
            }

            override fun onFail(exception: Exception) {
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

            override fun onFail(exception: Exception) {
                Timber.e(exception)
                when (exception) {
                    is UserDataIsNullException -> onFailLiveData.postValue(app.getString(R.string.message_user_data_null))

                }
            }
        })
    }
}
