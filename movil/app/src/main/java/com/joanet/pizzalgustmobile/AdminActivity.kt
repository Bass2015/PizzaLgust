package com.joanet.pizzalgustmobile

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Actividad que representa la pantalla de administrador.
 */
class AdminActivity : AppCompatActivity() {

    private lateinit var btnSalir1: Button
    private lateinit var tvFirstName1: TextView
    private lateinit var tvMessage1: TextView
    private lateinit var rvUserList: RecyclerView
    private lateinit var btnAdminUserList: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_admin) //

        btnSalir1 = findViewById(R.id.btnSalirAdmin)
        tvFirstName1 = findViewById(R.id.tvFirstNameAdmin)
        tvMessage1 = findViewById(R.id.tvMessageAdmin)
        rvUserList = findViewById(R.id.rvListado)
        btnAdminUserList = findViewById(R.id.btnAdminUsersList)

        rvUserList.layoutManager = LinearLayoutManager(this)
        rvUserList.visibility = View.GONE

        btnAdminUserList.setOnClickListener {

            fetchUsers()
        }


        // Obtiene los datos pasados desde la actividad anterior
        val authToken = intent.getStringExtra("authToken")
        val firstName = intent.getStringExtra("firstName")
        val message = intent.getStringExtra("message")

        // Muestra los datos recibidos en las vistas correspondientes
        tvFirstName1.text = "Nombre :$firstName"
        tvMessage1.text = "Mensaje:$message"


        btnSalir1.setOnClickListener {
            authToken?.let { token ->
                logoutUser(token)
            } ?: run {
                Toast.makeText(this, "Error: Token no encontrado", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun fetchUsers() {
        val sharedPref = getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
        val authToken = sharedPref.getString("authToken", null)
        if (authToken != null) {
            val retrofit = Retrofit.Builder()
                .baseUrl("http://10.0.2.2:5002/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val apiService = retrofit.create(ApiService::class.java)


            val getAllUsersRequest = GetAllUsersRequest(authToken)

            // Realizar la llamada al API
            val call = apiService.getAllUsers(getAllUsersRequest)

            call.enqueue(object : Callback<GetAllUsers> {
                override fun onResponse(call: Call<GetAllUsers>, response: Response<GetAllUsers>) {
                    if (response.isSuccessful) {
                        val users = response.body()?.users ?: emptyList()
                        rvUserList.adapter = UserListAdapter(users)
                        rvUserList.visibility = View.VISIBLE
                    } else {
                        Toast.makeText(
                            this@AdminActivity,
                            "Error: ${response.code()}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<GetAllUsers>, t: Throwable) {
                    Toast.makeText(
                        this@AdminActivity,
                        "Error en la red: ${t.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
        } else {
            Toast.makeText(this, "No se encontró el token de autenticación.", Toast.LENGTH_SHORT).show()
        }
    }



    /**
     * Método para realizar el logout del usuario.
     *
     * @param token El token de autenticación del usuario.
     */
    fun logoutUser(token: String) {
        Log.d("DEBUG", "Iniciando logout con token: $token")

        // Instancia de Retrofit
        val retrofit = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:5002/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        // Servicio de logout usando Retrofit
        val logoutService = retrofit.create(ApiService.ApiLogout::class.java)

        // llamada al servicio de logout
        val call = logoutService.logout(Logout(token, ""))

        //  respuesta de la llamada asíncrona
        call.enqueue(object : Callback<Logout> {
            override fun onResponse(call: Call<Logout>, response: Response<Logout>) {
                if (response.isSuccessful) {
                    val responseData = response.body()
                    responseData?.let {
                        Toast.makeText(this@AdminActivity, it.msg, Toast.LENGTH_SHORT).show()
                        Log.d("DEBUG", "Logout exitoso: ${it.msg}")
                    }
                    // Redirige a la actividad de inicio de sesión
                    val intent = Intent(this@AdminActivity, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    // Muestra un mensaje de error si la respuesta no fue exitosa
                    Toast.makeText(
                        this@AdminActivity,
                        "Error en el logout: ${response.code()}",
                        Toast.LENGTH_SHORT
                    ).show()
                    Log.e("ERROR", "Error en el logout: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<Logout>, t: Throwable) {
                // Muestra un mensaje de error en caso de fallo en la llamada de logout
                Toast.makeText(
                    this@AdminActivity,
                    "Fallo en la llamada de logout: ${t.message}",
                    Toast.LENGTH_SHORT
                ).show()
                Log.e("ERROR", "Fallo en la llamada de logout: ${t.message}")
            }
        })
    }
}
