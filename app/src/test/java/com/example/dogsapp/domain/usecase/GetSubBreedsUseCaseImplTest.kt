package com.example.dogsapp.domain.usecase

import com.example.dogsapp.domain.model.SearchResult
import com.example.dogsapp.domain.repository.DogRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class GetSubBreedsUseCaseImplTest {

    private val mockDogRepository: DogRepository = mockk()
    private lateinit var getSubBreedsUseCase: GetSubBreedsUseCase

    @Before
    fun setUp() {
        getSubBreedsUseCase = GetSubBreedsUseCaseImpl(mockDogRepository)
    }

    @Test
    fun `get sub-breeds with poodle breed should return success result`() = runBlocking {
        val poodleBreed = "poodle"
        val subBreeds = listOf("medium", "miniature", "standard", "toy")

        val successResult = SearchResult.Success(subBreeds)
        coEvery { mockDogRepository.searchSubBreeds(poodleBreed) } returns successResult

        val result = getSubBreedsUseCase.execute(poodleBreed)

        assertTrue(result is SearchResult.Success)
        assertEquals(subBreeds, (result as SearchResult.Success).data)
        coVerify(exactly = 1) { mockDogRepository.searchSubBreeds(poodleBreed) }
        confirmVerified(mockDogRepository)
    }

    @Test
    fun `get sub-breeds with invalid breed should return error result`() = runBlocking {
        val invalidBreed = "invalid"
        val errorMessage = "Error searching sub-breeds"
        val errorResult = SearchResult.Error(errorMessage)
        coEvery { mockDogRepository.searchSubBreeds(invalidBreed) } returns errorResult

        val result = getSubBreedsUseCase.execute(invalidBreed)

        assertTrue(result is SearchResult.Error)
        assertEquals(errorMessage, (result as SearchResult.Error).message)
        coVerify(exactly = 1) { mockDogRepository.searchSubBreeds(invalidBreed) }
        confirmVerified(mockDogRepository)
    }
}
