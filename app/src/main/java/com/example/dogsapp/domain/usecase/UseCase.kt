package com.example.dogsapp.domain.usecase

import com.example.dogsapp.domain.model.SearchResult

interface GetRandomBreedsUseCase {
    suspend fun execute(count: Int): SearchResult<List<String>>
}

interface GetSubBreedsUseCase {
    suspend fun execute(breed: String): SearchResult<List<String>>
}

interface GetRandomImageByBreedUseCase {
    suspend fun execute(breed: String): SearchResult<String>
}