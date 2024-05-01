package com.joanet.pizzalgustmobile

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton

class PizzasActivity : AppCompatActivity() {

    private lateinit var btnAddPizza: FloatingActionButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pizzas)

        btnAddPizza = findViewById(R.id.addPizza)

        btnAddPizza.setOnClickListener {
            val intent = Intent(this@PizzasActivity, CreatePizzaActivity::class.java)
            startActivity(intent)
        }
    }

}

