package com.example.coffeapp.data

import com.example.coffeapp.models.AllCoffesData
import retrofit2.Response
import retrofit2.http.GET

interface ApiCoffeInstance {

    @GET("hot")
    suspend fun getCoffes() : Response<AllCoffesData>

}