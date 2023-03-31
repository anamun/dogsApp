package com.example.dogsapp.domain.usecase

import com.example.dogsapp.domain.model.SearchResult
import com.example.dogsapp.domain.repository.DogRepository

interface GetSubBreedsUseCase {
    suspend fun execute(breed: String): SearchResult<List<String>>
}

class GetSubBreedsUseCaseImpl(private val dogRepository: DogRepository) : GetSubBreedsUseCase {
    override suspend fun execute(breed: String): SearchResult<List<String>> {
        return dogRepository.searchSubBreeds(breed)
    }
}