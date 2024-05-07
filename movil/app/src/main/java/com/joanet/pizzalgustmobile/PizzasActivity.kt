package com.joanet.pizzalgustmobile

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton

/**
 * Actividad principal que muestra las opciones para agregar una nueva pizza o ver la lista de pizzas.
 */
class PizzasActivity : AppCompatActivity() {

    private lateinit var btnCreate: FloatingActionButton
    private lateinit var btnListPizza: FloatingActionButton
    private lateinit var btnDeletePizza: FloatingActionButton
    private lateinit var btnUpdatePizza: FloatingActionButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pizzas)

        btnCreate = findViewById(R.id.create)
        btnListPizza = findViewById(R.id.find)
        btnDeletePizza = findViewById(R.id.delete_Pizzas)
        btnUpdatePizza = findViewById(R.id.ModifyPizzas)

        btnCreate.setOnClickListener {
            val intent = Intent(this@PizzasActivity, AllCreateActivity::class.java)
            startActivity(intent)
        }
        btnListPizza.setOnClickListener{
            val intent = Intent(this@PizzasActivity,AllFIndActivity::class.java)
            startActivity(intent)
        }
        btnDeletePizza.setOnClickListener{
            val intent = Intent(this@PizzasActivity,DeletePizzasActivity::class.java)
            startActivity(intent)
        }
        btnUpdatePizza.setOnClickListener{
            val intent = Intent(this@PizzasActivity,UpdatePizzasActivity::class.java)
            startActivity(intent)
        }
    }

}

