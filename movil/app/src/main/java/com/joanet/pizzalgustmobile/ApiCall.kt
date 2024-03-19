package com.joanet.pizzalgustmobile



import android.content.Context
import android.provider.ContactsContract.Data
import android.widget.Toast
import com.google.gson.JsonObject
import okhttp3.RequestBody
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body

class ApiCall {
    // This function takes a Context and callback function
    // as a parameter, which will be called
    // when the API response is received.
    fun getjokes(context: Context, callback: (DataModel) -> Unit) {

        // Create a Retrofit instance with the base URL and
        // a GsonConverterFactory for parsing the response.
        val retrofit: Retrofit = Retrofit.Builder().baseUrl("http://10.0.2.2:5002/").addConverterFactory(
            GsonConverterFactory.create()).build()

        // Create an ApiService instance from the Retrofit instance.
        val service: ApiService = retrofit.create<ApiService>(ApiService::class.java)

        // Call the getjokes() method of the ApiService
        // to make an API request.
        val requestBody = RequestBody.create(null,"")
        val call: Call<DataModel> = service.getJokes()

        // Use the enqueue() method of the Call object to
        // make an asynchronous API request.
        call.enqueue(object : Callback<DataModel> {
            // This is an anonymous inner class that implements the Callback interface.
            override fun onResponse(call: Call<DataModel>, response: Response<DataModel>) {
                // This method is called when the API response is received successfully.

                if(response.isSuccessful){
                    // If the response is successful, parse the
                    // response body to a DataModel object.
                    val jokes: DataModel = response.body()!!

                    // Call the callback function with the DataModel
                    // object as a parameter.
                    callback(jokes)
                }
            }

            override fun onFailure(call: Call<DataModel>, t: Throwable) {
                // This method is called when the API request fails.
                Toast.makeText(context, "Request Fail", Toast.LENGTH_SHORT).show()
            }
        })


        fun onFailure(t: Throwable?) {
                // This method is called when the API request fails.
                Toast.makeText(context, "Request Fail", Toast.LENGTH_SHORT).show()
            }
        }
    }

