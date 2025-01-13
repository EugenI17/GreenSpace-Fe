package ro.upt.greenspace.data

import android.graphics.Bitmap
import ro.upt.greenspace.models.Home
import ro.upt.greenspace.models.Plant

object HomeRepository {
    private val mockBitmap: Bitmap = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888) // Placeholder bitmap

    private val mockPlants = listOf(
        Plant(
            name = "Cactus",
            type = "Cactus",
            family = "Caryophyllales",
            water = "1/2 cup every 2 weeks",
            light = "No specific need",
            image = null
        ),
        Plant(
            name = "Fiddle Leaf Fig",
            type = "Fig",
            family = "Moraceae",
            water = "1 cup weekly",
            light = "Bright indirect light",
            image = null
        )
    )

    val mockHomes = listOf(
        Home(
            id = 1,
            name = "Green Villa",
            city = "Timisoara",
            plants = mockPlants
        ),
        Home(
            id = 2,
            name = "Eco Residence",
            city = "Cluj-Napoca",
            plants = emptyList()
        )
    )

    fun getAllHomes(): List<Home> = mockHomes
    fun getHomeById(id: Int): Home? = mockHomes.find { it.id == id }
}
