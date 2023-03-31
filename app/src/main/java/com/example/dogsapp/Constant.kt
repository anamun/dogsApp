package com.example.dogsapp

object Constant {

    const val DOGS_APP_TAG = "DogsApp"
    const val DOGS_NUMBER = 51

    object Api {
        const val BASE_URL = "https://dog.ceo/api/"
        const val TIMEOUT = 30L
    }

    object Animation {
        const val SPLASH_TIMEOUT = 1000L
        const val MESSAGE_SHOW_DELAY = 1000L
        const val PULSE_BUTTON_DELAY = 1500L
        const val NAVIGATION_TWEEN = 500
    }

    object Navigation {
        const val ROUTE_PRESENTATION = "presentation"
        const val ROUTE_IMAGE_GRID = "imageGrid"
        const val ROUTE_DOG_DETAILS = "dogDetails"
    }
}