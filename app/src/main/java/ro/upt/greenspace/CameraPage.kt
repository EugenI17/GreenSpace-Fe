import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.provider.MediaStore
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import ro.upt.greenspace.R
import ro.upt.greenspace.service.ApiClient
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarDuration
import androidx.compose.ui.platform.LocalFocusManager

@Composable
fun CameraPage(
    navController: androidx.navigation.NavHostController,
    homeId: Int
) {
    val apiService = ApiClient.plantApiService
    var photoBitmap by remember { mutableStateOf<Bitmap?>(null) }
    var name by remember { mutableStateOf(TextFieldValue("")) }
    var isLoading by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    val focusManager = LocalFocusManager.current

    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val photo = result.data?.extras?.get("data") as Bitmap?
            photoBitmap = photo
            Log.d("CameraPage", "Photo captured: $photo")
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFd1d5ca))
            .clickable { focusManager.clearFocus() },
        contentAlignment = Alignment.Center
    ) {
        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier.align(Alignment.TopCenter)
        )

        IconButton(
            onClick = {
                focusManager.clearFocus()
                navController.navigateUp()
            },
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
                .align(Alignment.Center)
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
            CaptureImageBox(photoBitmap, launcher)
            Spacer(modifier = Modifier.height(16.dp))
            NameInputField(name) { name = it }
            SaveButton(
                isLoading = isLoading,
                onClick = {
                    focusManager.clearFocus()
                    if (photoBitmap != null && name.text.isNotEmpty()) {
                        isLoading = true
                        scope.launch {
                            Log.d("CameraPage", "Attempting to save plant with name: ${name.text}")
                            handleSavePlant(
                                apiService = apiService,
                                homeId = homeId,
                                name = name.text,
                                photoBitmap = photoBitmap!!,
                                snackbarHostState = snackbarHostState,
                                onSuccess = {
                                    navController.navigateUp()
                                }
                            )
                            isLoading = false
                        }
                    } else {
                        Log.e("CameraPage", "Photo or name is missing")
                    }
                }
            )
        }
    }
}

@Composable
fun NameInputField(name: TextFieldValue, onValueChange: (TextFieldValue) -> Unit) {
    OutlinedTextField(
        value = name,
        onValueChange = onValueChange,
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
}

@Composable
fun SaveButton(isLoading: Boolean, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = Color.White),
        modifier = Modifier
            .height(60.dp)
            .width(200.dp)
    ) {
        Text(
            text = if (isLoading) "Saving..." else "Save",
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

@Composable
fun CaptureImageBox(
    photoBitmap: Bitmap?,
    launcher: androidx.activity.result.ActivityResultLauncher<Intent>
) {
    Box(
        modifier = Modifier
            .size(300.dp)
            .background(Color(0xFFe0e0e0), RoundedCornerShape(16.dp))
            .shadow(10.dp, RoundedCornerShape(16.dp)),
        contentAlignment = Alignment.Center
    ) {
        if (photoBitmap != null) {
            Image(
                bitmap = photoBitmap.asImageBitmap(),
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
}

suspend fun handleSavePlant(
    apiService: ro.upt.greenspace.service.PlantApiService,
    homeId: Int,
    name: String,
    photoBitmap: Bitmap,
    snackbarHostState: SnackbarHostState,
    onSuccess: () -> Unit
) {
    try {
        Log.d("handleSavePlant", "Preparing to send plant data...")

        val jsonPayload = """
            {
                "name": "$name",
                "home": $homeId
            }
        """.trimIndent()
        Log.d("handleSavePlant", "JSON Payload: $jsonPayload")
        val plantRequestBody = jsonPayload.toRequestBody("application/json".toMediaTypeOrNull())

        val byteArrayOutputStream = java.io.ByteArrayOutputStream()
        val isCompressed = photoBitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
        if (!isCompressed) {
            Log.e("handleSavePlant", "Failed to compress bitmap")
            snackbarHostState.showSnackbar(
                message = "Failed to process image",
                actionLabel = "OK",
                duration = SnackbarDuration.Short
            )
            return
        }

        val photoBytes = byteArrayOutputStream.toByteArray()
        Log.d("handleSavePlant", "Image byte size: ${photoBytes.size}")

        if (photoBytes.isEmpty()) {
            Log.e("handleSavePlant", "Image byte array is empty")
            snackbarHostState.showSnackbar(
                message = "Error: Image data is empty",
                actionLabel = "OK",
                duration = SnackbarDuration.Short
            )
            return
        }

        val imagePart = MultipartBody.Part.createFormData(
            name = "image",
            filename = "photo.jpg",
            body = photoBytes.toRequestBody("image/jpeg".toMediaTypeOrNull())
        )

        Log.d("handleSavePlant", "Sending request to API...")

        val response = apiService.createPlant(plant = plantRequestBody, image = imagePart)
        Log.d("handleSavePlant", "API Response: $response")

        snackbarHostState.showSnackbar(
            message = "Plant created successfully!",
            actionLabel = "OK",
            duration = SnackbarDuration.Short
        )
        onSuccess()
    } catch (e: Exception) {
        Log.e("handleSavePlant", "Error creating plant: ${e.message}", e)
        snackbarHostState.showSnackbar(
            message = "Error creating plant: ${e.message}",
            actionLabel = "Retry",
            duration = SnackbarDuration.Short
        )
    }
}
