package com.joanet.pizzalgustmobile

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitClient {
    private const val BASE_URL = "http://10.0.2.2:5002/"

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    fun createApiService(): ApiService {
        return retrofit.create(ApiService::class.java)
    }
    fun createLogoutService(): ApiService.ApiLogout {
        return retrofit.create(ApiService.ApiLogout::class.java)
    }
}