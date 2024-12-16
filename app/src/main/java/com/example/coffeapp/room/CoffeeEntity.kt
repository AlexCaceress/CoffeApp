package com.example.coffeapp.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CoffeeEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val description: String,
    val image: String,
    val ingredients: String,
    val title: String,
)


