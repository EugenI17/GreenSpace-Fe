package ro.upt.greenspace.service

import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import ro.upt.greenspace.models.HomeRequest
import ro.upt.greenspace.models.Home

interface HomeApiService {
    @GET("api/v1/homes")
    suspend fun getAllHomes(): List<Home>

    @GET("api/v1/homes/{homeId}")
    suspend fun getHome(@Path("homeId") homeId: Long): Home

    @POST("api/v1/homes")
    suspend fun createHome(@Body home: HomeRequest): Home

    @DELETE("api/v1/homes/{homeId}")
    suspend fun deleteHome(@Path("homeId") homeId: Long)
}
