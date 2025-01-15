package ro.upt.greenspace

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import ro.upt.greenspace.models.Plant
import android.graphics.BitmapFactory
import android.util.Base64
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import ro.upt.greenspace.data.PlantRepository
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll

@Composable
fun PlantDetailsScreen(navController: NavHostController, plantName: String?) {
    var plant by remember { mutableStateOf<Plant?>(null) }
    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(plantName) {
        if (plantName != null) {
            try {
                plant = PlantRepository.getPlantByName(plantName)
                errorMessage = null
            } catch (e: Exception) {
                errorMessage = e.message ?: "Error fetching plant details"
                plant = null
            } finally {
                isLoading = false
            }
        } else {
            errorMessage = "Invalid plant name"
            isLoading = false
        }
    }

    if (isLoading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else if (errorMessage != null) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = errorMessage ?: "Unknown Error",
                color = Color.Red,
                style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold)
            )
        }
    } else if (plant != null) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFd1d5ca))
        ) {
            IconButton(
                onClick = { navController.navigateUp() },
                modifier = Modifier
                    .padding(20.dp)
                    .align(Alignment.TopStart)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.arrow),
                    contentDescription = "Back"
                )
            }

            Column(
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(horizontal = 16.dp)
                    .background(
                        brush = Brush.horizontalGradient(
                            colors = listOf(
                                Color(0xFF77B97F),
                                Color(0xFF5A8A64)
                            )
                        ),
                        shape = RoundedCornerShape(16.dp)
                    )
                    .padding(50.dp)
                    .height(500.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.Start
            ) {
                val imageBitmap = plant!!.image?.let { base64Image ->
                    try {
                        val imageBytes = Base64.decode(base64Image, Base64.DEFAULT)
                        BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)?.asImageBitmap()
                    } catch (e: Exception) {
                        null
                    }
                }

                if (imageBitmap != null) {
                    Image(
                        bitmap = imageBitmap,
                        contentDescription = plant!!.name,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .background(Color.LightGray, shape = RoundedCornerShape(16.dp))
                    )
                } else {
                    Text(
                        text = "Image not available",
                        color = Color.Gray,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Name: ${plant!!.name}",
                    style = TextStyle(
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        lineHeight = 34.sp
                    ),
                    modifier = Modifier.padding(bottom = 12.dp)
                )
                Text(
                    text = "Type: ${plant!!.type}",
                    style = TextStyle(
                        fontSize = 22.sp,
                        color = Color.White,
                        lineHeight = 28.sp
                    ),
                    modifier = Modifier.padding(bottom = 12.dp)
                )
                Text(
                    text = "Family: ${plant!!.family}",
                    style = TextStyle(
                        fontSize = 22.sp,
                        color = Color.White,
                        lineHeight = 28.sp
                    ),
                    modifier = Modifier.padding(bottom = 12.dp)
                )
                Text(
                    text = "Watering: ${plant!!.water}",
                    style = TextStyle(
                        fontSize = 22.sp,
                        color = Color.White,
                        lineHeight = 28.sp
                    ),
                    modifier = Modifier.padding(bottom = 12.dp)
                )
                Text(
                    text = "Light: ${plant!!.light}",
                    style = TextStyle(
                        fontSize = 22.sp,
                        color = Color.White,
                        lineHeight = 28.sp
                    ),
                    modifier = Modifier.padding(bottom = 12.dp)
                )
                Text(
                    text = "Suggestion: ${plant!!.suggestion}",
                    style = TextStyle(
                        fontSize = 22.sp,
                        color = Color.White,
                        lineHeight = 28.sp
                    ),
                    modifier = Modifier.padding(bottom = 12.dp)
                )
            }
        }
    }
}



