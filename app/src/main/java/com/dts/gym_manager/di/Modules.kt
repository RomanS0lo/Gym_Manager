package com.dts.gym_manager.di

import com.dts.gym_manager.domain.ApiService
import com.dts.gym_manager.domain.retrofit.RestApiService
import com.dts.gym_manager.home.HomeViewModel
import com.dts.gym_manager.login.LoginViewModel
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val app = module {
    single { GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").create() }
    single { OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .build()
    }
    single {
        Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080")
            .addConverterFactory(GsonConverterFactory.create(get()))
            .client(get())
            .build()
    }
    single<ApiService> { get<Retrofit>().create(ApiService::class.java) }
    single { RestApiService(get(), get()) }
}

val storage = module {

}

val viewModels = module {
    viewModel { LoginViewModel(get()) }
    viewModel { HomeViewModel() }
}
