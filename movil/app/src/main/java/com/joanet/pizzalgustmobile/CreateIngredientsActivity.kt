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

class CreateIngredientsActivity : AppCompatActivity() {
    private lateinit var etIngredientName: EditText
    private lateinit var etIngredientDescription: EditText
    private lateinit var btnAddIngredient: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_ingredients)

        etIngredientName = findViewById(R.id.etIngredientName)
        etIngredientDescription = findViewById(R.id.etIngredientDescription)
        btnAddIngredient = findViewById(R.id.addIngredient)

        btnAddIngredient.setOnClickListener {
            val ingredientName = etIngredientName.text.toString().trim()
            val description = etIngredientDescription.text.toString().trim()

            if (ingredientName.isNotEmpty() && description.isNotEmpty()) {
                val sharedPref = getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
                val authToken = sharedPref.getString("authToken", null)
                if (authToken != null) {
                    createIngredient(authToken, ingredientName, description)
                } else {
                    Toast.makeText(this, "Por favor, inicie sesión primero.", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Todos los campos son obligatorios .", Toast.LENGTH_SHORT).show()
            }
        }

        }

    private fun createIngredient(token: String, ingredientName: String, description: String) {
        val createIngredientRequest = CreateIngredient(token, ingredientName, description )
        val call = RetrofitClient.createApiService().createIngredient(createIngredientRequest)

        call.enqueue(object : Callback<ResponseCreateIngredient> {
            override fun onResponse(call: Call<ResponseCreateIngredient>, response: Response<ResponseCreateIngredient>) {
                if (response.isSuccessful) {
                    val createResponse = response.body()
                    Toast.makeText(
                        this@CreateIngredientsActivity,
                        createResponse?.msg ?: "Ingrediente creado con éxito",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    val errorBody = response.errorBody()?.string()
                    Toast.makeText(
                        this@CreateIngredientsActivity,
                        "Error al crear el ingrediente: $errorBody",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<ResponseCreateIngredient>, t: Throwable) {
                Toast.makeText(
                    this@CreateIngredientsActivity,
                    "Error en la red: ${t.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }
}