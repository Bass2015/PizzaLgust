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

class GetAllMassesActivity :  AppCompatActivity() {

    private lateinit var rvMasesList: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_masses_list)

        rvMasesList = findViewById(R.id.rv_massesList)
        rvMasesList.layoutManager = LinearLayoutManager(this)

        fetchMasses()
    }

    /**
     * Método privado para obtener la lista de ingredientes del servidor y mostrarlas en el RecyclerView.
     */
    private fun fetchMasses() {
        val sharedPref = getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
        val authToken = sharedPref.getString("authToken", null)
        if (authToken != null) {
            val apiService = RetrofitClient.createApiService()
            val call = apiService.getAllMasses(GetAllMassesRequest(authToken))

            call.enqueue(object : Callback<GetAllMasses> {
                override fun onResponse(call: Call<GetAllMasses>, response: Response<GetAllMasses>) {
                    if (response.isSuccessful) {
                        val masas = response.body()?.masas ?: emptyList()
                        println("masas recibidas: $masas") // Imprime la lista de masas en la consola
                        rvMasesList.adapter = MasaListAdapter(masas)
                    } else {
                        Toast.makeText(this@GetAllMassesActivity, "Error: ${response.code()}", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<GetAllMasses>, t: Throwable) {
                    Toast.makeText(this@GetAllMassesActivity, "Error en la red: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
        } else {
            Toast.makeText(this, "No se encontró el token de autenticación.", Toast.LENGTH_SHORT).show()
        }
    }


}









