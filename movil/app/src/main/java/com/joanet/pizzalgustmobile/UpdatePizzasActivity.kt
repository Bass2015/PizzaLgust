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
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class UpdatePizzasActivity : AppCompatActivity() {
    private lateinit var etUpdatePizzaName: EditText
    private lateinit var etUpdatePrice: EditText
    private lateinit var etUpdateDescription: EditText
    private lateinit var etPizzaId: EditText
    private lateinit var btnSave2: FloatingActionButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_pizza)

        etUpdatePizzaName = findViewById(R.id.etUpdatePizzaName)
        etUpdatePrice = findViewById(R.id.EtPriceUpdate)
        etUpdateDescription = findViewById(R.id.etDescriptionUpdate)
        etPizzaId = findViewById(R.id.EtUpdatePizzaId)
        btnSave2 = findViewById(R.id.btnSave2)

        btnSave2.setOnClickListener {
            val pizzaName = etUpdatePizzaName.text.toString().trim().takeIf { it.isNotEmpty() }
            val pizzaPrice = etUpdatePrice.text.toString().trim().takeIf { it.isNotEmpty() }
            val description = etUpdateDescription.text.toString().trim().takeIf { it.isNotEmpty() }
            val pizzaId = etPizzaId.text.toString().trim().takeIf { it.isNotEmpty() }


            val sharedPref = getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
            val authToken = sharedPref.getString("authToken", null)
            if (authToken != null) {
                updatePizza(authToken, pizzaId, pizzaName, pizzaPrice, description)
            } else {
                Toast.makeText(
                    this,
                    "No se encontró el token de autenticación.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
    private fun updatePizza(
        authToken: String,
        pizzaId: String?,
        pizzaName: String?,
        pizzaPrice: String?,
        description: String?
    ) {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:5002/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService = retrofit.create(ApiService::class.java)

        val fieldsToUpdate = mutableMapOf<String, String?>().apply {
            pizzaName?.let { put("pizza_name", it) }
            pizzaPrice?.let { put("price", it.toString()) }
            pizzaId?.let { put("PizzaId", it) }
            description?.let { put("Description", it) }
    }
        if (fieldsToUpdate.isNotEmpty() && pizzaId != null) {
            val updatePizzas = UpdatePizza(
                authToken,
                pizzaId,
                fieldsToUpdate["pizza_name"],
                fieldsToUpdate["price"],
                fieldsToUpdate["Description"]
            )
            val call = apiService.updatePizza(updatePizzas)
            call.enqueue(object : Callback<UpdatePizzaResponse> {
                override fun onResponse(
                    call: Call<UpdatePizzaResponse>,
                    response: Response<UpdatePizzaResponse>
                ) {
                    if (response.isSuccessful) {
                        val updateResponse = response.body()
                        Toast.makeText(
                            this@UpdatePizzasActivity,
                            updateResponse?.msg ?: "Pizza actualizada con éxito",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        val errorBody = response.errorBody()?.string()
                        Toast.makeText(
                            this@UpdatePizzasActivity,
                            "Error al actualizar la pizza: $errorBody",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<UpdatePizzaResponse>, t: Throwable) {
                    Toast.makeText(
                        this@UpdatePizzasActivity,
                        "Error en la red: ${t.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
        } else {
            Toast.makeText(
                this@UpdatePizzasActivity,
                "No hay cambios para actualizar o falta el ID de la pizza",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}





