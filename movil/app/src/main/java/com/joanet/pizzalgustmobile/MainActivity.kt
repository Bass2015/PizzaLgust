package com.joanet.pizzalgustmobile

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.joanet.pizzalgustmobile.R.*
import com.joanet.pizzalgustmobile.api.ApiClient
import com.joanet.pizzalgustmobile.api.ApiService
import com.joanet.pizzalgustmobile.api.Post
import com.joanet.pizzalgustmobile.api.TestRequestBody
import kotlinx.coroutines.flow.internal.NoOpContinuation.context
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.coroutines.jvm.internal.CompletedContinuation.context

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        val button = findViewById<AppCompatButton>(id.btn_api)
        button.setOnClickListener {
            fun main() {
                val retrofit = Retrofit.Builder()
                    .baseUrl("http://localhost:5002/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()

                val apiService = retrofit.create(ApiService::class.java)

                val requestBody = TestRequestBody("Raul")
                val contentType = "application/json"

                val call = apiService.test(contentType, requestBody)

                call.enqueue(object : Callback<TestRequestBody> {
                    // This is an anonymous inner class that implements the Callback interface.
                    override fun onResponse(response: Response<TestRequestBody>?, retrofit: Retrofit?) {
                        // This method is called when the API response is received successfully.

                        if(response!!.isSuccess){
                            // If the response is successful, parse the
                            // response body to a DataModel object.
                            val jokes: TestRequestBody = response.body() as TestRequestBody

                            // Call the callback function with the DataModel
                            // object as a parameter.
                            callback(jokes)
                        }
                    }

                    override fun onFailure(call: Call<TestRequestBody>, t: Throwable) {
                        // This method is called when the API request fails.
                        println("fallo")
                    }


            }
        }
    }
}

