package com.joanet.pizzalgustmobile

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton

class AllCreateActivity: AppCompatActivity() {

    private lateinit var btnCreatePizza: FloatingActionButton
    private lateinit var btnCreateMassa: FloatingActionButton
    private lateinit var btnCreateIngredient: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_create)

        btnCreatePizza = findViewById(R.id.addPizza3)
        btnCreateMassa = findViewById(R.id.massas)
        btnCreateIngredient = findViewById(R.id.ingredientes)



        btnCreatePizza.setOnClickListener {
            val intent = Intent(this, CreatePizzaActivity::class.java)
            startActivity(intent)
        }
        btnCreateMassa.setOnClickListener {
            val intent = Intent(this, CreateMassesActivity::class.java)
            startActivity(intent)
        }
        btnCreateIngredient.setOnClickListener {
            val intent = Intent(this, CreateIngredientsActivity::class.java)
            startActivity(intent)
        }
    }
}