package com.example.coffeapp.models

data class CoffesDataItem(
    val description: String,
    val id: Int,
    val image: String,
    val ingredients: List<String>,
    val title: String
)