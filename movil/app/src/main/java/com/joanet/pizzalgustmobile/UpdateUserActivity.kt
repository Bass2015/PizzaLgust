package com.joanet.pizzalgustmobile

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

class UpdateUserActivity : AppCompatActivity() {

    private lateinit var etUpdateUserName: EditText
    private lateinit var etUpdateEmail: EditText
    private lateinit var etUpdateName: EditText
    private lateinit var etUpdateLastName: EditText
    private lateinit var btnSave: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_user)

        etUpdateUserName = findViewById(R.id.etUpdateUserName)
        etUpdateEmail = findViewById(R.id.etUpdateEmail)
        etUpdateName = findViewById(R.id.etUpdateName)
        etUpdateLastName = findViewById(R.id.etUpdateLastName)
        btnSave = findViewById(R.id.btnSave)

        btnSave.setOnClickListener {
            val userName = etUpdateUserName.text.toString().trim()
            val email = etUpdateEmail.text.toString().trim()
            val firstName = etUpdateName.text.toString().trim()
            val lastName = etUpdateLastName.text.toString().trim()

            val authToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjY2MWJmODhmMDdkZDRiYWZkYTEzYzljYiIsImVtYWlsIjoibnNpbXBzb25Ac3ByaW5nZmllbGQuY29tIiwiaWF0IjoxNzEzMTY3MDM0fQ.RRcmUFG2_L_RtQ0HTp09yvGsVvvttC2iW4gPvz7AYeU"
            updateUser(authToken, userName, email, firstName, lastName)
        }
    }

    private fun updateUser(authToken: String, userName: String?, email: String?, firstName: String?, lastName: String?) {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:5002/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService = retrofit.create(ApiService::class.java)

        val updateUser = UpdateUser(authToken, userName, email, firstName, lastName)

        val call = apiService.updateUser(updateUser)

        call.enqueue(object : Callback<UpdateResponse> {
            override fun onResponse(call: Call<UpdateResponse>, response: Response<UpdateResponse>) {
                if (response.isSuccessful) {
                    val updateResponse = response.body()
                    Toast.makeText(this@UpdateUserActivity, updateResponse?.msg ?: "Usuario actualizado con éxito", Toast.LENGTH_SHORT).show()
                } else {
                    val errorBody = response.errorBody()?.string()
                    Toast.makeText(this@UpdateUserActivity, "Error al actualizar usuario: $errorBody", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<UpdateResponse>, t: Throwable) {
                Toast.makeText(this@UpdateUserActivity, "Error en la red: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
