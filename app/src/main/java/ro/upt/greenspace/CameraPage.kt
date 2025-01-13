import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ro.upt.greenspace.R

@Composable
fun CameraPage(navController: androidx.navigation.NavHostController) {
    var photoBitmap by remember { mutableStateOf<Bitmap?>(null) }
    var name by remember { mutableStateOf(TextFieldValue("")) }

    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val photo = result.data?.extras?.get("data") as Bitmap?
            photoBitmap = photo
        }
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
            Spacer(modifier = Modifier.height(16.dp))
            Box(
                modifier = Modifier
                    .size(300.dp)
                    .background(Color(0xFFe0e0e0), RoundedCornerShape(16.dp))
                    .shadow(10.dp, RoundedCornerShape(16.dp)),
                contentAlignment = Alignment.Center
            ) {
                if (photoBitmap != null) {
                    Image(
                        bitmap = photoBitmap!!.asImageBitmap(),
                        contentDescription = "Captured Photo",
                        modifier = Modifier.fillMaxSize()
                    )
                } else {
                    Button(
                        onClick = {
                            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                            launcher.launch(cameraIntent)
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                        modifier = Modifier
                            .height(60.dp)
                            .width(200.dp)
                    ) {
                        Text(
                            text = "Open Camera",
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

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Name", color = Color.White) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    focusedBorderColor = Color.White,
                    unfocusedBorderColor = Color.White,
                    focusedLabelColor = Color.White,
                    unfocusedLabelColor = Color.White
                )
            )

            Button(
                onClick = {
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                modifier = Modifier
                    .height(60.dp)
                    .width(200.dp)
            ) {
                Text(
                    text = "Save",
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

