package com.example.dogsapp.presentation

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dogsapp.data.DogService
import com.example.dogsapp.data.SearchResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


data class ImageItem(val url: String)

@HiltViewModel
class DogViewModel @Inject constructor(
    private val dogService: DogService,
) : ViewModel() {
    private val _dogBreeds = MutableLiveData<List<String>>()
    val dogBreeds: LiveData<List<String>> = _dogBreeds

    private val _dogImages = MutableLiveData<List<String>>()
    val dogImages: LiveData<List<String>> = _dogImages

    fun addDogImage(newValue: String) {
        val currentList = _dogImages.value.orEmpty().toMutableList()
        currentList.add(newValue)
        _dogImages.value = currentList
        Log.d("DogBreedsViewModel", "added new image and now list size: ${dogImages.value?.size}")

    }


    fun getRandomBreeds(count: Int) {
        viewModelScope.launch {
            val result = dogService.search(count)
            when (result) {
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

    fun searchImageByBreed(breed: String) {
        Log.d("DogBreedsViewModel", "searchImageByBreed called ${breed}")
        var imageUrl = ""
        viewModelScope.launch {
            val result = dogService.searchImageByBreed(breed)
            if (result is SearchResult.Success) {
                // Log.d("DogBreedsViewModel", "Got image: ${result.data}")
                imageUrl = result.data
                addDogImage(imageUrl)
            }
        }
    }

    private fun addRandomImages() {
        _dogBreeds.value?.map {
            searchImageByBreed(it)
        }
        Log.d("DogBreedsViewModel", "Got image list of size: ${dogImages.value?.size}")
    }


}
