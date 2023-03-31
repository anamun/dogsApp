package com.example.dogsapp.domain.model

sealed class SearchResult<out T> {
    data class Success<T>(val data: T) : SearchResult<T>()
    data class Error(val message: String) : SearchResult<Nothing>()
}