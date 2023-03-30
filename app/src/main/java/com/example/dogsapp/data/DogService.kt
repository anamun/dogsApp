package com.example.dogsapp.data

import android.util.Log
import javax.inject.Inject

sealed class SearchResult<out T> {
    data class Success<T>(val data: T) : SearchResult<T>()
    data class Error(val message: String) : SearchResult<Nothing>()
}

class DogService @Inject constructor(private val dogApi: DogApi) {
    suspend fun searchBreeds(count: Int): SearchResult<List<String>> {
        return try {
            val response = dogApi.getRandomBreeds(count)
            if (response.status == "success") {
                SearchResult.Success(response.message)
            } else {
                SearchResult.Error("Error getting dog breeds: ${response.status}")
            }
        } catch (e: Exception) {
            SearchResult.Error("Error getting dog breeds: ${e.message}")
        }
    }

    suspend fun searchSubBreeds(breed: String): SearchResult<List<String>> {
        return try {
            val response = dogApi.getSubBreeds(breed)
            if (response.status == "success") {
                Log.d("DogBreedsViewModel", "sub-breeds called ${response.message}")
                SearchResult.Success(response.message)
            } else {
                SearchResult.Error("Error getting dog sub-breeds: ${response.status}")
            }
        } catch (e: Exception) {
            SearchResult.Error("Error getting dog sub-reeds: ${e.message}")
        }
    }

    suspend fun searchImageByBreed(breed: String): SearchResult<String> {
        return try {
            val response = dogApi.getRandomImageByBreed(breed)
            if (response.status == "success") {
                SearchResult.Success(response.message)
            } else {
                SearchResult.Error("Error getting dog image: ${response.status}")
            }
        } catch (e: Exception) {
            SearchResult.Error("Error getting dog image: ${e.message}")
        }
    }
}