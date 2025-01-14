package ro.upt.greenspace.service

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    private const val BASE_URL = "http://192.168.60.183:8080/"

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val homeApiService: HomeApiService = retrofit.create(HomeApiService::class.java)
    val plantApiService: PlantApiService = retrofit.create(PlantApiService::class.java)
}
