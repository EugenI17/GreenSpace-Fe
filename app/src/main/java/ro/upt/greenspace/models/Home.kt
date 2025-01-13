package ro.upt.greenspace.models

data class Home(
    val id: Int = 0,
    val name: String,
    val city: String,
    val plants: List<Plant>
)

data class HomeRequest(
    val name: String,
    val city: String
)

