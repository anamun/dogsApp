package com.example.dogsapp.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dogsapp.data.DogService
import com.example.dogsapp.data.SearchResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DogViewModel @Inject constructor(
    private val dogService: DogService
) : ViewModel() {
    private val _dogBreeds = MutableLiveData<List<String>>()
    val dogBreeds: LiveData<List<String>> = _dogBreeds

    private val _dogImage = MutableLiveData<String>()
    val dogImage: LiveData<String> = _dogImage

    fun getRandomBreeds(count: Int) {
        viewModelScope.launch {
            val result = dogService.search(count)
            when (result) {
                is SearchResult.Success -> {
                    _dogBreeds.value = result.data
                    Log.d("DogBreedsViewModel", "Got dog breeds: ${result.data}")
                }
                is SearchResult.Error -> {
                    Log.e("DogBreedsViewModel", "Error getting dog breeds: ${result.message}")
                }
            }
        }
    }

    fun searchImageByBreed(breed: String) {
        viewModelScope.launch {
            val result = dogService.searchImageByBreed(breed)
            if (result is SearchResult.Success) {
                _dogImage.value = result.data
                Log.d("DogBreedsViewModel", "Got image: ${result.data}")
            }
        }
    }


}
