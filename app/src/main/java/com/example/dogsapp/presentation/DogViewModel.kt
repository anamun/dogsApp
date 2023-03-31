package com.example.dogsapp.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dogsapp.Constant.DOGS_APP_TAG
import com.example.dogsapp.domain.model.Dog
import com.example.dogsapp.domain.model.SearchResult
import com.example.dogsapp.domain.usecase.GetRandomBreedsUseCase
import com.example.dogsapp.domain.usecase.GetRandomImageByBreedUseCase
import com.example.dogsapp.domain.usecase.GetSubBreedsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DogViewModel @Inject constructor(
    private val getRandomBreedsUseCase: GetRandomBreedsUseCase,
    private val getSubBreedsUseCase: GetSubBreedsUseCase,
    private val getRandomImageByBreedUseCase: GetRandomImageByBreedUseCase,
) : ViewModel() {

    private val dogList = mutableListOf<Dog>()
    private val _dogs = MutableLiveData<List<Dog>>()
    val dogs: LiveData<List<Dog>> get() = _dogs

    private val _selectedDog = MutableLiveData<Dog>()
    val selectedDog: LiveData<Dog> get() = _selectedDog
    fun updateSelectedDog(newDog: Dog) {
        _selectedDog.value = newDog
    }

    fun getRandomBreeds(count: Int) {
        viewModelScope.launch {
            when (val result = getRandomBreedsUseCase.execute(count)) {
                is SearchResult.Success -> {
                    result.data.forEach {
                        searchDogData(it)
                    }
                }
                is SearchResult.Error -> {
                    Log.e(DOGS_APP_TAG, "Error getting dog breeds: ${result.message}")
                }
            }
        }
    }

    private fun addDog(newDog: Dog) {
        dogList.add(newDog)
        _dogs.value = dogList
        Log.d(DOGS_APP_TAG, "added new image and now list size: ${dogs.value?.size}")
    }

    private fun searchDogData(breed: String) {
        viewModelScope.launch {
            when (val imgResult = getRandomImageByBreedUseCase.execute(breed)) {
                is SearchResult.Success -> when (
                    val subBreedsResult = getSubBreedsUseCase.execute(breed)
                ) {
                    is SearchResult.Success -> {
                        addDog(
                            Dog(
                                breed = breed,
                                imageUrl = imgResult.data,
                                subBreedList = subBreedsResult.data
                            )
                        )
                    }
                    is SearchResult.Error -> {
                        Log.e(DOGS_APP_TAG, "Error getting dog image")
                    }
                }
                is SearchResult.Error -> {
                    Log.e(DOGS_APP_TAG, "Error getting dog sub-breeds")
                }
            }
        }
    }
}
