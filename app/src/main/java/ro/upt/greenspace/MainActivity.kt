package ro.upt.greenspace

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ro.upt.greenspace.ui.theme.GreenSpaceFeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GreenSpaceFeTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    GreenPage()
                }
            }
        }
    }

    private fun openCamera() {
        val intent = Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, REQUEST_IMAGE_CAPTURE)
    }

    private fun openPlantNameScreen(photoUri: Uri) {
        setContent {
            GreenSpaceFeTheme {
                PlantNameScreen(photoUri = photoUri) { plantName ->
                    // Handle saving the plant name
                }
            }
        }
    }

    @Composable
    fun GreenPage() {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF236c1b)),
            contentAlignment = Alignment.Center
        ) {
            Button(
                onClick = { openCamera() },
                modifier = Modifier.padding(16.dp)
            ) {
                Text(text = "Add New Plant")
            }
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun GreenPagePreview() {
        GreenSpaceFeTheme {
            GreenPage()
        }
    }

    companion object {
        const val REQUEST_IMAGE_CAPTURE = 1
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            val imageUri = data?.data
            if (imageUri != null) {
                openPlantNameScreen(imageUri)
            }
        }
    }
}
