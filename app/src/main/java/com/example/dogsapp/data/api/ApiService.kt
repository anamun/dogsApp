package com.example.dogsapp.data.api

import com.example.dogsapp.Constant
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
                    .connectTimeout(Constant.Api.TIMEOUT, TimeUnit.SECONDS)
                    .build()
            )
            .baseUrl(Constant.Api.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(DogApi::class.java)
    }
}