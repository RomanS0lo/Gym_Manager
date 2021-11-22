package com.dts.gym_manager.sign_up

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dts.gym_manager.domain.OnApiResultCallback
import com.dts.gym_manager.domain.retrofit.RestApiService
import com.dts.gym_manager.model.RegistrationInfo
import com.dts.gym_manager.model.User
import timber.log.Timber

class SignUpViewModel(private val apiService: RestApiService) : ViewModel() {

    private val loginResultLiveData = MutableLiveData<Boolean>()
    private val userSexLiveData = MutableLiveData<String>()

    private var sex: User.Sex? = null

    private val userSexVariants = arrayListOf(User.Sex.MALE, User.Sex.FEMALE)

    fun onResultRegister(): LiveData<Boolean> = loginResultLiveData

    fun onUserSexUpdate(): LiveData<String> = userSexLiveData

    fun register(
        firstName: String,
        lastName: String,
        age: Int,
        sex: String,
        login: String,
        password: String
    ) {
        apiService.register(
            firstName,
            lastName,
            age,
            User.Sex.valueOf(sex),
            login,
            password,
            object : OnApiResultCallback<RegistrationInfo> {

                override fun onSuccess(response: RegistrationInfo) {
                    loginResultLiveData.postValue(true)
                    Timber.d("Register Success $response")
                }

                override fun onFail(exception: Exception, code: Int) {
                    Timber.e(exception)
                    loginResultLiveData.postValue(false)
                }
            })
    }

    fun selectedSex(index: Int, selectedSexName: String) {
        val sex = userSexVariants[index]
        this.sex = sex
        userSexLiveData.postValue(selectedSexName)
    }
}
