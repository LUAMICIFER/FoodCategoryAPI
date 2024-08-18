
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.tryingapi.CategoryResponse
import com.example.tryingapi.DataDisplay
import com.example.tryingapi.fetchData
import kotlinx.coroutines.launch


@Composable
fun eScreen() {
    val scope = rememberCoroutineScope()
    var data by remember { mutableStateOf<CategoryResponse?>(null) }
    var isLoading by remember { mutableStateOf(true) }
    var error by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(key1 = Unit) {
        scope.launch {
            try {
                data = fetchData()
            } catch (e: Exception) {
                error = e.message
            } finally {
                isLoading = false
            }
        }
    }
    val currentData = data
    if (isLoading) {
        CircularProgressIndicator(
            modifier = Modifier
                .fillMaxSize() // Make the progress indicator fill the parent container
                .wrapContentSize() // Allow the progress indicator to shrink to its intrinsic size
        )    } else if (error != null) {
        Text(text = "Error: $error")
    } else if (currentData != null) {
//        Log.d("YourTag", "Data fetched successfully")
        DataDisplay(data = currentData)
    }
}