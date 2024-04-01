package com.joanet.pizzalgustmobile

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Actividad principal de la aplicación.
 * Esta actividad maneja el inicio de sesión de los usuarios.
 */
class MainActivity : AppCompatActivity() {

    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnEntrar: Button
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        etEmail = findViewById(R.id.et_login)
        etPassword = findViewById(R.id.et_contraseña)
        btnEntrar = findViewById(R.id.btn_entrar)
        progressBar = findViewById(R.id.idLoadingPB)

        btnEntrar.setOnClickListener {
            val email = etEmail.text.toString().trim()
            val password = etPassword.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Por favor completa todos los campos", Toast.LENGTH_SHORT)
                    .show()
            } else {
                progressBar.visibility = View.VISIBLE
                login(email, password)
            }
        }
    }

    /**
     * Método para iniciar sesión en la aplicación.
     * Realiza una llamada a la API para autenticar al usuario.
     * @param email El correo electrónico del usuario.
     * @param password La contraseña del usuario.
     */
    private fun login(email: String, password: String) {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:5002/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService = retrofit.create(ApiService::class.java)

        val model = LoginDataModel(email, password, "", "", "", "", false, "")
        val call = apiService.getUserData(model)

        call.enqueue(object : Callback<LoginDataModel> {
            override fun onResponse(call: Call<LoginDataModel>, response: Response<LoginDataModel>) {
                if (response.isSuccessful) {
                    val dataModel = response.body()
                    if (dataModel?.token.isNullOrEmpty()) {
                        Toast.makeText(this@MainActivity, "Credenciales incorrectas", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this@MainActivity, "¡Correcto!", Toast.LENGTH_SHORT).show()
                        Log.i("INFO","¡Correcto! Token recibido: ${dataModel?.token}")
                        Log.i("INFO","¡dame nombre: ${dataModel?.first_name}")
                        Log.i("INFO","¡Correcto! isadmin: ${dataModel?.is_admin}")

                        val gson = Gson()
                        val jsonString = gson.toJson(dataModel)
                        Log.d("JSON Response", jsonString)

                        // Verifica si el usuario es un administrador o no
                        if (dataModel?.is_admin == true) {
                            val intent = Intent(this@MainActivity, AdminActivity::class.java)
                            intent.putExtra("authToken", dataModel.token)
                            intent.putExtra("firstName", dataModel.first_name)
                            intent.putExtra("message", dataModel.msg)
                            startActivity(intent)
                        } else {
                            val intent = Intent(this@MainActivity, UserActivity::class.java)
                            intent.putExtra("authToken", dataModel?.token)
                            intent.putExtra("firstName", dataModel?.first_name)
                            intent.putExtra("message", dataModel?.msg)
                            startActivity(intent)
                        }
                        finish()
                    }
                } else {
                    Toast.makeText(this@MainActivity, "Error: ${response.code()}", Toast.LENGTH_SHORT).show()
                }
                progressBar.visibility = View.GONE
            }

            override fun onFailure(call: Call<LoginDataModel>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                progressBar.visibility = View.GONE
            }
        })
    }
}
