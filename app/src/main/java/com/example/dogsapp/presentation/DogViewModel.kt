package com.example.dogsapp.presentation

import android.net.Uri
import android.util.Log
import androidx.core.net.toUri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dogsapp.data.DogService
import com.example.dogsapp.data.SearchResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


data class Dog(val breed: String, val imageUrl: Uri?, val subBreedList: List<String>?)

@HiltViewModel
class DogViewModel @Inject constructor(
    private val dogService: DogService,
) : ViewModel() {
    private val _dogBreeds = MutableLiveData<List<String>>()
    val dogBreeds: LiveData<List<String>> = _dogBreeds

    private val _dogs = MutableLiveData<List<Dog>>()
    val dogs: LiveData<List<Dog>> = _dogs

    fun getRandomBreeds(count: Int) {
        viewModelScope.launch {
            when (val result = dogService.searchBreeds(count)) {
                is SearchResult.Success -> {
                    _dogBreeds.value = result.data
                    addRandomImages()
                    Log.d("DogBreedsViewModel", "Got dog breeds: ${result.data}")
                }
                is SearchResult.Error -> {
                    Log.e("DogBreedsViewModel", "Error getting dog breeds: ${result.message}")
                }
            }
            Log.d("DogBreedsViewModel", "dog breeds size: ${_dogBreeds.value?.size}")

        }
    }

    private fun addDog(newDog: Dog) {
        val currentList = _dogs.value.orEmpty().toMutableList()
        currentList.add(newDog)
        _dogs.value = currentList
        Log.d("DogBreedsViewModel", "added new image and now list size: ${dogs.value?.size}")

    }


    private fun searchImageByBreed(breed: String) {
        Log.d("DogBreedsViewModel", "searchImageByBreed called ${breed}")
        var imageUrl: Uri? = null
        var subBreedList: List<String>? = null
        viewModelScope.launch {
            val imgResult = dogService.searchImageByBreed(breed)
            if (imgResult is SearchResult.Success) {
                imageUrl = imgResult.data.toUri()
            }
            val subBreedsResult = dogService.searchSubBreeds(breed)
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
        Log.d("DogBreedsViewModel", "Got image list of size: ${dogs.value?.size}")
    }


}
