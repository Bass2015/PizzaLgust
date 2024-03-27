package com.joanet.pizzalgustmobile

import com.google.gson.annotations.SerializedName


// Model class for our Jokes
data class LoginDataModel(
    val email: String,
    val password: String,
    val msg: String,
    val user_name: String,
    val first_name: String,
    val last_name: String,
    val is_admin: Boolean,
    val token: String


):java.io.Serializable

data class LogoutDataModel(
    val token: String,
    val msg: String

):java.io.Serializable