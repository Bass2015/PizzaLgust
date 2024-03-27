package com.joanet.pizzalgustmobile


import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

// This interface defines an API

interface ApiService {
    // This annotation specifies that the HTTP method
    //
    @Headers("Content-Type: application/json")

    @POST("pizzalgust/login")
    fun getUserData(@Body requestBody: LoginDataModel): Call<LoginDataModel>

    // This method returns a Call object with a generic
    // type of DataModel, which represents
    // the data model for the response.
    fun getUsers(): Call<LoginDataModel>
}
