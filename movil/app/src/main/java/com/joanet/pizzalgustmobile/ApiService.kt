package com.joanet.pizzalgustmobile

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

/**
 * Esta interfaz define una API para el servicio web.
 */
interface ApiService {

    /**
     * Este método realiza una solicitud POST para iniciar sesión en el sistema.
     *
     * @param requestBody Cuerpo de la solicitud que contiene los datos de inicio de sesión.
     * @return Objeto Call que representa la llamada asíncrona y su respuesta.
     */
    @Headers("Content-Type: application/json")
    @POST("pizzalgust/login")
    fun getUserData(@Body requestBody: LoginDataModel): Call<LoginDataModel>

    /**
     * Este método realiza una solicitud GET para obtener datos de usuarios.
     *
     * @return Objeto Call que representa la llamada asíncrona y su respuesta.
     */

    /**
     * Esta interfaz define una API para realizar el cierre de sesión en el sistema.
     */
    interface ApiLogout {

        /**
         * Este método realiza una solicitud POST para cerrar sesión en el sistema.
         *
         * @param requestBody Cuerpo de la solicitud que contiene los datos de cierre de sesión.
         * @return Objeto Call que representa la llamada asíncrona y su respuesta.
         */

        @Headers("Content-type: application/json")
        @POST("pizzalgust/logout")

        fun logout(@Body requestBody: LogoutDataModel): Call<LogoutDataModel>

    }
}
