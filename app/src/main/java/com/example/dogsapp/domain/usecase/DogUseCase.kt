package com.example.dogsapp.domain.usecase

import com.example.dogsapp.domain.model.SearchResult
import com.example.dogsapp.domain.repository.DogRepository

class GetRandomBreedsUseCaseImpl(private val dogRepository: DogRepository) : GetRandomBreedsUseCase {
    override suspend fun execute(count: Int): SearchResult<List<String>> {
        return dogRepository.searchBreeds(count)
    }
}

class GetSubBreedsUseCaseImpl(private val dogRepository: DogRepository) : GetSubBreedsUseCase {
    override suspend fun execute(breed: String): SearchResult<List<String>> {
        return dogRepository.searchSubBreeds(breed)
    }
}

class GetRandomImageByBreedUseCaseImpl(private val dogRepository: DogRepository) : GetRandomImageByBreedUseCase {
    override suspend fun execute(breed: String): SearchResult<String> {
        return dogRepository.searchImageByBreed(breed)
    }
}