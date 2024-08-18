package com.example.tryingapi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.tryingapi.ui.theme.TryingApiTheme
import eScreen
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TryingApiTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    eScreen()
                }
            }
        }
    }
}
private val retrofit = Retrofit.Builder()
    .baseUrl("https://www.themealdb.com/api/json/v1/1/") // Replace with your base URL
    .addConverterFactory(GsonConverterFactory.create())
    .build()
interface ApiService {
    @GET("categories.php")
    suspend fun getData(): CategoryResponse
}
data class YourData(
    val idCategory: String?,
    val strCategory: String?,
    val strCategoryThumb:String?,
    val strCategoryDescription:String?
)
data class CategoryResponse(
    val categories: List<YourData>
)
private val apiService = retrofit.create(ApiService::class.java)

suspend fun fetchData(): CategoryResponse {
    return apiService.getData()
}

@Composable
fun DataDisplay(data: CategoryResponse) {
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {
        LazyColumn(content = {

                items(data.categories) { YourData ->
                    Column {
                        AsyncImage(
                            model = YourData.strCategoryThumb,
                            contentDescription = "Category Image",
                            contentScale = ContentScale.FillBounds,
                            modifier = Modifier.size(width = 400.dp, height = 200.dp)
                        )
                        Spacer(modifier = Modifier.padding(16.dp))
                        Text(text = YourData.strCategory ?:"n/a")
                        Text(text = YourData.strCategoryDescription ?:"n/a")
                        Spacer(modifier = Modifier.padding(16.dp))

                    }
                    
                }
        } )
//        AsyncImage(
//            model = data.categories.firstOrNull()?.strCategoryThumb,
//            contentDescription = "Category Image",
//            contentScale = ContentScale.Crop,
//        )
//
//        Text(text = data.categories.get(0).strCategory ?: "notfound yaar")
    }
//    Text(text = data.categories.get(0).strCategory ?: "notfound yaar")
}

@Preview(showBackground = true)
@Composable fun eScreenPreview(){
    eScreen()
}