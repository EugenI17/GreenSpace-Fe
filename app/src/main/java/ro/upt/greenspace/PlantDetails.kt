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

@Composable
fun PlantDetailsScreen(navController: NavHostController, plant: Plant?) {
    if (plant == null) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFd1d5ca)),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Plant not found",
                style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold)
            )
        }
        return
    }

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
                .padding(50.dp),
            horizontalAlignment = Alignment.Start
        ) {
            if (plant.image != null) {
                Image(
                    bitmap = plant.image.asImageBitmap(),
                    contentDescription = plant.name,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .background(Color.LightGray, shape = RoundedCornerShape(16.dp))
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Name: ${plant.name}",
                style = TextStyle(
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    lineHeight = 34.sp
                ),
                modifier = Modifier.padding(bottom = 12.dp)
            )
            Text(
                text = "Type: ${plant.type}",
                style = TextStyle(
                    fontSize = 22.sp,
                    color = Color.White,
                    lineHeight = 28.sp
                ),
                modifier = Modifier.padding(bottom = 12.dp)
            )
            Text(
                text = "Family: ${plant.family}",
                style = TextStyle(
                    fontSize = 22.sp,
                    color = Color.White,
                    lineHeight = 28.sp
                ),
                modifier = Modifier.padding(bottom = 12.dp)
            )
            Text(
                text = "Watering: ${plant.water}",
                style = TextStyle(
                    fontSize = 22.sp,
                    color = Color.White,
                    lineHeight = 28.sp
                ),
                modifier = Modifier.padding(bottom = 12.dp)
            )
            Text(
                text = "Light: ${plant.light}",
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



