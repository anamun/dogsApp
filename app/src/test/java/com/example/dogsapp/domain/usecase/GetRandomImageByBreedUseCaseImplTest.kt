package com.example.dogsapp.domain.usecase

import com.example.dogsapp.domain.model.SearchResult
import com.example.dogsapp.domain.repository.DogRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class GetRandomImageByBreedUseCaseImplTest {

    private lateinit var useCase: GetRandomImageByBreedUseCase
    private val dogRepository: DogRepository = mockk()

    @Before
    fun setup() {
        useCase = GetRandomImageByBreedUseCaseImpl(dogRepository)
    }

    @Test
    fun `execute with valid breed returns random image`() = runBlocking {
        val breed = "Corgi"
        val imageUrl = "https://images.dog.ceo/breeds/corgi-cardigan/n02113186_13429.jpg"
        val expected = SearchResult.Success(imageUrl)
        coEvery { dogRepository.searchImageByBreed(breed) } returns SearchResult.Success(imageUrl)

        val actual = useCase.execute(breed)

        assertEquals(expected, actual)
    }
    @Test
    fun `execute with invalid breed returns error`() = runBlocking {
        val breed = "InvalidBreed"
        val expected = SearchResult.Error("Error getting random image for breed: $breed")
        coEvery { dogRepository.searchImageByBreed(breed) } returns SearchResult.Error(expected.message)

        val actual = useCase.execute(breed)

        assertEquals(expected, actual)
    }
}
