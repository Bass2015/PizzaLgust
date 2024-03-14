package com.joanet.pizzalgustmobile.api

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("pizzalgust/test")
    fun test(
        @Header("Content-Type") contentType: String,
        @Body body: TestRequestBody
    ): Call<Any>
}

data class TestRequestBody(val nombre: String)


