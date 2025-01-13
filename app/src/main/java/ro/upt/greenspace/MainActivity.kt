package ro.upt.greenspace

import CameraPage
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ro.upt.greenspace.ui.theme.GreenSpaceFeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GreenSpaceFeTheme {
                val navController = rememberNavController()
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavHost(navController = navController, startDestination = "greenPage") {
                        composable("greenPage") { GreenPage(navController) }
                        composable("addHome") { AddHomeScreen(navController) }
                        composable("viewHomes") { ViewHomesScreen(navController) }
                        composable("viewHome/{id}") { backStackEntry ->
                            val homeId = backStackEntry.arguments?.getString("id")?.toIntOrNull()
                            ViewHomeScreen(navController, homeId)
                        }
                        composable("cameraPage") { CameraPage(navController) }
                    }
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
                }
            }
        }
    }

    @Composable
    fun GreenPage(navController: androidx.navigation.NavHostController) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFd1d5ca)),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "App Logo",
                    modifier = Modifier
                        .padding(bottom = 16.dp)
                        .size(200.dp)
                        .shadow(80.dp, shape = RoundedCornerShape(100.dp))
                )

                Text(
                    text = "GreenSpace",
                    modifier = Modifier.padding(bottom = 50.dp),
                    style = TextStyle(
                        fontSize = 50.sp,
                        fontWeight = FontWeight.Bold,
                        brush = Brush.horizontalGradient(
                            colors = listOf(
                                Color(0xFF1B5E20),
                                Color(0xFF4E342E)
                            )
                        )
                    )
                )

                val buttonWidth = 200.dp

                Box(
                    modifier = Modifier
                        .padding(bottom = 40.dp)
                        .background(
                            brush = Brush.horizontalGradient(
                                colors = listOf(
                                    Color(0xFF77B97F),
                                    Color(0xFF5A8A64)
                                )
                            ),
                            shape = RoundedCornerShape(16.dp)
                        )
                ) {
                    Button(
                        onClick = {
                            navController.navigate("addHome")
                        },
                        modifier = Modifier
                            .padding(2.dp)
                            .width(buttonWidth),
                        contentPadding = PaddingValues(25.dp),
                        colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent
                        )
                    ) {
                        Text(
                            text = "Add Home",
                            color = Color.White,
                            fontSize = 22.sp,
                        )
                    }
                }

                Box(
                    modifier = Modifier
                        .padding(bottom = 120.dp)
                        .background(
                            brush = Brush.horizontalGradient(
                                colors = listOf(
                                    Color(0xFF77B97F),
                                    Color(0xFF5A8A64)
                                )
                            ),
                            shape = RoundedCornerShape(16.dp)
                        )
                ) {
                    Button(
                        onClick = {
                            navController.navigate("viewHomes")
                        },
                        modifier = Modifier
                            .padding(2.dp)
                            .width(buttonWidth),
                        contentPadding = PaddingValues(25.dp),
                        colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent
                        )
                    ) {
                        Text(
                            text = "View Homes",
                            color = Color.White,
                            fontSize = 22.sp,
                        )
                    }
                }
            }
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun GreenPagePreview() {
        val navController = rememberNavController()
        GreenSpaceFeTheme {
            GreenPage(navController)
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
