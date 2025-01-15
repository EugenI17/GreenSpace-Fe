package ro.upt.greenspace

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ro.upt.greenspace.data.HomeRepository
import ro.upt.greenspace.models.Plant
import androidx.compose.runtime.mutableStateOf
import ro.upt.greenspace.models.Home
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun ViewHomeScreen(navController: androidx.navigation.NavHostController, homeId: Int?) {
    val home = remember { mutableStateOf<Home?>(null) }
    val isLoading = remember { mutableStateOf(true) }
    val errorMessage = remember { mutableStateOf<String?>(null) }

    LaunchedEffect(homeId) {
        try {
            homeId?.let {
                home.value = HomeRepository.getHomeById(it)
            }
            isLoading.value = false
        } catch (e: Exception) {
            errorMessage.value = e.message ?: "Failed to fetch home details"
            isLoading.value = false
        }
    }

    when {
        isLoading.value -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
        errorMessage.value != null -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = errorMessage.value!!, color = Color.Red)
            }
        }
        home.value != null -> {
            HomeContent(navController = navController, home = home.value!!)
        }
    }
}

@Composable
fun HomeContent(navController: androidx.navigation.NavHostController, home: Home) {
    val scope = rememberCoroutineScope()

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
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = home.name,
                style = TextStyle(
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    brush = Brush.horizontalGradient(
                        colors = listOf(
                            Color(0xFF1B5E20),
                            Color(0xFF4E342E)
                        )
                    )
                ),
                modifier = Modifier.padding(bottom = 24.dp)
            )

            if (home.plants.isEmpty()) {
                Text(
                    text = "No plants available in this home.",
                    style = TextStyle(fontSize = 18.sp, color = Color.Gray)
                )
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    contentPadding = PaddingValues(bottom = 80.dp)
                ) {
                    items(home.plants) { plant ->
                        PlantCard(plant = plant, navController = navController)
                    }
                }
            }
        }

        Row(
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
                )
                .padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = {
                    navController.navigate("cameraPage/${home.id}")
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                modifier = Modifier
                    .height(50.dp)
                    .width(140.dp)
            ) {
                Text(
                    text = "Add Plant",
                    style = TextStyle(
                        fontSize = 16.sp,
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

            Button(
                onClick = {
                    scope.launch {
                        try {
                            HomeRepository.deleteHomeById(home.id)
                            navController.navigateUp()
                        } catch (e: Exception) {
                            Log.e("DeleteHome", "Failed to delete home: ${e.message}")
                        }
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                modifier = Modifier
                    .height(50.dp)
                    .width(140.dp)
            ) {
                Text(
                    text = "Delete Home",
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                )
            }
        }
    }
}

@Composable
fun PlantCard(plant: Plant, navController: androidx.navigation.NavHostController) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFF5F5F5))
            .padding(8.dp)
            .background(Color.White, shape = RoundedCornerShape(16.dp))
            .padding(16.dp)
            .clickable {
                navController.navigate("plantDetail/${plant.name}")
            },
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            val imageBitmap = plant.getImageBitmapForCompose()

            if (imageBitmap != null) {
                Image(
                    bitmap = imageBitmap,
                    contentDescription = plant.name,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .background(Color.LightGray, shape = RoundedCornerShape(16.dp))
                )
                Spacer(modifier = Modifier.height(8.dp))
            } else {
                Text(
                    text = "Image not available",
                    style = TextStyle(fontSize = 18.sp, color = Color.Gray),
                    modifier = Modifier.padding(bottom = 16.dp)
                )
            }

            Text(
                text = plant.name,
                style = TextStyle(
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    brush = Brush.horizontalGradient(
                        colors = listOf(
                            Color(0xFF1B5E20),
                            Color(0xFF4E342E)
                        )
                    )
                ),
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}






