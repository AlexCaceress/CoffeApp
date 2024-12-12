package com.example.coffeapp.room

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

interface CoffeeDAO {

    @Insert
    suspend fun addCoffee(coffeeEntity: CoffeeEntity)

    @Query("SELECT * FROM CoffeeEntity")
    fun getAllCoffees() : Flow<List<CoffeeEntity>>

    @Delete
    suspend fun deleteCoffee(coffeeEntity: CoffeeEntity)

    @Update
    suspend fun updateCoffee(coffeeEntity: CoffeeEntity)

}