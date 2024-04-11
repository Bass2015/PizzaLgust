package com.joanet.pizzalgustmobile

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
data class Login(
    val email: String,
    val password: String,
    val msg: String,
    val user_name: String,
    val first_name: String,
    val last_name: String,
    val is_admin: Boolean,
    val user_type: String,
    val token: String
) : java.io.Serializable

/**
 * Clase de modelo para los datos de cierre de sesión.
 *
 * @property token Token de autenticación del usuario.
 * @property msg Mensaje relacionado con el cierre de sesión.
 */
data class Logout(
    val token: String,
    val msg: String
) : java.io.Serializable

/**
 * Data class representing user creation data.
 *
 * @property user_name The username of the user.
 * @property email The email address of the user.
 * @property first_name The first name of the user.
 * @property last_name The last name of the user.
 * @property password The password of the user.
 * @property msg A message associated with the user creation.
 * @property user_id The unique identifier of the user.
 */
data class CreateUser(
    val user_name: String,
    val email: String,
    val first_name: String,
    val last_name: String,
    val password: String,
    val msg: String,
    val user_id: String
) : java.io.Serializable

/**
 * Clase de datos que representa la respuesta del endpoint POST /get-all-users.
 *
 * @property users La lista de usuarios obtenidos del servidor.
 */
data class GetAllUsers(
    val users: List<CreateUsers>
) : java.io.Serializable

/**
 * Clase de datos que representa la solicitud para eliminar un usuario.
 *
 * @property token El token de autenticación del usuario que realiza la solicitud.
 * @property user_id El ID del usuario que se desea eliminar.
 * @property msg Mensaje asociado con la solicitud de eliminación.
 */
data class DeleteUser(
    val token: String,
    val user_id: String,
    val msg: String
) : java.io.Serializable

data class UpdateUser(
    val token: String,
    val first_name: String,
    val last_name: String,
    val msg: String
)