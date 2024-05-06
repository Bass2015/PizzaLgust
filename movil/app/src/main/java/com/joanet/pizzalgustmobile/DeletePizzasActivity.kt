package com.joanet.pizzalgustmobile

import android.content.Context
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DeletePizzasActivity : AppCompatActivity() {

    private lateinit var btnDelete: FloatingActionButton
    private lateinit var etPizzaId: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delete_pizzas)

        btnDelete = findViewById(R.id.delete_Pizzas2)
        etPizzaId = findViewById(R.id.etPizzaId)

        btnDelete.setOnClickListener {
            val pizzaId = etPizzaId.text.toString()
            if (pizzaId.isNotEmpty()) {
                deletePizza(pizzaId)
            } else {
                Toast.makeText(this, "por favor, ingresa el ID de la pizza", Toast.LENGTH_SHORT)
                    .show()
            }
        }

    }

    private fun deletePizza(pizzaId: String) {
        val sharedPref = getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
        val authToken = sharedPref.getString("authToken", null)
        if (authToken != null) {
            val apiService = RetrofitClient.createApiService()

            val deletePizzaRequest = DeletePizza(authToken, pizzaId)

            // Realizar la llamada al API
            val call = apiService.deletePizza(deletePizzaRequest)
            call.enqueue(object : Callback<DeletePizzaResponse> {
                override fun onResponse(
                    call: Call<DeletePizzaResponse>,
                    response: Response<DeletePizzaResponse>
                ) {
                    if (response.isSuccessful) {
                        Toast.makeText(
                            this@DeletePizzasActivity,
                            "Pizza borrada con éxito.",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        Toast.makeText(
                            this@DeletePizzasActivity,
                            "Error: ${response.code()}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<DeletePizzaResponse>, t: Throwable) {
                    Toast.makeText(
                        this@DeletePizzasActivity,
                        "Error en la red: ${t.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
        } else {
            Toast.makeText(
                this,
                "No se encontró el token de autenticación.",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

}




