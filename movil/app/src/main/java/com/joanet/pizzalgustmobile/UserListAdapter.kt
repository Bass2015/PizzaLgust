package com.joanet.pizzalgustmobile

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class UserListAdapter(private val users: List<User>) : RecyclerView.Adapter<UserListAdapter.UserViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = users[position]
        holder.itemView.findViewById<TextView>(R.id.tv_UserIDRV).text = "User ID : \n${user._id}"
        holder.itemView.findViewById<TextView>(R.id.tv_EmailRV).text = "Email: \n${user.email}"
        holder.itemView.findViewById<TextView>(R.id.tv_FirstNameRV).text = "Nombre : \n${user.first_name}"
        holder.itemView.findViewById<TextView>(R.id.tv_LastNameRV).text = "Apellido : \n${user.last_name}"
        holder.itemView.findViewById<TextView>(R.id.tv_UserNameRV).text = "Nombre de usuario : \n${user.user_name}"
        holder.itemView.findViewById<TextView>(R.id.tv_UserTypeRV).text = "Tipo de usuario : \n${user.user_type}"
        holder.itemView.findViewById<TextView>(R.id.tv_IsAdminRv).text = "Eres Administrador? : \n${user.is_admin}"


    }

    override fun getItemCount(): Int = users.size

    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val idTextView: TextView = itemView.findViewById(R.id.tv_UserIDRV)
        private val emailTextView: TextView = itemView.findViewById(R.id.tv_EmailRV)
        private val firstNameTextView: TextView = itemView.findViewById(R.id.tv_FirstNameRV)
        private val isAdminTextView: TextView = itemView.findViewById(R.id.tv_IsAdminRv)
        private val lastNameTextView: TextView = itemView.findViewById(R.id.tv_LastNameRV)
        private val userNameTextView: TextView = itemView.findViewById(R.id.tv_UserNameRV)
        private val userTypeTextView: TextView = itemView.findViewById(R.id.tv_UserTypeRV)

        fun bind(user: User) {
            idTextView.text = user._id
            emailTextView.text = user.email
            firstNameTextView.text = user.first_name
            isAdminTextView.text = user.is_admin.toString()
            lastNameTextView.text = user.last_name
            userNameTextView.text = user.user_name
            userTypeTextView.text = user.user_type
            Log.i("UserListAdapter", "Vinculando usuario: ${user.email}")
        }
    }
}
