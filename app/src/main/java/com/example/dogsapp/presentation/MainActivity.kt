package com.example.dogsapp.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.dogsapp.Constant.Animation.NAVIGATION_TWEEN
import com.example.dogsapp.Constant.DOGS_NUMBER
import com.example.dogsapp.Constant.Navigation.ROUTE_DOG_DETAILS
import com.example.dogsapp.Constant.Navigation.ROUTE_IMAGE_GRID
import com.example.dogsapp.Constant.Navigation.ROUTE_PRESENTATION
import com.example.dogsapp.presentation.composable.DogDetailsScreen
import com.example.dogsapp.presentation.composable.DogImagesScreen
import com.example.dogsapp.presentation.composable.PresentationScreen
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val dogViewModel: DogViewModel by viewModels()
    private val mainViewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dogViewModel.getRandomBreeds(DOGS_NUMBER)

        installSplashScreen().apply {
            setKeepVisibleCondition {
                mainViewModel.isLoading.value
            }
        }
        setContent {
            DogsApp(viewModel = dogViewModel)
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun DogsApp(viewModel: DogViewModel) {
    val navController = rememberAnimatedNavController()
    AnimatedNavHost(
        navController = navController,
        startDestination = ROUTE_PRESENTATION
    ) {
        composable(ROUTE_PRESENTATION) {
            PresentationScreen(navController)
        }
        composable(
            ROUTE_IMAGE_GRID
        )
        {
            DogImagesScreen(viewModel = viewModel, navController)
        }
        composable(
            ROUTE_DOG_DETAILS,
            enterTransition = {
                expandVertically(
                    animationSpec = tween(NAVIGATION_TWEEN),
                    expandFrom = Alignment.Top
                )
            },
            exitTransition = {
                slideOutVertically(
                    targetOffsetY = { -it },
                    animationSpec = tween(durationMillis = NAVIGATION_TWEEN)
                )
            }
        ) {
            DogDetailsScreen(viewModel)
        }
    }
}