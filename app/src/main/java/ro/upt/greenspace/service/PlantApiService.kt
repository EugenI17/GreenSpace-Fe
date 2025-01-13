package ro.upt.greenspace.service

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import ro.upt.greenspace.models.Plant

interface PlantApiService {
    @GET("api/v1/plants")
    suspend fun getAllPlants(): List<Plant>

    @GET("api/v1/plants/{plantId}")
    suspend fun getPlant(@Path("plantId") plantId: Long): Plant

    @Multipart
    @POST("api/v1/plants")
    suspend fun createPlant(
        @Part("plant") plant: RequestBody,
        @Part image: MultipartBody.Part
    ): Plant

    @DELETE("api/v1/plants/{plantId}")
    suspend fun deletePlant(@Path("plantId") plantId: Long)
}
