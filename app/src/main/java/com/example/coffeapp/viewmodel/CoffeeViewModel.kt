package com.example.coffeapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coffeapp.repository.Repository
import com.example.coffeapp.room.CoffeeEntity
import kotlinx.coroutines.launch

class CoffeeViewModel(val repository: Repository) : ViewModel() {

    fun addCoffee(coffee : CoffeeEntity){
        viewModelScope.launch {
            repository.addCoffeeToRoom(coffee)
        }
    }

    fun deleteCoffee(coffee: CoffeeEntity){
        viewModelScope.launch {
            repository.deleteCoffeeFromRoom(coffee)
        }
    }

    fun updateCoffee(coffee: CoffeeEntity){
        viewModelScope.launch {
            repository.updateCoffeeFromRoom(coffee)
        }
    }

    fun insertAllCoffees(coffees: List<CoffeeEntity>){
        viewModelScope.launch {
            repository.insertCoffeesFromRoom(coffees)
        }
    }

    val coffees = repository.getAllCoffees()

}