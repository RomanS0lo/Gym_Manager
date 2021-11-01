package com.dts.gym_manager.di

import com.dts.gym_manager.login.LoginViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val app = module {

}

val storage = module {

}

val viewModels = module {
    viewModel { LoginViewModel() }
}
