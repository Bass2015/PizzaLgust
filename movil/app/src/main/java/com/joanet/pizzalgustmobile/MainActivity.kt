package com.joanet.pizzalgustmobile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import com.google.gson.Gson
import com.google.gson.JsonObject
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory




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
            apiService.getJokes().enqueue(object : Callback<DataModel> {
                override fun onResponse(call: Call<DataModel>, response: Response<DataModel>) {
                    // Establecer el texto del TextView con el
                    // valor de la broma devuelta por la respuesta de la API.
                    tv_jokes.setText(response.body()?.nombre ?: "No se encontraron Nombres")


                    // Ocultar la barra de progreso
                    progressBar.visibility = View.GONE
                }

                override fun onFailure(call: Call<DataModel>, t: Throwable) {
                    // Manejar la falla de la solicitud aquí
                    Log.e("API_CALL", "Error: ${t.message}")
                    // Ocultar la barra de progress
                    progressBar.visibility = View.GONE
                }
            })
        }
    }
}
