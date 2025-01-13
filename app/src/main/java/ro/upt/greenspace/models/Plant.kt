package ro.upt.greenspace.models

import android.graphics.Bitmap
import android.net.Uri

data class Plant(
    val name: String,
    val type: String,
    val family: String,
    val water: String,
    val light: String,
    val image: Bitmap?
)

