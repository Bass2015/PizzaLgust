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

class CreateMassesActivity: AppCompatActivity() {

    private lateinit var etMasaName: EditText
    private lateinit var btnAddMasa: FloatingActionButton
    private lateinit var etMasaDescription: EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_massas)

        etMasaName = findViewById(R.id.etMasaName)
        etMasaDescription = findViewById(R.id.etMasaDescription)
        btnAddMasa = findViewById(R.id.addMasa)

        btnAddMasa.setOnClickListener {
            val masaName = etMasaName.text.toString().trim()
            val description = etMasaDescription.text.toString().trim()

            if (masaName.isNotEmpty() && description.isNotEmpty()) {
                val sharedPref = getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
                val authToken = sharedPref.getString("authToken", null)
                if (authToken != null) {
                    createMasa(authToken, masaName, description)
                } else {
                    Toast.makeText(this, "Por favor, inicie sesión primero.", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Todos los campos son obligatorios .", Toast.LENGTH_SHORT).show()
            }
        }
}

    private fun createMasa(token: String, masaName: String, description: String) {

        val createMasaRequest = CreateMasa(token, masaName, description )
        val call = RetrofitClient.createApiService().createMasa(createMasaRequest)

        call.enqueue(object : Callback<ResponseCreateMasa> {
            override fun onResponse(call: Call<ResponseCreateMasa>, response: Response<ResponseCreateMasa>) {
                if (response.isSuccessful) {
                    val createResponse = response.body()
                    Toast.makeText(
                        this@CreateMassesActivity,
                        createResponse?.msg ?: "Masa creada con éxito",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    val errorBody = response.errorBody()?.string()
                    Toast.makeText(
                        this@CreateMassesActivity,
                        "Error al crear la masa: $errorBody",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<ResponseCreateMasa>, t: Throwable) {
                Toast.makeText(
                    this@CreateMassesActivity,
                    "Error en la red: ${t.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }
}

