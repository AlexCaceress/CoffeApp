package com.example.coffeapp.data

import com.example.coffeapp.models.AllCoffeesData
import retrofit2.Response
import retrofit2.http.GET

interface ApiCoffeeInstance {

    @GET("hot")
    suspend fun getCoffes() : Response<AllCoffeesData>

}