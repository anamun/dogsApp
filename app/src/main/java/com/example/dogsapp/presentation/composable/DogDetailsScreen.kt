package com.example.dogsapp.presentation.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.example.dogsapp.R
import com.example.dogsapp.domain.model.Dog
import com.example.dogsapp.presentation.DogViewModel

@Composable
fun DogDetailsText(text: String) {
    Text(
        text = text,
        textAlign = TextAlign.Center,
        fontSize = 24.sp,
        fontFamily = FontFamily.SansSerif,
        modifier = Modifier
            .fillMaxWidth()
            .background(
                Color.White.copy(alpha = 0.8f),
                shape = RoundedCornerShape(4.dp)
            )
    )
}

@Composable
fun DogDetailsScreen(viewModel: DogViewModel) {
    val dog: Dog? by viewModel.selectedDog.observeAsState()
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            bitmap = ImageBitmap.imageResource(R.drawable.paws_background),
            contentDescription = null,
            modifier = Modifier
                .scale(3f)
                .fillMaxSize(),
            alpha = 0.7f
        )
        Box(
            Modifier
                .fillMaxSize()
                .background(colorResource(R.color.tint_color).copy(alpha = 0.5f))
        )
        if (dog != null) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.TopCenter),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = rememberImagePainter(data = dog?.imageUrl),
                    contentDescription = null,
                    modifier = Modifier
                        .size(400.dp)
                        .padding(20.dp)
                        .border(
                            width = 2.dp,
                            color = Color.White,
                            shape = RoundedCornerShape(4.dp)
                        ),
                    contentScale = ContentScale.Crop
                )

                DogDetailsText(
                    text = LocalContext.current.getString(
                        R.string.guess_answer,
                        dog?.breed
                    )
                )
                Spacer(modifier = Modifier.height(16.dp))
                if (dog!!.subBreedList.isNotEmpty()) {
                    DogDetailsText(
                        text = LocalContext.current.getString(
                            R.string.about_sub_breeds,
                            dog?.breed
                        )
                    )
                    LazyColumn(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .padding(top = 16.dp)
                            .fillMaxWidth()
                            .background(
                                Color.White.copy(alpha = 0.7f),
                                shape = RoundedCornerShape(4.dp)
                            )
                    ) {
                        items(dog!!.subBreedList.size) { item ->
                            Text(
                                text = LocalContext.current.getString(
                                    R.string.sub_breed,
                                    dog!!.subBreedList[item]
                                ),
                                textAlign = TextAlign.Center,
                                fontSize = 22.sp,
                                fontStyle = FontStyle.Italic,
                                fontFamily = FontFamily.SansSerif,
                                modifier = Modifier
                                    .padding(8.dp)
                            )
                            Divider(
                                color = Color.White,
                                thickness = 1.dp,
                            )
                        }
                    }
                } else {
                    DogDetailsText(
                        text = LocalContext.current.getString(
                            R.string.fun_fact,
                            dog!!.breed
                        )
                    )
                }
            }

        }
    }
}


