package com.example.dogsapp.domain.usecase

import com.example.dogsapp.domain.model.SearchResult
import com.example.dogsapp.domain.repository.DogRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
internal class GetRandomBreedsUseCaseImplTest {

    private lateinit var useCase: GetRandomBreedsUseCaseImpl
    private val repository: DogRepository = mockk()
    @Before
    fun setUp() {
        useCase = GetRandomBreedsUseCaseImpl(repository)
    }
    @Test
    fun `when repository returns success, should return list of breeds`() = runBlocking {
        val count = 3
        val breeds = listOf("Husky", "Labrador", "Corgi")
        coEvery { repository.searchBreeds(count) } returns SearchResult.Success(breeds)

        val result = useCase.execute(count)

        assertTrue(result is SearchResult.Success)
        assertEquals(breeds, (result as SearchResult.Success).data)
    }
    @Test
    fun `when repository returns error, should return error result`() = runBlocking {
        val count = 3
        val errorMessage = "Error getting dog breeds"
        coEvery { repository.searchBreeds(count) } returns SearchResult.Error(errorMessage)

        val result = useCase.execute(count)

        assertTrue(result is SearchResult.Error)
        assertEquals(errorMessage, (result as SearchResult.Error).message)
    }
}
