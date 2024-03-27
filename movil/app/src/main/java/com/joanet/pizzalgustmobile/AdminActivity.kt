package com.joanet.pizzalgustmobile

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AdminActivity : AppCompatActivity() {

    private lateinit var btn_salir: Button
    private lateinit var tvFirstName: TextView
    private lateinit var tvMessage: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login) // Asegúrate de que este es el layout correcto

        btn_salir = findViewById(R.id.btn_salir)
        tvFirstName = findViewById(R.id.tvFirstName)
        tvMessage = findViewById(R.id.tvMessage)

        val authToken = intent.getStringExtra("authToken")
        val firstName = intent.getStringExtra("firstName")
        val message = intent.getStringExtra("message")

        tvFirstName.text = firstName
        tvMessage.text = message

        btn_salir.setOnClickListener {
            authToken?.let { token ->
                logoutUser(token)
            } ?: run {
                Toast.makeText(this, "Error: Token no encontrado", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun logoutUser(token: String) {
        Log.d("DEBUG", "Iniciando logout con token: $token")

        val retrofit = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:5002/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val logoutService = retrofit.create(ApiLogout::class.java)

        val call = logoutService.logout(LogoutDataModel(token,""))

        call.enqueue(object : Callback<LogoutDataModel> {
            override fun onResponse(
                call: Call<LogoutDataModel>,
                response: Response<LogoutDataModel>
            ) {
                if (response.isSuccessful) {
                    val responseData = response.body()
                    responseData?.let {
                        Toast.makeText(this@AdminActivity, it.msg, Toast.LENGTH_SHORT).show()
                        Log.d("DEBUG", "Logout exitoso: ${it.msg}")
                    }
                    val intent = Intent(this@AdminActivity, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this@AdminActivity, "Error en el logout: ${response.code()}", Toast.LENGTH_SHORT).show()
                    Log.e("ERROR", "Error en el logout: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<LogoutDataModel>, t: Throwable) {
                Toast.makeText(this@AdminActivity, "Fallo en la llamada de logout: ${t.message}", Toast.LENGTH_SHORT).show()
                Log.e("ERROR", "Fallo en la llamada de logout: ${t.message}")
            }
        })
    }

}
