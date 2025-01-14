package ro.upt.greenspace.models

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap

data class Plant(
    val name: String,
    val type: String,
    val family: String,
    val water: String,
    val light: String,
    val image: String?
) {
    fun getImageBitmap(): Bitmap? {
        return try {
            image?.let {
                val imageBytes = Base64.decode(it, Base64.DEFAULT)
                BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
            }
        } catch (e: Exception) {
            null
        }
    }

    fun getImageBitmapForCompose(): ImageBitmap? {
        return getImageBitmap()?.asImageBitmap()
    }
}
