package com.example.coffeapp.repository

import com.example.coffeapp.room.CoffeeDB
import com.example.coffeapp.room.CoffeeEntity

class Repository(private val coffeeDB: CoffeeDB) {
    suspend fun addCoffeeToRoom(coffeeEntity: CoffeeEntity) {
        coffeeDB.coffeeDAO().addCoffee(coffeeEntity)
    }

    suspend fun deleteCoffeeFromRoom(coffeeEntity: CoffeeEntity) {
        coffeeDB.coffeeDAO().deleteCoffee(coffeeEntity)
    }

    suspend fun updateCoffeeFromRoom(coffeeEntity: CoffeeEntity) {
        coffeeDB.coffeeDAO().updateCoffee(coffeeEntity)
    }

    fun getAllCoffees() = coffeeDB.coffeeDAO().getAllCoffees()
}