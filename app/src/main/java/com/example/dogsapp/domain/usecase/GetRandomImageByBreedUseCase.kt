package com.example.dogsapp.domain.usecase

import com.example.dogsapp.domain.model.SearchResult
import com.example.dogsapp.domain.repository.DogRepository

interface GetRandomImageByBreedUseCase {
    suspend fun execute(breed: String): SearchResult<String>
}

class GetRandomImageByBreedUseCaseImpl(private val dogRepository: DogRepository) :
    GetRandomImageByBreedUseCase {
    override suspend fun execute(breed: String): SearchResult<String> {
        return dogRepository.searchImageByBreed(breed)
    }
}