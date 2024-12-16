package com.example.coffeapp.repository

import com.example.coffeapp.models.CoffeesDataItem
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

    suspend fun insertCoffeesFromRoom(listCoffeeEntity : List<CoffeeEntity>){
        coffeeDB.coffeeDAO().insertAllCoffee(listCoffeeEntity)
    }

    suspend fun isDatabaseEmpty() : Boolean{
        return coffeeDB.coffeeDAO().getCount() == 0
    }

    fun mapDataItemToCoffeeEntity(dataItems : ArrayList<CoffeesDataItem>) : List<CoffeeEntity> {

        return dataItems.map { dataItem ->
            CoffeeEntity(
                description = dataItem.description,
                image = dataItem.image,
                title = dataItem.title,
                id = dataItem.id,
                ingredients = dataItem.ingredients.joinToString(", ")
            )
        }

    }

    fun getAllCoffees() = coffeeDB.coffeeDAO().getAllCoffees()
}