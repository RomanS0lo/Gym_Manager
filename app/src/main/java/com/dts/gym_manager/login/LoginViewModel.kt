package com.dts.gym_manager.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dts.gym_manager.data.PrefsRepository
import com.dts.gym_manager.domain.OnApiResultCallback
import com.dts.gym_manager.domain.retrofit.RestApiService
import com.dts.gym_manager.model.RegistrationInfo
import com.dts.gym_manager.model.Token
import timber.log.Timber

class LoginViewModel(private val apiService: RestApiService, private val prefs: PrefsRepository) :
    ViewModel() {

    private val loginResultLiveData = MutableLiveData<Boolean>()

    fun onResultLogin(): LiveData<Boolean> = loginResultLiveData

    fun login(email: String, password: String) {
        apiService.login(email, password, onResult = object : OnApiResultCallback<RegistrationInfo> {
            override fun onSuccess(response: RegistrationInfo) {
                prefs.isUserLoggedIn = true
                loginResultLiveData.postValue(true)
            }

            override fun onFail(exception: Exception, code: Int) {
                Timber.e(exception)
                loginResultLiveData.postValue(false)
            }
        })
    }

    fun isUserLogged(): Boolean = prefs.isUserLoggedIn
}
