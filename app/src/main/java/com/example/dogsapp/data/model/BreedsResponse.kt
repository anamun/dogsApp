package com.example.dogsapp.data.model

import com.google.gson.annotations.SerializedName

data class BreedsResponse(
    @SerializedName("status")
    val status: String,
    @SerializedName("message")
    val message: List<String>,
)
