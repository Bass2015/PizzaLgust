package com.joanet.pizzalgustmobile

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiLogout {

    @Headers("Content-type: application/json")
    @POST("pizzalgust/logout")

    fun logout(@Body requestBody: LogoutDataModel): Call<LogoutDataModel>

}