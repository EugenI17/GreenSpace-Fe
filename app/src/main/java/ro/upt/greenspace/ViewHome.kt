package ro.upt.greenspace

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.text.font.FontWeight

@Composable
fun ViewHomeScreen(navController: androidx.navigation.NavHostController) {
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
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.weight(1f)) // Push content to the top
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {}
            Spacer(modifier = Modifier.weight(1f))
            // Footer
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
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
}
