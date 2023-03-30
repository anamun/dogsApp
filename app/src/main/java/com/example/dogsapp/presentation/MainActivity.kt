package com.example.dogsapp.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.*
import dagger.hilt.android.AndroidEntryPoint
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.dogsapp.Constant.ROUTE_DOG_DETAILS
import com.example.dogsapp.Constant.ROUTE_IMAGE_GRID
import com.example.dogsapp.Constant.ROUTE_PRESENTATION
import com.example.dogsapp.presentation.composable.DogDetailsScreen
import com.example.dogsapp.presentation.composable.DogImagesScreen
import com.example.dogsapp.presentation.composable.PresentationScreen

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val dogViewModel: DogViewModel by viewModels()
    private val mainViewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().apply {
            setKeepVisibleCondition {
                mainViewModel.isLoading.value
            }
        }
        setContent {
            DogsApp(viewModel = dogViewModel)
        }
        dogViewModel.getRandomBreeds(51)
    }
}

@Composable
fun DogsApp(viewModel: DogViewModel) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = ROUTE_PRESENTATION
    ) {
        composable(ROUTE_PRESENTATION) {
            PresentationScreen(navController)
        }
        composable(ROUTE_IMAGE_GRID) {
            DogImagesScreen(viewModel = viewModel, navController)
        }
        composable(ROUTE_DOG_DETAILS) {
            DogDetailsScreen(viewModel = viewModel)
        }
    }
}