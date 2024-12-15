package com.example.coffeapp.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface CoffeeDAO {

    @Insert
    suspend fun addCoffee(coffeeEntity: CoffeeEntity)

    @Query("SELECT * FROM CoffeeEntity")
    fun getAllCoffees() : Flow<List<CoffeeEntity>>

    @Delete
    suspend fun deleteCoffee(coffeeEntity: CoffeeEntity)

    @Update
    suspend fun updateCoffee(coffeeEntity: CoffeeEntity)

    @Insert
    suspend fun insertAllCoffee(listCoffeeEntity: List<CoffeeEntity>)

    @Query("SELECT COUNT(*) FROM CoffeeEntity")
    suspend fun getCount() : Int

}