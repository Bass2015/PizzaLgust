package com.joanet.pizzalgustmobile

import com.google.gson.annotations.SerializedName

/**
 * Clase de modelo para los datos de inicio de sesión.
 *
 * @property email Correo electrónico del usuario.
 * @property password Contraseña del usuario.
 * @property msg Mensaje relacionado con el inicio de sesión.
 * @property user_name Nombre de usuario.
 * @property first_name Primer nombre del usuario.
 * @property last_name Apellido del usuario.
 * @property is_admin Indica si el usuario es un administrador.
 * @property token Token de autenticación del usuario.
 */
data class LoginDataModel(
    val email: String,
    val password: String,
    val msg: String,
    val user_name: String,
    val first_name: String,
    val last_name: String,
    val is_admin: Boolean,

    val token: String
) : java.io.Serializable

/**
 * Clase de modelo para los datos de cierre de sesión.
 *
 * @property token Token de autenticación del usuario.
 * @property msg Mensaje relacionado con el cierre de sesión.
 */
data class LogoutDataModel(
    val token: String,
    val msg: String
) : java.io.Serializable
