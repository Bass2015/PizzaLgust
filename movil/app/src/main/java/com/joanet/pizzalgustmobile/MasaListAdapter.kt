package com.joanet.pizzalgustmobile

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

/**
 * Adaptador para la lista de masas.
 * Este adaptador se encarga de mostrar la lista de masas en un RecyclerView.
 *
 * @property masas La lista de masas a mostrar.
 */
class MasaListAdapter(private val masas: List<Masa>) : RecyclerView.Adapter<MasaListAdapter.MasaViewHolder>() {

    /**
     * Crea una nueva instancia de MasaViewHolder.
     *
     * @param parent El ViewGroup padre en el que se añadirá la nueva vista.
     * @param viewType El tipo de vista de la nueva vista.
     * @return Una nueva instancia de MasaViewHolder.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MasaViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_masa, parent, false)
        return MasaViewHolder(view)
    }

    /**
     * Enlaza los datos de la masa en la posición especificada con la vista correspondiente.
     *
     * @param holder El MasaViewHolder que representa la vista de la masa.
     * @param position La posición de la masa en la lista.
     */
    override fun onBindViewHolder(holder: MasaViewHolder, position: Int) {
        val masa = masas[position]
        holder.bind(masa)
    }

    /**

     * Obtiene el número total de masas en la lista.
     *
     * @return El número total de masas.
     */
    override fun getItemCount(): Int = masas.size

    /**
     * Clase que representa la vista de cada elemento de masa en el RecyclerView.
     *
     * @property itemView La vista que representa el elemento de la masa.
     */
    class MasaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameTextView: TextView = itemView.findViewById(R.id.tvMasaName)
        private val descriptionTextView: TextView = itemView.findViewById(R.id.tvMasaDescription)
        private val idTextView: TextView = itemView.findViewById(R.id.tvMasaId)


        fun bind(masa: Masa) {
            nameTextView.text = "Nombre: \n${masa.name}"
            descriptionTextView.text = "Descripción: \n${masa.description}"
            idTextView.text = "Id: \n${masa._id}"
        }

    }
}

