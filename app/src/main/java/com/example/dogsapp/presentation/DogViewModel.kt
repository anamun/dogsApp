package com.example.dogsapp.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dogsapp.Constant.DOGS_APP_TAG
import com.example.dogsapp.domain.model.Dog
import com.example.dogsapp.domain.usecase.GetRandomBreedsUseCase
import com.example.dogsapp.domain.usecase.GetRandomImageByBreedUseCase
import com.example.dogsapp.domain.usecase.GetSubBreedsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import com.example.dogsapp.domain.model.SearchResult
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DogViewModel @Inject constructor(
    private val getRandomBreedsUseCase: GetRandomBreedsUseCase,
    private val getSubBreedsUseCase: GetSubBreedsUseCase,
    private val getRandomImageByBreedUseCase: GetRandomImageByBreedUseCase,
) : ViewModel() {

    private val _dogBreeds = MutableLiveData<List<String>>()
    val dogBreeds: LiveData<List<String>> = _dogBreeds

    private val _dogs = MutableLiveData<List<Dog>>()
    val dogs: LiveData<List<Dog>> = _dogs

    private val _selectedDog = MutableLiveData<Dog>()
    val selectedDog: LiveData<Dog> = _selectedDog

    fun updateSelectedDog(newDog: Dog) {
        _selectedDog.value = newDog
    }

    fun getRandomBreeds(count: Int) {
        viewModelScope.launch {
            when (val result = getRandomBreedsUseCase.execute(count)) {
                is SearchResult.Success -> {
                    _dogBreeds.value = result.data
                    addRandomImages()
                }
                is SearchResult.Error -> {
                    Log.e(DOGS_APP_TAG, "Error getting dog breeds: ${result.message}")
                }
            }
        }
    }

    private fun addDog(newDog: Dog) {
        val currentList = _dogs.value.orEmpty().toMutableList()
        currentList.add(newDog)
        _dogs.value = currentList
        Log.d(DOGS_APP_TAG, "added new image and now list size: ${dogs.value?.size}")
    }

    private fun searchImageByBreed(breed: String) {
        var imageUrl: String? = null
        var subBreedList: List<String> = emptyList()
        viewModelScope.launch {
            val imgResult = getRandomImageByBreedUseCase.execute(breed)
            if (imgResult is SearchResult.Success) {
                imageUrl = imgResult.data
            }
            val subBreedsResult = getSubBreedsUseCase.execute(breed)
            if (subBreedsResult is SearchResult.Success) {
                subBreedList = subBreedsResult.data
            }
            addDog(Dog(breed = breed, imageUrl = imageUrl, subBreedList = subBreedList))
        }
    }

    private fun addRandomImages() {
        _dogBreeds.value?.map {
            searchImageByBreed(it)
        }
        Log.d(DOGS_APP_TAG, "Got image list of size: ${dogs.value?.size}")
    }
}
