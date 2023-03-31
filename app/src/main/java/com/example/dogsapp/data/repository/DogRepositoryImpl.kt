package com.example.dogsapp.data.repository

import android.util.Log
import com.example.dogsapp.data.api.DogApi
import com.example.dogsapp.domain.model.SearchResult
import com.example.dogsapp.domain.repository.DogRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DogRepositoryImpl @Inject constructor(private val dogApi: DogApi) : DogRepository {
    override suspend fun searchBreeds(count: Int): SearchResult<List<String>> {
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

    override suspend fun searchSubBreeds(breed: String): SearchResult<List<String>> {
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

    override suspend fun searchImageByBreed(breed: String): SearchResult<String> {
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