package ro.upt.greenspace

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ro.upt.greenspace.models.Home
import ro.upt.greenspace.models.HomeRequest
import ro.upt.greenspace.service.ApiClient

class AddHome : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
//      AddHomeScreen()
    }
  }
}

@Composable
fun AddHomeScreen(navController: androidx.navigation.NavHostController) {
  var name by remember { mutableStateOf(TextFieldValue("")) }
  var city by remember { mutableStateOf(TextFieldValue("")) }
  val focusManager = LocalFocusManager.current

  Box(
    modifier = Modifier
      .fillMaxSize()
      .background(Color(0xFFd1d5ca))
      .clickable {
        focusManager.clearFocus()
      },
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
        text = "Add Home",
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
        ),
        singleLine = true
      )
      OutlinedTextField(
        value = city,
        onValueChange = { city = it },
        label = { Text("City", color = Color.White) },
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
        ),
        singleLine = true
      )

      Button(
        onClick = {
          focusManager.clearFocus()
          if (name.text.isNotEmpty() && city.text.isNotEmpty()) {
            createHomeApiCall(
              name = name.text,
              city = city.text,
              onSuccess = {
                navController.navigateUp()
              },
              onError = { error ->
                Log.e("AddHomeScreen", "Error: $error")
              }
            )
          } else {
            Log.e("AddHomeScreen", "Please fill in all fields.")
          }
        },
        modifier = Modifier
          .padding(top = 16.dp)
          .height(50.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color.White),
      ) {
        Text(
          text = "Save",
          style = TextStyle(
            fontSize = 22.sp,
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

fun createHomeApiCall(
  name: String,
  city: String,
  onSuccess: () -> Unit,
  onError: (String) -> Unit
) {
  val apiService = ApiClient.homeApiService
  val homeRequest = HomeRequest(name = name, city = city)

  CoroutineScope(Dispatchers.IO).launch {
    try {
      apiService.createHome(homeRequest)
      CoroutineScope(Dispatchers.Main).launch {
        onSuccess()
      }
    } catch (e: Exception) {
      CoroutineScope(Dispatchers.Main).launch {
        onError(e.message ?: "An error occurred")
      }
    }
  }
}

//@Preview(showBackground = true)
//@Composable
//fun AddHomeScreenPreview() {
//  AddHomeScreen()
//}
