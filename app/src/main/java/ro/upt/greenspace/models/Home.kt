package ro.upt.greenspace.models

data class Home(
    val id: Int,
    val name: String,
    val city: String,
    val plants: List<Plant>
)
