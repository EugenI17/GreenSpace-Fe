package ro.upt.greenspace.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ro.upt.greenspace.models.Plant
import ro.upt.greenspace.service.ApiClient

object PlantRepository {

    suspend fun getAllPlants(): List<Plant> {
        return withContext(Dispatchers.IO) {
            try {
                ApiClient.plantApiService.getAllPlants()
            } catch (e: Exception) {
                emptyList()
            }
        }
    }

    suspend fun getPlantByName(name: String): Plant? {
        return withContext(Dispatchers.IO) {
            try {
                getAllPlants().find { it.name == name }
            } catch (e: Exception) {
                null
            }
        }
    }

    suspend fun getPlantById(id: Long): Plant? {
        return withContext(Dispatchers.IO) {
            try {
                ApiClient.plantApiService.getPlant(id)
            } catch (e: Exception) {
                null
            }
        }
    }
}
