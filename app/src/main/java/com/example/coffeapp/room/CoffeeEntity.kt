package com.example.coffeapp.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CoffeeEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int,

    val description: String,
    val image: String,
    val ingredients: List<String>,
    val title: String,
)
