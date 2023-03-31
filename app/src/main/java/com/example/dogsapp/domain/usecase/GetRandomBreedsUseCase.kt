package com.example.dogsapp.domain.usecase

import com.example.dogsapp.domain.model.SearchResult
import com.example.dogsapp.domain.repository.DogRepository

interface GetRandomBreedsUseCase {
    suspend fun execute(count: Int): SearchResult<List<String>>
}

class GetRandomBreedsUseCaseImpl(private val dogRepository: DogRepository) :
    GetRandomBreedsUseCase {
    override suspend fun execute(count: Int): SearchResult<List<String>> {
        return dogRepository.searchBreeds(count)
    }
}