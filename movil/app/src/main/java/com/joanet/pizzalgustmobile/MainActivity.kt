package com.joanet.pizzalgustmobile

import android.graphics.ColorSpace.Model
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.google.gson.Gson
import com.google.gson.JsonObject

import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.joanet.pizzalgustmobile.DataModel




class MainActivity : AppCompatActivity() {

    private lateinit var btn_jokes: Button
    private lateinit var tv_jokes: TextView
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Find the button view by its ID and
        // assign it to a variable.
        btn_jokes = findViewById(R.id.btn_joke)

        // Find the text view by its ID and
        // assign it to a variable.
        tv_jokes = findViewById(R.id.tv_joke)

        // Find the progress bar and assign
        // it to the varriable.
        progressBar = findViewById(R.id.idLoadingPB)

        // Set an OnClickListener on the button view.
        // Dentro de tu función onClickListener
        btn_jokes.setOnClickListener {
            // Mostrar la barra de progreso
            progressBar.visibility = View.VISIBLE

            // Crear un objeto JSON
            val jsonObject = JsonObject()


            // Convertir el objeto JSON a una cadena utilizando Gson
            val jsonString = Gson().toJson(jsonObject)


            // Llamar al método getJokes() de la clase ApiCall,
            // pasando una función de devolución de llamada como parámetro.
            val retrofit = Retrofit.Builder()
                .baseUrl("http://10.0.2.2:5002/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val apiService = retrofit.create(ApiService::class.java)

            //construyendo la petición --> /test

            val model = DataModel("Joan", "")
            val call = apiService.getUserData(model)

            call.enqueue(object : Callback<DataModel> {
                override fun onResponse(call: Call<DataModel>, response: Response<DataModel>) {
                    if (response.isSuccessful) {
                        val dataModel = response.body()
                        tv_jokes.text = dataModel?.msg ?: "No se encontraron Nombres"

                        Log.d("API_CALL1", "Respuesta buena: $dataModel")
                    } else {
                        Log.e("API_CALL", "Error: ${response.code()}")
                        tv_jokes.text = "Error: ${response.code()}"

                    }
                    progressBar.visibility = View.GONE
                }

                override fun onFailure(call: Call<DataModel>, t: Throwable) {
                    Log.e("API_CALL", "Error: ${t.message}", t)
                    tv_jokes.text = "Request Fail"
                    progressBar.visibility = View.GONE
                }
            })
        }
    }
}

