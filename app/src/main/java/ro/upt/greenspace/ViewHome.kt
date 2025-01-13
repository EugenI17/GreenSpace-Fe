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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.text.font.FontWeight
import ro.upt.greenspace.data.HomeRepository
import ro.upt.greenspace.models.Plant

@Composable
fun ViewHomeScreen(navController: androidx.navigation.NavHostController, homeId: Int?) {
    val home = HomeRepository.getHomeById(homeId ?: 0)

    if (home == null) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFd1d5ca)),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Home not found", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        }
        return
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFd1d5ca))
    ) {
        // Back Button
        IconButton(
            onClick = { navController.navigateUp() },
            modifier = Modifier
                .padding(20.dp)
                .align(Alignment.TopStart) // Position at the top start
        ) {
            Image(
                painter = painterResource(id = R.drawable.arrow),
                contentDescription = "Back"
            )
        }

        // Main Content
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 32.dp), // Add vertical padding
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Header Title with Extra Padding
            Text(
                text = home.name,
                style = TextStyle(fontSize = 30.sp, fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(bottom = 24.dp) // Add more space below title
            )

            // Plant List
            home.plants.forEach { plant ->
                PlantCard(plant = plant)
                Spacer(modifier = Modifier.height(16.dp))
            }
        }

        // Footer
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(
                            Color(0xFF77B97F),
                            Color(0xFF5A8A64)
                        )
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            Button(
                onClick = {
                    navController.navigate("cameraPage")
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                modifier = Modifier
                    .padding(vertical = 16.dp) // Add top and bottom padding
                    .height(60.dp)
                    .width(120.dp)
            ) {
                Text(
                    text = "Add",
                    style = TextStyle(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        brush = Brush.horizontalGradient(
                            colors = listOf(
                                Color(0xFF1B5E20),
                                Color(0xFF4E342E)
                            )
                        )
                    )
                )
            }
        }
    }
}

@Composable
fun PlantCard(plant: Plant) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFF5F5F5))
            .padding(8.dp)
            .background(Color.White, shape = RoundedCornerShape(16.dp))
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            // Display image if it exists
            if (plant.image != null) {
                Image(
                    bitmap = plant.image.asImageBitmap(),
                    contentDescription = plant.name,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .background(Color.LightGray, shape = RoundedCornerShape(16.dp))
                )
                Spacer(modifier = Modifier.height(8.dp))
            }

            // Display plant name
            Text(
                text = plant.name,
                style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}




