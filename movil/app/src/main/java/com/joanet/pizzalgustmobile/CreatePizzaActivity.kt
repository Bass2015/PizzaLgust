package com.joanet.pizzalgustmobile

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.CheckBox
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CreatePizzaActivity : AppCompatActivity() {

    private lateinit var etPizzaName: EditText
    private lateinit var etPrice: EditText
    private lateinit var etDescription: EditText
    private lateinit var btnAddPizza: FloatingActionButton
    private lateinit var spinnerMasas: Spinner
    private lateinit var ingredientLayout: LinearLayout
    private var selectedIngredients: MutableList<String> = mutableListOf()
    private lateinit var selectedMasaId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_pizzas)

        etPizzaName = findViewById(R.id.etPizzaName)
        etPrice = findViewById(R.id.et_Price)
        etDescription = findViewById(R.id.etDescription)
        btnAddPizza = findViewById(R.id.addPizza1)
        spinnerMasas = findViewById(R.id.spinnerMasas)
        ingredientLayout = findViewById(R.id.ingredientLayout)

        btnAddPizza.setOnClickListener {
            val pizzaName = etPizzaName.text.toString().trim()
            val price = etPrice.text.toString().toFloatOrNull()
            val description = etDescription.text.toString().trim()

            if (pizzaName.isNotEmpty() && price != null && description.isNotEmpty()) {
                val sharedPref = getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
                val authToken = sharedPref.getString("authToken", null)
                if (authToken != null) {
                    createPizza(authToken, pizzaName, price, description, selectedMasaId, selectedIngredients)
                } else {
                    Toast.makeText(this, "Por favor, inicie sesión primero.", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Todos los campos son obligatorios y el precio debe ser un número.", Toast.LENGTH_SHORT).show()
            }
        }
        fillSpinnerWithMasas()
        fetchIngredientsForSpinner()
    }

    private fun fetchIngredientsForSpinner() {
        val sharedPref = getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
        val authToken = sharedPref.getString("authToken", null)
        if (authToken != null) {
            val apiService = RetrofitClient.createApiService()
            val call = apiService.getAllIngredients(GetAllIngredientsRequest(authToken))

            call.enqueue(object : Callback<GetAllIngredients> {
                override fun onResponse(call: Call<GetAllIngredients>, response: Response<GetAllIngredients>) {
                    if (response.isSuccessful) {
                        val ingredients = response.body()?.ingredients ?: emptyList()
                        setupIngredientLayout(ingredients)
                    } else {
                        Toast.makeText(this@CreatePizzaActivity, "Error: ${response.code()}", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<GetAllIngredients>, t: Throwable) {
                    Toast.makeText(this@CreatePizzaActivity, "Error en la red: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
        } else {
            Toast.makeText(this, "No se encontró el token de autenticación.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupIngredientLayout(ingredients: List<Ingredient>) {
        for (ingredient in ingredients) {
            val ingredientId = ingredient._id
            val checkBox = CheckBox(this@CreatePizzaActivity)
            checkBox.text = ingredient.name
            checkBox.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    if (!selectedIngredients.contains(ingredientId)) {
                        selectedIngredients.add(ingredientId)
                    }
                } else {
                    selectedIngredients.remove(ingredientId)
                }
            }
            ingredientLayout.addView(checkBox)
        }
    }

    private fun fillSpinnerWithMasas() {
        val sharedPref = getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
        val authToken = sharedPref.getString("authToken", null)

        if (authToken != null) {
            val apiService = RetrofitClient.createApiService()
            val call = apiService.getAllMasses(GetAllMassesRequest(authToken))

            call.enqueue(object : Callback<GetAllMasses> {
                override fun onResponse(call: Call<GetAllMasses>, response: Response<GetAllMasses>) {
                    if (response.isSuccessful) {
                        val masasList = response.body()?.masas?.map { it.name } ?: emptyList()
                        val adapter = ArrayAdapter(this@CreatePizzaActivity, android.R.layout.simple_spinner_item, masasList)
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        spinnerMasas.adapter = adapter
                        spinnerMasas.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                            override fun onItemSelected(parentView: AdapterView<*>, selectedItemView: View, position: Int, id: Long) {
                                // Obtener el ID de la masa seleccionada
                                selectedMasaId = response.body()?.masas?.get(position)?._id ?: ""
                            }

                            override fun onNothingSelected(parentView: AdapterView<*>) {
                                // No hacer nada
                            }
                        }
                    } else {
                        Toast.makeText(this@CreatePizzaActivity, "Error al obtener las masas: ${response.code()}", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<GetAllMasses>, t: Throwable) {
                    Toast.makeText(this@CreatePizzaActivity, "Error en la red: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
        } else {
            Toast.makeText(this, "No se encontró el token de autenticación.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun createPizza(token: String, name: String, price: Float, description: String, masaId: String, ingredientIds: List<String>) {
        val createPizzaRequest = CreatePizza(token, name, price, masaId, ingredientIds, description)
        val call = RetrofitClient.createApiService().createPizza(createPizzaRequest)

        call.enqueue(object : Callback<ResponseCreatePizza> {
            override fun onResponse(call: Call<ResponseCreatePizza>, response: Response<ResponseCreatePizza>) {
                if (response.isSuccessful) {
                    val createResponse = response.body()
                    createResponse?.let { response ->
                        val pizzaId = response.pizza_id
                        Toast.makeText(
                            this@CreatePizzaActivity,
                            "Pizza creada con éxito, ID: $pizzaId",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    val errorBody = response.errorBody()?.string()
                    Toast.makeText(
                        this@CreatePizzaActivity,
                        "Error al crear pizza: $errorBody",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<ResponseCreatePizza>, t: Throwable) {
                Toast.makeText(
                    this@CreatePizzaActivity,
                    "Error en la red: ${t.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }
}
