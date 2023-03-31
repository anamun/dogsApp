package com.example.dogsapp.presentation.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.dogsapp.Constant.Navigation.ROUTE_DOG_DETAILS
import com.example.dogsapp.R
import com.example.dogsapp.domain.model.Dog
import com.example.dogsapp.presentation.DogViewModel

@Composable
fun GridItem(dog: Dog, navController: NavController, viewModel: DogViewModel) {
    val dogImage = dog.imageUrl ?: R.drawable.error_loading
    Box(
        modifier = Modifier
            .clickable {
                viewModel.updateSelectedDog(dog)
                navController.navigate(ROUTE_DOG_DETAILS)
            }
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
fun DogImagesScreen(viewModel: DogViewModel, navController: NavController) {
    val images by viewModel.dogs.observeAsState(emptyList())
    LazyVerticalGrid(GridCells.Fixed(3)) {
        items(images) { dog ->
            GridItem(dog = dog, navController = navController, viewModel = viewModel)
        }
    }
}