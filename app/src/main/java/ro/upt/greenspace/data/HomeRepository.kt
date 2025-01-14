package ro.upt.greenspace.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ro.upt.greenspace.models.Home
import ro.upt.greenspace.service.ApiClient

object HomeRepository {
    suspend fun getAllHomes(): List<Home> {
        return withContext(Dispatchers.IO) {
            try {
                ApiClient.homeApiService.getAllHomes()
            } catch (e: Exception) {
                emptyList()
            }
        }
    }

    suspend fun getHomeById(id: Int): Home? {
        return withContext(Dispatchers.IO) {
            try {
                ApiClient.homeApiService.getAllHomes().find { it.id == id }
            } catch (e: Exception) {
                null
            }
        }
    }
}
