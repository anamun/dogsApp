package com.example.dogsapp.data

import com.example.dogsapp.data.api.DogService
import com.example.dogsapp.domain.model.SearchResult
import com.example.dogsapp.domain.repository.DogRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DogRepositoryImpl @Inject constructor(private val dogService: DogService) : DogRepository {
    override suspend fun searchBreeds(count: Int): SearchResult<List<String>> {
        return dogService.searchBreeds(count)
    }

    override suspend fun searchSubBreeds(breed: String): SearchResult<List<String>> {
        return dogService.searchSubBreeds(breed)
    }

    override suspend fun searchImageByBreed(breed: String): SearchResult<String> {
        return dogService.searchImageByBreed(breed)
    }
}
