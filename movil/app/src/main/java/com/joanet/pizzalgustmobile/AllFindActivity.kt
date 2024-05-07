package com.joanet.pizzalgustmobile

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton

class AllFIndActivity: AppCompatActivity() {

    private lateinit var btnFindPizza: FloatingActionButton
    private lateinit var btnFindMasa: FloatingActionButton
    private lateinit var btnFindIngredient: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_find)

        btnFindPizza = findViewById(R.id.findPizza4)
        btnFindMasa = findViewById(R.id.massas4)
        btnFindIngredient = findViewById(R.id.ingredientes4)




        btnFindPizza.setOnClickListener {
            val intent = Intent(this, GetAllPizzasActivity::class.java)
            startActivity(intent)
        }
        btnFindMasa.setOnClickListener {
            val intent = Intent(this, GetAllMassesActivity::class.java)
            startActivity(intent)

        }
        btnFindIngredient.setOnClickListener {
            val intent = Intent(this, GetAllIngredientsActivity::class.java)
            startActivity(intent)
        }

    }
}