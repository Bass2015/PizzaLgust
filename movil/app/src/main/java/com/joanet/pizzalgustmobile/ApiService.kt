package com.joanet.pizzalgustmobile


import com.google.gson.JsonObject
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

// This interface defines an API
// service for getting random jokes.
interface ApiService {
    // This annotation specifies that the HTTP method
    // is GET and the endpoint URL is "jokes/random".
    @Headers("Content-Type: application/json")

    @POST("pizzalgust/test")
    fun getUserData (@Body requestBody: DataModel): Call<DataModel>
    // This method returns a Call object with a generic
    // type of DataModel, which represents
    // the data model for the response.
    fun getJokes(): Call<DataModel>
}
