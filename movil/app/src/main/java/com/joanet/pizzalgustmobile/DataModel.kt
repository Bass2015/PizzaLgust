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
    val password: String

    ) : java.io.Serializable

/**
 * Clase de modelo para la respuesta del servidor después de la creación de un usuario.
 *
 * @property msg Mensaje relacionado con la creación de usuario.
 * @property user_id Identificador único del usuario.
*/
data class UserResponse(
    val msg: String,
    val user_id: String
) : java.io.Serializable

/**
 * Clase de datos que representa la respuesta del endpoint POST /get-all-users.
 *
 * @property users La lista de usuarios obtenidos del servidor.
 */
data class GetAllUsers(
    val users: List<User>
) : java.io.Serializable

/**
 * Clase de datos que representa la solicitud para obtener todos los usuarios.
 *
 * @property token El token de autenticación del usuario que realiza la solicitud.
 */
data class GetAllUsersRequest(
    val token: String
) : java.io.Serializable

/**
 * Clase de datos que representa un usuario.
 *
 * @property _id Identificador único del usuario.
 * @property email Correo electrónico del usuario.
 * @property first_name Primer nombre del usuario.
 * @property is_admin Indica si el usuario es un administrador.
 * @property last_name Apellido del usuario.
 * @property user_name Nombre de usuario.
 * @property user_type Tipo de usuario.
 * */
data class User(
    val _id: String,
    val email: String,
    val first_name: String,
    val is_admin: Boolean,
    val last_name: String,
    val user_name: String,
    val user_type: String
) : java.io.Serializable

/**
 * Clase de datos que representa la solicitud para eliminar un usuario.
 *
 * @property token El token de autenticación del usuario que realiza la solicitud.
 * @property user_id El ID del usuario que se desea eliminar.
 */
data class DeleteUser(
    val token: String,
    val user_id: String,
) : java.io.Serializable

/**
 * Clase de datos que representa la respuesta después de eliminar un usuario.
 *
 * @property msg Mensaje asociado con la solicitud de eliminación.
 */
data class DeleteUserResponse(
val msg: String
) : java.io.Serializable

/**
 * Clase de datos que representa la solicitud para actualizar un usuario.
 *
 * @property token El token de autenticación del usuario que realiza la solicitud.
 * @property user_name El nuevo nombre de usuario.
 * @property email El nuevo correo electrónico.
 * @property first_name El nuevo primer nombre.
 * @property last_name El nuevo apellido.
 */
data class UpdateUser(
    val token: String,
    val user_name: String? = null,
    val email: String? = null,
    val first_name: String? = null,
    val last_name: String? = null,
) : java.io.Serializable

/**
 * Clase de modelo para la respuesta del servidor después de actualizar un usuario.
 *
 * @property msg Mensaje asociado con la actualización del usuario.
 */
data class UpdateResponse(
    val msg: String
) : java.io.Serializable

/**
 * Clase de datos que representa la solicitud para actualizar un usuario administrador.
 *
 * @property token El token de autenticación del usuario que realiza la solicitud.
 * @property user_id El ID del usuario que se desea actualizar.
 * @property user_name El nuevo nombre de usuario.
 * @property email El nuevo correo electrónico.
 * @property first_name El nuevo primer nombre.
 * @property last_name El nuevo apellido.
 */
data class AdminUpdateUser(
    val token: String,
    val user_id: String,
    val user_name: String? = null,
    val email: String? = null,
    val first_name: String? = null,
    val last_name: String? = null
) : java.io.Serializable

/**
 * Clase de datos que representa la respuesta después de actualizar un usuario administrador.
 *
 * @property msg Mensaje asociado con la actualización del usuario.
 */
data class AdminUpdateResponse(
    val msg: String
) : java.io.Serializable


/* =========================================↓ PIZZAS ↓============================ */
/* =========================================↓ PIZZAS ↓============================ */

/**
 * Clase de datos que representa la creación de una pizza.
 *
 * @property token El token de autenticación del usuario que crea la pizza.
 * @property name El nombre de la pizza.
 * @property price El precio de la pizza.
 * @property description La descripción de la pizza.
 */

data class CreatePizza(
    val token: String,
    val name: String,
    val price: Float,
    val description: String,


    ) : java.io.Serializable

/**
 * Clase de datos que representa la respuesta del servidor después de crear una pizza.
 *
 * @property msg Un mensaje relacionado con la creación de la pizza.
 * @property pizza_id El identificador único de la pizza creada.
 */
data class ResponseCreatePizza(
    val msg: String,
    val pizza_id: String
) : java.io.Serializable

/**
 * Clase de datos que representa una pizza.
 *
 * @property _id El identificador único de la pizza.
 * @property name El nombre de la pizza.
 * @property description La descripción de la pizza.
 * @property price El precio de la pizza.
 */
