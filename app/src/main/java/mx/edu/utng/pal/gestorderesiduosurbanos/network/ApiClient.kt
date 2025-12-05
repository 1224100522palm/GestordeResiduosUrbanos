package mx.edu.utng.pal.gestorderesiduosurbanos.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    private const val BASE_URL = "https://tu-api.com/"

    val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val boteApi: BoteApi by lazy {
        retrofit.create(BoteApi::class.java)
    }
}
