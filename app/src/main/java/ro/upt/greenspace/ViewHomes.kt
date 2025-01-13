package ro.upt.greenspace

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ro.upt.greenspace.models.Home
import ro.upt.greenspace.service.ApiClient

class ViewHomes : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
        }
    }
}

@Composable
fun ViewHomesScreen(navController: androidx.navigation.NavHostController) {
    var homes by remember { mutableStateOf<List<Home>>(emptyList()) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        fetchHomes(
            onSuccess = { fetchedHomes ->
                homes = fetchedHomes
                isLoading = false
            },
            onError = { error ->
                errorMessage = error
                isLoading = false
            }
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFd1d5ca)),
        contentAlignment = Alignment.Center
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
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "App Logo",
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .size(150.dp)
                    .shadow(30.dp, shape = RoundedCornerShape(100.dp))
            )

            Text(
                text = "View Homes",
                modifier = Modifier.padding(bottom = 30.dp),
                style = TextStyle(
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    brush = Brush.horizontalGradient(
                        colors = listOf(
                            Color(0xFF1B5E20),
                            Color(0xFF4E342E)
                        )
                    )
                )
            )

            if (isLoading) {
                CircularProgressIndicator(color = Color(0xFF1B5E20))
            } else if (!errorMessage.isNullOrEmpty()) {
                Text(text = errorMessage!!, color = Color.Red)
            } else {
                homes.forEach { home ->
                    Button(
                        onClick = {
                            navController.navigate("viewHome/${home.id}")
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 15.dp)
                            .height(50.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(
                            text = home.name,
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
    }
}

fun fetchHomes(onSuccess: (List<Home>) -> Unit, onError: (String) -> Unit) {
    CoroutineScope(Dispatchers.IO).launch {
        try {
            val homes = ApiClient.homeApiService.getAllHomes()
            onSuccess(homes)
        } catch (e: Exception) {
            onError(e.message ?: "Failed to fetch homes")
        }
    }
}
