package com.example.dogsapp.presentation

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.*
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dagger.hilt.android.AndroidEntryPoint
import androidx.core.net.toUri
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberImagePainter
import com.example.dogsapp.R

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: DogViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp(viewModel = viewModel)
        }
        viewModel.getRandomBreeds(101)
    }
}

@Composable
fun MyApp(viewModel: DogViewModel) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable("home") {
            HomeScreen(navController)
        }
        composable("imageGrid") {
            ImageGridScreen(viewModel = viewModel, navController)
        }
        composable("dogDetails") {
            DogDetailsScreen(
                Dog(
                    "hound-afghan",
                    "https://images.dog.ceo/breeds/hound-afghan/n02088094_988.jpg".toUri(),
                    listOf("n", "o", "p")
                )
            )
        }
    }
}

@Composable
fun HomeScreen(navController: NavController) {
    BoxWithConstraints(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            bitmap = ImageBitmap.imageResource(R.drawable.paws_background),
            contentDescription = "Background Image",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds,
            alpha = 0.7f
        )

        Button(
            onClick = { navController.navigate("imageGrid") }
        ) {
            Text("View Images")
        }
    }
}

@Composable
fun GridItem(dog: Dog, navController: NavController) {
    val dogImage = dog.imageUrl ?: R.drawable.error_loading
    Box(
        modifier = Modifier
            .clickable { navController.navigate("dogDetails") }
            .size(200.dp)
            .border(1.dp, Color.White)
            .clip(MaterialTheme.shapes.medium)

    ) {
        Image(
            painter = rememberImagePainter(data = dogImage),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
        )
    }
}

@Composable
fun ImageGridScreen(viewModel: DogViewModel, navController: NavController) {
    val images by viewModel.dogs.observeAsState(emptyList())
    LazyVerticalGrid(GridCells.Fixed(3)) {
        items(images) { dog ->
            GridItem(dog = dog, navController = navController)
        }
    }
}

//@Composable
//fun ImageDetailsScreen(imageId: String?) {
//    Box(
//        contentAlignment = Alignment.Center,
//        modifier = Modifier.fillMaxSize()
//    ) {
//        Text("Image Details: $imageId")
//    }
//}

@Composable
fun DogDetailsScreen(dog: Dog) {
    BoxWithConstraints(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            bitmap = ImageBitmap.imageResource(R.drawable.paws_background),
            contentDescription = "Background Image",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds,
            alpha = 0.7f
        )
        Box(
            modifier = Modifier
                .background(Color.White.copy(alpha = 0.5f))
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = "test",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.padding(vertical = 16.dp)
            )

            if (dog.subBreedList?.isNotEmpty() == true) {
                LazyColumn(
                    modifier = Modifier
                        .width(250.dp)
                        .wrapContentHeight(),
                    state = rememberLazyListState()
                ) {
                    items(dog.subBreedList.size) { index ->
                        Text(
                            text = dog.subBreedList[index].toString(),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Normal,
                            color = Color.Black,
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color.White)
                        )
                    }
                }
            } else {
                Box(
                    modifier = Modifier
                        .background(Color.White.copy(alpha = 0.5f))
                        .fillMaxSize()
                        .padding(16.dp)
                ) {

                    Text(
                        text = "empty",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        modifier = Modifier.padding(vertical = 16.dp)
                    )

                }
            }
        }
    }
}


