package com.example.dogsapp

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.example.dogsapp.presentation.DogViewModel
import com.example.dogsapp.ui.theme.DogsAppTheme
import dagger.hilt.android.AndroidEntryPoint
import androidx.compose.material.Button
import androidx.compose.ui.platform.LocalContext

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: DogViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DogImageScreen(viewModel = viewModel)
        }

        viewModel.searchImageByBreed("poodle")

        viewModel.getRandomBreeds(50)


        viewModel.dogBreeds.observe(this) { dogBreeds ->
            //Toast.makeText(this, "Dog breeds: ${dogBreeds.joinToString()}", Toast.LENGTH_SHORT)
            ///    .show()
        }
    }
}

@Composable
fun CoilImage(url: String, contentDescription: String, modifierParams: Modifier) {
    Image(
        painter = rememberImagePainter(url),
        contentDescription = contentDescription,
        modifier = modifierParams
    )
}
@Composable
fun DogImageScreen(viewModel: DogViewModel) {
    val dogImageUrl by viewModel.dogImage.observeAsState("")
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CoilImage(
            url = dogImageUrl,
            contentDescription = "Dog Image",
            modifierParams = Modifier
                .size(300.dp)
                .padding(bottom = 16.dp)
        )
        Button(onClick = {
            viewModel.searchImageByBreed("husky")
        }) {
            Text(text = "Get New Dog")
        }
    }
}


@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    DogsAppTheme {
        Greeting("Android")
    }
}