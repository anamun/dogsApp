package com.example.dogsapp.domain.repository

import com.example.dogsapp.domain.model.SearchResult

interface DogRepository {
    suspend fun searchBreeds(count: Int): SearchResult<List<String>>
    suspend fun searchSubBreeds(breed: String): SearchResult<List<String>>
    suspend fun searchImageByBreed(breed: String): SearchResult<String>
}