package com.joanet.pizzalgustmobile

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Actividad para obtener y mostrar la lista de ingredientes disponibles en la aplicación.
 */

class GetAllIngredientsActivity :  AppCompatActivity() {

    private lateinit var rvIngredientList: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ingredient_list)

        rvIngredientList = findViewById(R.id.rv_ingredientList)
        rvIngredientList.layoutManager = LinearLayoutManager(this)

        fetchIngredients()
    }

    /**
     * Método privado para obtener la lista de ingredientes del servidor y mostrarlas en el RecyclerView.
     */
    private fun fetchIngredients() {
        val sharedPref = getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
        val authToken = sharedPref.getString("authToken", null)
        if (authToken != null) {
            val apiService = RetrofitClient.createApiService()
            val call = apiService.getAllIngredients(GetAllIngredientsRequest(authToken))

            call.enqueue(object : Callback<GetAllIngredients> {
                override fun onResponse(call: Call<GetAllIngredients>, response: Response<GetAllIngredients>) {
                    if (response.isSuccessful) {
                        val ingredients = response.body()?.ingredients ?: emptyList()
                        println("Ingredients received: $ingredients") // Imprime la lista de ingredientes en la consola
                        rvIngredientList.adapter = IngredientListAdapter(ingredients)
                    } else {
                        Toast.makeText(this@GetAllIngredientsActivity, "Error: ${response.code()}", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<GetAllIngredients>, t: Throwable) {
                    Toast.makeText(this@GetAllIngredientsActivity, "Error en la red: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
        } else {
            Toast.makeText(this, "No se encontró el token de autenticación.", Toast.LENGTH_SHORT).show()
        }
    }


}









