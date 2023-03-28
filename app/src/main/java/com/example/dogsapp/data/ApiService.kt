package com.example.dogsapp.data

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiService {
    val dogApi: DogApi by lazy {
        Retrofit.Builder()
            .client(
                OkHttpClient
                    .Builder()
                    .connectTimeout(30L, TimeUnit.SECONDS)
                    .build()
            )
            .baseUrl("https://dog.ceo/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(DogApi::class.java)
    }
}