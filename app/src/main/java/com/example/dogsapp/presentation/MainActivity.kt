package com.example.dogsapp.presentation

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.*
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import dagger.hilt.android.AndroidEntryPoint
import androidx.core.net.toUri
import coil.compose.rememberImagePainter
import com.example.dogsapp.R

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: DogViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ImageGrid(viewModel = viewModel)
        }
        viewModel.getRandomBreeds(101)
    }
}

@Composable
fun GridItem(img: Uri) {
    Box(
        modifier = Modifier
            .size(200.dp)
            .padding(2.dp)
            .clip(MaterialTheme.shapes.medium)
            .background(MaterialTheme.colors.surface)
    ) {
        Image(
            painter = rememberImagePainter(data = img),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
    }
}
@Composable
fun ImageGrid(viewModel: DogViewModel) {
    val images by viewModel.dogImages.observeAsState(emptyList())
    LazyVerticalGrid(GridCells.Fixed(3)) {
        items(images) { image ->
            GridItem(img = image.toUri())
        }
    }
}
