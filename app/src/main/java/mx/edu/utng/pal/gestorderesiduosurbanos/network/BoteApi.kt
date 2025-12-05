package mx.edu.utng.pal.gestorderesiduosurbanos.network

import mx.edu.utng.pal.gestorderesiduosurbanos.data.Bote
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface BoteApi {
    @GET("botes")
    suspend fun obtenerBotes(): List<Bote>

    @POST("botes")
    suspend fun publicarBote(@Body bote: Bote): Response<Void>
}
