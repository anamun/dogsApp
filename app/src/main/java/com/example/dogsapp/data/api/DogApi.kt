package com.example.dogsapp.data.api

import com.example.dogsapp.data.model.BreedsResponse
import com.example.dogsapp.data.model.ImageResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface DogApi {
    @GET("breeds/list/random/{count}")
    suspend fun getRandomBreeds(@Path("count") count: Int): BreedsResponse

    @GET("breed/{breed}/list")
    suspend fun getSubBreeds(@Path("breed") breed: String): BreedsResponse

    @GET("breed/{breed}/images/random")
    suspend fun getRandomImageByBreed(@Path("breed") breed: String): ImageResponse

}
