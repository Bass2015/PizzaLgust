package com.joanet.pizzalgustmobile

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

/**
 * Adaptador para la lista de Ingredientes.
 * Este adaptador se encarga de mostrar la lista de ingredientes en un RecyclerView.
 *
 * @property ingredientes La lista de ingredientes a mostrar.
 */
class IngredientListAdapter(private val ingredientes: List<Ingredient>) : RecyclerView.Adapter<IngredientListAdapter.IngredientViewHolder>() {

    /**
     * Crea una nueva instancia de IngredientViewHolder.
     *
     * @param parent El ViewGroup padre en el que se añadirá la nueva vista.
     * @param viewType El tipo de vista de la nueva vista.
     * @return Una nueva instancia de IngredientViewHolder.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_ingredient, parent, false)
        return IngredientViewHolder(view)
    }

    /**
     * Enlaza los datos del ingrediente en la posición especificada con la vista correspondiente.
     *
     * @param holder El IngredientViewHolder que representa la vista del ingrediente.
     * @param position La posición del ingrediente en la lista.
     */
    override fun onBindViewHolder(holder: IngredientViewHolder, position: Int) {
        val ingredient = ingredientes[position]
        holder.bind(ingredient)
    }

    /**

     * Obtiene el número total de ingredientes en la lista.
     *
     * @return El número total de ingredientes.
     */
    override fun getItemCount(): Int = ingredientes.size

    /**
     * Clase que representa la vista de cada elemento de ingrediente en el RecyclerView.
     *
     * @property itemView La vista que representa el elemento del ingrediente.
     */
    class IngredientViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameTextView: TextView = itemView.findViewById(R.id.tvIngredientName)
        private val descriptionTextView: TextView = itemView.findViewById(R.id.tvIngredientDescription)
        private val idTextView: TextView = itemView.findViewById(R.id.tvIngredientId)


        fun bind(ingredient: Ingredient) {
            nameTextView.text = "Nombre: \n${ingredient.name}"
            descriptionTextView.text = "Descripción: \n${ingredient.description}"
            idTextView.text = "Id: \n${ingredient._id}"
        }
    }
}