data class Pizza(
    val _id: String,
    val name: String,
    val description: String,
    val price: Float
) : java.io.Serializable

/**
 * Clase de datos que representa la respuesta que contiene una lista de pizzas.
 *
 * @property pizzas La lista de pizzas obtenidas del servidor.
 */
data class GetAllPizzas(
    val pizzas: List<Pizza>
) : java.io.Serializable

/**
 * Clase de datos que representa la solicitud para obtener todas las pizzas.
 *
 * @property token El token de autenticación del usuario que realiza la solicitud.
 */
data class GetAllPizzasRequest(
    val token: String
) : java.io.Serializable

/**
 * Clase de datos que representa la solicitud para eliminar un usuario.
 *
 * @property token El token de autenticación del usuario que realiza la solicitud.
 * @property user_id El ID del usuario que se desea eliminar.
 */
data class DeletePizza(
    val token: String,
    val pizza_id: String,
) : java.io.Serializable

/**
 * Clase de datos que representa la respuesta después de eliminar un usuario.
 *
 * @property msg Mensaje asociado con la solicitud de eliminación.
 */
data class DeletePizzaResponse(
    val msg: String
) : java.io.Serializable

data class UpdatePizza(
    val token: String,
    val pizza_id: String,
    val name: String? = null,
    val price: String? = null,
    val description: String? = null,
) : java.io.Serializable

/**
 * Clase de datos que representa la respuesta después de actualizar un usuario administrador.
 *
 * @property msg Mensaje asociado con la actualización del usuario.
 */
data class UpdatePizzaResponse(
    val msg: String
) : java.io.Serializable

/**
 * Clase de datos que representa la creación de una masa.
 *
 * @property token El token de autenticación del usuario que crea la pizza.
 * @property name El nombre de la masa.
 * @property description La descripción de la masa.
 */

data class CreateMasa(
    val token: String,
    val name: String,
    val description: String,


    ) : java.io.Serializable

/**
 * Clase de datos que representa la respuesta del servidor después de crear una masa.
 *
 * @property msg Un mensaje relacionado con la creación de la masa.
 * @property masa_id El identificador único de la masa creada.
 */
data class ResponseCreateMasa(
    val msg: String,
    val masa_id: String
) : java.io.Serializable

/**
 * Clase de datos que representa la creación de una masa.
 *
 * @property token El token de autenticación del usuario que crea el ingrediente.
 * @property name El nombre del ingrediente.
 * @property description La descripción del ingrediente.
 */

data class CreateIngredient(
    val token: String,
    val name: String,
    val description: String,


    ) : java.io.Serializable

/**
 * Clase de datos que representa la respuesta del servidor después de crear un ingrediente.
 *
 * @property msg Un mensaje relacionado con la creación del ingrediente.
 * @property msg Un mensaje relacionado con la creación del ingrediente.
 * @property masa_id El identificador único del ingrediente.
 */
data class ResponseCreateIngredient(
    val msg: String,
    val ingredient_id: String
) : java.io.Serializable

/**
 * Clase de datos que representa un ingrediente.
 *
 * @property _id El identificador único del ingrediente.
 * @property name El nombre del ingrediente.
 * @property description La descripción del ingrediente.
 */

data class Ingredient(
    val _id: String,
    val name: String,
    val description: String

) : java.io.Serializable


/**
 * Clase de datos que representa la respuesta que contiene una lista de Ingredientes.
 *
 * @property ingredients La lista de ingredientes obtenidas del servidor.
 */
data class GetAllIngredients(
    val ingredients: List<Ingredient>
) : java.io.Serializable


/**
 * Clase de datos que representa la solicitud para obtener todos los ingredientes.
 *
 * @property token El token de autenticación del usuario que realiza la solicitud.
 */
data class GetAllIngredientsRequest(
    val token: String
) : java.io.Serializable

/**
 * Clase de datos que representa una masa.
 *
 * @property _id El identificador único de la masa.
 * @property name El nombre de la masa.
 * @property description La descripción de la masa.
 */

data class Masa(
    val _id: String,
    val name: String,
    val description: String

    ) : java.io.Serializable

/**
 * Clase de datos que representa la respuesta que contiene una lista de masas.
 *
 * @property ingredients La lista de ingredientes obtenidas de masas.
 */
data class GetAllMasses(
    val masas: List<Masa>
) : java.io.Serializable

/**
 * Clase de datos que representa la solicitud para obtener todas las masas.
 *
 * @property token El token de autenticación del usuario que realiza la solicitud.
 */
data class GetAllMassesRequest(
    val token: String
) : java.io.Serializable
